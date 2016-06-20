/**
 * Classe que contém métodos para que o utilizador possa consultar os seus records pessoais nas diversas atividades.
 * 
 * @author jdc
 * @version 17/05/2014
 * 
 */

import java.io.Serializable;
import java.text.DecimalFormat;

import atividades.Atividade;
import atividades.AtvBase;
import atividades.Distancia;

public class ConsultarScoresUI implements Serializable {

    public static void consultarScores(Users users, String mail) {
        int user_key = -1;

        while (user_key != 0) {
            
            printMenu();
            
            user_key = Input.lerInt();

            switch (user_key) {

            case 1:
                String nome = "";
                System.out.print('\u000C');
                while (!nome.equals("sair")) {

                    System.out.println(users.listarAtividadesDe(mail));

                    System.out.print("\nNome da atividade (ou (sair) para Sair): ");
                    nome = Input.lerString();

                    if (nome.equals("sair"));
                    else {
                        System.out.print('\u000C');

                        if (!nome.equals("Canoagem") && !nome.equals("Cycling")
                                && !nome.equals("Corrida")
                                && !nome.equals("Pilates")
                                && !nome.equals("Zumba")
                                && !nome.equals("Futsal")
                                && !nome.equals("Basket")
                                && !nome.equals("Futebol")) {
                            System.out.println("Nome incorreto ou atividade inexistente. Por favor introduza corretamente o nome de uma atividade da lista");
                            break;
                        } else {

                            try {

                                String path = ("atividades." + nome);
                                AtvBase a = (AtvBase) Class.forName(path).newInstance();
                                System.out.println(users.imprimirUmScoreDe(mail,a));
                            } catch (Exception e) {
                                System.out.println("\nERRO!");
                                return;
                            }

                        }
                    }
                }
                System.out.print('\u000C');
                break;

            case 2:
                String key = "";
                System.out.print('\u000C');
                System.out.println(users.listarAtividadesDe(mail));
                System.out
                        .print("Nome da atividade (ou (cancelar) para Cancelar operação): ");
                key = Input.lerString();
                System.out.print('\u000C');

                AtvBase a;
                try {
                    String path = ("atividades." + key);
                    a = (AtvBase) Class.forName(path).newInstance();
                } catch (Exception e) {
                    System.out.println("\nERRO!");
                    return;
                }

                DecimalFormat f = new DecimalFormat("##.00");

                if (!key.equals("cancelar")){

                    if (a instanceof Distancia) {
                        System.out.println("\n###### MELHOR & PIOR: " + key
                                + " ######\nMelhor tempo: "
                                + f.format(users.getBestDe(mail,a))
                                + " km/h\nPior tempo: "
                                + f.format(users.getWorstDe(mail,a)) + " km/h\n\n");
                    } else if (a instanceof Atividade) {
                        System.out.println("\n###### MELHOR & PIOR: " + key
                                + " ######\nMaior duração: " + users.getBestDe(mail,a)
                                + " minutos\nMenor duração: "
                                + users.getWorstDe(mail,a) + " minutos\n\n");
                    } else {
                        System.out.println("\n###### MELHOR & PIOR: " + key
                                + " ######\nMelhor Pontuação: "
                                + users.getBestDe(mail,a) + "\nPior pontuação: "
                                + users.getWorstDe(mail,a) + "\n\n");

                    }
                } else {
                    System.out.print('\u000C');
                }
                break;

            case 0:
                break;
            }

        }

    }
    
    
    /**
     * Imprime o menu principal
     */
    public static void printMenu(){
        System.out.println("\n################ SCORES ################");
        System.out.println(" 1 - Consultar o meu score para uma determinada atividade");
        System.out.println(" 2 - Melhor & Pior");
        System.out.println(" 0 - Sair");
        System.out.print("\n> ");
    }

}
