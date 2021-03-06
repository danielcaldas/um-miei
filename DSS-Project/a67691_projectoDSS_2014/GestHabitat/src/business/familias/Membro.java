package business.familias;

import java.util.GregorianCalendar;
import java.util.Arrays;

/**
 *
 * @author Jorge Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
 * @version 30.12.2014
 */

class Membro implements IMembro{

    
    private int id; //não esta no Diagrama de classe
    private String nome;
    private String parentesco;
    private GregorianCalendar dataNasc;
    private String estadoCivil;
    private String escolaridade;
    private String ocupacao;
    
    //Construtores
    /**
     * 
     * @param id
     * @param nome
     * @param parentesco
     * @param dataNasc
     * @param estadoCivil
     * @param escolaridade
     * @param ocupacao 
     */
    public Membro(int id, String nome, String parentesco, GregorianCalendar dataNasc, 
            String estadoCivil, String escolaridade, String ocupacao) {
        this.id = id;
        this.nome = nome;
        this.parentesco = parentesco;
        this.dataNasc = dataNasc;
        this.estadoCivil = estadoCivil;
        this.escolaridade = escolaridade;
        this.ocupacao = ocupacao;
    }
    public Membro(){
        this.id = 0;
        this.nome = "";
        this.parentesco = "";
        this.dataNasc = new GregorianCalendar();
        this.estadoCivil = "";
        this.escolaridade = "";
        this.ocupacao = "";
    }
    public Membro(Membro m){
        this.id = m.getId();
        this.nome = m.getNome();
        this.parentesco = m.getParentesco();
        this.dataNasc = m.getDataNasc();
        this.estadoCivil = m.getEstadoCivil();
        this.escolaridade = m.getEscolaridade();
        this.ocupacao = m.getOcupacao();
    }
    
    //Get`s e Set`s
    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
    
    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String getParentesco() {
        return parentesco;
    }

    @Override
    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    @Override
    public GregorianCalendar getDataNasc() {
        return dataNasc;
    }

    @Override
    public void setDataNasc(GregorianCalendar dataNasc) {
        this.dataNasc = dataNasc;
    }

    @Override
    public String getEstadoCivil() {
        return estadoCivil;
    }

    @Override
    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    @Override
    public String getEscolaridade() {
        return escolaridade;
    }

    @Override
    public void setEscolaridade(String escolaridade) {
        this.escolaridade = escolaridade;
    }

    @Override
    public String getOcupacao() {
        return ocupacao;
    }

    @Override
    public void setOcupacao(String ocupacao) {
        this.ocupacao = ocupacao;
    }
  
    
    
    // HashCode
   
    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[] {this.id, this.nome,
        this.parentesco, this.dataNasc, this.estadoCivil, this.escolaridade, this.ocupacao});
    }

    //Equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return false;
        }
        if (obj==null || getClass() != obj.getClass()) {
            return false;
        }
        Membro m = (Membro) obj;
        return (this.id==m.getId() && this.nome.equals(m.getNome()) && this.parentesco.equals(m.getParentesco()) &&
                this.dataNasc.equals(m.getDataNasc())&& this.estadoCivil.equals(m.getEstadoCivil())&&
                this.escolaridade.equals(m.getEscolaridade())&& this.ocupacao.equals(m.getOcupacao()));
    }
    
    
    //Clone
    @Override
    public Membro clone(){
        return new Membro(this);
    }
  
    /**
     * Método que permite obter primeiro e último nome de um Membro, de forma a sintetizar nome para visualização
     * @return String, com primeiro e último nome do Membro
     */
    @Override
    public String getFirstAndLastName(){
        String[] names = this.nome.split(" ");
        if(names.length==1) return this.nome; // Apenas tem um nome (nunca deveria ocorrer mas fica salvaguardado)
        StringBuilder sb = new StringBuilder();
        sb.append(names[0]).append(" ").append(names[names.length-1]);
        return sb.toString();
    }
}
