/**
 * Classe que faz interface de utilizador para a gestão e consulta de amigos.
 * 
 * @author José Franciso
 * @versio 20/05/2014
 * 
 */

import atividades.*;
import exceptions.*;
import java.util.HashSet;

public class GestorDeAmigosUI {
    
    public static void gestAmizades(Users users, String mail) {
        int user_key = -1, opt_key, requests;
        String string;
        String friend;
        User f;
        Atividades atvs;
        while (user_key != 0) {
            requests = users.getNrRequestsDe(mail);
            printMenu(requests);

            user_key = Input.lerInt();
            switch (user_key) {
            case 0:
                break;
            case 1:
                if (users.getNrFriendsDe(mail) == 0) {
                    System.out.println("\nAinda não tem amigos!");
                    break;
                }
                System.out.println("\nLista de amigos:");
                System.out.println(users.friendsListDe(mail));
                System.out.println("\nInsira o email de um amigo.");
                string = Input.lerString();
                try {
                    f = users.getFriend(mail,string);
                    friend=string;                    
                } catch (UserNaoExisteException e2) {
                    System.out.println(e2.getMessage()+ " não possui uma conta no FitnessUM!");
                    break;
                } catch (NaoAmigoException e) {
                    System.out.println(e.getMessage() + " não é seu amigo!");
                    break;
                } catch (ProprioUserException e) {
                    System.out.println("Você é " + e.getMessage()+"!");
                    break;
                }
                opt_key = -1;
                while (opt_key != 0) {
                    System.out.println("Consulta a: "+users.getNomeDe(friend)+"\n");
                    System.out.println("\n1-Perfil do utilizador");
                    System.out.println("2-10 atividades mais recentes");
                    System.out.println("0-Sair");
                    opt_key = Input.lerInt();
                    switch (opt_key) {
                    case 1:
                        System.out.print('\u000C');
                        System.out.println(users.getDadosDe(friend));
                        break;
                    case 2:
                         System.out.print('\u000C');
                        atvs = users.getAtividadesDe(friend);
                        menuAtividades(users.getAtividadesDe(friend), users.getNomeDe(friend));
                        break;
                    case 0:
                         System.out.print('\u000C');
                         break;
                    default:
                         System.out.print('\u000C');
                        System.out.println("\nOpção errada!");
                        break;
                    }
                }
                break;
            case 2:
                System.out.print('\u000C');
                System.out.println("\nLista de amigos\n");
                if (users.getNrFriendsDe(mail) == 0){
                    System.out.print('\u000C');
                    System.out.println("\nAinda não tem amigos!");
                }
                else{
                    System.out.println(users.friendsListDe(mail));
                }
                break;
            case 3:
                System.out.println("\nInsira o nome da pessoa que quer procurar.");
                HashSet<String> ams = users.findUser(Input.lerString());
                if (ams == null)
                    System.out.println("\nUtilizador não encontrado");
                else{
                      for(String a : ams){
                          System.out.println(a);
                        }
                }
                break;
            case 4:
                System.out.println("\nInsira o email do utilizador que quer adicionar como amigo.");
                string = Input.lerString();
                System.out.print('\u000C');
                try {
                    users.addFriend(mail,string);
                    System.out.println("Pedido enviado com sucesso!");
                } catch (UserNaoExisteException e1) {
                    System.out.println(e1.getMessage()
                            + " não possui uma conta no FitnessUM!");
                } catch (AmigoExisteException e) {
                    System.out.println("Já adicionou " + e.getMessage()
                            + " como amigo!");
                } catch (ProprioUserException e) {
                    System.out.println("Você é " + e.getMessage()
                            + "!! Não se pode adicionar a si mesmo!");
                } catch (ConviteEnviadoException e) {
                    System.out.println("Já enviou convite para "
                            + e.getMessage() + "!");
                }
                break;
            case 5:
                System.out.print('\u000C');
                if (requests == 0) {
                    System.out.println("\nNão tem pedidos de amizade pendentes!");
                    break;
                }
                System.out.println("\nLista de pedidos de amizade recebidos.");
                System.out.println(users.requestsListDe(mail));
                System.out.println("1-Aceitar pedido");
                System.out.println("2-Rejeitar pedido");
                System.out.println("3-Sair");
                opt_key = Input.lerInt();
                switch (opt_key) {
                case 1:
                    System.out.println("\nEscreva o email do pedido a aceitar.");
                    string = Input.lerString();
                    try {
                        users.acceptFriend(mail,string);
                        System.out.println("\nPedido de amizade aceite com sucesso.");
                    } catch (UserNaoExisteException e) {
                        System.out.println(e.getMessage()+ " não possui uma conta no FitnessUM!");
                    } catch (NaoEnviouPedidoException e) {
                        System.out.println(e.getMessage()+ " não lhe enviou nenhum pedido de amizade!!");
                    } catch (ProprioUserException e) {
                        System.out.println("Você é " + e.getMessage()+"!");
                        break;
                    }
                    break;
                case 2:
                    System.out.println("\nEscreva o email do pedido a rejeitar.");
                    string = Input.lerString();
                    try {
                        users.rejectRequest(mail,string);
                        System.out.println("\nPedido rejeitado com sucesso!");
                    } catch (UserNaoExisteException e) {
                        System.out.println(e.getMessage()
                                + " não possui uma conta no FitnessUM!");
                    } catch (NaoEnviouPedidoException e) {
                        System.out.println(e.getMessage()
                                + " não lhe enviou nenhum pedido de amizade!!");
                    } catch (ProprioUserException e) {
                        System.out.println("Você é " + e.getMessage()+"!");
                        break;
                    }
                    break;
                }
                break;
                
            case 6:
                if (users.getNrFriendsDe(mail) == 0) {
                    System.out.println("\nAinda não tem amigos!");
                    break;
                }
                System.out.println(users.friendsListDe(mail));
                System.out.println("\nEscreva o email do amigo que quer remover.");
                string = Input.lerString();
                try {
                    users.removeFriend(mail,string);
                } catch (UserNaoExisteException e) {
                    System.out.println(e.getMessage()
                            + " não possui uma conta no FitnessUM!");
                } catch (NaoAmigoException e) {
                    System.out.println(e.getMessage() + " não é seu amigo!");
                } catch (ProprioUserException e) {
                    System.out.println("Você é " + e.getMessage()+"!");
                    break;
                }
                System.out.println("Amigo removido com sucesso!");
                break;
            default:
                System.out.print('\u000C');
                System.out.println("\nOpção inváida! Por favor insira uma opção válida.");
                break;
            }

        }
        
    }

    
    
