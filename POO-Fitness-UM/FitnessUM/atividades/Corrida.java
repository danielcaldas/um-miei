package atividades;

/**
 * Classe para a atividade corrida.
 * 
 * @author António Anjo
 * @version 21/05/2014
 */

import java.io.Serializable;
import java.util.GregorianCalendar;

public class Corrida extends AtvCDistCAltCTempoMet implements Serializable,Distancia, DistAlt, Simulavel {

    private double incerteza;
    
	// Construtores
	public Corrida() {
		super();
		this.incerteza=0.5;
	}

	public Corrida(Time duracao, int idade, int peso, int altura,String genero, GregorianCalendar data, double hidratacao,double distancia,
	double velmax, int altmax, int altmin, int tsub,int tdes, String tempomet) {
		super(duracao, idade, peso, altura, genero, data, hidratacao,distancia, velmax, altmax, altmin, tsub, tdes, tempomet);
		this.incerteza=0.5;
	}

	public Corrida(Corrida c) {
		super(c);
		this.incerteza=0.5;
	}

	public double getIncerteza(){return this.incerteza;}
	
    @Override
    public String getNome(){return this.getClass().getSimpleName();}
	
	// Métodos de instãncia
    @Override
    public int calcularCalorias(int idade, int altura, String genero,Time duracao, int peso){
      double r = 0.0;
      int totalsubido = this.getTotalSubido();
      /*String tempomet = this.getTempoMet();*/
      int i;
               if (genero.equals("F")) {
                 r = 150*(duracaoEmMinutos()/30);
                 if(idade<18) r = (160*duracaoEmMinutos()/30); 
                 else if(idade>=18 && idade<=50) r = (150*duracaoEmMinutos()/30);                              
                 else if(idade>50) r = (140*duracaoEmMinutos()/30); 
                 r = r*( (peso/100) + 1);                 
                 if (totalsubido >= 10 && totalsubido <30) r=r*1.1;
                 else if (totalsubido > 30) r=r*1.15;
               }
               else if (genero.equals("M")) {
                 if(idade<18) r = (170*duracaoEmMinutos()/30); 
                 else if(idade>=18 && idade<=50) r = (160*duracaoEmMinutos()/30);                              
                 else if(idade>50) r = (155*duracaoEmMinutos()/30); 
                 r = r*( (peso/100) + 1);
                 if (totalsubido >= 10 && totalsubido <30) r=r*1.1;
                 else if (totalsubido > 30) r=r*1.15;  
               }
               else r = 3*duracaoEmMinutos();
      i= (int) r;
      return i;
    }

	@Override
	public Corrida clone() {
		return new Corrida(this);
	}

}
