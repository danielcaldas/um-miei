/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import client.gui.Quiz;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author carlosmorais
 */
public class DisplayGame  extends Thread {

    private Quiz quiz;
    
    public DisplayGame(Quiz q){
        this.quiz = q;
    }
    
    public void startGame() throws InterruptedException{
        try {
            this.quiz.updateQuestion();            
        } catch (IOException ex) {
            Logger.getLogger(DisplayGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @Override
    public void run() {
        try {       
            // Perguntar ao jogador se quer ou não realizar o desafio, caso a resposta seja positiva
            // mostramos a janela, no entanto o jogo já está a ser processado em background
            int option = JOptionPane.showConfirmDialog(null, "O jogo \""+quiz.getChallengeName()+"\", está prestes a começar, jogamos?", "Saír", JOptionPane.YES_NO_OPTION);
            this.startGame();
            
            if (option == JOptionPane.YES_OPTION) {
                this.quiz.show();
            }
            else{
                this.join(); // A thread auto destroí-se
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(DisplayGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
    
}
