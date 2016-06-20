package scores;

/**
 *Comparator para scores de atividades que apenas consideram a duração da atividade
 * 
 * @author jdc
 * @version 16/05/2014
 */

import java.io.Serializable;
import java.util.Comparator;

import atividades.Atividade;

public class ScoreTimeComparator implements Comparator<Atividade>, Serializable {

	@Override
	public int compare(Atividade a1, Atividade a2) {
		double s1 = a1.getScore().duracaoEmMinutos();
		double s2 = a2.getScore().duracaoEmMinutos();

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
