package scores;

/**
 * Class que armazena os scores de um dado utilizador. Os scores são de três tipos: Duração, Distância e Rating (qualitativo)
 * 
 * @author jdc
 * @version 01/06/2014
 */

import java.io.Serializable;

import atividades.Atividade;
import atividades.AtvBase;
import atividades.Distancia;
import atividades.Rating;

public class Score implements Serializable {

	// Variáveis de instãncia
	private ScoreDistancia scoredist;
	private ScoreDuracao scoreduracao;
	private ScoreRating scorerating;

	// Construtores
	public Score() {
		this.scoredist = new ScoreDistancia();
		this.scoreduracao = new ScoreDuracao();
		this.scorerating = new ScoreRating();
	}

	public Score(ScoreDistancia sd, ScoreRating sr, ScoreDuracao sdr) {
		this.scoredist = sd.clone();
		this.scorerating = sr.clone();
		this.scoreduracao = sdr.clone();
	}

	public Score(Score s) {
		this.scoredist = new ScoreDistancia(s.getScoreDist());
		this.scorerating = new ScoreRating(s.getScoreRating());
		this.scoreduracao = new ScoreDuracao(s.getScoreDuracao());
	}

	// Métodos de instãncia
	public ScoreDistancia getScoreDist() {return this.scoredist.clone();}
	public ScoreRating getScoreRating() {return this.scorerating.clone();}
	public ScoreDuracao getScoreDuracao() {	return this.scoreduracao.clone();}

	// equals, clone e toString
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		else if (o == null || this.getClass() != o.getClass())
			return false;

		else {
			Score score = (Score) o;

			return (this.scoredist.equals(score.getScoreDist())
					&& this.scoreduracao.equals(score.getScoreDuracao()) && this.scorerating
						.equals(score.getScoreRating()));
		}
	}

	@Override
	public Score clone() {
		return new Score(this);
	}

	
	
	/*-------------------------------------------------------------------------------------------------------------------------------------*/
	/*
	 * Métodos para comunicar com classes de score apropriadas num nível de construção inferior
	 */
	/*-------------------------------------------------------------------------------------------------------------------------------------*/
	
	
	

	/**
	 * Adiciona um score conforme a atividade recebeida for comparável por Distancia, Duração e Rating respetivamente
	 */
	public void addScore(AtvBase atv) {
		if (atv instanceof Distancia) {
			this.scoredist.addAtividadeScore(atv);
		} else if (atv instanceof Atividade) {
			this.scoreduracao.addAtividadeScore(atv);
		} else if (atv instanceof Rating) {
			this.scorerating.addAtividadeScore(atv);
		}
	}

	/**
	 * Chama o método que imprime scoreBoard de uma atividade que é comparável pela distãncia
	 */
	public String toStringDist() {return this.scoredist.toString();}
	
	
	/**
	 * Chama método que imprime scoreBoard de uma atividade que é comparável pelo rating
	 */
	public String toStringRat() {return this.scorerating.toString();}

	/**
	 * Chama método que imprime scoreBoard de uma atividade que é comparável pela duração
	 */
	public String toStringDur() {return this.scoreduracao.toString();}

	
	
	/**
	 * Score dada atividade
	 */
	public String scoreDadoNomeAtividade(AtvBase atv) {
		if (atv instanceof Distancia) {
			return this.scoredist.scoreDadoNomeAtividade(atv.getNome());
		} else if (atv instanceof Atividade) {
			return this.scoreduracao.scoreDadoNomeAtividade(atv.getNome());
		} else if (atv instanceof Rating) {
			return this.scorerating.scoreDadoNomeAtividade(atv.getNome());
		}

		return "";
	}

	/**
	 * Melhor score dada atividade
	 */
	public double getMelhor(AtvBase atv) {
		if (atv instanceof Distancia) {
			return this.scoredist.getMelhor(atv.getNome());
		} else if (atv instanceof Atividade) {
			return this.scoreduracao.getMelhor(atv.getNome());
		} else if (atv instanceof Rating) {
			return this.scorerating.getMelhor(atv.getNome());
		}

		return 0;
	}

	
	/**
	 * Pior score dada atividade
	 */
	public double getPior(AtvBase atv) {
		if (atv instanceof Distancia) {
			return this.scoredist.getPior(atv.getNome());
		} else if (atv instanceof Atividade) {
			return this.scoreduracao.getPior(atv.getNome());
		} else if (atv instanceof Rating) {
			return this.scorerating.getPior(atv.getNome());
		}

		return 0;
	}

	
	/**
	 * Score médio
	 */
	public double getMed(AtvBase atv) {
		if (atv instanceof Distancia) {
			return this.scoredist.getMed(atv.getNome());
		} else if (atv instanceof Atividade) {
			return this.scoreduracao.getMed(atv.getNome());
		} else if (atv instanceof Rating) {
			return this.scorerating.getMed(atv.getNome());
		}

		return 0;
	}

	/**
	 * Número total de atividades em score (trata-se do nº de atividades diferentes que o utilizador pratica)
	 */
	public int getTotalAtividadesEmScore() {
		return this.scoredist.getNumAtividades()
				+ this.scoreduracao.getNumAtividades()
				+ this.scorerating.getNumAtividades();
	}

}
