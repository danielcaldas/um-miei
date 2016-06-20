package scores;

/**
 *Comparator para scores de atividades que contemplam distãncia
 * 
 * @author jdc
 * @version 16/05/2014
 */

import java.io.Serializable;
import java.util.Comparator;

import atividades.Distancia;

public class ScoreDistComparator implements Comparator<Distancia>, Serializable {

	@Override
	public int compare(Distancia a1, Distancia a2) {
		double s1 = a1.getScore();
		double s2 = a2.getScore();

		if (s1 > s2)
			return -1;
		else if (s1 < s2)
			return 1;
		else if (a1.duracaoEmMinutos() > a2.duracaoEmMinutos())
			return -1;
		else if (a2.duracaoEmMinutos() < a2.duracaoEmMinutos())
			return 1;
		else
			return 0;
	}
}
