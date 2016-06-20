package atividades;

/**
 * Uma interface que fornece um tipo de dados para todas as atividades em que
 * calculamos o score com base na distãncia percorrida e tempo
 * 
 * @author jdc
 * @version 3/06/2014
 */

public interface Distancia {
	public double getDist();
	public Distancia clone();
	public String getNome();
	public double getScore();
	public int duracaoEmMinutos();
	public double getVelMedia();
	public Time getDuracao();
}