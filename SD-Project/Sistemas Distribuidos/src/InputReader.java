
import java.io.*;
import java.util.*;
import static java.lang.System.out;

/**
 * Classe que define métodos de leitura mais sofisticados que os standard, fazendo parse e tratamento de erros.
 * @author Jorge Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
 * @version 2014.12.15
 */

public class InputReader implements Serializable {
 
   public static String readString() throws IOException {
        Scanner input = new Scanner(System.in);
        boolean ok = false; 
        String txt = "";
        while(!ok) {
            try {
                txt = input.nextLine();
                ok = true;
            }
            catch(InputMismatchException e) 
                { out.println("Texto Inválido"); 
                  out.print("Novo valor: ");
                  input.nextLine(); 
                }
        }
        return txt;
    } 

   public static int readInt() throws IOException {
        Scanner input = new Scanner(System.in);
        boolean ok = false; 
        int i = 0; 
        while(!ok) {
            try {
                String line = input.nextLine();
                i = Integer.parseInt(line);
                ok = true;
            }
            catch(NumberFormatException e){
                out.println("Inteiro Inválido"); 
                out.print("Novo valor: ");
            }
            catch(InputMismatchException e){
                out.println("Inteiro Inválido"); 
                out.print("Novo valor: ");
            }
        }
        return i;
     }

   public static Map<String,String> readList() throws IOException {
       BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
       boolean ok=false;

       String line = "*";
       String object = "";
       String qtd = "";

       HashMap<String,String> objects = new HashMap<>();

       for(;!line.equals("");ok=false){

           while(!ok){
               try{
                   System.out.print("Nome do objecto: ");
                   line = stdin.readLine();
                   object=line;
                   ok=true;                
               }
               catch(Exception e){System.out.println("Nome de objecto inválido!");}
           }

           if(line.equals("")) break;
           ok=false;

           while(!ok){
               try {
                   System.out.print("Quantidade desejada: ");
                   String i = stdin.readLine();
                   Integer.parseInt(i);
                   qtd=i;
                   ok=true;
               }
               catch(NumberFormatException e) { 
                   System.out.println("Número inválido!");
                   ok=false; 
               }
            }        
           objects.put(object,qtd);
       }
       return objects;    
      }
 
    public static List<Integer> readWList() throws IOException {
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        boolean ok = false;

        String id = "*";
        int t = 0;

        ArrayList<Integer> tasks = new ArrayList<>();

        for( ; !id.equals(""); ok = false){
            while(!ok){
                try{
                    System.out.print("ID da tarefa a seguir: ");
                    id = stdin.readLine();
                    t = Integer.parseInt(id);
                    ok = true;                
                }
                catch(NumberFormatException e){            
                    if(id.equals("")) break;
                    else{
                        System.out.println("ID da tarefa inválida!");
                        ok = false;
                    }
                }
            }
            if(id.equals("")) break;
            tasks.add(t);
        }
        return tasks;    
    }
}

