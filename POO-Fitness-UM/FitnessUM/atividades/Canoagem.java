package atividades;

/**
 * Classe para atividade Canoagem.
 * 
 * @author António Anjo
 * @version 21/05/2014
 */

import java.io.Serializable;
import java.util.GregorianCalendar;

public class Canoagem extends AtvCDistCTempoMet implements Serializable,Distancia, Simulavel {
    
    double incerteza;
    
    // Construtores
    public Canoagem() {
        super();
        this.incerteza=2.52;
    }

    public Canoagem(Time duracao, int idade, int peso, int altura,String genero, GregorianCalendar data, double hidratacao,
            double distancia, double velmax, String tempomet) {
        super(duracao, idade, peso, altura, genero, data, hidratacao,distancia, velmax, tempomet);
        this.incerteza=2.52;        
    }

    public Canoagem(Canoagem c) {
        super(c);
        this.incerteza=2.52;
    }

    
    @Override
    public String getNome(){return this.getClass().getSimpleName();}
    
    @Override
    public double getIncerteza(){return this.incerteza;}
    
    // Métodos de instãncia
    @Override
    public int calcularCalorias(int idade, int altura, String genero,Time duracao, int peso){
        double r = 0.0;
        int i;
                if (genero.equals("F")) {
                    if(idade<18) r = (190*duracaoEmMinutos()/30); 
                    else if(idade>=18 && idade<=50) r = (180*duracaoEmMinutos()/30);                              
                    else if(idade>50) r = (150*duracaoEmMinutos()/30); 
                    r = r*( (peso/100) + 1);
                }              
                else if (genero.equals("M")) {
                    if(idade<18) r = (195*duracaoEmMinutos()/30); 
                    else if(idade>=18 && idade<=50) r = (185*duracaoEmMinutos()/30);                              
                    else if(idade>50) r = (155*duracaoEmMinutos()/30); 
                    r = r*( (peso/100) + 1);
                }
                else r = 3*duracaoEmMinutos();
        i = (int) r;
        return i;  
    }

    @Override
    public Canoagem clone() {
        return new Canoagem(this);
    }

}
