package server;

import middleware.Question;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe que faz parse de ficheiros com desafios.
 * @author jdc
 * @version 2015.04.03
 */

public class ParseQuestions {
    
    public ParseQuestions(){}
    
    public ArrayList<Question> parse(String filename) {
        //String filename = "desafio-000001.txt";
        BufferedReader reader = null;
        
        ArrayList<Question> questions = new ArrayList<>();
        
        /*
        music_DIR=D:\CC\CC\musica
        images_DIR=D:\CC\CC\imagens
        questions_#=10
        0000001.mp3,000001.jpg,"Quem canta esta canção?","Robert Smith","Bono Vox","Ninguém, é uma música instrumental",3
        */
        String linha = null;
        String musicDir = null;
        String imagesDir = null;
        String song = null;
        String image = null;
        String question=null;
        ArrayList<String> options = null;
        int correct=-1;
        int size=-1;
        int lineCounter;
        String[] tokens=null;
        
        try{
            FileReader file = new FileReader(filename);
            reader = new BufferedReader(file);
            
            for(lineCounter=1; (linha=reader.readLine())!=null; lineCounter++){
                options = new ArrayList<>();
                switch(lineCounter){
                    case 1:
                        tokens = linha.split("=");
                        musicDir = tokens[1];
                        break;
                    case 2:
                        tokens = linha.split("=");
                        imagesDir = tokens[1];
                        break;
                    case 3:
                        tokens = linha.split("=");
                        size = Integer.parseInt(tokens[1]);
                        break;
                    default:
                        tokens = linha.split(";");
                        song = tokens[0];
                        image = tokens[1];
                        question = tokens[2];
                        for(int i=3; i<6; i++) options.add(tokens[i]);
                        correct = Integer.parseInt(tokens[6]);
                        
                        Question q = new Question(song,image,question,options.get(0),options.get(1),options.get(2),correct);
                        
                        questions.add(q);
                        break;                          
                }    
            }
	}
        catch (IOException e){
            System.out.println("\nERRO A LER O FICHEIRO!\n");
        }
        
        System.out.println("Diretoria das musicas: "+musicDir);
        System.out.println("Diretoria das imagens: "+imagesDir);
        System.out.println("Número de questões: "+size);
        
        return questions;
    }
}
