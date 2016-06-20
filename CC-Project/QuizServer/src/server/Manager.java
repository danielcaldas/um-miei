package server;

import middleware.Question;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import middleware.User;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import middleware.PDU;
import middleware.UserGame;

/**
 * Objeto partilhado que agrupa dados partilhados pelo servidor
 * e pelos vários stubs de atendimento a clientes (entendam-se Threads).
 * @author carlosmorais, jdc
 * @version 2015.04.18
 */

public class Manager {
    
    // Variáveis de instância
    private int idChallengeGenerator;    
    private Map<String,User> users;
    private Map<String,Challenge> challenges;        
    //para saber qual a Thread que esta a comunicar com o Cliente
    private Map<String, ClienteStub> threads;     
    private List<Question> questions;//questoes           
    private Lock lock; // Lock para organizar acessos ao objeto partilhado

    // Construtor
    public Manager() {
        this.idChallengeGenerator = 1000;
        this.users = new HashMap<>();
        this.challenges = new HashMap<>();
        this.threads = new HashMap<String, ClienteStub>();
        this.questions = new ArrayList<Question>();
        this.loadQuestions(); // carrega as questoes para memoria
        this.lock = new ReentrantLock();
        
        //Utilizadores registados para teste
        User us1 = new User("jcm","jcm","123");
        this.users.put(us1.getUsername(), us1);
        User us2 = new User("rc","rc","12345");
        this.users.put(us2.getUsername(), us2);
        User us3 = new User("carlos","jjm","12345");
        this.users.put(us3.getUsername(), us3);
        
    }
    
    
    //load das Questions => alterar para o parser a um fifeiro...
    public void loadQuestions(){        
        /*
        ->Aqui temos de ler de um ficheiro questoes.txt
        ->Todas as questoes são carregadas e depois sorteadas paras os desafios 
        */                     
        try {
            FileReader file = new FileReader("questions.txt");
            BufferedReader reader = new BufferedReader(file);
            String linha;
            String[] tokens;
            
            while((linha=reader.readLine())!=null){
                tokens = linha.split(";");
                Question q = new Question(tokens[0],tokens[1],tokens[2].replace("\"", ""),tokens[3].replace("\"", ""),tokens[4].replace("\"", ""),tokens[5].replace("\"", ""),Integer.parseInt(tokens[6]));                
                this.questions.add(q);
            }                        
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
        }                                
        /*
        Question q = new Question("0000005.mp3","000001.jpg","Que outra arte é referida no poema desta canção?","Pintura","Escultura","Literatura",1);
        res.add(q);
        q = new Question("0000006.mp3","000001.jpg","Que outra arte é referida no poema desta canção?","Pintura","Escultura","Literatura",2);
        //res.add(q);
        q = new Question("0000007.mp3","000001.jpg","Que outra arte é referida no poema desta canção?","Pintura","Escultura","Literatura",3);
        //res.add(q);
        q = new Question("0000008.mp3","000001.jpg","Que outra arte é referida no poema desta canção?","Pintura","Escultura","Literatura",2);
        //res.add(q);
     
        return res;*/
    }
    
    public Map<Integer, Question> generateQuestions(){
        Map<Integer, Question> res = new HashMap<Integer, Question>();
        List<Integer> rand = new ArrayList<Integer>();
        Random randomGenerator = new Random();
        int n = this.questions.size();
        int i=0, nQuest=1;
        
        while(i<10){                    
            int index = 0 + randomGenerator.nextInt(this.questions.size());
            if (!rand.contains(index)){
                rand.add(index);
                i++;
            }
        }
        Collections.sort(rand);
        
        for(Integer p:rand){        
            System.out.println("Numero aleatorio: "+p);
            res.put(nQuest, this.questions.get(p));
            nQuest++;
        }
        
        return res;
    }

    /**
     * Método que permite consulta da lista de utilizadores registados (devolve cópia).
     * @return Map<String,User>, mapa de utilizadores registados
     */
    public Map<String,User> getUsers() {
        this.lock();
        try{
            Map<String,User> aux = new HashMap<String,User>();
            for(User user : this.users.values()){
                aux.put(user.getUsername(), user.clone());
            }
            return aux;
        } finally { this.unlock(); }
    }

    public Map<String, ClienteStub> getThreads() {
        return this.threads;
    }    
    
    
    /**
     * Adicionar à base de dados um novo utilizador.
     * @param user, novo utilizador a registar no sistema.
     * @throws UserException 
     */
    public void addUser(User user) throws UserException{
        this.lock();
        try{
            if(!users.containsKey(user.getUsername())){            
                users.put(user.getUsername(), user);                                                                        
            }
            else{
                throw new UserException(Messages.NICK_ALREADY_EXISTS);             
            }
        } finally { this.unlock(); }
    }
    
