/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import middleware.Question;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import middleware.PDU;
import middleware.UserGame;

/**
 *
 * @author carlosmorais
 */
public class ChallengeThread extends Thread{
    
    private Challenge challenge;
    private Manager manager;
    
    //private List<Question> questions;
    private Map<Integer, Question> questions; //<#Question, Question>
    
    //guarda as PDU's para poder reenviar!    
    private Map<Integer, Map<Integer,PDU>> respostas; //<#Questao, <#PDU,PDU> >
    
    int usersEND;
    int totalPlayers;
    
    final Lock lock;
    final Condition notEnded;
        
    //recebe aqui o desafio e utiliza o generateQuestions para carregar as questoes
    public ChallengeThread(Challenge challenge, Manager manager){
        this.challenge = challenge;
        this.manager = manager;
        this.questions = this.manager.generateQuestions(); 
        this.respostas = new HashMap<Integer,Map<Integer,PDU>>();
        this.lock = new ReentrantLock();
        this.notEnded = lock.newCondition();
        this.usersEND=0;
        this.totalPlayers=this.challenge.getPlayers().size(); //LOCK...
    }

    public Map<Integer, Question> getQuestions() {
        return this.questions;
    }

    public Challenge getChallenge() {
        return challenge;
    }
    
    
    
    
    
    public boolean checkAnswer(String user, int question, int option){
        if(option == 0){
            this.challenge.addUserIgnoreQuestion(user);
            return true;
        }
        
        else if(this.questions.get(question).getCorrectAnswer()==option){                    
            this.challenge.addUserCorrectAnswer(user);
            return true;
        }
        else{
            this.challenge.addUserWrongAnswer(user);
            return false;
        }
    }
    
    public void retransmit(String user, int question, int bloco) throws IOException{
        PDU res = this.respostas.get(question).get(bloco);
        this.manager.getThreads().get(user).sendReeply(res); 
    }
    
    
    public PDU buildScore(int label){
        PDU res = new PDU(label);
            res.creatReply();
            res.replyOK();
            res.setDesafio(this.challenge.getName()); 
            
        int bloco = 0;    
        int last = this.totalPlayers;    
            
        List<String> usernames = new ArrayList<String>();
        List<Integer> scores = new ArrayList<Integer>();
        
        for(UserGame c: this.challenge.getChallengeRanking()){                        
            usernames.add(c.getUsername());
            scores.add(c.getPoits());           
        }         
        res.setListAlcunha(usernames);
        res.setListScore(scores);
        
        return res;
    }
    
    public void sendScore(String user, int label) throws IOException{
        PDU res = this.buildScore(label);
        this.manager.getThreads().get(user).sendReeply(res);
    }
    
    public void incUserEnd(){
        this.lock.lock();
        this.usersEND++;
        this.notEnded.signalAll();
        this.lock.unlock();
    }
    
