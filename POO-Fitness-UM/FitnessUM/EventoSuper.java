/**
 * SuperClasse para eventos, define os nomes e datas, campos básicos comuns aos 2 tipos de eventos.
 * 
 * @author jdc
 * @version 03/06/2014
 */

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

public abstract class EventoSuper implements Serializable {
    // VariÃ¡veis de instãncia
    private String nome;                        // nome de evento
    private GregorianCalendar data;             // data de realização do evento
    private GregorianCalendar finalinscricoes;  // data limite das inscrições
    private int limite;                         // nº limite máximo de inscrições
    private double dist;                        // distãncia da prova
    private String resultado;

    // Construtores
    public EventoSuper() {
        this.nome = "";
        this.data = new GregorianCalendar();
        finalinscricoes = new GregorianCalendar();
        limite = 0;
        this.dist = 0;
        this.resultado="";
    }

    /* Construtor preferido */
    public EventoSuper(String nome, GregorianCalendar data,
            GregorianCalendar datalim, int limite, double dist) {

        this.nome = nome;
        this.data = new GregorianCalendar(data.get(Calendar.YEAR),
                data.get(Calendar.MONTH), data.get(Calendar.DAY_OF_MONTH));
        this.finalinscricoes = new GregorianCalendar(
                datalim.get(Calendar.YEAR), datalim.get(Calendar.MONTH),
                datalim.get(Calendar.DAY_OF_MONTH));
        this.limite = limite;
        this.dist = dist;
    }

    public EventoSuper(EventoSuper evento) {
        this.nome = evento.getNome();
        this.data = (GregorianCalendar) evento.getData();
        this.finalinscricoes = (GregorianCalendar) evento
                .getDataLimiteInscricoes();
        this.limite = evento.getLimiteInsc();
        this.dist = evento.getDist();
        this.resultado=evento.getResultado();
    }

    
    // Métodos de instãncia

    // gets & sets
    public String getNome() {return this.nome;}
    public Object getData() {return this.data.clone();}
    public Object getDataLimiteInscricoes() {return this.finalinscricoes.clone();}
    public int getLimiteInsc() {return this.limite;}
    public double getDist() {return this.dist;}      
    public String getResultado(){ return this.resultado;}
    
    public void setResultado(String r){this.resultado=r;}

    // equals, clone e toString
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        else if (o == null || o.getClass() != this.getClass())
            return false;

        else {
            EventoSuper evento = (EventoSuper) o;

            // Dois eventos são iguais se têm o mesmo nome, 2 eventos podem ter
            // nomes diferentes mas terem exatamento os mesmo concorrentes
            if (this.nome.equals(evento.getNome()))
                return true;
            else
                return false;
        }
    }

    // É IMPERATIVO: que método clone esteja bem definido para subclasse
    // EventoSimples para que cada utilizador tenha o seu estado Unico e mutável
    @Override
    public abstract EventoSuper clone();

    // toString devolve o evento detalhado
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("\n######### " + this.nome + " #########\n");

        sb.append("Data de realização do evento: "
                + (this.data.get(Calendar.DAY_OF_MONTH) + 1) + "/"
                + this.data.get(Calendar.MONTH) + "/"
                + this.data.get(Calendar.YEAR) + "\n");
        sb.append("Data limite das inscrições: "
                + (this.finalinscricoes.get(Calendar.DAY_OF_MONTH) + 1) + "/"
                + this.finalinscricoes.get(Calendar.MONTH) + "/"
                + this.finalinscricoes.get(Calendar.YEAR) + "\n");
        sb.append("Nº limite de inscrições: " + this.limite + "\n");
        sb.append("Distância da prova: " + this.dist + " km\n\n");

        return sb.toString();
    }

}