    /**
     * Método que permite autenticar um utilizador ao nível do sistema.
     * @param userId, username.
     * @param userSecInf, password do utilizador.
     * @throws UserException 
     */
    public void loginUser(String userId, String userSecInf) throws UserException {
        this.lock();
        try{
            User user=null; 
            if( this.users.containsKey(userId) )
                user = this.users.get(userId);
            else
                throw new UserException(Messages.NO_USER); 

            if(user.getPassword().equals(userSecInf)){                
                if(!user.login())                  
                    throw new UserException(Messages.ALREADY_LOGGED);
            }
            else throw new UserException(Messages.WRONG_PASSWORD);
        } finally { this.unlock(); }
    }
    
    public void logoutUser(String user){       
        this.users.get(user).logout();
    }
    
    /**
     * Método que nos diz se um dado utilizador se encontra a meio de um jogo.
     * @param id username do utilizador.
     * @return true caso o utilizador esteja num jogo, false caso contrário.
     */
    public boolean isUserPlaying(String id){
        Iterator it = this.challenges.values().iterator(); 
        boolean keepOn = true;
        Challenge challenge=null;
        
        while(it.hasNext() && keepOn){
            challenge = (Challenge)it.next();
            if( (challenge.isRunning()) && (challenge.containsIdPlayer(id)) )
                keepOn = false;
        }
        
        return !keepOn;
    }
    
    
    public void sendNewChallengeToServers(ChallengeThread ct){
        PDU msg = new PDU(0);
        msg.creatInfo();
        
        msg.setAlcunha(ct.getChallenge().getUserCreator());
        msg.addGregorianCalendar(ct.getChallenge().getDate());
        msg.setDesafio(ct.getChallenge().getName());
        
        Map<Integer, Question> questions = ct.getQuestions();
        List<String> questoes = new ArrayList<String>(); 
        List<String> respostas = new ArrayList<String>(); 
        List<Integer> certas = new ArrayList<Integer>(); 
        List<byte[]> imagens = new ArrayList<byte[]>(); 
        List<byte[]> musicas = new ArrayList<byte[]>(); 
        
        for(Question q : questions.values()){
            questoes.add(q.getQuestion());
            respostas.add(q.getOptionA());
            respostas.add(q.getOptionB());
            respostas.add(q.getOptionC());
            certas.add(q.getCorrectAnswer());
            try {
                imagens.add(q.getAllByteImage());
                musicas.add(q.getAllByteSong());
            } catch (IOException ex) {
                Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        //enviar para ServerSub
        
            
    }
    
    
    
    public void addNewChallenge(String user, String desafio , GregorianCalendar date) throws UserException{
        Challenge newChallenge = new Challenge(user, desafio, date);
        
        if( !this.challenges.containsKey(desafio)){
            this.challenges.put(newChallenge.getName(), newChallenge);
            ChallengeThread ch = new ChallengeThread( newChallenge ,this);
            this.challenges.get(newChallenge.getName()).addChallengeThread(ch);
            
            this.sendNewChallengeToServers(ch);
            ch.start();
        }
        else
            throw new UserException(Messages.CHALLENGE_ALREADY_EXIST);
                            
    }
    
    public void addUserToChallenge(String user, String challenge) throws UserException{                        
        if( (! this.isUserPlaying(user))){
            if(this.challenges.get(challenge).isAvailable())
                this.challenges.get(challenge).addPlayer(user);
            else
                throw new UserException(Messages.CHALLENGE_UNAVAILABLE);
        }
        else
            throw new UserException(Messages.USER_ALREADY_PAYING);
    }
    
    
    public void deleteChallenge(String user, String challenge){
        Challenge c = this.challenges.get(challenge);
        
        //não é necessario verificar e lançar exceção, se o pedido chega é pq o user é o criador
        if( c.getUserCreator().equals(user) ){
            this.challenges.get(challenge).noRunning();//para a Thread não correr
            this.challenges.remove(challenge);            
        }        
        //enviar mensagem aos jogadores inscritos???                
    }
    
    public boolean checkUserAnswer(String user, String challenge, int question, int option){
        if (this.challenges.get(challenge).checkUserAnswer(user, question, option))
            return true;
        else
            return false;
    }
    
    public void retransmit(String challenge, String user, int question, int bloco) throws IOException{
        this.challenges.get(challenge).getChallengeThread().retransmit(user, question, bloco);
    }
    
    public String getNewIdChallenge(){
        return String.valueOf(this.idChallengeGenerator++);        
    }
    
    public void addUserThread(String user, ClienteStub thread){
        this.threads.put(user, thread);
    }
    
    public void listChallenges(String user, int label) throws IOException{
        List<String> challenges = new ArrayList<>(); 
        List<String> creators = new ArrayList<>(); 
        List<GregorianCalendar> dates = new ArrayList<>();                                    
                
        for(Challenge c: this.challenges.values()){
            challenges.add(c.getName());
            dates.add(c.getDate());            
            creators.add(c.getUserCreator());
        }
                      
        PDU res = new PDU(label);
            res.creatReply();
            res.replyOK(); 
            res.setListAlcunha(creators);
            res.setListDesafio(challenges);
            res.addListGregorianCalendar(dates);            
            
        this.threads.get(user).sendReeply(res);
        
        /*
        int bloco = 0;    
        int last = this.challenges.size(); 
        
        for(Challenge c: this.challenges.values()){
            res.setDesafio(c.getName());
            res.addGregorianCalendar(c.getDate());
            bloco++;
            if(bloco<last)
                res.hasNextPDU();
            this.threads.get(user).sendReeply(res);
        }
        */
    }
    
    public LinkedList<User> getRanking(){
        LinkedList<User>list = new LinkedList<User>();
        for(User u:this.users.values())
            list.add(u);
        Collections.sort(list, new ScoreComparator());
        return list;
    }
    
    public void listRanking(String user, int label) throws IOException{
        LinkedList<User> ranking = this.getRanking();
        PDU res = new PDU(label);
            res.creatReply();
            res.replyOK();
        
        int bloco = 0;    
        int last = ranking.size();
        
        List<String> nomes = new ArrayList<String>();
        List<String> usernames = new ArrayList<String>();
        List<Integer> points = new ArrayList<Integer>();
        
        for(User u : ranking){
            nomes.add(u.getNome());
            usernames.add(u.getUsername());
            points.add(u.getIntegerScore());
        }
        
        res.setListNome(nomes);
        res.setListAlcunha(usernames);
        res.setListScore(points);
        
        this.threads.get(user).sendReeply(res); 
        
        /*
        for(User u: ranking){
            res.setNome(u.getNome());
            res.setAlcunha(u.getUsername());
            res.setBloco(bloco);
            bloco++;
            if(bloco<last)
                res.willBeNextPDU();
            this.threads.get(user).sendReeply(res);            
        } 
        */
    }
    
    public void endChallenge(String user, String desafio, int label) throws IOException{
        this.challenges.get(desafio).getChallengeThread().userEnd(user, label);
    }
    
    public void userQuitChallenge(String user, String challenge, int label){
        this.challenges.get(challenge).getChallengeThread().quitUser(user, label);
    }
    
    public void AddScoreChallenge(LinkedList<UserGame> gamers){
        this.lock.lock();
        try {
            for(UserGame u: gamers){
                this.users.get(u.getUsername()).addScore(u.getPoits());
            }
        } finally {
            this.lock.unlock();
        }
    }
    
    /*Métodos para evitar fadiga na escrita de locks*/
    private void lock() { this.lock.lock(); }
    private void unlock() { this.lock.unlock(); }
    
    /*-------------------------------------------------------------------------------------------*/
    /*---------------------------- SALVAGUARDAR ESTADO DA APLICAÇÃO -----------------------------*/
    /*-------------------------------------------------------------------------------------------*/
    
    /**
     * Método que permite guardar em ficheiro base de dados do servidor. 
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void saveManager() throws FileNotFoundException, IOException {
        String workingDir = System.getProperty("user.dir");
        FileOutputStream fos = new FileOutputStream(workingDir+"\\database.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        try{
            oos.writeObject(this);
            oos.close();
        }
        catch (IOException e){
            System.out.println("\nerror saving file!\n");
        }        
    }
    
    /**
     * Método que permite carregar estado guardado do servidor através de um ficheiro.
     * @return
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public static Manager loadManager() throws IOException, ClassNotFoundException{
        try{
            String workingDir = System.getProperty("user.dir");
            FileInputStream fin=new FileInputStream(workingDir+"\\database.ser");
            ObjectInputStream oin=new ObjectInputStream(fin);
            Manager m = (Manager)oin.readObject();
            if(m!=null) return m;
            else return new Manager();
        }
        catch (IOException e){
            System.out.println("\nerror loading file!\n");
            return new Manager();
        }  
    }
}
