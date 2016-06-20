import java.util.concurrent.locks.*;
import java.io.*;
import java.util.*;

/**
 * Manager é a "capital" das classes é onde se guarda a referência para utilizadores, tarefas e armazém e onde
 * são implementados os métodos que permitem o controlo de concorrência ao acesso desses recursos.
 * 
 * @author Jorge Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
 * @version 2014.12.15
 */

public class Manager implements Serializable {
    /* Contador para dar o ID ás tarefas */
    private int countID;
    /* Map com todos os utilizadores do sistema */
    private HashMap<String, User> users;
    /* Map com todas as tarefas definidas no sistema */
    private HashMap<String, Task> tasks;
    /* Map com todas as tarefas a decorrer no sistema */
    private HashMap<Integer, String> tasksRunning; 
    /* Armazém com os objetos partilhados */
    private Warehouse warehouse;
	
    private Lock lock = new ReentrantLock();

    public Manager(Warehouse wh) {		
        this.users = new HashMap<>();
	this.tasks = new HashMap<>();
        this.tasksRunning = new HashMap<>();
        this.warehouse = wh;
        this.countID = 0;
    }

    /* Retorna um Map com os utilizadores existentes no sistema */
    public HashMap<String, User> getUsers() { 
        HashMap<String,User> r = new HashMap<>();
        for(Map.Entry<String,User> entry : users.entrySet()){
            r.put(entry.getKey(),entry.getValue());
        }  
        return r; 
    }
	
    /* Regista um novo utilizador no sistema */
    public boolean registerUser(String nick, String pass) {
        boolean res = false;
	if(nick.equals("") || (pass.equals(""))) return res;
        
        lock.lock();
	try {
            if (!this.users.containsKey(nick)) {
                User u = new User(nick, pass);
		this.users.put(nick, u);
                res = true;
            }
        } finally { lock.unlock(); }
        return res;
    }

    /* Valida a existencia e os dados de um Utilizador */
    public boolean validateUser(String nick, String pass) {
        boolean res = false;
	
        lock.lock();	
        try {
            if (this.users.containsKey(nick)) {
                if (this.users.get(nick).getPassword().equals(pass)) {
                    this.users.get(nick).setAtivo(true);
                    res = true;
		}
            }
        } finally { lock.unlock(); }
	return res;
    }

    /* Termina a sessão de um utilizador */
    public void logout(String nick) {	
        lock.lock();
	try {
            this.users.get(nick).setAtivo(false);
	} finally { lock.unlock(); }
    }	

    /* 
        * Cria uma nova Tarefa. 
        * Retorna true se foi criada com sucesso false caso contrário
    */
    public boolean newTask(String type, Packet objects) {
        if(type.equals("")) return false;
        lock.lock();
        try{
            if(this.tasks.containsKey(type)){
                return false;
            }
            else{
                HashMap<String,String> aux = objects.getArgs();
                HashMap<String,Integer> objs = new HashMap<>();
                for(Map.Entry<String,String> entry : aux.entrySet()){
                    objs.put(entry.getKey(),Integer.parseInt((entry.getValue())));
                }            
                Task t = new Task(type, objs,lock);
                
                this.tasks.put(type,t);

                return true;
            }
        } finally{ lock.unlock(); }
    }

    /* 
        * Inicia a realização de uma Tarefa. 
        * Retorna o ID se foi iniciada com sucesso -1 caso contrário
    */    
    public int beginTask(String type) {
        Map<String,Integer> objectsToConsume;
        lock.lock();
        try{
            if(!this.tasks.containsKey(type)) return -1;
            else{
                objectsToConsume = this.tasks.get(type).getObjects();
            }
        } finally{ lock.unlock(); }
        
        try{ warehouse.consume(objectsToConsume); }
        catch(Exception e){ return -1; }
        
        lock.lock();
        try{ 
            countID++; 
            this.tasksRunning.put(countID,type);
            return countID; // Retornar o ID da tarefa
        } finally{ lock.unlock(); }
    }

