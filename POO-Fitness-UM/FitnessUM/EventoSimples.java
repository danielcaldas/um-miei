 

/**
 * Classe que permite que um user tenha na sua conta acesso a dados do evento de um modo protegido i.e não tem
 * acesso aos mesmo dados do administrador, ao mesmo tempo que o utilizador possui um estado (boolean status) que indica ao administrador
 * se o utilizador se inscreveu ou não no evento
 * 
 * @author jdc
 * @version 18/05/2014
 */

import java.io.Serializable;
import java.util.GregorianCalendar;

public class EventoSimples extends EventoSuper implements Serializable {
    // Variáveis de instãncia
    private boolean status;    // aceite ou não aceite por parte do utilizador
    private String resultado;  // resultado que é disponibilizado pela administraçãoo a todos os participantes de um dado evento

    // Construtores
    public EventoSimples() {
        super();
        this.status = false;
        this.resultado="";
    }

    /* Construtor preferido */
    public EventoSimples(String nome, GregorianCalendar data,GregorianCalendar datalim, int limite, double dist) {
        super(nome, data, datalim, limite, dist);
        this.status = false;
        this.resultado="";
    }

    public EventoSimples(EventoSimples evento) {
        super(evento.getNome(), (GregorianCalendar) evento.getData(),(GregorianCalendar) evento.getDataLimiteInscricoes(), evento.getLimiteInsc(),
        evento.getDist());
        this.status = evento.getStatus();
        this.resultado = evento.getResultado();
    }

    // Métodos de instãncia

    // gets & sets
    public boolean getStatus() {return this.status;}
    public void aceitarConvite() {this.status = true;}
    
    public String getResultado(){return this.resultado;}
    
    public void setResultado(String s){this.resultado=s;}

    // equals, clone e toString
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        else if (o == null || o.getClass() != this.getClass())
            return false;

        else {
            EventoSimples evento = (EventoSimples) o;

            // Dois eventos são iguais se têm o mesmo nome, 2 eventos podem ter
            // nomes diferentes mas terem exatamento os mesmo concorrentes
            if (super.equals(evento) && this.status == evento.getStatus()) return true;
            else return false;
        }
    }

    
    @Override
    public EventoSimples clone() {
        return new EventoSimples(this);
    }

    
    // toString devolve string com alguns detalhes bÃ¡sicos do evento (Tanto públicos como da administração)
    @Override
    public String toString() {
        if(this.resultado.equals("")){
            return super.toString();
        }
        else{
            StringBuilder sb = new StringBuilder();
            sb.append(super.toString());
            sb.append("\nResultados do Evento!\n");
            sb.append(this.resultado);
            return sb.toString();
        }
    }    

}