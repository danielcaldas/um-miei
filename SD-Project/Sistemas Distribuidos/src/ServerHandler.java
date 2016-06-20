
import java.io.*;
import java.util.*;

/**
 * Classe que faz o tratamento da interface do servidor.
 * @author Jorge Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
 * @version 2014.12.15
 */

public class ServerHandler extends Thread {
    
    private static Manager dM;
    private static Menus menuMain, menuU;
    public static String nick = null; 

    int opt = 1;
    
    public ServerHandler(Manager m){ this.dM = m; }
    
    @Override
    public void run() {
        boolean flag = true;
        while(flag){
            try{
                loadMenus();
                mainMenu();
                flag = false;
            } catch (IOException | InterruptedException e) {
                System.out.println("Ocorreu um erro na execução da Thread");
            }
        }   
    }
    
    public static void mainMenu() throws IOException, InterruptedException {  
        do {
            menuMain.execute("Servidor"); 
            switch (menuMain.getOption()) {
                case 1: 
                    register();
                    break;
                case 2: 
                    autenticate();
                    break;
            }
        } while (menuMain.getOption()!=-1);
    }
    
    public static void optMenu() throws IOException, InterruptedException {  
        do {
            menuU.execute("Servidor"); 
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
                
        boolean existe = dM.registerUser(user, pw);
					
        if(existe) System.out.println("Inserido com Sucesso");
        else System.out.println("Utilizador já existe");
    }

    private static void autenticate() throws IOException, InterruptedException {
        System.out.println("######################## Entrar ##########################");
        System.out.println("#                                                        #");
        System.out.println(" - Introduza um username: ");
        String user = InputReader.readString();
        System.out.println(" - Introduza uma password: ");
        String pw = InputReader.readString();
        System.out.println("#                                                        #");
        System.out.println("##########################################################");
        
        boolean inSession = false;
	if(dM.getUsers().containsKey(user))
            inSession = dM.getUsers().get(user).isAtivo();
						
	boolean existe = dM.validateUser(user, pw);
						
	if(existe && !inSession){ 
            nick = user; 
            optMenu();
        }
	else System.out.println("Login não efectuado!");
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
        
        Packet p = new Packet(Server.OBJ, (HashMap<String, String>) objects);
        
        boolean ok = dM.newTask(type, p);
						
        if(ok) System.out.println("Tarefa definida com sucesso!");
        else System.out.println("Erro ao definir tarefa!");				                  
    }

    private static void beginTask() throws IOException { 
        System.out.println("#################### Começar Tarefa ######################");
        System.out.println("#                                                        #");
        System.out.println(" - Introduza o tipo de tarefa a iniciar: ");
        String type = InputReader.readString();
        System.out.println("#                                                        #");
        System.out.println("##########################################################");
                                                             
        int id = dM.beginTask(type);
        
        if(id != -1){ 
            System.out.println("O ID da sua tarefa é " + String.valueOf(id) + " Pode começar a realização da tarefa.");
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
                  
        boolean r;
        try {
            r = dM.endTask(id);
            
            if(r) System.out.println("Tarefa terminada com sucesso!");
            else System.out.println("Erro ao terminar tarefa!");				
        } catch (InterruptedException ex) {
            System.out.println("Erro ao terminar tarefa!");				
        }
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
                  
        try {    
            boolean r = dM.supply(pr,String.valueOf(qtd));
            
            if(r) System.out.println("Produto abastecido com sucesso!");
            else System.out.println("Não conseguiu abastecer com sucesso!");
        } catch (InterruptedException ex) {
            System.out.println("Não conseguiu abastecer com sucesso!");
        }
    }

    private static void lisTasks() { 
        
        List<String> tasks = dM.listTask();
        
        if(tasks.isEmpty()){ System.out.println("Não existem tarefas a definidas!"); }
        else{
            StringBuilder sb = new StringBuilder();
            sb.append("Lista de tarefas definidas:\n");
            for(String entry : tasks){
                sb.append("Tipo: ").append(entry).append("\n");
            }
            System.out.println(sb.toString());
        }
    }
    
    private static void listStock() {
        HashMap<String,Integer> stock = dM.listStock();
        if(stock.isEmpty()){ System.out.println("Não existem ferramentas em stock!"); }
        else{
            StringBuilder sb = new StringBuilder();
            sb.append("Listagem do stock em armazém:\n");
            
            for(Map.Entry<String,Integer> entry : stock.entrySet()){
                sb.append("Nome: ").append(entry.getKey()).append("  Qtd: ").append(entry.getValue()).append("\n");
            }
            
            System.out.println(sb.toString());
        }
    }
    
    private static void lisTasksR() {
        
        HashMap<Integer,String> tasks = dM.listTaskRunnig();

        if(tasks.isEmpty()){ System.out.println("Não existem tarefas a decorrer!"); }
        else{
            StringBuilder sb = new StringBuilder();
            sb.append("Lista de tarefas a decorrer:\n");
            
            for(Map.Entry<Integer,String> entry : tasks.entrySet()){
                sb.append("Tipo: ").append(entry.getValue()).append(" - ID: ").append(entry.getKey());
            }
            System.out.println(sb.toString());
        }
    }

    private static void waitTasks() throws IOException, InterruptedException {
        System.out.println("############## Esperar termino de tarefas ################");
        System.out.println("#                                                        #");
        System.out.println(" - Introduza a lista de tarefas a esperar: ");
        List<Integer> wL = InputReader.readWList();
        System.out.println("#                                                        #");
        System.out.println("##########################################################");
        
        int id = dM.waitTasks(wL);
        
        if(id == -1) {
            System.out.println("As tarefas que introduziu já terminaram com sucesso!");
        }
        else{ System.out.println("Ocorreu um erro. A tarefa com o ID " + id + " ainda não começou!"); }
        
    }
    
    private static void logout() throws IOException {
        dM.logout(nick);
        // Guardar informação no servidor antes de se efetuar logout
        Server.saveManager();
        
        System.out.println("Saiu com sucesso. Até Breve!");
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
                           "Listar stock",
                          };

        menuMain = new Menus(ops);
        menuU = new Menus(opsad);
    }
}
