
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import middleware.PDU;

/**
 *
 * @author carlosmorais
 */
public class GameThread extends Thread{
    Map<Integer, Map<Integer,PDU>> pdus; //<#Questao <#Bloco, PDU> >
    
    private int currentQuestion; //questao atual
    
    public final Lock lock;
    private Condition newPDU;
    private Connection connection;
    private String challenge;
    

    public GameThread(Connection con, String challenge) {
        this.pdus = new HashMap<Integer, Map<Integer,PDU>>();
        this.currentQuestion = 1;
        this.lock = new ReentrantLock();
        this.newPDU = lock.newCondition();                 
        this.connection = con;
        this.challenge = challenge;
    }    
    
        
    public void addPDU(PDU pdu){
        //System.out.print("ADD PDU GAME");
        lock.lock();
        try{
            if(this.pdus.containsKey(pdu.getNumQuestao()))
                this.pdus.get(pdu.getNumQuestao()).put(pdu.getBloco(), pdu);
            else{            
                Map<Integer,PDU> newMap = new HashMap<Integer,PDU>();
                newMap.put(pdu.getBloco(), pdu);
                this.pdus.put(pdu.getNumQuestao(), newMap);                
            }            
            this.newPDU.signalAll();
        } finally{lock.unlock();}  
        //System.out.println("- FIMADD PDU GAME");
    }
    
    public void retransmitPDU(){
        boolean flag = true;
        int actualBloco=0;
        
        Map<Integer,PDU> pdus = this.pdus.get(this.currentQuestion);
        
        Iterator it = this.pdus.get(this.currentQuestion).keySet().iterator();
        
        while(flag && it.hasNext()){
            int bloco = (int)it.next();
            if(actualBloco!=bloco)
                flag = false;
            else
                actualBloco++;
        }
        
        PDU res = new PDU(this.connection.getLabelCount());
        res.creatRetransmit();
        res.setDesafio(this.challenge);
        res.setNumQuestao(this.currentQuestion);
        res.setBloco(actualBloco);
        
        System.out.println("Retransmit Questao="+this.currentQuestion+" Bloco="+actualBloco);
        
        try {
            this.connection.send(res);
        } catch (IOException ex) {
            Logger.getLogger(GameThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public Question getNextQuestion() throws IOException, InterruptedException{
        Question res=new Question();
        int tam, tamSong=0, tamImg=0, iSong=0, iImg=0;;        
        byte[][] song;
        byte[][] img;        
        Map<Integer,PDU> pdusAux=new HashMap<>();
        
        long initTime = ((GregorianCalendar) GregorianCalendar.getInstance()).getTimeInMillis();
                  
        this.lock.lock();
        try{
            tam = this.pdus.get(this.currentQuestion).size();
            while( (!this.pdus.get(this.currentQuestion).containsKey(tam-1)) || 
                            ((this.pdus.get(this.currentQuestion).containsKey(tam-1)) && 
                            (this.pdus.get(this.currentQuestion).get(tam-1).hasNextPDU())) ){
                //System.out.println("tenho o tam = "+tam+" vou dormir");
                this.newPDU.awaitNanos(4500);
                tam = this.pdus.get(this.currentQuestion).size();
                
                if( (initTime+3500) < (((GregorianCalendar) GregorianCalendar.getInstance()).getTimeInMillis())){
                    this.retransmitPDU();
                    initTime += 1500;
                }                
            }
            pdusAux = this.pdus.get(this.currentQuestion);
        }
        catch (InterruptedException ex) {
            Logger.getLogger(GameThread.class.getName()).log(Level.SEVERE, null, ex);
        }        finally{
            this.lock.unlock();
            //System.out.println("Acordei e vou embora");
        }            
        
        for(PDU auxPDU:pdusAux.values()){
            if(auxPDU.hasAudio())
                tamSong ++;
            if(auxPDU.hasImagem())
                tamImg ++;
        }   
        
        song = new byte[tamSong][];
        img = new byte[tamImg][];
        
        for(PDU auxPDU:pdusAux.values()){
            //vars k & j vão saber a posicao para a imagem e som            
            if(auxPDU.hasAudio()){
                song[iSong] = new byte[auxPDU.getLenghtAudio()];
                song[iSong] = auxPDU.getAudio();                
                iSong++;
            }
            if(auxPDU.hasImagem()){
                img[iImg] = new byte[auxPDU.getLenghtImagem()];
                img[iImg] = auxPDU.getImagem(); 
                iImg++;
            }
            
        }
        
        System.out.println("Já tenho a Questão!");
        /*os nomes dos ficheiro são definidos aqui, podem ser alterados, em alternativa o 
        servidor pode enviar os nomes dos mesmos no PDU(0), mas não há necessidade...*/
        res = new Question(pdusAux.get(0).getQuestao(), pdusAux.get(0).getResposta(), pdusAux.get(1).getResposta(), pdusAux.get(2).getResposta(),song,"song"+pdusAux.get(0).getNumQuestao()+".mp3",img,"img"+pdusAux.get(0).getNumQuestao()+".jpg");        
        
        this.currentQuestion++;        
        return res;                              
    }

    @Override
    public void run() {
         //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
}

