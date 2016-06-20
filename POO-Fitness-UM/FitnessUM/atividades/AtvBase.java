package atividades;

/**SuperClasse abstrata que define o que será comum a todo o tipo de atividades.
 *
 * @author jdc
 * @version 20/05/2014
 */

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

public abstract class AtvBase implements Serializable {
    
    // Variéveis de instãncia comuns a todo o tipo de atividades
    private Time duracao; // duração
    private int calorias; // calorias gastas/queimadas (em Kcal)
    private double hidratacao; // quantidade de água perdida (em Litros)
    private GregorianCalendar data; // data de realização da atividade

    // Construtores
    public AtvBase() {
        this.duracao = new Time();
        this.hidratacao = 0;
        this.calorias = 0;
        this.data = new GregorianCalendar();
    }

    public AtvBase(Time duracao, int idade, int peso, int altura,String genero, GregorianCalendar data, double hidratacao) {
        this.duracao = duracao;
        this.hidratacao = hidratacao;
        this.data = data;
        this.calorias = calcularCalorias(idade, altura, genero, duracao, peso);
    }

    public AtvBase(AtvBase atv) {
        this.duracao = atv.getDuracao();
        this.hidratacao = atv.getH2O();
        this.calorias = atv.getKcal();
        this.data = (GregorianCalendar) atv.getData();
    }

    // Métodos de instãncia

    // gets
    public Time getDuracao() {return this.duracao.clone();}
    public double getH2O() {return this.hidratacao;}
    public int getKcal() {return this.calorias;}
    public Object getData() {return this.data.clone();}
    public abstract  String getNome();
    
    // equals, clone e toString
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        else if (o == null || this.getClass() != o.getClass())
            return false;

        else {
            AtvBase atv = (AtvBase) o;
            return (this.duracao.equals(atv.getDuracao())
                    && this.hidratacao == atv.getH2O()
                    && this.calorias == atv.getKcal() && this.data.equals(atv
                    .getData()));
        }
    }

    @Override
    public abstract AtvBase clone();

    @Override
    public String toString() {
        return ("\n#########REGISTO DE ATIVIDADE - " + this.getNome()
                + "#########\n" + "Desporto/Atividade: " + this.getNome()+ "\n"
                + "Duração: " + this.duracao + "\n" + "Hidratação: "
                + this.hidratacao + " L\n" + "Calorias gastas: "
                + this.calorias + " kcal\n" + "Data: "
                + this.data.get(Calendar.DAY_OF_MONTH) + "/"
                + (this.data.get(Calendar.MONTH) + 1) + "/"
                + this.data.get(Calendar.YEAR) + "\n");
    }

    /**
     * Método que devolve uma String que é um cabeçalho da atividade
     */
    public String tituloAtividade() {
        return ("Desporto/Atividade: " + this.getNome() + "  Data: "
                + this.data.get(Calendar.DAY_OF_MONTH) + "/"
                + (this.data.get(Calendar.MONTH) + 1) + "/"
                + this.data.get(Calendar.YEAR) + "\n");
    }

    
    /**
     * Método que devolve a duração da atividade em minutos (int)
     */
    public int duracaoEmMinutos() {
        return (int) this.getDuracao().duracaoEmMinutos();
    }


    /**
     * Método abstractto do cÃ¡lculo de calorias a ser concretizado nas classes que instanciam atividades
     */
    public abstract int calcularCalorias(int idade, int altura, String genero,Time duracao, int peso);

}
