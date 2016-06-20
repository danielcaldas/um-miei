package atividades;

/**
 * Classe para atividade Zumba.
 * 
 * @author Antonio Anjo
 * @version 21/05/2014
 */

import java.io.Serializable;
import java.util.GregorianCalendar;

public class Zumba extends Atividade implements Serializable {

	// Construtores
	public Zumba() {
		super();
	}

	public Zumba(Time duracao, int idade, int peso, int altura,String genero, GregorianCalendar data, double hidratacao) {
		super(duracao, idade, peso, altura, genero, data, hidratacao);
	}

	public Zumba(Zumba c) {
		super(c);
	}

    @Override
    public String getNome(){return this.getClass().getSimpleName();}
    
	// Metodos de instancia
    @Override
    public int calcularCalorias(int idade, int altura, String genero,Time duracao, int peso){
        double r = 0.0;
        int i;
                if (genero.equals("F")){
                    if(idade<18) r = (230*duracaoEmMinutos()/30); 
                    else if(idade>=18 && idade<=50) r = (220*duracaoEmMinutos()/30);                              
                    else if(idade>50) r = (210*duracaoEmMinutos()/30); 
                    r = r*( (peso/100) + 1);                    
                }
                else if (genero.equals("M")){
                    if(idade<18) r = (260*duracaoEmMinutos()/30); 
                    else if(idade>=18 && idade<=50) r = (240*duracaoEmMinutos()/30);                              
                    else if(idade>50) r = (235*duracaoEmMinutos()/30); 
                    r = r*( (peso/100) + 1);                    
                }
                else return 3*duracaoEmMinutos();
        i = (int) r;
        return i;
    }

	@Override
	public Zumba clone() {
		return new Zumba(this);
	}
}
