package client;

import client.gui.Home;
import client.gui.Profile;
import client.gui.Quiz;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import middleware.PDU;
import middleware.Utils;

/**
 *
 * @author carlosmorais
 */
public class Connection {

    public static final int MAXSIZE = 65507;
    String serverHostName;
    int helloport;
    int serverPort;
    int labelCount;
    String currentUser;
    Map<String, GameThread> games; //Threds com os jogos

    /*ultimo PDU recebido (quando a connection recebe um PDU, é guardado aqui sendo 
     enviado um sinal para as Threds que estao á espera de um PDU)*/
    PDU lastPDU;

    public final Lock lock;
    private Condition newPDU;
    DatagramSocket clientSocket;
    InetAddress IPAddress;

    Quiz quiz;

    public Connection() throws SocketException, UnknownHostException {
        this.lock = new ReentrantLock();
        this.newPDU = lock.newCondition();
        this.serverHostName = "localhost";
        this.helloport = 9877;
        this.serverPort = 9877;
        this.labelCount = 1;
        this.clientSocket = new DatagramSocket();
        this.IPAddress = InetAddress.getByName(this.serverHostName);
        this.games = new HashMap<String, GameThread>(); //<nomeDesafio, GameThread>         
    }

    public Condition getConditionNewPDU() {
        return newPDU;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public Map<String, GameThread> getGames() {
        return games;
    }

    public PDU getSendAndRecive(PDU msg) {
        PDU resposta = null;
        this.lock.lock();
        long beginTime = System.currentTimeMillis();
        long endTime = beginTime + 60000;
        try {
            try {
                this.send(msg);
                do {
                    this.getConditionNewPDU().await();
                    long timer = System.currentTimeMillis();
                    if (timer > endTime) {
                        break;
                    }
                } while (msg.getLabel() != this.lastPDU.getLabel());
                resposta = this.lastPDU;
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            this.lock.unlock();
        }
        return resposta;
    }
    
    public ArrayList<PDU> getSendAndListReceive(PDU msg) {
        ArrayList<PDU> resposta = new ArrayList<PDU>();
        this.lock.lock();
        long beginTime = System.currentTimeMillis();
        long endTime = beginTime + 60000;
        boolean flag=true;
        
        try {
            try {
                this.send(msg);
                do {
                    this.getConditionNewPDU().await();
                    long timer = System.currentTimeMillis();
                    if (timer > endTime) {
                        break;
                    }
                    if(msg.getLabel() == this.lastPDU.getLabel()){
                        resposta.add(this.lastPDU);
                        if(!msg.hasNextPDU()) flag=false;
                    }
                } while (/*(msg.getLabel() == this.lastPDU.getLabel()) &&*/ flag);
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            this.lock.unlock();
        }
        return resposta;
    }

    public void addPDUgame(PDU pdu) {
        if (this.games.containsKey(pdu.getDesafio())) {
            this.games.get(pdu.getDesafio()).addPDU(pdu);
        } else {
            GameThread gt = new GameThread(this, pdu.getDesafio());
            gt.start();
            gt.addPDU(pdu);
            this.games.put(pdu.getDesafio(), gt);

            this.quiz = new Quiz();
            Profile.desktop.add(quiz);
            
            this.quiz.setClosable(true);
            try {
                this.quiz.setMaximum(true); // Maximizar a frame interior  
            } catch (PropertyVetoException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }

            quiz.setConnection(this);
            quiz.setChallenge(pdu.getDesafio());
            quiz.setGameThread(gt);
            new DisplayGame(quiz).start();
        }
    }

    public void send(PDU msg) throws IOException {
        byte[] packet = Utils.toBytes(msg);
        DatagramPacket sendPacket = new DatagramPacket(packet, packet.length, this.IPAddress, this.serverPort);
        this.clientSocket.send(sendPacket);
        this.labelCount++;
    }

    public void sendFirst(PDU msg) throws IOException {
        byte[] packet = Utils.toBytes(msg);
        DatagramPacket sendPacket = new DatagramPacket(packet, packet.length, this.IPAddress, this.helloport);
        this.clientSocket.send(sendPacket);
        this.labelCount++;
    }

    public PDU receive() throws IOException {
        byte[] receive = new byte[MAXSIZE];
        DatagramPacket receivePacket = new DatagramPacket(receive, receive.length);
        clientSocket.receive(receivePacket);
        return ((PDU) Utils.fromBytes(receive));

    }

    public PDU receiveFirst() throws IOException {
        byte[] receive = new byte[MAXSIZE];
        DatagramPacket receivePacket = new DatagramPacket(receive, receive.length);
        clientSocket.receive(receivePacket);

        this.IPAddress = receivePacket.getAddress();
        this.serverPort = receivePacket.getPort();

        return ((PDU) Utils.fromBytes(receive));
    }

    public PDU getLastPDU() {
        return lastPDU;
    }

    public void setLastPDU(PDU newPDU) {
        lock.lock();
        try {
            this.lastPDU = newPDU;
            this.newPDU.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public boolean initCommunication() throws IOException {
        int label = this.labelCount;

        PDU msg = new PDU(label);
        msg.creatHello();
        this.sendFirst(msg);

        msg = this.receiveFirst();

        if ((msg.getTipo() == 1) && (msg.getOK())) {
            return true;
        }
        return false;
    }

    public int getLabelCount() {
        return this.labelCount;
    }

}
