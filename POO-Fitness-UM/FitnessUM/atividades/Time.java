package atividades;

/**Classe para manipulação de tempos com o formato hh:mm:ss (horas:minutos:segundos)
 *
 * @author jdc
 * @version 1/05/2014
 * 
 * @author JosÃ© Francisco
 * @version 2/06/2014
 */

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.io.Serializable;

public class Time implements Serializable {
    // Variáveis de instÃ¢ncia de um marcador de tempo
    private int horas;
    private int minutos;
    private int segundos;

    // Construtores
    public Time() {
        this.horas = 0;
        this.minutos = 0;
        this.segundos = 0;
    }

    public Time(int horas, int minutos, int segundos) {
        this.horas = horas;
        this.minutos = minutos;
        this.segundos = segundos;
    }

    public Time(Time t) {
        this.horas = t.getHoras();
        this.minutos = t.getMinutos();
        this.segundos = t.getSegundos();
    }

    // gets & sets
    public int getHoras() {return this.horas;}
    public int getMinutos() {return this.minutos;}
    public int getSegundos() {return this.segundos;}
    public void setHoras(int horas) {this.horas = horas;}
    public void setMinutos(int minutos) {this.minutos = minutos;}
    public void setSegundos(int segundos) {this.segundos = segundos;}

    // Métodos de instÃ¢ncia
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        else if (this.getClass() != o.getClass())
            return false;

        else {
            Time time = (Time) o;
            return (this.horas == time.getHoras()&& this.minutos == time.getMinutos() && this.segundos == time.getSegundos());
        }
    }

    @Override
    public String toString() {
        return (this.horas + "h" + ":" + this.minutos + "m" + ":"
                + this.segundos + "s"); // hh:mm:ss
    }

    @Override
    public Time clone() {
        return new Time(this);
    }

    // Determinar a duração da atividade em minutos (retorna um inteiro)
    public double duracaoEmMinutos() {
        double min = (double) this.getHoras() * 60;
        min += this.getMinutos();
        min += (double) this.getSegundos() / 60;
        return min;
    }

    /**
     * Método que compara objetos time
     */
    public int maiorDuracaoQue(Time t) {
        if (this.getHoras() > t.getHoras())
            return 1;
        else if (this.getHoras() == t.getHoras()
                && this.getMinutos() > t.getMinutos())
            return 1;
        else if (this.getHoras() == t.getHoras()
                && this.getMinutos() == t.getMinutos()
                && this.getSegundos() > t.getSegundos())
            return 1;
        else if (this.getHoras() == t.getHoras()
                && this.getMinutos() == t.getMinutos()
                && this.getSegundos() == t.getSegundos())
            return 0;
        else
            return -1;
    }
    
    
    /**
     * Método que adiciona tempo em minutos a um objecto Time (muito util no contexto dos eventos)
     */
    public void addTime(double minutos){
        GregorianCalendar c = new GregorianCalendar();
        
        double aux;
        
        aux = minutos - (int) minutos;      // 0,2 min -----> x
        aux*=60;                            // 1 min   -----> 60s regra trÃªs simples para cÃ¡lculo dos segundo<
        
        c.set(1,1,1,this.horas,this.minutos,this.segundos);
        c.add(Calendar.SECOND,((int)minutos*60));
        this.horas = c.get(Calendar.HOUR_OF_DAY);
        this.minutos = c.get(Calendar.MINUTE);
        this.segundos = c.get(Calendar.SECOND);
        this.segundos = (int)aux;

    }
}