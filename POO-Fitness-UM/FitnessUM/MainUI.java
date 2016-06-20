/**
 * Classe de log in e registo de utilizadores, o portal de entrada do FitnessUM.
 * 
 * @author jdc
 * @version 10/05/2014
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.GregorianCalendar;

public class MainUI {

    // Método main, menu principal da API (operacoes de registo e login)
    public static void main(String args[]) throws IOException {

        /*ler ficheiro*/
        Users users=open();
        if (users==null) users=new Users();        
        int user_key1 = -1; // var para ler resposta do utilizador

        String email, password, nome, genero, fav;// vars para registo de um utilizador
        int altura, peso;
        GregorianCalendar datanasc = new GregorianCalendar();

        while (user_key1 != 0) {
            printMenuPrincipal();

            user_key1 = Input.lerInt();

            switch (user_key1) {

            case 1:
                System.out.print('\u000C');
                System.out.println("Email: ");

                email = Input.lerString();

                // Validação do endereço email, antes de se prosseguir com o
                // registo
                if (users.containsUser(email)) {
                    System.out .println("\nEndereço de email já registado, por favor efetue o registo com outra conta mail.\n");
                    break;
                } else{
                    System.out.println("\nEndereço de email válido. Continuar registo ...\n");
                }

                System.out.println("Password: ");
                password = Input.lerString();
                System.out.println("Nome: ");
                nome = Input.lerString();
                System.out.println("Género (M/F): ");
                genero = Input.lerString();
                System.out.println("Altura (em cm): ");
                altura = Input.lerInt();
                System.out.println("Peso (em kg): ");
                peso = Input.lerInt();
                System.out.println("Desporto favorito: ");
                fav = Input.lerString();

                int dia,
                mes,
                ano;
                
                boolean ok = false;
                while (!ok) {
                    System.out.println("Data de nascimento");
                    System.out.println("Dia?");
                    dia = Input.lerInt();
                    System.out.println("Mês?");
                    mes = Input.lerInt();
                    System.out.println("Ano?");
                    ano = Input.lerInt();

                    if (GestorDeAtividadesUI.validarData(dia, mes, ano)) {
                        datanasc = new GregorianCalendar(ano, mes - 1, dia);
                        ok = true;
                    } else {
                        System.out.println("\nData inválida!Tente novamente.");
                    }
                }

                users.registerUser(email, password, nome, genero, altura, peso,datanasc, fav);

                System.out.print('\u000C');
                break;

            case 2:
                System.out.print('\u000C');
                System.out.println("Email:");
                email = Input.lerString();
                System.out.println("Password:");
                password = Input.lerString();

                // Entrada no painel da administração
                if (email.equals("admin") && password.equals("admin")) {
                    users = AdministradorUI.admin(users);
                    System.out.print('\u000C');
                } else {
                    User user = users.login(email, password);
                    if (user == null) {
                        System.out.print('\u000C');
                        System.out.println("\nCampos inválidos! Por favor tente novamente ou crie uma conta.\n");
                    } else
                        login_cmd(user, users);
                }
                break;

            case 0:
                save(users);
                System.exit(0);
                break;

            default:
                System.out.print('\u000C');
                System.out.println("\nOperação inválida! Por favor insira uma operação válida.\n");
                break;
            }
        }
    }

    
    /**
     * Método que permite a um utilizador ou administrador fazer login na sua conta FitnessUM
     */
    public static void login_cmd(User user, Users users) throws IOException {
        int user_key2 = -1;

        System.out.print('\u000C');
        while (user_key2 != 0) {
            // Menu de opções pessoais do utilizador
            System.out.println("\nOlá! " + user.getNome() + ",");

            System.out.println("1 - As minhas atividades");
            System.out.println("2 - Amigos");
            System.out.println("3 - Dados pessoais");
            System.out.println("4 - Os meus recordes pessoais");
            System.out.println("5 - Eventos");
            System.out.println("0 - Log out\n");
            System.out.print("> ");
            user_key2 = Input.lerInt();

            switch (user_key2) {

            //consultar atividades
            case 1:
                System.out.print('\u000C');
                GestorDeAtividadesUI.atividades(users,user.getEmail());
                System.out.print('\u000C');
                break;
            
            //consultar amigos
            case 2:
                System.out.print('\u000C');
                GestorDeAmigosUI.gestAmizades(users,user.getEmail());
                System.out.print('\u000C');
                break;

            //consultar dados pessoais
            case 3:
                System.out.print('\u000C');
                System.out.println(user.meusDados() + "\n");
                break;

            //consultar os scores
            case 4:
                System.out.print('\u000C');
                ConsultarScoresUI.consultarScores(users,user.getEmail()); //FUTURO CLONE É MUITO FÁCIL!!
                System.out.print('\u000C');
                break;

            //consultar os eventos
            case 5:
                System.out.println('\u000C');
                GestorDeEventosUI.OsEventos(users,user.getEmail());
                System.out.println('\u000C');
                break;

            case 0:
                System.out.print('\u000C');
                return;

            default:
                System.out.print('\u000C');
                System.out.println("\nOperação inválida! Por favor insira uma operação válida.\n");
                break;
            }

        }
    }

    
    /**
     * Imprimir o menu principal
     */
    public static void printMenuPrincipal(){
        System.out.println("#################### BEM-VINDO AO FITNESS UM ####################");
        System.out.println("Uma aplicação fitness para gestão pessoal de atividades desportivas e partilha dos resultados numa rede de amigos.");
        System.out.println("@de: António Anjo a67760, J. Daniel Caldas a67691, J. Francisco A. de Sousa a67724     GRUPO 29");
        System.out.println("\n1 - Criar uma conta");
        System.out.println("2 - Log in");
        System.out.println("0 - Sair (salvaguardando estado da aplicação)");
        System.out.print("\n> ");
    }

    
    
    
    /**
     * Abrir ficheiro e carregar estado anteriormente guardado do programa
     */
    public static Users open() throws IOException {
        try {
            String workingDir = System.getProperty("user.dir");
            FileInputStream fin = new FileInputStream(workingDir
                    + "fitness.ser");
            ObjectInputStream oin = new ObjectInputStream(fin);
            System.out.println("LOAD SUCCESSFUL!");
            Users users = (Users) oin.readObject();
            oin.close();
            return users;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Guardar estado da API
     */
    public static void save(Users users) throws IOException {
        String workingDir = System.getProperty("user.dir");
        FileOutputStream fos = new FileOutputStream(workingDir + "fitness.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        try {
            oos.writeObject(users);
            oos.close();
            System.out.println("\n\nSAVE SUCESSFUL!");
        } catch (IOException e) {
            System.out.println("ERROR SAVING FILE!");
        }
    }
}
