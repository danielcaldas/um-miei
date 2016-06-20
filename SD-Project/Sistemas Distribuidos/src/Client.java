
import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Cliente representa um funcionário que interage com o servidor via socket.
 * @author Jorge Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
 * @version 2014.12.20
 */

public class Client {
    
    public static int PORT = 12345;
    private static Socket server = null;
    public static ObjectOutputStream writer = null;    
    public static ObjectInputStream reader = null;
    
    public static HashMap<String, String> hash = new HashMap<>();
    public static Packet p = null;
    public static String nick = null; 
    
    private static Menus menuMain, menuU;

    int opt = 1;
    
    public static void main(String[] args) {
        boolean flag = true;
        while(flag){
            try{
                server = new Socket("localhost", PORT);
                loadMenus();
                mainMenu();
                flag = false;
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Ocorreu um erro na execução do Cliente");
            }
        }   
    }

    private static BufferedReader getSocketReader() throws IOException {
        return new BufferedReader(new InputStreamReader(server.getInputStream()));
    }
    
    private static ObjectInputStream getSocketOReader() throws IOException {
        return new ObjectInputStream(server.getInputStream());
    }
    
    private static ObjectOutputStream getSocketWriter() throws IOException {
        return new ObjectOutputStream(server.getOutputStream());
    }
    
    public static void mainMenu() throws IOException, ClassNotFoundException {  
        do {
            menuMain.execute(nick); /*()*/
            switch (menuMain.getOption()) {
                case 1: 
                    register();
                    break;
                case 2: 
                    autenticate();
                    break;
            }
        } while (menuMain.getOption()!=0);
        if(writer != null) writer.close();
        if(reader != null) reader.close();
        System.out.println("Até breve ...");
    }
    
    public static void optMenu() throws IOException, ClassNotFoundException {  
        do {
            menuU.execute(nick);
            switch (menuU.getOption()) {
                case 1: supply();
                        break;
                case 2: newTask();
                        break;
                case 3: beginTask();
                        break;
                case 4: endTask();
                        break;
                case 5: waitTasks();
                        break;
                case 6: lisTasks();
                        break; 
                case 7: lisTasksR();
                    break;
                case 8: listStock();
                    break;
            }
        } while (menuU.getOption()!=0);
        logout();
    }
                    
    private static void register() throws IOException {
        System.out.println("#################### Novo Utilizador #####################");
        System.out.println("#                                                        #");
        System.out.println(" - Defina um username: ");
        String user = InputReader.readString();
        System.out.println(" - Defina uma password: ");
        String pw = InputReader.readString();
        System.out.println("#                                                        #");
        System.out.println("##########################################################");
                
        hash = new HashMap<>();
        hash.put(Server.NAME, user);
        hash.put(Server.PW, pw);
        p = new Packet(Server.REGISTER,hash);
                
        writer = getSocketWriter();
        writer.writeObject(p);
        writer.flush();
                
        BufferedReader sReader = getSocketReader();
        System.out.println("\n" + sReader.readLine() + "\n");
    }

    private static void autenticate() throws IOException, ClassNotFoundException {
        System.out.println("######################## Entrar ##########################");
        System.out.println("#                                                        #");
        System.out.println(" - Introduza um username: ");
        String user = InputReader.readString();
        System.out.println(" - Introduza uma password: ");
        String pw = InputReader.readString();
        System.out.println("#                                                        #");
        System.out.println("##########################################################");
        
        hash = new HashMap<>();
        hash.put(Server.NAME, user);
        hash.put(Server.PW, pw);
        p = new Packet(Server.ENTER,hash);
                    
        writer = getSocketWriter();
        writer.writeObject(p);
        writer.flush();
                    
        BufferedReader sReader = getSocketReader();
        String result;
        result = sReader.readLine();
                    
        if(result.equals("Entrou")) {
            nick = user;
            optMenu();
        } else System.out.println("Login não efectuado!"); 
    }
    
    private static void newTask() throws IOException {
        System.out.println("##################### Nova  Tarefa #######################");
        System.out.println("#                                                        #");
        System.out.println(" - Introduza o tipo de tarefa: ");
        String type = InputReader.readString();
        System.out.println(" - Introduza os objetos necessários: ");
        Map<String,String> objects = InputReader.readList();
        System.out.println("#                                                        #");
        System.out.println("##########################################################");
                  
        hash = new HashMap<>();
        hash.put(Server.NAME, nick);
        hash.put(Server.TASK, type);
                    
        p = new Packet(Server.NEWTASK,hash);
                    
        sendPacket(p);
        p = new Packet(Server.OBJ, (HashMap<String, String>) objects);
        sendPacket(p);
        
        BufferedReader sReader = getSocketReader();
        System.out.println("\n" + sReader.readLine());
    }

    private static void beginTask() throws IOException, ClassNotFoundException { 
        System.out.println("#################### Começar Tarefa ######################");
        System.out.println("#                                                        #");
        System.out.println(" - Introduza o tipo de tarefa a iniciar: ");
        String type = InputReader.readString();
        System.out.println("#                                                        #");
        System.out.println("##########################################################");
                  
        hash = new HashMap<>();
        hash.put(Server.NAME, nick);
        hash.put(Server.TASK, type);
                    
        p = new Packet(Server.DOTASK,hash);
                    
        sendPacket(p);
        
        reader = getSocketOReader();
        
    	@SuppressWarnings("unchecked")        
	Packet r = (Packet) reader.readObject();
        
        if(r.getAction().equals(Server.OK)){
            System.out.println("O ID da sua tarefa é " + r.getArgs().get(Server.TASKID) + " Pode começar a realização da tarefa.");
        }
        else{ System.out.println("Ocorreu um erro. Não pode realizar a tarefa."); }
    }
    
