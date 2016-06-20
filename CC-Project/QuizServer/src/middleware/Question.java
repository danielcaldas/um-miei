package middleware;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import javax.imageio.ImageIO;
import middleware.Protocol;
import middleware.SO;

/**
 * Classe que encapsula dados relativos a uma questão.
 * @author jdc
 * @version 2015.04.03
 */

public class Question {

    // Variáveis de instância
    private String song;
    private String image;
    private String quest;
    private String opA, opB, opC;
    private int correctAnsw;

    // Construtores
    public Question() {
            this.song=""; 
            this.image="";
            this.quest=""; 
            this.opA=""; 
            this.opB=""; 
            this.opC="";
            this.correctAnsw=-1;
    }

    public Question(String song, String image, String q, String a, String b, String c, int corrc) {
        //this.song=System.getProperty("user.dir") + "/songs/"+song; 
        //this.image=System.getProperty("user.dir") + "/images/"+image;
        this.song = SO.getSeverDirSongs() + song;
        this.image = SO.getSeverDirImages() + image;        
        this.quest = q;
        this.opA = a;
        this.opB = b;
        this.opC = c;
        this.correctAnsw = corrc;
    }

    public Question(String quest, String opA, String opB, String opC, byte[][] songB,String songName, byte[][] imgB, String imageName) throws FileNotFoundException, IOException {        
        //new File( (System.getProperty("user.dir")+"/temp/songs/") ).mkdirs();
        //new File( (System.getProperty("user.dir")+"/temp/images/") ).mkdirs();
        SO.creatDirToFiles();
        
        //this.song = System.getProperty("user.dir") + "/temp/songs/"+songName;
        //this.image = System.getProperty("user.dir") + "/temp/images/"+imageName;        
        this.song = SO.getUserDirSongs() + songName;
        this.image = SO.getUserDirImages() + imageName;
                
        this.quest = quest;
        this.opA = opA;
        this.opB = opB;
        this.opC = opC;
        
        int tamSong=0, tamImg=0;
        
        for(int i=0, k=0; i<songB.length; i++)
            tamSong += songB[i].length;
        
        for(int i=0, k=0; i<imgB.length; i++)
            tamImg += imgB[i].length;
        
        byte song[] = new byte[tamSong];
        byte image[] = new byte[tamImg];
        
        for(int i=0, k=0; i<songB.length; i++){
            for(int j=0; j<songB[i].length; j++,k++)
                song[k] = songB[i][j];
        }               
        
        for(int i=0, k=0; i<imgB.length; i++){
            for(int j=0; j<imgB[i].length; j++,k++)
                image[k] = imgB[i][j];
        } 
                
        //cria File song
        FileOutputStream songOuputStream = new FileOutputStream(this.song); 
	songOuputStream.write(song);
	songOuputStream.close();
        
        //cria File image
        FileOutputStream imageOuputStream = new FileOutputStream(this.image);
	imageOuputStream.write(image);
	imageOuputStream.close();
    }
        
    public Question(Question q) {
            this.song = q.getSong();
            this.image = q.getImage();
            this.quest = q.getQuestion();
            this.opA = q.getOptionA(); this.opB = q.getOptionB(); this.opC = q.getOptionC();
            this.correctAnsw = q.getCorrectAnswer();
    }

    public byte[][] getByteSong() throws IOException{
        return this.getByteFile(this.song);
    }
    
    public byte[] getAllByteSong() throws IOException{
        File fi = new File(this.song);
        byte[] b = Files.readAllBytes(fi.toPath());
        return b;
    }
    
    public byte[][] getByteImage() throws IOException{
        return this.getByteFile(this.image);
    }
    
    public byte[] getAllByteImage() throws IOException{
        File fi = new File(this.image);
        byte[] b = Files.readAllBytes(fi.toPath());
        return b;
    }
    
    //work??? yes, I'm an enginner !
    private byte[][] getByteFile(String file) throws IOException{
        
        File fi = new File(file);
        byte[] b = Files.readAllBytes(fi.toPath());
        
        byte[][] res = new byte[((b.length)/Protocol.MAX_BYTE)+1][];
        
        for(int i=0, sum=0; i<res.length; i++){
            int limit=0;            
            
            if((b.length-sum) < Protocol.MAX_BYTE)
                limit = (b.length-sum);
            else
                limit = Protocol.MAX_BYTE;            
            
            res[i] = new byte[limit];
            
            for(int k=sum, j=0; j<limit ; j++, k++)
                res[i][j] = b[k];
            
            sum+=limit;           
        }
                
        return res;
    }

    // gets & sets
    public String getSong() { return this.song; }
    public String getImage() { return this.image; }
    public String getQuestion() { return this.quest; }
    public String getOptionA() { return this.opA; }
    public String getOptionB() { return this.opB; }
    public String getOptionC() { return this.opC; }
    public int getCorrectAnswer() { return this.correctAnsw; }

    public void setSong(String s) { this.song=s; }
    public void setImage(String i) { this.image=i; }
    public void setQuestion(String q) { this.quest=q; }
    public void setOptionA(String a) { this.opA=a; }
    public void setOptionB(String b) { this.opB=b; }
    public void setOptionC(String c) { this.opC=c; }
    public void setCorrectAnswer(int ca) { this.correctAnsw=ca; }

    // equals, clone and toString
    @Override
    public Question clone() {
            return new Question(this);
    }

    @Override
    public String toString() {
            return ("Q.: "+this.quest+"\nA.: "+this.opA+"\nB: "+this.opB+"\nC: "
                    +this.opC+"\nSong: "+this.song+"\nCorrect Answer: "+this.getCharAnswValue());
    }


    // Outros métodos

    /*Método que permite obter String com resposta correta, em vez de inteiro*/
    public String getCAnswValue() {
            String r;

            switch(this.correctAnsw){
                    case 1:
                       r=this.opA;
                       break;
                    case 2:
                       r = this.opB;
                       break;
                    case 3:
                       r = this.opC;
                       break;
                    default:
                       r = "código de resposta correta inválido";
            }

            return r;
    }
    
    /*Método que devolve carater correspondente à resposta correta*/
    public char getCharAnswValue() {
            char r;

            switch(this.correctAnsw){
                    case 1:
                       r='A';
                       break;
                    case 2:
                       r = 'B';
                       break;
                    case 3:
                       r = 'C';
                       break;
                    default:
                       r = 'Z';
            }

            return r;
    }
}		
