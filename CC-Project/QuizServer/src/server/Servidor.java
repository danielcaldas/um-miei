package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import middleware.PDU;
import middleware.Utils;

/**
 * Classe servidor que fica à escuta de pacotes enviados por utilizador e reencaminha cada utilizador para um thread
 * ClientStub para ser tratado.
 * @author xavier
 * @version 2014.04.12
 */

public class Servidor {
    // Variáveis de classe
    private static Manager manager;
        
    public static void main(String args[]) throws IOException, ClassNotFoundException{
        int port = 9877;
        int count = 0;
       
        //manager = Manager.loadManager();
        //if(manager==null) 
        manager = new Manager();        
        
        try{
            DatagramSocket serverSocket = new DatagramSocket(port);
            /*FICA A RECEBER NOVOS CLIENTES*/
            while(true){
                count++;
                //em principio são mais que suficientes 1024 bytes
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
                serverSocket.receive(receivePacket);
                
                //verica que recebe o PDU HELLO
                byte[] dados = receivePacket.getData();
                PDU msg = (PDU)Utils.fromBytes(dados);

                if(msg.getTipo() == 1){
                    // Lançar thread para atender cliente na qual são passados os parâmetros:
                    // - receivePacket: Pacote de dados recebido (primeiro pacote enviado pelo respetivo cliente)
                    // - port+count: Porta na qual esse cliente será atendido
                    // - manager: Objeto partilhado do sistema que contém informação que pode vir a ser necessária
                    ClienteStub c = new ClienteStub(receivePacket, port+count, manager);
                    
                    
                    System.out.println("recebeu um cliente");                                    
                    c.start();
                    c.doReplyOK(msg.getLabel());                    
                }
                else System.out.println("erro: não reconhece Hello!"); //enviar um erro ao Cliente
                
            }
        }catch(Exception e){}
    }
    
}
