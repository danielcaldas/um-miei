package scores;

/**
 * Classe que faz o registo de melhores tempos em provas que contemplam distãncia.
 * 
 * @author jdc
 * @version 16/05/2014
 * 
 * @version 1/06/2014
 */

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

import atividades.AtvBase;
import atividades.Distancia;

public class ScoreDistancia implements Serializable {

	// Variáveis de instancia
	private Map<String, TreeSet<Distancia>> scores;

	// Construtores
	public ScoreDistancia() {
		this.scores = new HashMap<>();
		TreeSet<Distancia> aux = new TreeSet<>(new ScoreDistComparator());
		this.scores.put("", aux);
	}

	public ScoreDistancia(Map<String, TreeSet<Distancia>> scores) {
		this.scores = new HashMap<>();

		Iterator<Map.Entry<String, TreeSet<Distancia>>> it = scores.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, TreeSet<Distancia>> entry = it.next();

			TreeSet<Distancia> aux = new TreeSet<>(new ScoreDistComparator());

			Iterator<Distancia> ittree = entry.getValue().iterator();

			while (ittree.hasNext()) {
				Distancia atv = ittree.next();
				aux.add(atv.clone());
			}
			
			this.scores.put(entry.getKey(),aux);

		}
	}

	public ScoreDistancia(ScoreDistancia s) {
		this.scores = new HashMap<>();

		Iterator<Map.Entry<String, TreeSet<Distancia>>> it = this.scores.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, TreeSet<Distancia>> entry = it.next();

			TreeSet<Distancia> aux = new TreeSet<>(new ScoreDistComparator());

			Iterator<Distancia> ittree = entry.getValue().iterator();

			while (ittree.hasNext()) {
				Distancia atv = ittree.next();
				aux.add(atv.clone());
			}

			this.scores.put(entry.getKey(), aux);
		}
	}

	// Métodos de instãncia

	// gets

	/**
	 * Metodo que devolve copia dos scores de uma dada atividade que contempla distancia, passada como parametro
	 */
	public TreeSet<Distancia> getAtividadesScore(String nomeatividade) {
		TreeSet<Distancia> aux = new TreeSet<Distancia>(
				new ScoreDistComparator());

		if (!this.scores.containsKey(nomeatividade)) {
			return aux;
		} else {

			for (Distancia atv : this.scores.get(nomeatividade))
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
			ScoreDistancia sc = (ScoreDistancia) o;

			if (sc.getNumAtividades() != this.getNumAtividades())
				return false;

			else {
				Iterator<Map.Entry<String,TreeSet<Distancia>>> itthis = this.scores.entrySet().iterator();
				while (itthis.hasNext()) {
					Map.Entry<String, TreeSet<Distancia>> entry = itthis.next();

					TreeSet<Distancia> aux = getAtividadesScore(entry.getKey());

					Iterator<Distancia> it1 = entry.getValue().iterator();
					Iterator<Distancia> it2 = aux.iterator();

					while (it1.hasNext()) {
						if (!it2.hasNext())
							return false;
						else if (it1.next().getScore() != it2.next().getScore())
							return false;
					}
				}

				return true;
			}
		}
	}

	@Override
	public ScoreDistancia clone() {
		return new ScoreDistancia(this);
	}

	// toString devolve quadro com info de todas as atividades com score do utilizador
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		Iterator<Map.Entry<String, TreeSet<Distancia>>> it = this.scores.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, TreeSet<Distancia>> entry = it.next();
			String nome = entry.getKey();
			TreeSet<Distancia> aux = entry.getValue();

			if (!nome.equals("")) {
				sb.append("\n###### RECORD PESSOAL: " + nome + " ######\n");
			}

			int flag = 0;
			for (Distancia atv : aux) {
				if (flag != 0)
					break;
				else
					flag++;

				DecimalFormat f = new DecimalFormat("##.00");

				sb.append("Duração: " + atv.getDuracao() + "\n");
				sb.append("Distância percorrida: " + atv.getDist() + " km\n");
				sb.append("Velocidade média: " + f.format(atv.getVelMedia())
						+ " km/h\n\n");
			}
		}

		return sb.toString();
	}

	/**
	 * Método que devolve um score dado o nome de uma atividade
	 */
	public String scoreDadoNomeAtividade(String a) {
		String score = "VAZIO\n";
		if (this.scores.containsKey(a)) {
			TreeSet<Distancia> aux = this.scores.get(a);

			DecimalFormat f = new DecimalFormat("##.00");

			for (Distancia atv : aux) {
				score = ("\n###### RECORD PESSOAL: " + atv.getNome()
						+ " ######\n" + "Duração: " + atv.getDuracao() + "\n"
						+ "Distância percorrida: " + atv.getDist() + " km\n"
						+ "Velocidade média: " + f.format(atv.getVelMedia()) + " km/h\n\n");
				break;
			}
			return score;
		} else
			return score;
	}

	/**
	 *Método que permite adicionar um score de uma atividade que contempla distancia
	 */
	public void addAtividadeScore(AtvBase a) {
		Distancia atv = (Distancia) a;

		if (atv == null)
			return;

		if (this.scores.containsKey(atv.getNome())) {
			TreeSet<Distancia> aux = this.scores.get(atv.getNome());
			aux.add(atv);
			this.scores.put(atv.getNome(), aux);
		} else {
			TreeSet<Distancia> aux = new TreeSet<Distancia>(
					new ScoreDistComparator());
			aux.add(atv);
			this.scores.put(atv.getNome(), aux);
		}
	}

	/**
	 * Método que retorna o valor da melhor velocidade média (score)
	 */
	public double getMelhor(String a) {

		double score = 0;

		if (this.scores.containsKey(a)) {
			TreeSet<Distancia> aux = this.scores.get(a);

			DecimalFormat f = new DecimalFormat("##.00");

			for (Distancia atv : aux) {
				score = atv.getScore();
				break;
			}
			return score;
		} else
			return score;
	}

	
	/**
	 * Método que retorna pior prestação
	 */
	public double getPior(String a) {

		double score = 0;

		if (this.scores.containsKey(a)) {
			TreeSet<Distancia> aux = this.scores.get(a);

			for (Distancia atv : aux) {
				score = atv.getScore();
			}
			return score; // score fica com o Ãºltimo valor no set, i.e o pior
							// tempo
		} else
			return score;
	}

	
	/**
	 * Método que retorna tempo médio
	 */
	public double getMed(String a) {

		double media = 0;

		if (this.scores.containsKey(a)) {
			TreeSet<Distancia> aux = this.scores.get(a);

			for (Distancia atv : aux) {
				media += atv.getScore(); // faz get da velocidade mÃ©dia de cada
											// atividade
			}

			media = (media / (double) aux.size());

			return media;
		}

		return media;
	}

	
	/**
	 * Método que return número de atividades diferentes no Map de scores de Distancia
	 */
	public int getNumAtividades() {
		return this.scores.size();
	}

}
