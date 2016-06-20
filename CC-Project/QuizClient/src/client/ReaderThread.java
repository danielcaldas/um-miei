/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import static client.Connection.MAXSIZE;
import java.io.IOException;
import java.net.DatagramPacket;
import java.util.logging.Level;
import java.util.logging.Logger;
import middleware.PDU;
import middleware.Utils;

/**
 *
 * @author carlosmorais
 */
public class ReaderThread extends Thread{
    
    Connection con;
    
    public ReaderThread(Connection con) {
        this.con = con;
    }

    @Override
    public void run() {
        
        while(true){
            PDU newPDU;
            try {
                newPDU = this.con.receive();
                
                if(newPDU.isQuestao()){                    
                    this.con.addPDUgame(newPDU);
                    //System.out.println("Recebi um PDU_GAME");
                }
                else
                    this.con.setLastPDU(newPDU);
            } catch (IOException ex) {
                Logger.getLogger(ReaderThread.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }        
    }     
}
