
import java.io.*;
import java.net.*;

/**
 * Classe onde é definido o servidor e onde são lançadas threads para os diversos clientes.
 * @author Jorge Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
 * @version 2014.12.14
 */

public class Server {
    
    public static int PORT = 12345;
    private static Manager m = null;
    private static Warehouse wH = null;

    public static final String REGISTER = "RegisterUser";
    public static final String NAME = "NameUser";
    public static final String PW = "PwUser";
    public static final String ENTER = "Enter";
    public static final String SAIR = "Sair";
    
    public static final String SUPPLY = "Supply";
    public static final String NAMEP = "NameProduct";
    public static final String QTD = "ProductQuantity";
    
    public static final String TASK = "TaskType";
    public static final String OBJ = "ObjectsList";
    public static final String TASKID = "TaskId";
    
    public static final String NEWTASK = "NewTask";
    public static final String DOTASK = "DoTask";
    public static final String TASKDONE = "TaskDone";
    
    public static final String WAITTASKS = "WaitTasks";
    public static final String LISTTASKS = "ListTasks";
    public static final String LISTTASKSR = "ListTasksRunning";
    
    public static final String LISTSTOCK = "ListStock";
    
    public static final String OK = "OkMessage";
    public static final String ERR = "ErrorMessage";

    public static void main(String[] args) throws IOException {
        try {
            new Server().start();
        } catch (IOException ex) {
            System.out.println("IOException!\n");
        }
    }
    
    public void start() throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        Socket client;
        
        m = new Manager(new Warehouse());
        try{
            m = m.loadManager();
            wH = m.getWarehouse();
        } catch(ClassNotFoundException | NullPointerException ex){
            wH = new Warehouse();
            m = new Manager(wH);
        } 

        try {
            ServerHandler console = new ServerHandler(m);
            console.start();
            
            while(true){
                client = server.accept();
                ClientHandler thread = new ClientHandler(client,m);
                thread.start();
            }
        } finally { server.close(); }
    }

    public static void saveManager() throws IOException{m.saveManager();}
}