    /* 
        * Termina a realização da Tarefa. 
        * Retorna true se foi terminada com sucesso false caso contrário
    */
    public boolean endTask(String id) throws InterruptedException {
        Map<String,Integer> objectsToSupply;
        String type;
        lock.lock();
        try{
            if(!this.tasksRunning.containsKey(Integer.valueOf(id))){
                return false;
            } else{
                type = this.tasksRunning.get(Integer.valueOf(id));
                objectsToSupply = this.tasks.get(type).getObjects();
            }
        } finally{ lock.unlock(); }
        
        // Supply de todos os objetos
        for(Map.Entry<String,Integer> entry : objectsToSupply.entrySet()){
            warehouse.supply(entry.getKey(),entry.getValue());
        }
        
        lock.lock();
        try{ 
            this.tasksRunning.remove(Integer.valueOf(id)); 
            this.tasks.get(type).signalP();
        } finally{ lock.unlock(); }
        
        return true;
    }
    
    /* Devolve lista com o tipo de tarefas */
    public List<String> listTask() {
        ArrayList<String> tasklist = new ArrayList<>();
        
        lock.lock();
        try{
            for(Task task : this.tasks.values()){
                tasklist.add(task.getType());
            }
            return tasklist;
        } finally{ lock.unlock(); }
    }
    
    /*Devolve uma lista do stock corrente*/
    public HashMap<String,Integer> listStock() {
        HashMap<String,Integer> liststock = new HashMap<>();
        
        lock.lock();
        try{
            for(Map.Entry<String,Product> entry : this.warehouse.getStock().entrySet()){
                liststock.put(entry.getKey(), entry.getValue().getQuantity());
            }
            return liststock;
        } finally{ lock.unlock(); }
    }
    
    /* Devolve lista com o tipo de tarefas em desenvolvimento */
    public HashMap<Integer,String> listTaskRunnig() {
        HashMap<Integer,String> tasklist = new HashMap<>(); 
        lock.lock();
        try{
            for(Map.Entry<Integer,String> entry : this.tasksRunning.entrySet()){
                tasklist.put(entry.getKey(),entry.getValue());
            }
            return tasklist;
        } finally{ lock.unlock(); }
    }

    /* Abastece o armazém com o produto - name, e a quantidade - qtd */
    public boolean supply(String name, String qtd) throws InterruptedException {
        if(name.equals("") || qtd.equals("")) return false;
        try{
            warehouse.supply(name, Integer.parseInt(qtd));
            return true;
        } catch(InterruptedException ie){
            return false;
        }
    }

    int waitTasks(List<Integer> wL) throws InterruptedException {        
        lock.lock();
        try{
            boolean i = true;
            while(i){
                i = false;
                for(Integer id : wL){
                    if(countID < id) return id;
                    String type = this.tasksRunning.get(id);
                    if(type!=null){
                        this.tasks.get(type).awaitP();
                        i = true; break;
                    }
                }
            }
            return -1;
        } finally{ lock.unlock(); }
    }
    
    public Warehouse getWarehouse(){return this.warehouse;}
    
    public void saveManager() throws FileNotFoundException, IOException {
        String workingDir = System.getProperty("user.dir");
        FileOutputStream fos = new FileOutputStream(workingDir+"\\Manager.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        try{
            oos.writeObject(this);
            oos.close();
        }
        catch (IOException e){
            System.out.println("\nerror saving file!\n");
        }        
    }
    
    public Manager loadManager() throws IOException, ClassNotFoundException{
        try{
            String workingDir = System.getProperty("user.dir");
            FileInputStream fin=new FileInputStream(workingDir+"\\Manager.ser");
            ObjectInputStream oin=new ObjectInputStream(fin);
            Manager m = (Manager)oin.readObject();
            if(m!=null) return m;
            else return new Manager(new Warehouse());
        }
        catch (IOException e){
            System.out.println("\nerror loading file!\n");
            return new Manager(new Warehouse());
        }  
    }
}
