/**
 * Classe a que apenas o administrador da API tem acesso, onde podemos remover utilizadores, e criar eventos.
 * 
 * @author jdc 
 * @version 18/05/2014
 */

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.Iterator;

public class AdministradorUI implements Serializable {

    public static Users admin(Users users) {

        int user_key = -1;

        String pass;
        String email;

        while (user_key != 0) {

            System.out
                    .println("\n########### PAINEL DA ADMINISTRAÇÃO ###########");
            System.out.println("1 - Criar evento");
            System.out.println("2 - Detalhes de um evento");
            System.out.println("3 - Simular evento");
            System.out.println("4 - Apagar um utilizador");
            System.out.println("5 - Apagar todos os utilizadores");
            System.out.println("0 - Sair");
            System.out.print("> ");

            user_key = Input.lerInt();

            switch (user_key) {

            case 1:
                System.out.print('\u000C');
                users = CriarUmEvento(users);
                System.out.print('\u000C');

                break;

            case 2:
                System.out.print('\u000C');
                DetalhesEventos(users);
                System.out.print('\u000C');
                break;

            case 3:
                SimulaEventos(users);
                break;

            case 4:
                System.out.print('\u000C');
                System.out.print("password: ");
                pass = Input.lerString();

                if (pass.equals("admin")) {

                    System.out.print('\u000C');
                    System.out.print("Email do utilizador: ");
                    email = Input.lerString();

                    System.out.print('\u000C');
                    if (users.removeUser(email) == true) {
                        System.out.println("\nUser: " + email + " eliminado\n");
                    } else {
                        System.out.println("\nERRO! Utilizador não existe\n");
                    }

                    break;
                } else {
                    System.out.println("\nAcess Denied!\n");
                }

                break;

            case 5:
                System.out.print('\u000C');
                System.out.print("password: ");
                pass = Input.lerString();
                if (pass.equals("perigo")) {
                    users = new Users();
                    System.out.print('\u000C');
                    System.out.println("\nall Deleted\n");
                } else {
                    System.out.println("\nAcesso Negado!\n");
                }

                break;

            default:
                System.out.println("\nOperação inexistente! Introduzir operação de 0 a 5\n");
                break;
            }
        }

        return users;
    }

    /**
     * Método para criar um Evento
     */
    public static Users CriarUmEvento(Users users) {
        String nome;
        int dia, mes, ano;
        double dist;
        GregorianCalendar data;
        GregorianCalendar datalimite;
        int limiteinscricoes;
        String atividade;
        HashSet<String> atividades = new HashSet<String>();

        // Campos bÃ¡sicos
        System.out.println("###### CRIAR EVENTO #####\n\n");
        System.out.print("Nome do evento: ");
        nome = Input.lerString();
        System.out.println("Data do evento (dia mes ano)");

        System.out.print("Dia: ");
        dia = Input.lerInt();
        System.out.print("Mês: ");
        mes = Input.lerInt();
        System.out.print("Ano: ");
        ano = Input.lerInt();
        data = new GregorianCalendar(ano, (mes - 1), dia);

        System.out.println("Data de limite de inscrições (dia mes ano)");
        System.out.print("Dia: ");
        dia = Input.lerInt();
        System.out.print("Mês: ");
        mes = Input.lerInt();
        System.out.print("Ano: ");
        ano = Input.lerInt();
        datalimite = new GregorianCalendar(ano, (mes - 1), dia);

        System.out.print("Limite máximo de inscrições: ");
        limiteinscricoes = Input.lerInt();

        System.out.print("Atividade associada: ");
        atividade = Input.lerString();

        System.out.print("Distância da prova: ");
        dist = Input.lerInt();

        // Criar objecto evento
        EventoAdmin evento = new EventoAdmin(nome, atividade, data,datalimite, limiteinscricoes, dist);

        // Convidar utilizadores cujas as atividades sejam compatíveis com o
        // evento
        users.enviaConvitesDeEvento(evento);

        // Adicionar evento á "base de dados"
        users.addEvento(evento);

        return users;
    }

    /*
     * Método que permite aceder aos detalhes dos eventos criados pela
     * administração até à data
     */
    public static void DetalhesEventos(Users users) {

        String nome = "";
        // boolean first = true;

        while (!nome.equals("sair")) {

            System.out.println(users.listarEventos());
            System.out.print("Insira o nome do evento ou sair (para sair): ");
            nome = Input.lerString();
            System.out.print('\u000C');
            System.out.println(users.detalhesDoEvento(nome));
            // first = false;
        }

    }

    /**
     * Método que faz ligação com o objecto que evento que queremos simular.
     * @param users, todos os utilizadores
     */
    public static void SimulaEventos(Users users) {

        String user_key = "";
        String resultado = "";
        String nome = "";

        System.out.println(users.listarEventos());
        System.out.print("Insira o nome do evento para simular! (para sair): ");
        user_key = Input.lerString();
        // System.out.print('\u000C');

        if (users.existeEventoDeNome(user_key)) { // entÃ£o simulamos
            String tempomet; // o administrador passa info ao sistema acerca das condiÃ§Ãµes metereolÃ³gicas
            System.out.print("Descrição do tempo: ");
            tempomet = Input.lerString();
            nome = user_key;
            resultado = users.SimularEventoDeNome(user_key,tempomet);

            
            //MÃ‰TODO PARA MOSTRAR RESULTADO DA PROVA KM A KM
            TreeMap<Integer,String> tabelas = users.getTabelasDoEvento(nome);
            
            if(tabelas!=null){visualizarSimulacaoDaProva(tabelas);}
        }
        else{
             System.out.print('\u000C');
             System.out.println("\nEvento inexistente!\n");
        }

    }
    
    
    
    
    /**
     * Método que permite vizualizar decorrer do evento em cada km, após a sua simulação
     * @param TreeMap<Integer,String>, tabela de classificações
     * 
     */
    public static void visualizarSimulacaoDaProva(TreeMap<Integer,String> tabelas){
        
        int kms=1;
        int user_key=-1;
        
        Iterator<Map.Entry<Integer,String>> it = tabelas.entrySet().iterator();
        
        
        do{
              System.out.print('\u000C');
              String t = it.next().getValue();
              
              System.out.println(t);
              System.out.println("1 - Avançar   0 - Sair   > ");
              
              user_key = Input.lerInt();
        }
        while(it.hasNext() && user_key!=0);
    }
            
                
        
    
    
  
    
}
