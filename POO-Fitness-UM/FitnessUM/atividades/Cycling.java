package atividades;

/**
 * Classe para atividade cycling.
 * 
 * @author António Anjo
 * @version 21/05/2014
 */

import java.io.Serializable;
import java.util.GregorianCalendar;

public class Cycling extends AtvComDistancia implements Serializable, Distancia, Simulavel {
    private double incerteza;
    
    // Construtores
    public Cycling() {
        super();
        this.incerteza=3.4;
    }

    public Cycling(Time duracao, int idade, int peso, int altura,
            String genero, GregorianCalendar data, double hidratacao,
            double distancia, double velmax) {
        super(duracao, idade, peso, altura, genero, data, hidratacao,
                distancia, velmax);
        this.incerteza=3.4;
    }

    public Cycling(Cycling c) {
        super(c);
        this.incerteza=3.4;
    }

    public double getIncerteza(){return this.incerteza;}
    
    @Override
    public String getNome(){return this.getClass().getSimpleName();}
    
    // Métodos de instãncia
    @Override
    public int calcularCalorias(int idade, int altura, String genero,Time duracao, int peso){
        double r= 0.0;
        int i;
                if (genero.equals("F")){
                    if(idade<18) r = (170*duracaoEmMinutos()/30); 
                    else if(idade>=18 && idade<=50) r = (165*duracaoEmMinutos()/30);                              
                    else if(idade>50) r = (155*duracaoEmMinutos()/30); 
                    r = r*( (peso/100) + 1);                    
                    
                }
                else if (genero.equals("M")){
                    if(idade<18) r = (190*duracaoEmMinutos()/30); 
                    else if(idade>=18 && idade<=50) r = (180*duracaoEmMinutos()/30);                              
                    else if(idade>50) r = (170*duracaoEmMinutos()/30); 
                    r = r*( (peso/100) + 1);
                }
                else r= 20*duracaoEmMinutos();
        i=(int) r;
        return i;
    }

    @Override
    public Cycling clone() {
        return new Cycling(this);
    }
}
