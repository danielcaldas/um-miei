/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author USER
 */
public class CandidaturaNaoExisteException extends Exception{
    public CandidaturaNaoExisteException(int nr){
        super("Candidatura com nr: "+nr+" não existe");
    }
}
