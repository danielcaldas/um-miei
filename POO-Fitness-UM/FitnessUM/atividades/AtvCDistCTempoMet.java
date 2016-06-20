package atividades;

/**Classe para atividades como: Canoagem, Atletismo, Rafting etc. ...
 *
 * @author jdc
 * @version 02/05/2014
 */

import java.io.Serializable;
import java.util.GregorianCalendar;

public abstract class AtvCDistCTempoMet extends AtvComDistancia implements Serializable {
	// Variável de instãncia complementar
	private String tempomet;

	// Construtores
	public AtvCDistCTempoMet() {
		super();
		this.tempomet = "";
	}

	public AtvCDistCTempoMet(Time duracao, int idade, int peso,int altura, String genero, GregorianCalendar data,
			double hidratacao, double distancia, double velmax, String tempomet) {
		super(duracao, idade, peso, altura, genero, data, hidratacao,distancia, velmax);
		this.tempomet = tempomet;
	}

	public AtvCDistCTempoMet(AtvCDistCTempoMet atv) {
		super(atv);
		this.tempomet = atv.getTempoMet();
	}

	
	// Métodos de instãncia

	// gets
	public String getTempoMet() {return this.tempomet;}

	// equals, clone e toString
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		else if (o == null || this.getClass() != o.getClass())
			return false;

		else {
			AtvCDistCTempoMet atv = (AtvCDistCTempoMet) o;
			return (super.equals(atv) && this.tempomet
					.equals(atv.getTempoMet()));
		}
	}

	@Override
	public abstract AtvCDistCTempoMet clone();
	public abstract String getNome();

	@Override
	public String toString() {
		return (super.toString() + "Descrição do Tempo: " + this.tempomet + "\n\n");
	}

	@Override
	public abstract int calcularCalorias(int idade, int altura, String genero,Time duracao, int peso);

}
