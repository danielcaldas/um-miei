package atividades;

/**SuperClasse para atividades como: Futsal, Andebol, Badminton etc. ...
 *
 * @author jdc
 * @version 01/05/2014
 */

import java.io.Serializable;
import java.util.GregorianCalendar;

public abstract class AtividadeComRating extends AtvBase implements Serializable {
	// Variáveis de instãncia
	private double rating; // prestação qualitativa de 0 a 10

	// Construtores da Classe
	public AtividadeComRating() {
		super();
	}

	public AtividadeComRating(Time duracao, int idade, int peso,int altura, String genero, GregorianCalendar data,double hidratacao, double rating) {
		super(duracao, idade, peso, altura, genero, data, hidratacao);
		this.rating = rating;
	}

	public AtividadeComRating(AtividadeComRating atv) {
		super(atv);
		this.rating = atv.getRating();
	}

	// Métodos de instãncia

	// gets

	public double getRating() {return this.rating;}

	// equals, clone e toString
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		else if (this.getClass() != o.getClass()) return false;

		else {
			AtividadeComRating atv = (AtividadeComRating) o;
			return (super.equals(atv) && this.getRating() == atv.getRating());
		}
	}

	@Override
	public abstract AtividadeComRating clone();
	public abstract String getNome();

	@Override
	public String toString() {
		return (super.toString() + "\nRating: " + this.rating);
	}

	// Cálculo do gasto calórico calculado em função da idade, altura, sexo
	public abstract int calcularCalorias(int idade, int altura, String genero,Time duracao, int peso);

}