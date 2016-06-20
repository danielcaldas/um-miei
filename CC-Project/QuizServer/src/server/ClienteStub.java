package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.GregorianCalendar;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import middleware.PDU;
import middleware.Utils;
import middleware.User; 

/**
 * Thread responsável pela comunicação com o Cliente
 * @author xavier
 */

public class ClienteStub extends Thread{
    public static final int MAXSIZE = 1024;
    
    private DatagramPacket receivePacket;
    private boolean ok = true;
    private InetAddress IPAdress;
    private int MyPort;
    private int ClPort;
    private Manager manager;
    private DatagramSocket serverSocket;    
    private String currentUser; //para saber qual o Id do Cliente a ser atendido
    
    private Lock lock;
    
    public ClienteStub(DatagramPacket r, int port, Manager manager){
        this.receivePacket = r;
        this.MyPort = port;
        this.manager = manager;          
        this.currentUser = null;
        this.lock = new ReentrantLock();
    }
    
    /**
     * run() é executado sempre que é lançada nova thread ou enviado pacote ao utilizador a ser tratado na mesma.
     */
    @Override
    public void run(){
        PDU pdu;
        
        try {
            IPAdress = receivePacket.getAddress();
            ClPort = receivePacket.getPort();
            byte receiveData[] = new byte[MAXSIZE];            
            serverSocket = new DatagramSocket(MyPort);                          
                        
            while(true){  
                
                this.receivePacket = new DatagramPacket(receiveData,receiveData.length);
                System.out.println("waiting for packet");
                try {
                    serverSocket.receive(receivePacket);
                } catch (IOException ex) {
                    Logger.getLogger(ClienteStub.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("ja recebi...");
                
                receiveData = receivePacket.getData();
                pdu = (PDU)Utils.fromBytes(receiveData);
                
                
                //os metodos "do" são responsaveis por tratar os pedidos do Cliente
                switch (pdu.getTipo()){
                    //cada mensagem tem um método para a tratar
                    case 1:  //01 HELLO                             
                        //faz sentido receber um HELLO aqui?
                        this.doReplyOK(pdu.getLabel());
                        break;
                    case 2:  //02 REGISTER               
                        this.doRegister(pdu);
                        break;
                    case 3:  //03 LOGIN
                        this.doLogin(pdu);
                        break;
                    case 4:  //04 LOGOUT
                        this.doLogout(pdu);
                        break;
                    case 5: //05 QUIT
                        this.doQuit(pdu);
                        break;
                    case 6: //06 END
                        this.doEnd(pdu);
                        break;
                    case 7: //05 LIST_CHALLENGE
                        this.doListChallenges(pdu);
                        break;
                    case 8:  //08 MAKE_CHALLENGE
                        this.doMakeChallenge(pdu);
                        break;
                    case 9: //09 ACCEPT_CHALLENGE
                        this.doAcceptChallenge(pdu);
                        break;
                    case 10: //10 DELELTE_CHALLENGE 
                        //apaga um desafio (apenas o criador)
                        this.doDeleteChallenge(pdu);
                        break;
                    case 11: //11 ANSWER
                        this.doAnswer(pdu);
                        break;
                    case 12: //12 RETRANSMIT 
                        this.doRetransmit(pdu);
                        break;
                    case 13: //13 LIST_RANKING
                        this.doListRanking(pdu);
                        break;
                    default:
                        break;
                        /*enviar uma msg de erro?*/
                }                                
            }
        } catch (SocketException ex) {
            Logger.getLogger(ClienteStub.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClienteStub.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Método que regista um novo utilizador na base de dados.
     * @param pdu, PDU do tipo REGISTER
     * @throws SocketException
     * @throws IOException 
     */
    public void doRegister(PDU pdu) throws SocketException, IOException{        
        PDU resposta = new PDU(pdu.getLabel());
        pdu.creatReply();        
        
        User newUser = new User(pdu.getNome(), pdu.getAlcunha(), pdu.getSecInfo());
        
        try {
            this.manager.addUser(newUser);
            resposta.replyOK();            
        } catch (UserException ex) {
            resposta.replyErro(ex.getMessage());
        }
        
        this.sendReeply(resposta);
    }
    
    /**
     * Método que autentica utilizador.
     * @param pdu, PDU do tipo LOGIN.
     * @throws SocketException
     * @throws IOException 
     */
    public void doLogin(PDU pdu) throws SocketException, IOException{                
        PDU resposta = new PDU(pdu.getLabel());
        resposta.creatReply();                
        
        try {
            this.manager.loginUser(pdu.getAlcunha(), pdu.getSecInfo());
            this.currentUser = pdu.getAlcunha();
            //guarda a Thread que esta a comunicar com este clinte
            this.manager.addUserThread(this.currentUser, (ClienteStub) Thread.currentThread());
            resposta.replyOK();
            resposta.setNome(this.manager.getUsers().get(pdu.getAlcunha()).getNome());
        } catch (UserException ex) {
            resposta.replyErro(ex.getMessage());
        }        
        
        this.sendReeply(resposta);
    }
    
    /**
     * Método que termina sessão de um dado utilizador.
     * @param pdu, PDU do tipo LOGOUT
     * @throws IOException 
     */
    public void doLogout(PDU pdu) throws IOException{ 
        // Primeiro vamos salvaguardar as alterações deste utilizador
        this.manager.saveManager();
        
        PDU resposta = new PDU(pdu.getLabel());
        resposta.creatReply();

        //logout (é supoto o user existir sempre)->sem exceção
        this.manager.logoutUser(this.currentUser);
        
        resposta.replyOK();
        this.sendReeply(resposta);        
    }
    
    /**
     * Método que permite que um utilizador gere desafios.
     * @param pdu, PDU do tipo MAKECHALLENGE.
     * @throws IOException 
     */
    public void doMakeChallenge(PDU pdu) throws IOException{
        PDU resposta = new PDU(pdu.getLabel());
        resposta.creatReply();
        
        GregorianCalendar data = new GregorianCalendar();                        
        data.set(pdu.getDataAno(), pdu.getDataMes(), pdu.getDataDia(), pdu.getDataHora(), pdu.getDataMinuto(), pdu.getDataSegundo());
        
        InetAddress IPAdressChallenge = receivePacket.getAddress();
        int ClPortChallenge = receivePacket.getPort();
        
        try {            
            this.manager.addNewChallenge(this.currentUser, pdu.getDesafio(), data);
            //adiciona aqui o user, não adiona no método, pois este é utilizado pelo outros servers...
            this.manager.addUserToChallenge(currentUser, pdu.getDesafio());
            resposta.replyOK();
            resposta.setData(pdu.getDataAno(), pdu.getDataMes(), pdu.getDataDia());
            resposta.setHora(pdu.getDataHora(), pdu.getDataMinuto(), pdu.getDataSegundo());
            resposta.setDesafio(pdu.getDesafio());
        } catch (UserException ex) {            
            resposta.replyErro(ex.getMessage());
        }        
        this.sendReeply(resposta); 
    }  
    
    /**
     * Método que permite que um utilizador aceitar um desafio.
     * @param pdu, PDU do tipo AcceptChallenge.
     * @throws IOException 
     */
    public void doAcceptChallenge(PDU pdu) throws IOException{
        PDU resposta = new PDU(pdu.getLabel());
        resposta.creatReply();
                                       
        try {            
            this.manager.addUserToChallenge(currentUser, pdu.getDesafio());
            resposta.replyOK();            
        } catch (UserException ex) {            
            resposta.replyErro(ex.getMessage());
        }        
        this.sendReeply(resposta);
    }
    
     /**
     * Método que permite que um utilizador cancelar um desafio.
     * @param pdu, PDU do tipo DeleteChallenge.
     * @throws IOException 
     */
    public void doDeleteChallenge(PDU pdu) throws IOException{
        PDU resposta = new PDU(pdu.getLabel());
        resposta.creatReply();        
        this.manager.deleteChallenge(currentUser, pdu.getDesafio());        
        resposta.replyOK();
        this.sendReeply(resposta);        
    }
    
    
    public void doAnswer(PDU pdu) throws IOException{
        PDU resposta = new PDU(pdu.getLabel());
        resposta.creatReply();
        boolean res = this.manager.checkUserAnswer(this.currentUser, pdu.getDesafio(), pdu.getNumQuestao(), pdu.getEscolha());
        resposta.replyOK();
        resposta.setCerta(res);
        this.sendReeply(resposta); 
    }
    
    public void doRetransmit(PDU pdu) throws IOException{
        //não envia a respostas aqui-> retransmit do ChallengeThread...
        this.manager.retransmit(pdu.getDesafio(), this.currentUser, pdu.getNumQuestao(), pdu.getBloco());
    }
    
    public void doListChallenges(PDU pdu) throws IOException{
        this.manager.listChallenges(this.currentUser, pdu.getLabel());
    }
    
    public void doEnd(PDU pdu) throws IOException{
        this.manager.endChallenge(this.currentUser, pdu.getDesafio(), pdu.getLabel());
    }
    
    public void doListRanking(PDU pdu) throws IOException{
        this.manager.listRanking(currentUser, pdu.getLabel());
    }
    
    public void doQuit(PDU pdu){
        this.manager.userQuitChallenge(this.currentUser, pdu.getDesafio(), pdu.getLabel());
    }
    
    /**
     * Envia resposta OK para o cliente.
     * @param label, inteiro que associa resposta a um pedido.
     * @throws IOException 
     */
    public void doReplyOK(int label) throws IOException{
        PDU resposta = new PDU(label);
        resposta.creatReply();
        resposta.replyOK();                
        this.sendReeply(resposta);
    }
    
    /**
     * Método que permite enviar resposta ao cliente através de um socket UDP.
     * @param resposta, PDU com a resposta para o cliente.
     * @throws IOException 
     */
    //substituir por LOCK
    public  void sendReeply(PDU resposta) throws IOException{
        this.lock.lock();        
        try {
            byte[] sendData = Utils.toBytes(resposta);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,IPAdress,ClPort);
            serverSocket.send(sendPacket); 
            /*
            NOTA: PROBLEMA
            ->os locks não não suficientes para evitar o problema no envio
            ->com um peq sleep parece resolver o problema
            ->tentar perceber o pq do problema e se possivel resolver de outra maneira
            */
            //Relatorio -> (PSEUDO) CONTROLO DE FLUXO
            Thread.sleep(120);
        } catch (InterruptedException ex) {
            Logger.getLogger(ClienteStub.class.getName()).log(Level.SEVERE, null, ex);
        } finally { this.lock.unlock(); }
    }    
}
