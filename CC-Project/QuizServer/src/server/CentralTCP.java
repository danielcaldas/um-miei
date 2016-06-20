/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author xavier
 */
public class CentralTCP extends Thread{
    ServerSocket  ss;
    private HashMap<InetAddress,Integer> enderecos;
    public ReentrantLock l;
    private Manager m;
    
    public CentralTCP(HashMap<InetAddress,Integer> enderecos,
                       ReentrantLock l,Manager m){
        this.enderecos = enderecos;
        this.l = l;
        this.m = m;
    }
    
    public void run(){
        boolean b = true;
        try {
            ss = new ServerSocket(12345);
            while(b){
                Socket s = ss.accept();
                ReaderThread r = new ReaderThread(s,this.enderecos,this.l,m);
                r.start();
            }
            ss.close();
            
        } catch (IOException ex) {
            Logger.getLogger(CentralTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