    public void userEnd(String user, int label) throws IOException{
        this.incUserEnd();        
        this.lock.lock();
        try{
        while(this.usersEND<this.totalPlayers){
            try {
                this.notEnded.await();
            } catch (InterruptedException ex) {
                Logger.getLogger(ChallengeThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        } finally {this.lock.unlock();}
        
        this.sendScore(user, label);
    }
    
    public void sendRankingToServers(){
        this.lock.lock();
        try{
        while(this.usersEND<this.totalPlayers){
            try {
                this.notEnded.await();
            } catch (InterruptedException ex) {
                Logger.getLogger(ChallengeThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        } finally {this.lock.unlock();}
        
        this.manager.AddScoreChallenge(this.challenge.getChallengeRanking());
        
        //PDU res = this.buildScore(0);
        //ENVIA PARA OS SERVIDORES
    }
    
    public void quitUser(String user, int label){
        
        this.lock.lock();
        try {
            this.usersEND++;
            this.notEnded.signalAll();
        } finally {
            this.lock.unlock();
        }
    }
    
    public void run(){
        long time, start, stop;
        int nPDUs;        
        
        GregorianCalendar d1 = (GregorianCalendar) GregorianCalendar.getInstance(), d2 =this.challenge.getDate();
        start = d1.getTimeInMillis();
        stop = this.challenge.getDate().getTimeInMillis();
        
         System.out.println("Hora do servidor "+
                d1.get(GregorianCalendar.YEAR)+"/"+d1.get(GregorianCalendar.MONTH)+
                "/"+d1.get(GregorianCalendar.DAY_OF_MONTH)+
                " "+d1.get(GregorianCalendar.HOUR_OF_DAY)+":"+d1.get(GregorianCalendar.MINUTE)+":"+
                d1.get(GregorianCalendar.SECOND));
        
         System.out.println("Data de inicio "+
                d2.get(GregorianCalendar.YEAR)+"/"+d2.get(GregorianCalendar.MONTH)+
                "/"+d2.get(GregorianCalendar.DAY_OF_MONTH)+
                " "+d2.get(GregorianCalendar.HOUR_OF_DAY)+":"+d2.get(GregorianCalendar.MINUTE)+":"+
                d2.get(GregorianCalendar.SECOND));
         
        time = stop-start;
        
        try {
            Thread.sleep(time); 
        } catch (InterruptedException ex) {
            Logger.getLogger(ChallengeThread.class.getName()).log(Level.SEVERE, null, ex);
        }       
        
        
    
        //verificar numero de inscritos
            //enviar Erro aos jogadores?
        System.out.println("Estou pronto a  começar o Desafio!!");
        if(this.challenge.isReadyToStart()){ //desafio deixa de estar disponivel a partir daqui            
            System.out.println("Vou começa o Desafio!!");
            
        //para cada questao, contruir PDU e enviar ao jogadores
            //esperar que todos respondam a primeira?
            
        //for(Question quest:this.questions.values()){
        for(Integer nQuest:this.questions.keySet()){   
            Question quest = this.questions.get(nQuest);
            //nQuest++;            
            try {                                               
                //já recebe o ficheiros divididos em byte[][]
                byte[][] image = quest.getByteImage();
                byte[][] song = quest.getByteSong(); 
                
                //aqui estao os PDU's desta Questao
                Map<Integer,PDU> auxRespostas = new HashMap<Integer,PDU>();                                 
                
                int total=0;
                for(int h=0;h<song.length;h++)
                    total += song[h].length;
                
                
                //total de PDUs para enviar uma questaao
                //existem pelo menos 3 PDUs, cada uma com 1 Opcao da Questao
                nPDUs=3;
                if ( (image.length+song.length) > nPDUs)
                    nPDUs = image.length+song.length;                            
                
                System.out.println("vou enviar "+nPDUs);
                
                
                int iImage=0, iSong=0, iPDU=0;
                
                while(iPDU < nPDUs){
                    //inicializa iPDU e a sua info BASE                                        
                    PDU newPDU = new PDU(0);
                    newPDU.creatReply();
                    newPDU.setDesafio(this.challenge.getName());
                    newPDU.setBloco(iPDU);
                    newPDU.setNumQuestao(nQuest);
                    
                    if(iPDU==0){
                       //apenas o primeiro necessita de levar esta info                       
                       newPDU.setQuestao(quest.getQuestion());
                       
                       newPDU.setNumResposta(1);
                       newPDU.setResposta(quest.getOptionA());
                    }
                    
                    if(iPDU==1){
                       newPDU.setNumResposta(2);
                       newPDU.setResposta(quest.getOptionB());
                    }
                    
                    if(iPDU==2){
                       newPDU.setNumResposta(3);
                       newPDU.setResposta(quest.getOptionC());
                    }                                       
                    
                    //se ainda tem bytes de imagem ou som
                    if(iImage < image.length){
                        //copias bytes
                        newPDU.setImagem(image[iImage]);
                        iImage++;
                    }                    
                    else if(iSong < song.length){
                        //copias bytes
                        newPDU.setAudio(song[iSong]);
                        iSong++;
                    }
                                         
                    if( (iPDU+1) < nPDUs ){
                        //então há um PDU depois deste
                        newPDU.willBeNextPDU();
                    }  
                    auxRespostas.put(iPDU, newPDU);                    
                    iPDU++; 
                }
                
                this.respostas.put(nQuest, auxRespostas);
                
                for(PDU res : this.respostas.get(nQuest).values()){                                    
                    for (Iterator it = this.challenge.getPlayers().iterator(); it.hasNext();) {
                        String user = (String) it.next();                    
                        this.manager.getThreads().get(user).sendReeply(res);                        
                    }
                }
                
            } catch (IOException ex) {
                Logger.getLogger(ChallengeThread.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
        this.sendRankingToServers();
        }    
    }               
}
