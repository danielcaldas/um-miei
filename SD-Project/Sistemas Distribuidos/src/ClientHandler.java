
import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Classe que faz o tratamento de um cliente ligado ao servidor.
 * @author Daniel Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
 * @version 2014.12.15
 */

class ClientHandler extends Thread {
    private Socket client;
    private Manager dM;
    private ObjectInputStream sIn;
    private PrintWriter sOut;
    private HashMap<String, String> hash;
    
    public ClientHandler(Socket client, Manager m){
        this.client = client;
        this.dM = m;
        this.sIn = null;
        this.sOut = null;
        this.hash = null;
    }
    
    @Override
    public void run() {
        try{
            serve();
        } catch (IOException e) {
            sOut.println("Ocorreu um erro na execução da Thread");
        }
    }
    
    private void serve() throws IOException {
        do{
            try {
                this.sIn = getSocketReader();
                this.sOut = getSocketWriter();
                
                @SuppressWarnings("unchecked")
                        Packet p = (Packet) sIn.readObject();
                
                if(p == null){ break; }
                
                switch (p.getAction()) {
                    case Server.REGISTER:
                        register(p);
                        break;
                    case Server.ENTER:
                        autenticate(p);
                        break;
                    case Server.NEWTASK:
                        newTask(p);
                        break;
                    case Server.DOTASK:
                        beginTask(p);
                        break;
                    case Server.TASKDONE:
                        endTask(p);
                        break;
                    case Server.SUPPLY:
                        supply(p);
                        break;
                    case Server.LISTTASKS:
                        listTasks(p);
                        break;
                    case Server.LISTTASKSR:
                        listTasksR(p);
                        break;
                    case Server.LISTSTOCK:
                        listStock(p);
                        break;
                    case Server.WAITTASKS:
                        waitTask(p);
                        break;
                    case Server.SAIR:
                        logout(p);
                        break;  
                }
            } catch (IOException | ClassNotFoundException | InterruptedException ex) {
                sOut.println("Ocorreu um erro na execução da Thread");
            }
        }while(true);
        if(sIn != null) sIn.close();
        if(sOut != null) sOut.close();
    }
    
    private void register(Packet p) {
	String nick = p.getArgs().get(Server.NAME);
        String pw = p.getArgs().get(Server.PW);

        boolean existe = this.dM.registerUser(nick, pw);
					
        if(existe) this.sOut.println("Inserido com Sucesso");
        else this.sOut.println("Utilizador já existe");
					
        this.sOut.flush();
    }
    
    private void autenticate(Packet p) {
	String nick = p.getArgs().get(Server.NAME);
	String pw = p.getArgs().get(Server.PW);

	boolean inSession = false;
	if(dM.getUsers().containsKey(nick))
            inSession = dM.getUsers().get(nick).isAtivo();
						
	boolean existe = dM.validateUser(nick, pw);
						
	if(existe && !inSession) sOut.println("Entrou");
	else sOut.println("Não Entrou");
        sOut.flush();
    }
    
    private void newTask(Packet p) throws IOException {
        String type = p.getArgs().get(Server.TASK);
        String nick = p.getArgs().get(Server.NAME);
        
        this.sIn = getSocketReader();
        
        Packet objects;
        
        try {
            objects = (Packet) sIn.readObject();
            boolean ok = dM.newTask(type, objects);
						
            if(ok) sOut.println("Tarefa definida com sucesso!");
            else sOut.println("Erro ao definir tarefa!");				
            sOut.flush();
        } catch (ClassNotFoundException ex) {
            sOut.println("Erro ao definir tarefa!");
            sOut.flush();
        }
    }

    private void beginTask(Packet p) throws IOException {
        String type = p.getArgs().get(Server.TASK);
                                           
        int id = dM.beginTask(type);
        
        hash = new HashMap<>();
        hash.put(Server.TASK, type);
	hash.put(Server.TASKID, String.valueOf(id));
	Packet e;
        
        if(id != -1){ e = new Packet(Server.OK,hash); }
        else{ e = new Packet(Server.ERR,hash); }	
        sendPacket(e);
    }

    private void endTask(Packet p) throws IOException {
        String id = p.getArgs().get(Server.TASKID);
                                            
        boolean r;
        try {
            r = dM.endTask(id);
            
            if(r) sOut.println("Tarefa terminada com sucesso!");
            else sOut.println("Erro ao terminar tarefa!");				
            sOut.flush();
        } catch (InterruptedException ex) {
            sOut.println("Erro ao terminar tarefa!");				
            sOut.flush();   
        }
    }
    
    private void supply(Packet p) {
        String name = p.getArgs().get(Server.NAMEP);
        String qtd = p.getArgs().get(Server.QTD);
        
        try {    
            boolean r = dM.supply(name,qtd);
            
            if(r) sOut.println("Produto abastecido com sucesso!");
            else sOut.println("Não conseguiu abastecer com sucesso!");
            sOut.flush();
        } catch (InterruptedException ex) {
            sOut.println("Não conseguiu abastecer com sucesso!");
            sOut.flush();
        }
    }
    
    private void listTasksR(Packet p) throws IOException {
        HashMap<Integer,String> tasks = dM.listTaskRunnig();
        
        ObjectOutputStream writer = getSocketOWriter();
        writer.writeObject(tasks);
        writer.flush(); 
    }

    private void listTasks(Packet p) throws IOException {
        List<String> tasks = dM.listTask();
        
        ObjectOutputStream writer = getSocketOWriter();
        writer.writeObject(tasks);
        writer.flush();     
    }
    
    private void listStock(Packet p) throws IOException {
        HashMap<String,Integer> stock = dM.listStock();
        
        ObjectOutputStream writer = getSocketOWriter();
        writer.writeObject(stock);
        writer.flush();
    }
    
    private void waitTask(Packet p) throws IOException, InterruptedException, ClassNotFoundException {
        this.sIn = getSocketReader();
        @SuppressWarnings("unchecked")
        List<Integer> wL = (List<Integer>) sIn.readObject();
        
        int id = dM.waitTasks(wL);
	Packet e;
        if(id != -1) {
            hash = new HashMap<>();
            hash.put(Server.TASKID, String.valueOf(id)); 
            e = new Packet(Server.ERR,hash);
        } else { e = new Packet(Server.OK); }	
        sendPacket(e);
    }
    
    private void logout(Packet p) throws IOException {        
        String user = p.getArgs().get(Server.NAME);
        dM.logout(user);
        
        // Guardar informação no servidor antes de se efetuar logout
        Server.saveManager();
        
        sOut.println("Saiu com sucesso. Até Breve!!!!!");
        sOut.flush();      
    }

    private void sendPacket(Packet p) throws IOException {
        ObjectOutputStream writer = getSocketOWriter();
        writer.writeObject(p);
        writer.flush();
    }
    
    private ObjectInputStream getSocketReader() throws IOException {
        return new ObjectInputStream(this.client.getInputStream());
    }
    
    private PrintWriter getSocketWriter() throws IOException {
        return new PrintWriter(new OutputStreamWriter(this.client.getOutputStream()), true);
    }
    
    private ObjectOutputStream getSocketOWriter() throws IOException {
        return new ObjectOutputStream(this.client.getOutputStream());
    }
}