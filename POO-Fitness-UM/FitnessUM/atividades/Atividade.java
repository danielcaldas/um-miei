package atividades;

/**Esta Superclasse Atividade dÃ¡ suporte a atividades como: Zumba, Pilates, Ginástica, Musculação etc. ...
 *
 * @author jdc
 * @version 01/05/2014
 * @version 20/05/2014
 */

import java.io.Serializable;
import java.util.GregorianCalendar;

public abstract class Atividade extends AtvBase implements Serializable {

	// Construtores da Classe
	public Atividade() {
		super();
	}

	public Atividade(Time duracao, int idade, int peso,int altura, String genero, GregorianCalendar data, double hidratacao) {
		super(duracao, idade, peso, altura, genero, data, hidratacao);
	}

	public Atividade(Atividade atv) {
		super(atv);
	}

	public abstract Atividade clone();
	public abstract String getNome();

	public abstract int calcularCalorias(int idade, int altura, String genero,Time duracao, int peso);

	// Serve para medir o score
	public Time getScore() {
		return this.getDuracao();
	}

}
