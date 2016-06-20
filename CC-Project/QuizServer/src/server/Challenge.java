package server;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import middleware.User;
import middleware.UserGame;

/**
 *
 * @author carlosmorais
 */
public class Challenge {
    private String name;
    private GregorianCalendar date;
    private String userCreator;   
    //private ArrayList<String> players; //lista dos usernames dos utilizadores registados 
    //private Map<String, Integer> playersPonints;
    private Map<String, UserGame> playersPonints;
    private boolean running;  //quando começa o desafio fica a false
    
    private ChallengeThread challengeThread;

    public Challenge(String userCreator, String name, GregorianCalendar date) {        
        this.name = name;
        this.date = date;
        this.userCreator = userCreator;

        this.playersPonints = new HashMap<String, UserGame>();
        //this.playersPonints.put(userCreator, new UserGame(userCreator));
        
        this.running = true;
        
        System.out.println("Desafio criado com a a data "+
                this.date.get(GregorianCalendar.YEAR)+"/"+this.date.get(GregorianCalendar.MONTH)+
                "/"+this.date.get(GregorianCalendar.DAY_OF_MONTH)+
                " "+this.date.get(GregorianCalendar.HOUR_OF_DAY)+":"+this.date.get(GregorianCalendar.MINUTE)+":"+
                this.date.get(GregorianCalendar.SECOND));
    }

    public ChallengeThread getChallengeThread() {
        return challengeThread;
    }


    public String getName() {
        return name;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public String getUserCreator() {
        return userCreator;
    }
    
    
    
    public void noRunning(){ this.running=false; }

    public boolean isRunning() {
        return running;
    }
    
    public boolean isReadyToStart(){
        if(this.running==false)
            return false;
        
        //desafio deixa de estar disponivel a partir daqui
        this.noRunning();
        
        //ALTERAR PARA > 1
        if(this.playersPonints.size() >= 1)
            return true;
        else
            return false;
    }

    public Set<String> getPlayers() {
        return this.playersPonints.keySet();
    }
    
    public LinkedList<UserGame> getChallengeRanking(){
        LinkedList<UserGame>list = new LinkedList<UserGame>();
        for(UserGame u:this.playersPonints.values())
            list.add(u);
        Collections.sort(list, new ScoreGameComparator());
        return list;
    }    
    
    public boolean containsIdPlayer(String id){
        return this.playersPonints.containsKey(id);
    }
    
    public void addPlayer(String id){
        this.playersPonints.put(id, new UserGame(id));      
    }
    
    
    public boolean isAvailable() {
        GregorianCalendar now = new GregorianCalendar();
        now = (GregorianCalendar) GregorianCalendar.getInstance();
    
        if(this.date.compareTo(now) > 0 ){
            return true;
        }
        else
            return false;
    }
        
    public void addUserIgnoreQuestion(String user){
        this.playersPonints.get(user).incQuestions();
    }
    
    public void addUsergameQuit(String user){
        this.playersPonints.get(user).quitGame();
    }
    
    public void addUserCorrectAnswer(String user){;
        this.playersPonints.get(user).correctAnswer();
    }
    
    public boolean checkUserAnswer(String user, int question, int option){
        return this.challengeThread.checkAnswer(user , question, option);
    }
    
    public void addUserWrongAnswer(String user){
        this.playersPonints.get(user).wrongAnswer();
    }
    
    public void addChallengeThread(ChallengeThread ch){
        this.challengeThread = ch;
    }
    
    /**
     * Retorna string devidamente formatada com a data do desafio para apresentar na interface.
     * @return String
     */
    public String getStringDate() {
        return (this.date.get(Calendar.DAY_OF_MONTH)+"/"+(this.date.get(Calendar.MONTH) + 1)+"/"+this.date.get(Calendar.YEAR));
    }
    
    /**
     * Retorna string com a hora do desafio devidademente formatada para apresentar na interface.
     * @return String
     */
    public String getStringTime() {
        int h = date.get(Calendar.HOUR_OF_DAY);
        int m = date.get(Calendar.MINUTE);
        int s = date.get(Calendar.SECOND);
        
        String hour, min, seg;
        
        if(h<10){
            hour = "0"+String.valueOf(h);
        } else {
            hour = String.valueOf(h);
        }
        
        if(m<10){
            min = "0"+String.valueOf(m);
        } else {
            min = String.valueOf(m);
        }
        
        if(s<10){
            seg = "0"+String.valueOf(s);
        } else {
            seg = String.valueOf(s);
        }
        
        return(hour+":"+min+":"+seg);
    }      
}
