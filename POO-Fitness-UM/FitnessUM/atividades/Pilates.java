package atividades;

/**
 * Classe para atividade Pilates.
 * 
 * @author Anténio Anjo
 * @version 21/05/2014
 */

import java.io.Serializable;
import java.util.GregorianCalendar;

public class Pilates extends Atividade implements Serializable {

	// Construtores
	public Pilates() {
		super();
	}

	public Pilates(Time duracao, int idade, int peso, int altura,
			String genero, GregorianCalendar data, double hidratacao) {
		super(duracao, idade, peso, altura, genero, data, hidratacao);
	}

	public Pilates(Pilates c) {
		super(c);
	}

    @Override
    public String getNome(){return this.getClass().getSimpleName();}
    
	// Métodos de instãncia
	@Override
    public int calcularCalorias(int idade, int altura, String genero,Time duracao, int peso){
        double r = 0.0;
        int i;
                if (genero.equals("F")){
                    if(idade<18) r = (170*duracaoEmMinutos()/30); 
                    else if(idade>=18 && idade<=50) r = (165*duracaoEmMinutos()/30);                              
                    else if(idade>50) r = (150*duracaoEmMinutos()/30); 
                    r = r*( (peso/100) + 1);                
                }
                else if (genero.equals("M")){
                    if(idade<18) r = (180*duracaoEmMinutos()/30); 
                    else if(idade>=18 && idade<=50) r = (175*duracaoEmMinutos()/30);                              
                    else if(idade>50) r = (165*duracaoEmMinutos()/30); 
                    r = r*( (peso/100) + 1);                
                }
                else return 3*duracaoEmMinutos();
        i = (int) r;
        return i;
    }

	@Override
	public Pilates clone() {
		return new Pilates(this);
	}
}
