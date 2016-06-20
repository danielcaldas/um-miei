package atividades;

/**Comparator para Atividades, como estas têm de estar organizadas por ordem cronológica, comparamos as datas das atividades (sem segundo plano o nome das mesmas)
 *
 * @author jdc
 * @version 02/05/2014
 */

import java.io.Serializable;
import java.util.Comparator;
import java.util.GregorianCalendar;

public class AtividadeComparator implements Comparator<AtvBase>, Serializable {

	@Override
	public int compare(AtvBase a1, AtvBase a2) {
		GregorianCalendar data1 = (GregorianCalendar) a1.getData();
		GregorianCalendar data2 = (GregorianCalendar) a2.getData();
		int aux = data2.compareTo(data1);

		if (aux == 0) {
			return a1.getNome().compareTo(a2.getNome());
		}
		else return aux;
	}
}
