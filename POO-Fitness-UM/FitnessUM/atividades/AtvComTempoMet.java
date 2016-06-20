package atividades;

/**Classe para atividades como: Futebol, Ténis, Rugby, Surf etc. ...
 *
 * @author jdc
 * @version 2/05/2014
 */

import java.io.Serializable;
import java.util.GregorianCalendar;

public abstract class AtvComTempoMet extends AtividadeComRating implements Serializable {
	// Variável de instãncia complementar
	private String tempomet;

	// Construtores
	public AtvComTempoMet() {
		super();
		this.tempomet = "";
	}

	public AtvComTempoMet(Time duracao, int idade, int peso,int altura, String genero, GregorianCalendar data,
			double hidratacao, String tempomet, double rating) {
		super(duracao, idade, peso, altura, genero, data, hidratacao,rating);
		this.tempomet = tempomet;
	}

	public AtvComTempoMet(AtvComTempoMet atv) {
		super(atv);
		this.tempomet = atv.getTempoMet();
	}

	// Métodos de instãncia

	// gets
	public String getTempoMet() {
		return this.tempomet;
	}

	// equals, clone e toString
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		else if (o == null || this.getClass() != o.getClass())
			return false;

		else {
			AtvComTempoMet atv = (AtvComTempoMet) o;
			return (super.equals(atv) && this.tempomet
					.equals(atv.getTempoMet()));
		}
	}

	@Override
	public abstract AtvComTempoMet clone();
	public abstract String getNome();

	@Override
	public String toString() {
		return (super.toString() + "\nDescrição do tempo: " + this.tempomet + "\n");
	}

	public abstract int calcularCalorias(int idade, int altura, String genero,Time duracao, int peso);
}
