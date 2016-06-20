 
/**Comparator para participantes de um evento, comparamos pelo tempo de prova. (aquele com menor tempo de prova fica no topo da tabela)
 *
 * @author jdc
 * @version 05/06/2014
 */

import java.io.Serializable;
import java.util.Comparator;
import java.util.GregorianCalendar;

public class ParticipanteComparator implements Comparator<Participante>, Serializable {

	@Override
	public int compare(Participante p1, Participante p2) {
		double r1 = p1.getTime();
		double r2 = p2.getTime();
		
		if(r1>r2) return 1;
		else if(r1<r2) return -1;
		else return p1.getNome().compareTo(p2.getNome());
	}
}
