package business.doacoes;

import java.util.GregorianCalendar;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

/** Classe que agrega informação sobre um evento de angariação.
 *
 * @author Jorge Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
 * @version 29.12.2014
 */

class Evento implements IEvento {
    private int nr;
    private String designacao;
    private int nrPessoas;
    private GregorianCalendar dataRealizacao;
    private float totalAngariado;
    private String notas;
    private Set<Integer> donativos;
    
    /**
     * Construtor vazio
     */
    public Evento ()
    {
        //Variáveis de instância
        this.nr = 0;
        this.nrPessoas = 0;
        this.dataRealizacao = new GregorianCalendar();
        this.totalAngariado = 0;
        this.designacao = "";
        this.notas = "";
        this.donativos = new HashSet();
    }
        /**Construtor parametrizado
         * 
         * @param nr Número de identificação
         * @param nrP Número de pessoas presentes
         * @param dataR Data de realização
         * @param total Dinheiro total angariado
         * @param designacao
         * @param notas Notas adicionais 
         * @don Donativos angariados 
         */
        public Evento (int nr, int nrP, GregorianCalendar dataR, float total, String designacao, String notas,
                Set<Integer> don)
        {
            this.nr = nr;
            this.nrPessoas = nrP;
            this.dataRealizacao = dataR;
            this.totalAngariado = total;
            this.designacao = designacao;
            this.notas = notas;
            this.donativos = don;
        }
        
        /**Construtor parametrizado
         * 
         * @param e Um evento
         */
        public Evento (Evento e)
        {
            this.nr = e.getNr();
            this.nrPessoas = e.getNrPessoas();
            this.dataRealizacao = e.getDataRealizacao();
            this.totalAngariado = e.getTotalAngariado();
            this.designacao = e.getDesignacao();
            this.notas = e.getNotas();
            this.donativos = e.getDonativos();
        }
        
        /*Métodos get*/
        @Override
        public int getNr () { return this.nr;}
        @Override
        public int getNrPessoas () { return this.nrPessoas;}
        @Override
        public GregorianCalendar getDataRealizacao() { return this.dataRealizacao;}
        @Override
        public float getTotalAngariado() { return this.totalAngariado;}
        @Override
        public String getDesignacao () {return this.designacao;}
        @Override
        public String getNotas () { return this.notas;}
        @Override
        public Set<Integer> getDonativos () {return this.donativos;}
        /*Métodos set*/
        @Override
        public void setNr (int nr) { this.nr = nr;}
        @Override
        public void setNrPessoas (int nrP) {this.nrPessoas = nrP;}
        @Override
        public void setDataRealizacao(GregorianCalendar data) { this.dataRealizacao = data;}
        @Override
        public void setTotalAngariado (float total) { this.totalAngariado = total;}
        @Override
        public void setDesignacao (String des) { this.designacao = des;}
        @Override
        public void setNotas (String notas) {this.notas = notas;}
        @Override
        public void setDonativos (Set<Integer> don) {this.donativos = don;}
        
        
        @Override
        public boolean equals (Object o)
        {
            if (o==null) return false;
            if (o==this || this.getClass()!=o.getClass()) return true;
            Evento e = (Evento) o;
            
            for (Integer i : donativos)
                if (!e.getDonativos().contains(i)) return false;
            return (this.nr == e.getNr() && this.nrPessoas == e.getNrPessoas()
                    && this.dataRealizacao.equals(e.getDataRealizacao()) && this.totalAngariado == e.getTotalAngariado()
                    && this.designacao.equals(e.getDesignacao()) && this.notas.equals(e.getNotas()));
        }
        
        @Override
        public IEvento clone ()
        {
            return new Evento(this);
        }
        
        @Override
        public int hashCode ()
        {
            return Arrays.hashCode (new Object[] {this.nr, this.nrPessoas, this.dataRealizacao, this.totalAngariado,
                this.designacao, this.notas, this.donativos});
        }
    
}
