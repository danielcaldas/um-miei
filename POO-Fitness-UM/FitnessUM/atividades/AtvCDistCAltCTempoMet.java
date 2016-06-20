package atividades;

/**Classe para atividades como: Ciclismo, Corrida, Maratona, Canoagem etc. ...
 *
 * @author jdc
 * @version 01/05/2014
 */

import java.io.Serializable;
import java.util.GregorianCalendar;

public abstract class AtvCDistCAltCTempoMet extends AtvComDistancia implements Serializable {
    
	// Variáveis de instância complementares de altitude (em metros)
	private String tempomet;    // curta descrição do tempo metereológico no
								// decorrer da atividade
	private int altitudemax;
	private int altitudemin;
	private int totalsubido;
	private int totaldescido;

	// Construtores
	public AtvCDistCAltCTempoMet() {
		super();
		this.altitudemax = 0;
		this.altitudemin = 0;
		this.totalsubido = 0;
		this.totaldescido = 0;
		this.tempomet = "";
	}

	public AtvCDistCAltCTempoMet(Time duracao, int idade,int peso, int altura, String genero, GregorianCalendar data,
			double hidratacao, double distancia, double velmax, int altmax,
			int altmin, int tsub, int tdes, String tempomet) {
		super(duracao, idade, peso, altura, genero, data, hidratacao,distancia, velmax);
		this.altitudemax = altmax;
		this.altitudemin = altmin;
		this.totalsubido = tsub;
		this.totaldescido = tdes;
		this.tempomet = tempomet;
	}

	public AtvCDistCAltCTempoMet(AtvCDistCAltCTempoMet atvcdca) {
		super(atvcdca);
		this.altitudemax = atvcdca.getMaxAltitude();
		this.altitudemin = atvcdca.getMinAltitude();
		this.totalsubido = atvcdca.getTotalSubido();
		this.totaldescido = atvcdca.getTotalDescido();
		this.tempomet = atvcdca.getTempoMet();
	}

	// Métodos de instância

	// gets
	public int getMaxAltitude() {return this.altitudemax;}
	public int getMinAltitude() {return this.altitudemin;}
	public int getTotalSubido() {return this.totalsubido;}
	public int getTotalDescido() {return this.totaldescido;}
	public String getTempoMet() {return this.tempomet;}
	public abstract String getNome();

	// equals, clone e toString
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		else if (o == null || this.getClass() != o.getClass())
			return false;

		else {
			AtvCDistCAltCTempoMet atv = (AtvCDistCAltCTempoMet) o;
			return (super.equals(atv)
					&& this.getMaxAltitude() == atv.getMaxAltitude()
					&& this.getMinAltitude() == atv.getMinAltitude()
					&& this.totaldescido == atv.totaldescido
					&& this.totalsubido == atv.totalsubido && this.tempomet
						.equals(atv.getTempoMet()));
		}
	}

	@Override
	public abstract AtvCDistCAltCTempoMet clone();

	@Override
	public String toString() {
		return (super.toString() + "Min Altitude: " + this.altitudemin
				+ " m   Max Altitude: " + this.altitudemax + " m\n"
				+ "Total Ascendido: " + this.totalsubido
				+ " m  Total Descido: " + this.totaldescido + " m\n"
				+ "Descri��o do tempo: " + this.getTempoMet());
	}

	@Override
	public abstract int calcularCalorias(int idade, int altura, String genero,Time duracao, int peso);

}
