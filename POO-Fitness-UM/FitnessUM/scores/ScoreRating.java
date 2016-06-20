package scores;

/**
 * Classe que faz o registo de melhores scores para atividades que respondem a métodos da interface Rating.
 * 
 * @author jdc
 * @version 23/05/2014
 */

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

import atividades.AtvBase;
import atividades.Rating;

public class ScoreRating implements Serializable {

	// Variáveis de instãncia
	private Map<String, TreeSet<Rating>> scores;

	// Construtores
	public ScoreRating() {
		this.scores = new HashMap<>();
		TreeSet<Rating> aux = new TreeSet<Rating>(new ScoreRatComparator());
		this.scores.put("", aux);
	}

	public ScoreRating(Map<String, TreeSet<Rating>> scores) {
		this.scores = new HashMap<>();

		for (Map.Entry<String, TreeSet<Rating>> entry : scores.entrySet()) {
			TreeSet<Rating> tree = new TreeSet<>(new ScoreRatComparator());

			for (Rating atv : entry.getValue())
				tree.add(atv.clone());

			this.scores.put(entry.getKey(), tree);
		}
	}

	public ScoreRating(ScoreRating sr) {
		this(sr.getScores());
	}

	// Métodos de instÃ¢ncia

	// gets e sets

	/**
	 * Retorna cópia do map de scores
	 */
	public HashMap<String, TreeSet<Rating>> getScores() {
		HashMap<String, TreeSet<Rating>> aux = new HashMap<>();

		for (Map.Entry<String, TreeSet<Rating>> entry : scores.entrySet()) {
			TreeSet<Rating> tree = new TreeSet<>();

			for (Rating atv : entry.getValue())
				tree.add(atv.clone());

			aux.put(entry.getKey(), tree);
		}

		return aux;
	}

	
	/**
	 * Retorna TreeSet de scores da atividade cujo nome é passado como parâmetro
	 */
	public TreeSet<Rating> getAtividadesScore(String nomeatividade) {
		TreeSet<Rating> aux = new TreeSet<Rating>(new ScoreRatComparator());

		if (!this.scores.containsKey(nomeatividade)) {
			return aux;
		} else {

			for (Rating atv : this.scores.get(nomeatividade))
				// iterar sobre os scores da atividade passada como parÃ¢metro
				aux.add(atv.clone());

			return aux;
		}
	}

	// equals, clone e toString
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		else if (o == null || this.getClass() != o.getClass())
			return false;

		else {
			ScoreRating sc = (ScoreRating) o;

			if (sc.getNumAtividades() != this.getNumAtividades())
				return false;

			else {
				Iterator<Map.Entry<String, TreeSet<Rating>>> itthis = this.scores
						.entrySet().iterator();
				while (itthis.hasNext()) {
					Map.Entry<String, TreeSet<Rating>> entry = itthis.next();

					TreeSet<Rating> aux = getAtividadesScore(entry.getKey());

					Iterator<Rating> it1 = entry.getValue().iterator();
					Iterator<Rating> it2 = aux.iterator();

					while (it1.hasNext()) {
						if (!it2.hasNext())
							return false;
						else if (it1.next().getRating() != it2.next()
								.getRating())
							return false;
					}
				}

				return true;
			}
		}
	}

	@Override
	public ScoreRating clone() {
		return new ScoreRating(this);
	}

	/**
	 * Score board
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		Iterator<Map.Entry<String, TreeSet<Rating>>> it = this.scores
				.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, TreeSet<Rating>> entry = it.next();
			String nome = entry.getKey();
			TreeSet<Rating> aux = entry.getValue();

			if (!nome.equals("")) {
				sb.append("\n###### RECORD PESSOAL: " + nome + " ######\n");
			}

			int flag = 0;
			for (Rating atv : aux) {
				if (flag != 0)
					break;
				else
					flag++;

				sb.append("Rating " + atv.getRating() + "\n");
			}
		}

		return sb.toString();
	}

	/**
	 * Score dado nome da atividade
	 */
	public String scoreDadoNomeAtividade(String a) {
		String score = "VAZIO\n";
		if (this.scores.containsKey(a)) {
			TreeSet<Rating> aux = this.scores.get(a);

			for (Rating atv : aux) {
				score = ("\n###### RECORD PESSOAL: " + a + " ######\n"
						+ "Rating: " + atv.getRating() + "\n");
				break;
			}
			return score;
		} else
			return score;
	}

	/**
	 * Adicionar uma atividade aos scores
	 */
	public void addAtividadeScore(AtvBase a) {
		Rating atv = (Rating) a;

		if (atv == null)
			return;

		if (this.scores.containsKey(a.getNome())) {
			TreeSet<Rating> aux = this.scores.get(atv.getNome());
			aux.add(atv);
			this.scores.put(a.getNome(), aux); // repor Set modificado na Ã¡rvore
		} else {
			TreeSet<Rating> aux = new TreeSet<Rating>(new ScoreRatComparator());
			aux.add(atv);
			this.scores.put(a.getNome(), aux); // colocar uma nova
												// correspondÃªncia na Ã¡rvore
		}
	}

	
	/**
	 * O melhor rating
	 */
	public double getMelhor(String a) {

		double score = 0;

		if (this.scores.containsKey(a)) {
			TreeSet<Rating> aux = this.scores.get(a);

			for (Rating atv : aux) {
				score = atv.getRating();
				break;
			}
			return score;
		} else
			return score;
	}

	/**
	 * O pior rating
	 */
	public double getPior(String a) {

		double score = 0;

		if (this.scores.containsKey(a)) {
			TreeSet<Rating> aux = this.scores.get(a);

			for (Rating atv : aux) {
				score = atv.getRating();
			}
			return score; // score fica com o Ãºltimo valor no set, i.e o pior tempo
		} else
			return score;
	}

	
	/**
	 * O rating médio das prestações
	 */
	public double getMed(String a) {

		double media = 0;

		if (this.scores.containsKey(a)) {
			TreeSet<Rating> aux = this.scores.get(a);

			for (Rating atv : aux) {
				media += atv.getRating(); // faz get da velocidade mÃ©dia de cada
											// atividade
			}

			media = (media / (double) aux.size());

			return media;
		}

		return media;
	}

	/**
	 * Número de atividades comparáveis por rating
	 */
	public int getNumAtividades() {
		return this.scores.size();
	}

}
