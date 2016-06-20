/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
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
public class ReaderThread extends Thread {
    
    private HashMap<InetAddress,Integer> enderecos;
    public ReentrantLock l;
    private Socket s;
    private Manager manager;
    
    public ReaderThread(Socket s,HashMap<InetAddress,Integer> enderecos,
                       ReentrantLock l, Manager m){
        this.s = s;
        this.l = l;
        this.enderecos = enderecos;
    }
    public void run(){
        while(!s.isInputShutdown()){
            BufferedReader i = null;
            try {
                int cont = 0, ordem = 0;
                Integer n = 0;
                HashMap<Integer,byte[]> byteMap = new HashMap<Integer,byte[]>();
                i = new BufferedReader(new InputStreamReader(s.getInputStream()));
                n = new Integer(i.readLine());
                DataInputStream di =new DataInputStream(s.getInputStream());
                while (cont < n){
                    byte [] temp = new byte[65535];
                    cont+=di.read(temp);
                    byteMap.put(new Integer(ordem), temp);
                    ordem++;
                }
               doAction(gluePDU(byteMap));
                
            } catch (IOException ex) {
                Logger.getLogger(ReaderThread.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    i.close();
                } catch (IOException ex) {
                    Logger.getLogger(ReaderThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void addServer(InetAddress novo, Integer port){
        
    }
    
    public PDU gluePDU(HashMap<Integer,byte[]> byteMap){
        ArrayList<Byte> temp = new ArrayList<Byte>();
        for(Integer i : byteMap.keySet()){
            for(int j = 0; j < byteMap.get(i).length; j++){
                temp.add(new Byte(byteMap.get(i)[j]));
            }
        }
        byte [] ret = new byte[temp.size()];
        for(int j = 0; j < temp.size(); j++){
            ret[j] = temp.get(j);
        }
        return (PDU)Utils.fromBytes(ret);
    }
    
    public void doAction(PDU pdu){
        if(pdu.getIPServer()!=null){
            
            l.lock();
            try{
                InetAddress ip = InetAddress.getByName(pdu.getIPServer());
                String port = pdu.getPort();
                this.enderecos.put(ip, new Integer(port));
                
            }
            catch (UnknownHostException ex) {
                Logger.getLogger(ReaderThread.class.getName()).log(Level.SEVERE, null, ex);
            }           
            finally{
                l.unlock();
            }
        }
    }
    
}
