/**
 * Classe de Teste
 * 
 * @author jdc 
 * @version 01/06/2014
 */

import java.util.GregorianCalendar;

import atividades.*;
import java.util.HashSet;

public class FitnessUMTest {

    public static Users test() {
       
        Users users;
        users = new Users();
        
        //Utilizadores para teste de simulação
        //String email, String password, String nome, String genero,int altura, int peso, GregorianCalendar datanasc, String fav)
        GregorianCalendar dataM = new GregorianCalendar(1959,2,9);
        User marta = new User("marta","1234","Marta Costa","F",165,60,dataM,"Futebol"); //idade 55
        
        GregorianCalendar dataJ = new GregorianCalendar(1978,6,7);
        User jorge = new User("jorgecaldas","1234","J. Caldas","M",174,71,dataJ,"Corrida"); //idade 36
        
        GregorianCalendar dataMa = new GregorianCalendar(1993,8,8);
        User mario = new User("mario","1234","Mario Silva","M",188,89,dataMa,"Zumba"); //idade 21
        
        
        GregorianCalendar dataJo = new GregorianCalendar(1944,8,8);
        User joao = new User("joao","1234","Joao Silva","M",178,80,dataJo,"Corrida"); //idade 70
        
        GregorianCalendar dataB = new GregorianCalendar(1984,8,22);
        User bino = new User("bino","1234","Bino Silva","M",155,50,dataB,"Corrida"); //idade 30
        
        GregorianCalendar dataA = new GregorianCalendar(1944,7,8);
        User arlindo = new User("arlindo","1234","Arlindo Reis","M",168,78,dataA,"Basket"); //idade 60
        
        GregorianCalendar dataE = new GregorianCalendar(1991,12,2);
        User eliana = new User("eliana","1234","Elina Castigo","F",158,55,dataE,"Canoagem"); //idade 23
        
        GregorianCalendar dataG = new GregorianCalendar(1944,8,8);
        User gina = new User("gina","1234","Gina Portela","F",158,52,dataG,"Futsal"); //idade 70
        
        GregorianCalendar dataAf = new GregorianCalendar(2000,9,1);
        User antonio = new User("antonio","1234","AntÃ³nio Castanheira","M",178,77,dataAf,"Pilates"); //idade 13
        
        
        //super(nome, duracao, idade, peso, altura, genero, data, hidratacao,distancia, velmax, altmax, altmin, tsub, tdes, tempomet);
        //Registos de corridas
        GregorianCalendar dataC1 = new GregorianCalendar(2014,1,9); GregorianCalendar dataC5 = new GregorianCalendar(2014,2,13);
        GregorianCalendar dataC2 = new GregorianCalendar(2014,1,12); GregorianCalendar dataC6 = new GregorianCalendar(2014,2,15);
        GregorianCalendar dataC3 = new GregorianCalendar(2014,1,15); GregorianCalendar dataC7 = new GregorianCalendar(2014,2,20);
        GregorianCalendar dataC4 = new GregorianCalendar(2014,1,19); GregorianCalendar dataC8 = new GregorianCalendar(2014,2,22);

        Time tC1 = new Time(1,50,12);
        Time tC2 = new Time(1,22,12); Time tC3 = new Time(1,03,12); Time tC4 = new Time(1,00,2); Time tC5 = new Time(0,55,12);
        Time tC6 = new Time(0,53,44); Time tC7 = new Time(0,45,33); Time tC8 = new Time(0,40,22); Time tC9 = new Time(0,23,12);
        
        
        //---------------JORGE
        Corrida jc1 = new Corrida(tC2,36,80,172,"M",dataC1,0.1,16.1,12.4,100,200,100,150,"chuva");
        Corrida jc2 = new Corrida(tC3,36,80,172,"M",dataC2,0.1,12.2,12.3,100,200,100,150,"chuva");
        Corrida jc3 = new Corrida(tC4,36,80,172,"M",dataC3,0.1,11.4,12.4,100,200,100,150,"chuva");
        Corrida jc4 = new Corrida(tC5,36,80,172,"M",dataC4,0.1,10.4,12,100,200,100,150,"chuva");
        Corrida jc5 = new Corrida(tC6,36,80,172,"M",dataC5,0.1,9.4,12,100,200,100,150,"chuva");
        Corrida jc6 = new Corrida(tC7,36,80,172,"M",dataC6,0.1,7.4,12,100,200,100,150,"chuva");
        
        
        
        jorge.addAtividade(jc1); jorge.addAtividade(jc2); jorge.addAtividade(jc3); jorge.addAtividade(jc4); jorge.addAtividade(jc5);
        jorge.addAtividade(jc6); jorge.addScore(jc1); jorge.addScore(jc2); jorge.addScore(jc3); jorge.addScore(jc4); jorge.addScore(jc5);
        jorge.addScore(jc6);
        
        
        
        
        //--------------------MÁRIO
        Corrida mc1 = new Corrida(tC2,21,80,172,"M",dataC1,0.1,17.3,12.3,100,200,100,150,"chuva");
        Corrida mc2 = new Corrida(tC3,21,80,172,"M",dataC2,0.1,12.1,12.3,100,200,100,150,"chuva");
        Corrida mc3 = new Corrida(tC4,21,80,172,"M",dataC3,0.1,12.3,12,100,200,100,150,"chuva");
        Corrida mc4 = new Corrida(tC5,21,80,172,"M",dataC4,0.1,10.3,12,100,200,100,150,"chuva");
        Corrida mc5 = new Corrida(tC6,21,80,172,"M",dataC5,0.1,9.1,12,100,200,100,150,"chuva");
        Corrida mc6 = new Corrida(tC7,21,80,172,"M",dataC6,0.1,7.1,12,100,200,100,150,"chuva");
        
        mario.addAtividade(mc1); mario.addAtividade(mc2); mario.addAtividade(mc3); mario.addAtividade(mc4); mario.addAtividade(mc5);
        mario.addAtividade(mc6); mario.addScore(mc1); mario.addScore(mc2); mario.addScore(mc3); mario.addScore(mc4); mario.addScore(mc5);
        mario.addScore(mc6);
        
        
        
        
        
        //---------------------MARTA
        Corrida mac1 = new Corrida(tC2,55,80,172,"M",dataC1,0.1,13.2,12,100,200,100,150,"chuva");
        Corrida mac2 = new Corrida(tC3,55,80,172,"M",dataC2,0.1,10.2,12,100,200,100,150,"chuva");
        Corrida mac3 = new Corrida(tC4,55,80,172,"M",dataC3,0.1,9.2,12,100,200,100,150,"chuva");
        Corrida mac4 = new Corrida(tC5,55,80,172,"M",dataC4,0.1,8.2,12,100,200,100,150,"chuva");
        Corrida mac5 = new Corrida(tC6,55,80,172,"M",dataC5,0.1,7.2,12,100,200,100,150,"chuva");
        Corrida mac6 = new Corrida(tC7,55,80,172,"M",dataC6,0.1,6.2,12,100,200,100,150,"chuva");
        
        marta.addAtividade(mac1); marta.addAtividade(mac2); marta.addAtividade(mac3); marta.addAtividade(mac4); marta.addAtividade(mac5);
        marta.addAtividade(mac6); marta.addScore(mac1); marta.addScore(mac2); marta.addScore(mac3); marta.addScore(mac4); marta.addScore(mac5);
        marta.addScore(mac6);
        
        
        //--------------------JOÃO
        Corrida joao1 = new Corrida(tC2,55,80,172,"M",dataC1,0.1,16.2,12,100,200,100,150,"chuva");
        Corrida joao2 = new Corrida(tC3,55,80,172,"M",dataC2,0.1,15.1,12,100,200,100,150,"chuva");
        Corrida joao3 = new Corrida(tC4,55,80,172,"M",dataC3,0.1,14.1,12,100,200,100,150,"chuva");
        Corrida joao4 = new Corrida(tC5,55,80,172,"M",dataC4,0.1,5.1,12.1,100,200,100,150,"chuva");
        Corrida joao5 = new Corrida(tC6,55,80,172,"M",dataC5,0.1,7,12,100,200,100,150,"chuva");
        Corrida joao6 = new Corrida(tC7,55,80,172,"M",dataC6,0.1,6,12,100,200,100,150,"chuva");
        
        joao.addAtividade(joao1); joao.addAtividade(joao2); joao.addAtividade(joao3); joao.addAtividade(joao4); joao.addAtividade(joao5);
        joao.addAtividade(joao6); joao.addScore(joao1); joao.addScore(joao2); joao.addScore(joao3); joao.addScore(joao4); joao.addScore(joao5);
        joao.addScore(joao6);
        
        
        //--------------------BINO
        Corrida bin1 = new Corrida(tC2,55,80,172,"M",dataC1,0.1,19,12,100,200,100,150,"chuva");
        Corrida bin2 = new Corrida(tC3,55,80,172,"M",dataC2,0.1,18,12,100,200,100,150,"chuva");
        Corrida bin3 = new Corrida(tC4,55,80,172,"M",dataC3,0.1,13,12,100,200,100,150,"chuva");
        Corrida bin4 = new Corrida(tC5,55,80,172,"M",dataC4,0.1,14,12,100,200,100,150,"chuva");
        Corrida bin5 = new Corrida(tC6,55,80,172,"M",dataC5,0.1,12,12,100,200,100,150,"chuva");
        Corrida bin6 = new Corrida(tC7,55,80,172,"M",dataC6,0.1,9,12,100,200,100,150,"chuva");
        
        bino.addAtividade(bin1); bino.addAtividade(bin2); bino.addAtividade(bin3); bino.addAtividade(bin4); bino.addAtividade(bin5);
        bino.addAtividade(bin6); bino.addScore(bin1); bino.addScore(bin2); bino.addScore(bin3); bino.addScore(bin4); bino.addScore(bin5);
        bino.addScore(bin6);
        
        
        //---------------ARLINDO
        Corrida ar1 = new Corrida(tC2,36,80,172,"M",dataC1,0.1,10,12,100,200,100,150,"chuva");
        Corrida ar2 = new Corrida(tC3,36,80,172,"M",dataC2,0.1,9,12,100,200,100,150,"chuva");
        Corrida ar3 = new Corrida(tC4,36,80,172,"M",dataC3,0.1,8,12,100,200,100,150,"chuva");
        Corrida ar4 = new Corrida(tC5,36,80,172,"M",dataC4,0.1,8,12,100,200,100,150,"chuva");
        Corrida ar5 = new Corrida(tC6,36,80,172,"M",dataC5,0.1,5,12,100,200,100,150,"chuva");
        Corrida ar6 = new Corrida(tC7,36,80,172,"M",dataC6,0.1,4,12,100,200,100,150,"chuva");
        
        arlindo.addAtividade(ar1); arlindo.addAtividade(ar2); arlindo.addAtividade(ar3); arlindo.addAtividade(ar4); arlindo.addAtividade(ar5);
        arlindo.addAtividade(ar6); arlindo.addScore(ar1); arlindo.addScore(ar2); arlindo.addScore(ar3); arlindo.addScore(ar4); arlindo.addScore(ar5);
        arlindo.addScore(ar6);
        
        
        
        //---------------ELIANA
        Corrida el1 = new Corrida(tC2,36,80,172,"M",dataC1,0.1,16,12,100,200,100,150,"chuva");
        Corrida el2 = new Corrida(tC3,36,80,172,"M",dataC2,0.1,12,12,100,200,100,150,"chuva");
        Corrida el3 = new Corrida(tC4,36,80,172,"M",dataC3,0.1,11,12,100,200,100,150,"chuva");
        Corrida el4 = new Corrida(tC5,36,80,172,"M",dataC4,0.1,10,12,100,200,100,150,"chuva");
        Corrida el5 = new Corrida(tC6,36,80,172,"M",dataC5,0.1,9,12,100,200,100,150,"chuva");
        Corrida el6 = new Corrida(tC7,36,80,172,"M",dataC6,0.1,7,12,100,200,100,150,"chuva");
        
        eliana.addAtividade(el1); eliana.addAtividade(el2); eliana.addAtividade(el3); eliana.addAtividade(el4); eliana.addAtividade(el5);
        eliana.addAtividade(el6); eliana.addScore(el1); eliana.addScore(el2); eliana.addScore(el3); eliana.addScore(el4); eliana.addScore(el5);
        eliana.addScore(el6);
        
        
        //---------------GINA
        Corrida g1 = new Corrida(tC2,36,80,172,"M",dataC1,0.1,16,12,100,200,100,150,"chuva");
        Corrida g2 = new Corrida(tC3,36,80,172,"M",dataC2,0.1,12,12,100,200,100,150,"chuva");
        Corrida g3 = new Corrida(tC4,36,80,172,"M",dataC3,0.1,11,12,100,200,100,150,"chuva");
        Corrida g4 = new Corrida(tC5,36,80,172,"M",dataC4,0.1,10,12,100,200,100,150,"chuva");
        Corrida g5 = new Corrida(tC6,36,80,172,"M",dataC5,0.1,9,12,100,200,100,150,"chuva");
        Corrida g6 = new Corrida(tC7,36,80,172,"M",dataC6,0.1,7,12,100,200,100,150,"chuva");
        
        gina.addAtividade(g1); gina.addAtividade(g2); gina.addAtividade(g3); gina.addAtividade(g4); gina.addAtividade(g5);
        gina.addAtividade(g6); gina.addScore(g1); gina.addScore(g2); gina.addScore(g3); gina.addScore(g4); gina.addScore(g5);
        gina.addScore(g6);
        
        
        
        //--------------ANTONIO
        Corrida ant1 = new Corrida(tC2,36,80,172,"M",dataC1,0.1,10,12,100,200,100,150,"chuva");
        Corrida ant2 = new Corrida(tC3,36,80,172,"M",dataC2,0.1,5,12,100,200,100,150,"chuva");
        Corrida ant3 = new Corrida(tC4,36,80,172,"M",dataC3,0.1,6,12,100,200,100,150,"chuva");
        Corrida ant4 = new Corrida(tC5,36,80,172,"M",dataC4,0.1,5,12,100,200,100,150,"chuva");
        Corrida ant5 = new Corrida(tC6,36,80,172,"M",dataC5,0.1,5,12,100,200,100,150,"chuva");
        Corrida ant6 = new Corrida(tC7,36,80,172,"M",dataC6,0.1,3,12,100,200,100,150,"chuva");
        
        antonio.addAtividade(ant1); antonio.addAtividade(ant2); antonio.addAtividade(ant3); antonio.addAtividade(ant4); antonio.addAtividade(ant5);
        antonio.addAtividade(ant6); antonio.addScore(ant1); antonio.addScore(ant2); antonio.addScore(ant3); antonio.addScore(ant4); antonio.addScore(ant5);
        antonio.addScore(ant6);
        
        
        
        
        
        
        
        
        
        //------------------------------------------------------------------------------------------------------------------//
        //----------------------------------SIMULAR CICLISMO----------------------------------------------------------------//
        //------------------------------------------------------------------------------------------------------------------//
        //jorge, mario, marta, joao
        
        //---------------JORGE
        Cycling jcy1 = new Cycling(tC2,36,80,172,"M",dataC1,0.1,33.3,55);
        Cycling jcy2 = new Cycling(tC3,36,80,172,"M",dataC2,0.1,31.1,50);
        Cycling jcy3 = new Cycling(tC4,36,80,172,"M",dataC3,0.1,25.2,52.2);
        Cycling jcy4 = new Cycling(tC5,36,80,172,"M",dataC4,0.1,22.4,44.3);
        Cycling jcy5 = new Cycling(tC6,36,80,172,"M",dataC5,0.1,19.6,33.6);
        Cycling jcy6 = new Cycling(tC7,36,80,172,"M",dataC6,0.1,14.1,22.3);
        
        
        
        jorge.addAtividade(jcy1); jorge.addAtividade(jcy2); jorge.addAtividade(jcy3); jorge.addAtividade(jcy4); jorge.addAtividade(jcy5);
        jorge.addAtividade(jcy6); jorge.addScore(jcy1); jorge.addScore(jcy2); jorge.addScore(jcy3); jorge.addScore(jcy4); jorge.addScore(jcy5);
        jorge.addScore(jcy6);
        
        
        //---------------MARIO
        Cycling may1 = new Cycling(tC2,36,80,172,"M",dataC1,0.1,33.3,55);
        Cycling may2 = new Cycling(tC3,36,80,172,"M",dataC2,0.1,31.1,50);
        Cycling may3 = new Cycling(tC4,36,80,172,"M",dataC3,0.1,25.2,52.2);
        Cycling may4 = new Cycling(tC5,36,80,172,"M",dataC4,0.1,22.4,44.3);
        Cycling may5 = new Cycling(tC6,36,80,172,"M",dataC5,0.1,19.6,33.6);
        Cycling may6 = new Cycling(tC7,36,80,172,"M",dataC6,0.1,14.1,22.3);
        
        
        
        //---------------MARTA
        Cycling mary1 = new Cycling(tC2,36,80,172,"M",dataC1,0.1,33.3,55);
        Cycling mary2 = new Cycling(tC3,36,80,172,"M",dataC2,0.1,31.1,50);
        Cycling mary3 = new Cycling(tC4,36,80,172,"M",dataC3,0.1,25.2,52.2);
        Cycling mary4 = new Cycling(tC5,36,80,172,"M",dataC4,0.1,22.4,44.3);
        Cycling mary5 = new Cycling(tC6,36,80,172,"M",dataC5,0.1,19.6,33.6);
        Cycling mary6 = new Cycling(tC7,36,80,172,"M",dataC6,0.1,14.1,22.3);
        
        
        
        marta.addAtividade(mary1); marta.addAtividade(mary2); marta.addAtividade(mary3); marta.addAtividade(mary4); marta.addAtividade(mary5);
        marta.addAtividade(mary6); marta.addScore(mary1); marta.addScore(mary2); marta.addScore(mary3); marta.addScore(mary4); marta.addScore(mary5);
        marta.addScore(mary6);
        
        
        mario.addAtividade(may1); mario.addAtividade(may2); mario.addAtividade(may3); mario.addAtividade(may4); mario.addAtividade(may5);
        mario.addAtividade(may6); mario.addScore(may1); mario.addScore(may2); mario.addScore(may3); mario.addScore(may4); mario.addScore(may5);
        mario.addScore(may6);
       
        
        //--------------------JOÃO
        Cycling joaocy1 = new Cycling(tC2,55,80,172,"M",dataC1,0.1,41.2,22);
        Cycling joaocy2 = new Cycling(tC3,55,80,172,"M",dataC2,0.1,29.1,52);
        Cycling joaocy3 = new Cycling(tC4,55,80,172,"M",dataC3,0.1,28.1,32);
        Cycling joaocy4 = new Cycling(tC5,55,80,172,"M",dataC4,0.1,15.1,22.1);
        Cycling joaocy5 = new Cycling(tC6,55,80,172,"M",dataC5,0.1,17,12.1);
        Cycling joaocy6 = new Cycling(tC7,55,80,172,"M",dataC6,0.1,10,12.6);
        
        joao.addAtividade(joaocy1); joao.addAtividade(joaocy2); joao.addAtividade(joaocy3); joao.addAtividade(joaocy4); joao.addAtividade(joaocy5);
        joao.addAtividade(joaocy6); joao.addScore(joaocy1); joao.addScore(joaocy2); joao.addScore(joaocy3); joao.addScore(joaocy4); joao.addScore(joaocy5);
        joao.addScore(joaocy6);
       
        //------------------------------------------------------------------------------------------------------------------//
        //------------------------------------------------------------------------------------------------------------------//
        //------------------------------------------------------------------------------------------------------------------//

        
        
       
        
        
        
        
        
        
        
        
        users.registerUser2(marta);//55 anos
        users.registerUser2(jorge);//36 anos
        users.registerUser2(mario);//21 anos
        users.registerUser2(bino);
        users.registerUser2(joao);
        users.registerUser2(arlindo);
        users.registerUser2(eliana);
        users.registerUser2(gina);
        users.registerUser2(antonio);
        jorge.addFriend("joao");
        jorge.addFriend("bino");
        jorge.addFriend("antonio");
        
        //EVENTOS----------------------------------------------------------------------------
        String nome = "M";
        int dia, mes, ano;
        double dist;
        GregorianCalendar data;
        GregorianCalendar datalimite;
        int limiteinscricoes = 10;
        String atividade;
        HashSet<String> atividades = new HashSet<String>();


        dia = 20;
        mes = 8;
        ano = 2014;
        data = new GregorianCalendar(ano, (mes - 1), dia);

        dia = 19;
        mes = 8;
        ano = 2014;
        datalimite = new GregorianCalendar(ano, (mes - 1), dia);

        
        atividade="Corrida";
        dist = 20;

        // Criar objecto evento
        EventoAdmin evento = new EventoAdmin(nome,atividade,data,datalimite,limiteinscricoes,dist);

        
        // Convidar utilizadores cujas as atividades sejam compatíveis com o evento
        users.enviaConvitesDeEvento(evento);

        // Adicionar evento á  "base de dados"
        users.addEvento(evento);
        
        
        //users.inscreveUserEmEvento("jorge","M");
        users.inscreveUserEmEvento("marta","M");
        users.inscreveUserEmEvento("mario","M");
        users.inscreveUserEmEvento("bino","M");
        users.inscreveUserEmEvento("joao","M");
        users.inscreveUserEmEvento("arlindo","M");
        users.inscreveUserEmEvento("eliana","M");
        users.inscreveUserEmEvento("gina","M");
        users.inscreveUserEmEvento("mario","M");
        
        
        
        
        
        nome = "Tour de Braga";
        data = new GregorianCalendar(2014,7,22);
        datalimite = new GregorianCalendar(2014,7,22);
        atividade="Cycling";
        dist=44;
        int lim=10;
        evento = new EventoAdmin(nome,atividade,data,datalimite,lim,dist);
        
        users.enviaConvitesDeEvento(evento);
        users.addEvento(evento);
        //inscrever manualmente jorge  mario  marta e joao   pass: 1234

        return users;
    }

}

