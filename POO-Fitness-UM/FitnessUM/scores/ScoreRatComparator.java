package scores;

/**
 *Comparator para scores de atividades que contemplam um rating qualitativo.
 * 
 * @author jdc
 * @version 16/05/2014
 */

import java.io.Serializable;
import java.util.Comparator;

import atividades.Rating;

public class ScoreRatComparator implements Comparator<Rating>, Serializable {

	@Override
	public int compare(Rating a1, Rating a2) {
		double s1 = a1.getRating();
		double s2 = a2.getRating();

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
