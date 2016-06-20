/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware;

/**
 *
 * @author carlosmorais
 */
public class UserGame {
    String username;
    Integer poits;
    int questions;

    public UserGame(String username) {
        this.username = username;
        this.poits = 0;
        this.questions = 0;
    }

    public String getUsername() {
        return username;
    }

    public int getPoits() {
        return poits;
    }
        
    public Integer getIntegerPoits() {
        return poits;
    }

    public void correctAnswer() {
        this.poits +=2;
        this.incQuestions();
    }
    
    public void wrongAnswer() {
        this.poits--;
        this.incQuestions();
    }
    
    public void incQuestions(){
        this.questions++;
    }
    
    public void quitGame(){
        this.poits += this.questions-10;
    }
    
    
}