    private static void endTask() throws IOException {
        System.out.println("################### Terminar  Tarefa #####################");
        System.out.println("#                                                        #");
        System.out.println(" - Introduza o ID da sua tarefa: ");
        String id = InputReader.readString();
        System.out.println("#                                                        #");
        System.out.println("##########################################################");
                  
        hash = new HashMap<>();
        hash.put(Server.NAME, nick);
        hash.put(Server.TASKID, id);
                    
        p = new Packet(Server.TASKDONE,hash);      
        sendPacket(p);
               
        BufferedReader sReader = getSocketReader();
        System.out.println("\n" + sReader.readLine());
    }

    private static void supply() throws IOException {
        System.out.println("################## Abastecer  Armazém ####################");
        System.out.println("#                                                        #");
        System.out.println(" - Introduza o nome do produto a abastecer: ");
        String pr = InputReader.readString();
        System.out.println(" - Introduza a quantidade: ");
        int qtd = InputReader.readInt();
        System.out.println("#                                                        #");
        System.out.println("##########################################################");
                  
        hash = new HashMap<>();
        hash.put(Server.NAME, nick);
        hash.put(Server.NAMEP, pr);
        hash.put(Server.QTD, String.valueOf(qtd));
        
        p = new Packet(Server.SUPPLY,hash);      
        sendPacket(p);
               
        BufferedReader sReader = getSocketReader();
        System.out.println("\n" + sReader.readLine());
    }

    private static void lisTasks() throws IOException, ClassNotFoundException { 
        
        p = new Packet(Server.LISTTASKS);      
        sendPacket(p);
        
        reader = getSocketOReader();
      
        @SuppressWarnings("unchecked")
        List<String> res = (List<String>) reader.readObject();
        
        if(res.isEmpty()){ System.out.println("Não existem tarefas a definidas!"); }
        else{
        
            StringBuilder sb = new StringBuilder();
            sb.append("Lista de tarefas definidas:\n");
            
            for(String entry : res){
                sb.append("Tipo: ").append(entry).append("\n");            
            }
            System.out.println(sb.toString());
        }
    }
    
    private static void listStock() throws IOException, ClassNotFoundException {
        p = new Packet(Server.LISTSTOCK);      
        sendPacket(p);
        
        reader = getSocketOReader();
      
        @SuppressWarnings("unchecked")
        HashMap<String,Integer> res = (HashMap<String,Integer>) reader.readObject();
        
        if(res.isEmpty()){ System.out.println("Não existem tarefas a definidas!"); }
        else{
        
            StringBuilder sb = new StringBuilder();
            sb.append("Listagem do stock em armazém:\n");
            
            for(Map.Entry<String,Integer> entry : res.entrySet()){
                sb.append("Nome: ").append(entry.getKey()).append("  Qtd: ").append(entry.getValue()).append("\n");
            }
            System.out.println(sb.toString());
        }
    }
    
    private static void lisTasksR() throws IOException, ClassNotFoundException {
        
        p = new Packet(Server.LISTTASKSR);      
        sendPacket(p);
               
        reader = getSocketOReader();
        
        @SuppressWarnings("unchecked")
        HashMap<Integer,String> res = (HashMap<Integer,String>) reader.readObject();
        
        if(res.isEmpty()){ System.out.println("Não existem tarefas a decorrer!"); }
        else{
            StringBuilder sb = new StringBuilder();
            sb.append("Lista de tarefas a decorrer:\n");
            
            for(Map.Entry<Integer,String> entry : res.entrySet()){
                sb.append("Tipo: ").append(entry.getValue()).append(" - ID: ").append(entry.getKey());
            }
            System.out.println(sb.toString());
        }
    }

    private static void waitTasks() throws IOException, ClassNotFoundException {
        System.out.println("############## Esperar termino de tarefas ################");
        System.out.println("#                                                        #");
        System.out.println(" - Introduza a lista de tarefas a esperar: ");
        List<Integer> wL = InputReader.readWList();
        System.out.println("#                                                        #");
        System.out.println("##########################################################");
        
        p = new Packet(Server.WAITTASKS);      
        sendPacket(p);
        
        writer = null;
        writer = getSocketWriter();
        writer.writeObject(wL);
        writer.flush();
          
        reader = getSocketOReader();
        
        @SuppressWarnings("unchecked")
        Packet r = (Packet)reader.readObject();
        
        if(r.getAction().equals(Server.OK)){
            System.out.println("As tarefas que introduziu já terminaram com sucesso!");
        }
        else{ System.out.println("Ocorreu um erro. A tarefa com o ID " + r.getArgs().get(Server.TASKID) + " ainda não começou!"); }        
    }
    
    private static void logout() throws IOException {       
        hash = new HashMap<>();
        hash.put(Server.NAME, nick);
        p = new Packet(Server.SAIR,hash);
        nick=null;
        sendPacket(p);
        
        BufferedReader sReader = getSocketReader();
        System.out.println("\n" + sReader.readLine());
    }
    
    private static void sendPacket(Packet p) throws IOException {
        writer = null;
        writer = getSocketWriter();
        writer.writeObject(p);
        writer.flush();
    }
    
    private static void loadMenus() {
        String[] ops = {"Registar",
                        "Entrar",};
        
        String [] opsad = {"Abastecer armazém ",
                           "Definir nova tarefa",
                           "Iniciar tarefa",
                           "Concluir tarefa",
                           "Esperar por conclusão de tarefas",
                           "Listar tarefas", 
                           "Listar tarefas a decorrer",
                           "Listar Stock",
                          };

        menuMain = new Menus(ops);
        menuU = new Menus(opsad);
    }
}