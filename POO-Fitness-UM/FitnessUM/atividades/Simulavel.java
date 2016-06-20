package atividades;

/**
 * Uma interface que fornece um tipo de dados para todas as atividades que a API FitnessUM consegue simular.
 * 
 * @author jdc
 * @version 05/06/2014
 */

public interface Simulavel extends Distancia  {
	public double getIncerteza();
}
