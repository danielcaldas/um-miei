package atividades;

/**SuperClasse para atividades como: Corrida Plana, Cycling, Remo, Natação, Ciclismo, Maratona, etc. ...
 *
 * @author jdc
 * @version 1/05/2014
 */

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.GregorianCalendar;

public abstract class AtvComDistancia extends AtvBase implements Serializable {
    
	// Variáveis de instãncia complementares de atividade
	private double distancia; // distãncia percorrida (em km)
	private double velmedia; // velocidade média (em km/h)
	private double velmax; // velocidade máxima (em km)

	// Construtores
	public AtvComDistancia() {
		super();
		this.distancia = 0;
		this.velmax = 0;
		this.velmedia = 0;
	}

	public AtvComDistancia(Time duracao, int idade, int peso,
			int altura, String genero, GregorianCalendar data,
			double hidratacao, double distancia, double velmax) {
		super(duracao, idade, peso, altura, genero, data, hidratacao);
		this.distancia = distancia;
		this.velmax = velmax;
		this.velmedia = calcularVelocidadeMedia(distancia, duracao);
	}

	public AtvComDistancia(AtvComDistancia atvcd) {
		super(atvcd);
		this.distancia = atvcd.getDist();
		this.velmax = atvcd.getVelMedia();
		this.velmedia = atvcd.getVelMax();
	}

	// Métodos de instãncia

	// gets
	public double getDist() {return this.distancia;}
	public double getVelMedia() {return this.velmedia;}
	public double getVelMax() {return this.velmax;}
	public abstract String getNome();

	// equals, clone e toString
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		else if (o == null || this.getClass() != o.getClass())
			return false;

		else {
			AtvComDistancia atv = (AtvComDistancia) o;
			return (super.equals(atv) && this.distancia == atv.getDist()
					&& this.getVelMedia() == atv.getVelMedia() && this
						.getVelMax() == atv.getVelMax());
		}
	}

	
	@Override
	public abstract AtvComDistancia clone();

	
	@Override
	public String toString() {
		BigDecimal bd = new BigDecimal(this.velmedia).setScale(2,
				RoundingMode.HALF_EVEN);
		return (super.toString() + "\nDistância Percorrida: " + this.distancia
				+ " km\n" + "Velocidade Média: " + bd + " km/h\n"
				+ "Velocidade Máxima: " + this.velmax + " km\n\n");
	}

	/**
	 * Método para o cálculo da velocidade média dada a distãncia percorrida e a duração.
	 */
	public double calcularVelocidadeMedia(double distancia, Time duracao) {
		// converter duração em minutos para horas
		double horas = (duracao.duracaoEmMinutos()) / 60;
		return (double) distancia / horas; // velocidade média em km/h
	}

	/**
	 * Recolher um score pela atividade média
	 */
	public double getScore() {
		return this.getVelMedia();
	}

	public abstract int calcularCalorias(int idade, int altura, String genero,Time duracao, int peso);
}
