
import java.util.*;

/**
 * Class menus onde são definidos os outputs das consolas.
 * @author Jorge Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
 * @version 2014.12.15
 */

public class Menus {
    private List<String> opcoes;
    private int op;
    
    public Menus(String[] opcoes) {
        this.opcoes = new ArrayList<>();
        for (String opt : opcoes){ 
            this.opcoes.add(opt);
        }
        this.op = 0;
    }

    public void execute(String user) {
        do {
            /*showMenu();*/
            if(user!=null) showMenu(user);
            else showMenu("Cliente");
            this.op = readOption();
        } while (this.op == -1);
    }
    
    private void showMenu(String user) {
        System.out.println("\n");
        
        StringBuilder sb = new StringBuilder();
        sb.append("####################### Menu: ").append(user).append(" ");
        int hashs = 30-user.length();
        for(int i=0; i<hashs; i++) sb.append("#");
        
        System.out.println(sb.toString());
        
        int whitespaces = sb.toString().length()-2;
        sb = new StringBuilder();
        sb.append("#");
        for(int i=0; i<whitespaces; i++) sb.append(" ");
        sb.append("#");
        
        System.out.println(sb.toString());
        
        for (int i=0; i<this.opcoes.size(); i++) {
            System.out.print("\t");
            System.out.print(i+1);
            System.out.print(" - ");
            System.out.println(this.opcoes.get(i));
        }
        if(user.equals("Servidor") && this.opcoes.size() == 2) { System.out.println(sb.toString()); }
        else{
            System.out.println("\t0 - Sair");
            System.out.println(sb.toString());        
        }

        hashs = sb.toString().length();
        sb = new StringBuilder();
        for(int i=0; i<hashs; i++) sb.append("#");
        System.out.println(sb.toString());
    }
    
    
    private int readOption() {
        String line=null; /*Alterei aqui*/
        int opt=-1; 
        Scanner is = new Scanner(System.in);
        
        while(opt==-1){
            System.out.print("Opção: ");
            try{
                line = is.nextLine();               
                opt=Integer.parseInt(line); // Se não for um número a excepção trata do erro
                if (opt < 0 || opt > this.opcoes.size()) {
                    System.out.println("Opção Inválida!!!");
                    opt = -1;
                }
            } catch(NumberFormatException e){
                System.out.println("Opção Inválida!!!");
                opt=-1;
            }
        }
        return opt;
    }
 
    public int getOption() {
        return this.op;
    }
}