    /**
     * Método que imprime o menu
     */
    public static void printMenu(int requests){
        System.out.println("\n################# OS MEUS AMIGOS #################");
        if (requests > 1) {
                System.out.println("Tem " + requests+ " pedido por aceitar/rejeitar!!!");
        }
        if (requests == 1) {
                System.out.println("Tem " + requests+ " pedidos por aceitar/rejeitar!!!");
        }
        System.out.println("1 - Consultar amigos");
        System.out.println("2 - Lista de amigos");
        System.out.println("3 - Procurar pessoas");
        System.out.println("4 - Enviar pedido de amizade"); 
        System.out.println("5 - Pedidos de amizade");
        System.out.println("6 - Remover amizade");
        System.out.println("0 - Sair");
        System.out.println("\n> ");
    }
    
    
    
    
    /**
     * Imprimir menu das atividades de um amigo
     */
    public static void menuAtividades(Atividades atvs,String nome) {
        int detalhe = -1;
        Atividades toprint = atvs.get10MaisRecentes();
        while (detalhe != 0) {
            System.out.println("\n#####ATIVIDADES DE " + nome + "######");
            System.out.println(toprint.toString());
            System.out
                    .print("\nPara consultar o detalhe de uma atividade insira o seu número. Para sair insira 0: ");
            detalhe = Input.lerInt();

            if (detalhe >= 0 && detalhe <= toprint.getSizeLista()) {
                AtvBase printatv = atvs.atividadeDadoIndice(detalhe);
                if (printatv != null) {
                    System.out.print('\u000C');
                    System.out.println(printatv.toString());
                } else if (detalhe == 0) {
                    System.out.print('\u000C');
                } else {
                    System.out.print('\u000C');
                    System.out.println("\nOpção inválida! Por favor insira uma opção válida.");
                }
            } else {
                System.out.print('\u000C');
                System.out.println("\nERRO! Por favor introduza um índice de 1 a "+ toprint.getSizeLista() + "\n");
            }
        }
    }
}
