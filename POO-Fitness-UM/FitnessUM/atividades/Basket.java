package atividades;

/**
 * Classe para atividade Basket.
 * 
 * @author António Anjo
 * @version 21/05/2014
 */

import java.io.Serializable;
import java.util.GregorianCalendar;

public class Basket extends AtividadeComRating implements Serializable, Rating {

	// Construtores
	public Basket() {
		super();
	}

	public Basket(Time duracao, int idade, int peso, int altura,String genero, GregorianCalendar data, double hidratacao,double rating) {
		super(duracao, idade, peso, altura, genero, data, hidratacao,rating);
	}

	public Basket(Basket c) {
		super(c);
	}

	// Métodos de instãncia
    @Override
    public int calcularCalorias(int idade, int altura, String genero,Time duracao, int peso){
        double r=0.0;
        int i;
                if (genero.equals("F")){
                    if(idade<18) r = (300*duracaoEmMinutos()/30); 
                    else if(idade>=18 && idade<=50) r = (285*duracaoEmMinutos()/30);                              
                    else if(idade>50) r = (270*duracaoEmMinutos()/30); 
                    r = r*( (peso/100) + 1);                    
                }
                else if (genero.equals("M")){
                    if(idade<18) r = (310*duracaoEmMinutos()/30); 
                    else if(idade>=18 && idade<=50) r = (300*duracaoEmMinutos()/30);                              
                    else if(idade>50) r = (290*duracaoEmMinutos()/30); 
                    r = r*( (peso/100) + 1);                    
                }
                else return 3*duracaoEmMinutos();
        i = (int) r;
        return i;
    }
    
    @Override
    public String getNome(){return this.getClass().getSimpleName();}

	@Override
	public Basket clone() {
		return new Basket(this);
	}

}
