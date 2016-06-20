package atividades;

/**
 * Classe para atividade Futebol.
 * 
 * @author Antonio Anjo
 * @version 21/05/2014
 */

import java.io.Serializable;
import java.util.GregorianCalendar;

public class Futebol extends AtvComTempoMet implements Serializable, Rating {

    // Construtores
    public Futebol() {
        super();
    }

    public Futebol(Time duracao, int idade, int peso, int altura,String genero, GregorianCalendar data, double hidratacao,String tempomet, double rating) {
        super(duracao, idade, peso, altura, genero, data, hidratacao,
        tempomet, rating);
    }

    public Futebol(Futebol c) {
        super(c);
    }

    // Metodos de instância
    @Override
    public int calcularCalorias(int idade, int altura, String genero,Time duracao, int peso){
        double r=0.0;
        int i;
                if (genero.equals("F")) {
                    if(idade<18) r = (320*duracaoEmMinutos()/30); 
                    else if(idade>=18 && idade<=50) r = (310*duracaoEmMinutos()/30);                              
                    else if(idade>50) r = (295*duracaoEmMinutos()/30); 
                    r = r*( (peso/100) + 1);            
                }
                else if (genero.equals("M")){
                    if(idade<18) r = (340*duracaoEmMinutos()/30); 
                    else if(idade>=18 && idade<=50) r = (330*duracaoEmMinutos()/30);                              
                    else if(idade>50) r = (320*duracaoEmMinutos()/30); 
                    r = r*( (peso/100) + 1);
                }
                else r = 3*duracaoEmMinutos();
        i = (int) r;
        return i;
    }

    @Override
    public String getNome(){return this.getClass().getSimpleName();}
    
    @Override
    public Futebol clone() {
        return new Futebol(this);
    }

}
