/**Classe gestão e consulta de atividades dos utilizadores.
 *
 * @author jdc
 * @version 1/05/2014
 */

import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.HashSet;

import atividades.*;


public class GestorDeAtividadesUI {

    //recebe utilizadores e chave email do utilizador que está a usar a interface
    public static void atividades(Users users,String mail) throws IOException {
        int user_key = -1;

        String nome = users.getNomeDe(mail);
        String genero = users.getGeneroDe(mail);
        int altura = users.getAlturaDe(mail);
        int peso = users.getPesoDe(mail);
        int idade = users.getIdadeDe(mail);

        Atividades atividades;
        Atividades atvs = users.getAtividadesDe(mail);

        System.out.print('\u000C');
        while (user_key != 0) {
            
            printMenu(nome);

            user_key = Input.lerInt();

            switch (user_key) {

            case 0:
                break;

            case 1:
                System.out.print('\u000C');

                if (registarAtividade(users, mail, genero, altura, peso, idade)) {
                    System.out.print('\u000C');
                    System.out.println("\nAtividade registada.");
                    atvs = users.getAtividadesDe(mail);
                } else {
                    System.out.print('\u000C');
                    System.out.println("\nERRO!");
                }
                break;

            case 2:
                System.out.print('\u000C');
                int iremove = -1;
                System.out.println("\n#####ATIVIDADES DE " + nome + "######");
                System.out.println(atvs.toString());
                System.out.print("\nEscolha um índice para remover uma atividade ou insira 0 para cancelar: ");
                iremove = Input.lerInt();

                if (iremove != 0) {
                    AtvBase rm = atvs.atividadeDadoIndice(iremove);
                    atvs.removeAtividade(rm);
                    System.out.print('\u000C');
                    System.out.println("\nOperação efetuada com sucesso");
                } else {
                    System.out.print('\u000C');
                }

                break;

            case 3:
                System.out.print('\u000C'); // DisponÃ­vel para utilizador e amigos (10 atividades mais recentes)
                int detalhe = -1;
                Atividades toprint = atvs.get10MaisRecentes();

                while (detalhe != 0) {
                    System.out.println("\n#####ATIVIDADES DE " + nome+ "######");
                    System.out.println(toprint.toString());
                    System.out.print("\nPara consultar o detalhe de uma atividade insira o seu número para saír insira 0: ");

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
                            System.out.println("\nOperação inválida, por favor insira uma opção válida.\n");
                        }
                    } else {
                        System.out.print('\u000C');
                        System.out.println("\nERRO! Por favor introduza um índice de 1 a "+ toprint.getSizeLista() + "\n");
                    }
                }
                System.out.print('\u000C');
                break;

            case 4:
                GregorianCalendar thisyear = new GregorianCalendar();
                System.out.print('\u000C');
                String cal = "";
                int ano = thisyear.get(Calendar.YEAR); // ano a partir do qual fazemos a navegação no calendário;
                int mes = 0;
                int primeirodia = 3;

                while (!cal.equals("sair")) {

                    System.out.println("\n### O MEU CALENDÁRIO DE ATIVIDADES ###\n");
                    primeirodia = imprimirCalendarioMes(primeirodia, mes, ano,atvs);

                    // Dar possibilidade de se consultar detalhes dos dias do mes que aparece no ecrã
                    System.out.print("Acções: ant (Mês anterior)  seg (Mês seguinte)    Dia do mes (Consultar atividade(s) do dia)    sair (Para saír):  ");
                    cal = Input.lerString();

                    switch (cal) {

                    case "sair":
                        break;

                    case "ant":
                        if (mes == 0) {
                            mes = 11; // em Janeiro, andar para trás implica mudança de ano
                            ano--;
                            if (ano == 2013)
                                primeirodia = 0;
                        } else {
                            int aux1 = diasDoMes(mes, ano);
                            int aux2;
                            if (mes > 0) {
                                aux2 = diasDoMes(mes - 1, ano);
                            } else {
                                aux2 = diasDoMes(mes, ano);
                            }

                            mes--;
                            int paratras = (aux1 + aux2);
                            for (int i = 0; i < paratras; i++) {
                                if (primeirodia == 0)
                                    primeirodia = 6;
                                else
                                    primeirodia--;
                            }
                        }
                        System.out.print('\u000C');
                        break;

                    case "seg":
                        if (mes == 11) {
                            mes = 0;//em dezembro andar para a frente implica avançar um ano
                            ano++;
                            if (ano == 2015)
                                primeirodia = 4;
                        } else
                            mes++;
                        System.out.print('\u000C');
                        break;

                    default:

                        boolean isInteger = true;
                        int size = cal.length();
                        for (int i = 0; (i < size) && isInteger == true; i++) {
                            isInteger = Character.isDigit(cal.charAt(i));
                        }

                       
                        if (isInteger == true) {

                            int indice = -1;
                            int odia;
                            
                            try{
                                odia = Integer.parseInt(cal);
                            }
                            catch(Exception e){
                                System.out.print('\u000C');
                                System.out.print("\nOpcção inválida! Por favor tente outra vez.");
                                break;
                            }
                            
                            if(odia<=0 && odia>31){
                                System.out.print('\u000C');
                                System.out.println("\nErro! dia inválido!\n");
                                break;
                            }
                            
                            GregorianCalendar datateste = new GregorianCalendar(ano, mes, odia);
                            Atividades atvsdodia = atvs.atividadesDadoData(datateste);

                            System.out.print('\u000C');
                            while (indice != 0) {
                                System.out.println("\n#####ATIVIDADES " + odia+ "/" + mes + "/" + ano + " ######");
                                System.out.println(atvsdodia.toString());
                                System.out.print("\nPara consultar o detalhe de uma atividade insira o seu número para saír insira 0: ");
                                indice = Input.lerInt();

                                System.out.print('\u000C');

                                if (indice != 0) {
                                    AtvBase printatv = atvs.atividadeDadoIndice(indice);
                                    if (printatv != null)
                                        System.out.println(printatv.toString());
                                    else
                                        System.out.println("\nOpção inválida, por favor insira uma opção válida.\n");
                                }
                            }
                            
                            System.out.print('\u000C');
                        } else {
                            System.out.print('\u000C');
                            System.out.println("Opção Inválida! Por favor insira uma opção válida!\n");
                            break;
                        }

                        int paratras = diasDoMes(mes, ano);
                        for (int i = 0; i < paratras; i++) {
                            if (primeirodia == 0)
                                primeirodia = 6;
                            else
                                primeirodia--;
                        }
                        break;
                    }
                }
                System.out.print('\u000C');
                break;

            case 5:
                int omes;
                int oano;
                atividades = new Atividades();
                System.out.print('\u000C');
                System.out.println("Por favor escolha o mês sobre o qual quer consultar a estatística: ");
                System.out.println("1 - Janeiro");
                System.out.println("2 - Fevereiro");
                System.out.println("3 - Março");
                System.out.println("4 - Abril");
                System.out.println("5 - Maio");
                System.out.println("6 - Junho");
                System.out.println("7 - Julho");
                System.out.println("8 - Agosto");
                System.out.println("9 - Setembro");
                System.out.println("10 - Outubro");
                System.out.println("11 - Novembro");
                System.out.println("12 - Dezembro");
                System.out.print("> ");
                omes = Input.lerInt();
                omes--;
                System.out.println("\nPor favor introduza o ano: ");
                oano = Input.lerInt();

                System.out.print('\u000C');
                GregorianCalendar m = new GregorianCalendar(oano, omes, 1);
                System.out.println(atividades.estatisticaMensal(m,users.getSetAtividadesDe(mail)));
                break;

            case 6:
                atividades = new Atividades();
                GregorianCalendar year;
                int aux;
                System.out.print('\u000C');
                System.out.print("Ano sobre o qual pretende consultar estatística (2013 ou 2014): ");
                aux = Input.lerInt();
                year = new GregorianCalendar(aux, 1, 1);
                System.out.print('\u000C');
                System.out.println(atividades.estatisticaAnual(year,users.getSetAtividadesDe(mail)));
                break;

            default:
                System.out.print('\u000C');
                System.out.println("\nOpção inválida! Por favor insira uma opção válida.");
                break;
            }
        }

    }

    
    /**
     * Imprimir menu principal de opções
     */
    public static void printMenu(String nome){
            System.out.println("\n#############AS MINHAS ATIVIDADES#############"+ "    Utilizador: " + nome);
            System.out.println(" 1 - Registar Atividade");
            System.out.println(" 2 - Remover uma Atividade");
            System.out.println(" 3 - Consultar as 10 atividades mais recentes (Os meus amigos podem ver)");
            System.out.println(" 4 - O meu calendário de atividades");
            System.out.println(" 5 - Consultar estatística mensais");
            System.out.println(" 6 - Consultar estatísticas anuais");
            System.out.println(" 0 - Sair");
            System.out.print("\n> ");
    }

    
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------//
    // ---------------------------------------------------------------REGISTO DE ATIVIDADES---------------------------------------------------------------//
    // ---------------------------------------------------------------------------------------------------------------------------------------------------//

    // Método para registo de um desporto coletivo
    private static boolean registarAtividade(Users users, String mail, String genero,int altura, int peso, int idade) {
        String atividadeID = null;
        AtvBase atividade = null; // atividade genÃ©rica
        boolean ok = false;
        Time duracao = new Time();
        GregorianCalendar data = new GregorianCalendar();
        int dia, mes, ano;
        int horas, min, seg;
        double H2O;
        String tempomet;
        double distancia;
        double velmax;
        int altmax, altmin, tsub, tdes;
        double rating;

        System.out.println("\n#REGISTO DE ATIVIDADE#\n\n");
        System.out.println(menuDeAtividades());
        System.out.print("Por favor insira o nome da atividade: ");

        atividadeID = Input.lerString();

        System.out.print('\u000C');

        if (!atividadeID.equals("Canoagem") && !atividadeID.equals("Cycling")
                && !atividadeID.equals("Corrida")
                && !atividadeID.equals("Pilates")
                && !atividadeID.equals("Zumba")
                && !atividadeID.equals("Futsal")
                && !atividadeID.equals("Basket")
                && !atividadeID.equals("Futebol")) {
            System.out.println("Nome incorreto ou atividade inexistente. Por favor introduza corretamente o nome de uma atividade da lista");
            return false;
        }

        while (!ok) {
            System.out.println("Duracao da atividade");
            System.out.println("Horas?");
            horas = Input.lerInt();
            System.out.println("Minutos?");
            min = Input.lerInt();
            System.out.println("Segundos?");
            seg = Input.lerInt();

            if (validarHora(horas, min, seg)) {
                duracao = new Time(horas, min, seg);
                ok = true;
            } else {
                System.out.println("\nIntroduziu campos de duração inválidos, por favor tente outra vez.");
            }
        }

        ok = false;
        while (!ok) {
            System.out.println("Data");
            System.out.println("Dia?");
            dia = Input.lerInt();
            System.out.println("Mês?");
            mes = Input.lerInt();
            System.out.println("Ano?");
            ano = Input.lerInt();

            if (validarData(dia, mes, ano)) {
                data = new GregorianCalendar(ano, mes - 1, dia);
                ok = true;
            } else {
                System.out.println("\nData inválida!Tente novamente.");
            }
        }

        System.out.print("Hidratacao: ");
        H2O = Input.lerDouble();

        switch (atividadeID) {
        case "Canoagem":

            System.out.print("Descrição do tempo: ");
            tempomet = Input.lerString();

            System.out.print("Distância percorrida (em km): ");
            distancia = Input.lerDouble();

            System.out.print("Velocidade máxima: ");
            velmax = Input.lerDouble();

            atividade = new Canoagem(duracao, idade, peso, altura,genero, data, H2O, distancia, velmax, tempomet);
            break;

        case "Cycling":

            System.out.print("Distância percorrida (em km): ");
            distancia = Input.lerDouble();

            System.out.print("Velocidade máxima: ");
            velmax = Input.lerDouble();

            atividade = new Cycling(duracao, idade, peso, altura,genero, data, H2O, distancia, velmax);
            break;

        case "Corrida":

            System.out.print("Descrição do tempo: ");
            tempomet = Input.lerString();

            System.out.print("Distância percorrida (em km): ");
            distancia = Input.lerDouble();

            System.out.print("Velocidade máxima: ");
            velmax = Input.lerDouble();

            System.out.print("Altura máxima (em metros): ");
            altmax = Input.lerInt();

            System.out.print("Altura mínima (em metros): ");
            altmin = Input.lerInt();

            System.out.print("Total subido (em metros): ");
            tsub = Input.lerInt();

            System.out.print("Total descido (em metros): ");
            tdes = Input.lerInt();

            atividade = new Corrida(duracao, idade, peso, altura,genero, data, H2O, distancia, velmax, altmax, altmin, tsub,tdes, tempomet);
            break;

        case "Pilates":
            atividade = new Pilates(duracao, idade, peso, altura,genero, data, H2O);
            break;

        case "Zumba":
            atividade = new Zumba(duracao, idade, peso, altura,genero, data, H2O);
            break;

        case "Futsal":
        case "Basket":

            System.out.print("Rating (0 a 10): ");
            rating = Input.lerDouble();

            if (atividadeID.equals("Futsal")) {
                atividade = new Futsal(duracao, idade, peso,altura, genero, data, H2O, rating);
            } else {
                atividade = new Basket(duracao, idade, peso,altura, genero, data, H2O, rating);
            }

            break;

        case "Futebol":

            System.out.print("Rating (0 a 10): ");
            rating = Input.lerDouble();

            System.out.print("Descrição do tempo: ");
            tempomet = Input.lerString();

            atividade = new Futebol(duracao, idade, peso, altura,genero, data, H2O, tempomet, rating);

            break;

        default:
            return false;
        }

        // adicionar a atividade
        if (atividade != null) {
            users.addAtividadeDe(mail,atividade);
            users.addScoreDe(mail,(AtvBase) atividade);
        }

        return true;
    }

    public static String menuDeAtividades() {
        return ("##### MENU ATIVIDADES #####\n" + "- Corrida\n"
                + "- Canoagem\n" + "- Cycling\n" + "- Pilates\n" + "- Zumba\n"
                + "- Futsal\n" + "- Basket\n" + "- Futebol\n\n");
    }

    // Validar data
    public static boolean validarData(int dia, int mes, int ano) {
        boolean ret = true;
        switch (mes) {
        case 1:
            if (dia < 0 && dia > 31)
                ret = false;
            break;
        case 2:
            if (dia < 0 && dia > 29)
                ret = false;
            break;
        case 3:
            if (dia < 0 && dia > 31)
                ret = false;
            break;
        case 4:
            if (dia < 0 && dia > 30)
                ret = false;
            break;
        case 5:
            if (dia < 0 && dia > 31)
                ret = false;
            break;
        case 6:
            if (dia < 0 && dia > 30)
                ret = false;
            break;
        case 7:
            if (dia < 0 && dia > 31)
                ret = false;
            break;
        case 8:
            if (dia < 0 && dia > 31)
                ret = false;
            break;
        case 9:
            if (dia < 0 && dia > 30)
                ret = false;
            break;
        case 10:
            if (dia < 0 && dia > 31)
                ret = false;
            break;
        case 11:
            if (dia < 0 && dia > 30)
                ret = false;
            break;
        case 12:
            if (dia < 0 && dia > 31)
                ret = false;
            break;
        default:
            ret = false;
            break;
        }
        return ret;
    }

    /**
     * Método auxiliar para validação de tempo
     */
    public static boolean validarHora(int horas, int min, int seg) {
        return (horas >= 0 && horas <= 23 && min >= 0 && min <= 59 && seg >= 0 && seg <= 59);
    } 
    
    
    
    // ---------------------------------------------------------------------------------------------------------------------------------------------------//
    // ---------------------------------------------------------------IMPRESSÃO DO CALENDÁRIO-------------------------------------------------------------//
    // ---------------------------------------------------------------------------------------------------------------------------------------------------//

    static int imprimirCalendarioMes(int day, int mes, int year, Atividades atvs) {

        // Set de dias em que utilizador realizou pelo menos uma atividade para
        // que se posso formatar output em conformidade
        HashSet<Integer> atividadesdomes = atvs.existeAtividadesDiasDoMes(mes,
                year);

        int k;
        for (k = 0; k != mes; k++);

        String month = "";
        int numOfDays = 0;

        switch (k) {
        case 0:
            month = "Janeiro ";
            numOfDays = 31;
            break;
        case 1:
            month = "Fevereiro ";
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
                numOfDays = 29;
            else
                numOfDays = 28;
            break;
        case 2:
            month = "Março ";
            numOfDays = 31;
            break;
        case 3:
            month = "Abril ";
            numOfDays = 30;
            break;
        case 4:
            month = "Maio ";
            numOfDays = 31;
            break;
        case 5:
            month = "Junho ";
            numOfDays = 30;
            break;
        case 6:
            month = "Julho ";
            numOfDays = 31;
            break;
        case 7:
            month = "Agosto ";
            numOfDays = 31;
            break;
        case 8:
            month = "Setembro ";
            numOfDays = 30;
            break;
        case 9:
            month = "Outubro ";
            numOfDays = 31;
            break;
        case 10:
            month = "Novembro ";
            numOfDays = 30;
            break;
        case 11:
            month = "Dezembro ";
            numOfDays = 31;
            break;
        }

        System.out.println(month + year);
        System.out.println("Dom   Seg   Ter   Qua   Qui   Sex   Sab");
        for (int i = 1; i <= day; i++)
            System.out.print("      ");
        for (int j = 1; j <= numOfDays; j++) {
            if (day % 7 == 0 && day != 0)
                System.out.println();
            if (atividadesdomes.contains(j))
                System.out.printf("%3d*  ", j);
            else
                System.out.printf("%3d   ", j);
            day += 1;
        }
        day %= 7;
        System.out.print("\n\n");

        return day;
    }

    private static int diasDoMes(int k, int year) {

        int numOfDays = 0;

        switch (k) {
        case 0:
            numOfDays = 31;
            break;
        case 1:
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
                numOfDays = 29;
            else
                numOfDays = 28;
            break;
        case 2:
            numOfDays = 31;
            break;
        case 3:
            numOfDays = 30;
            break;
        case 4:
            numOfDays = 31;
            break;
        case 5:
            numOfDays = 30;
            break;
        case 6:
            numOfDays = 31;
            break;
        case 7:
            numOfDays = 31;
            break;
        case 8:
            numOfDays = 30;
            break;
        case 9:
            numOfDays = 31;
            break;
        case 10:
            numOfDays = 30;
            break;
        case 11:
            numOfDays = 31;
            break;
        }

        return numOfDays;
    }
    
    
}
