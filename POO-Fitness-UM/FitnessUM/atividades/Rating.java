package atividades;

/**
 * Uma interface que fornece um tipo de dados para todas as atividades em que
 * calculamos o score com base num rating qualitativo
 * 
 * @author jdc
 * @version 19/05/2014
 */

public interface Rating {
	public double getRating();
	public abstract Rating clone();
	public String getNome();
	public int duracaoEmMinutos();
	public Time getDuracao();
}