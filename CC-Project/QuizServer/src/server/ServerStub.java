/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import middleware.PDU;
import middleware.Utils;

/**
 *
 * @author xavier
 */
public class ServerStub extends Thread {
    
    private Socket s;

    private Manager manager;
    private HashMap<InetAddress,Integer> enderecos;
    public ReentrantLock l;
    
    public ServerStub(Socket s, Manager manager,HashMap<InetAddress,Integer> enderecos,
                       ReentrantLock l ){
        this.s = s;
        this.manager = manager;
        this.enderecos = enderecos;
        this.l = l;
    }
    
    public void run(){
        
    }
    
    public void enviaData(PDU msg){
        for(InetAddress adr : enderecos.keySet()){
            try {
                Socket temp = new Socket(adr.getHostAddress(),enderecos.get(adr));
                byte [] send = Utils.toBytes(msg);
                //enviar o tamanho
                PrintWriter o = new PrintWriter(s.getOutputStream());
                o.write(send.length);
                o.flush();
                //enviar a mensagem
                OutputStream out = s.getOutputStream();
                DataOutputStream dos = new DataOutputStream(out);
                dos.write(send);
                dos.flush();   
            } catch (IOException ex) {
                Logger.getLogger(ServerStub.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    

    
    public void setSocket(Socket s){
        this.s = s;
    }
    
    

}
