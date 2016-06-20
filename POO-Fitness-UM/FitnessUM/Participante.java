
/**
 * Classe que dá tipo a um participante de um evento.
 * 
 * @author jdc 
 * @version 05/06/2014
 */

import java.text.DecimalFormat;
import atividades.Time;
public class Participante
{
    //Variáveis de instÃ¢ncia
    private String email;           //email do participante
    private String nome;            //nome do participante
    private int idade;              //idade do participante
    private String genero;
    private int probdesistencia;    //probabilidade de o participante desistir consoante o escalão étário
    private String escalao;         //escalão étário
    private double ritmo;
    private Time tempodeprova;
    private double sumritmos;

    //Construtores
    public Participante(){
        this.email ="";
        this.nome="";
        this.genero="";
        this.idade=0;
        this.escalao="";
        this.probdesistencia=0;
        this.ritmo=0;
        this.tempodeprova = new Time();
        this.sumritmos = 0;
    }

    /*Parametrizado, construtor favorito*/
    public Participante (String email, String nome, String genero, int idade,double ritmo){
        this.email = email;
        this.nome = nome;
        this.genero = genero;
        this.idade = idade;

        this.escalao = escalaoDadoIdade(idade);
        this.probdesistencia = calculaProbabilidadeDeDesistencia(this.escalao,this.genero);
        this.ritmo=ritmo;
        this.tempodeprova = new Time();
        this.sumritmos = 0;
    }

    public Participante (Participante p){
        this.email = p.getEmail();
        this.nome = p.getNome();
        this.genero = p.getGenero();
        this.idade = p.getIdade();
        this.escalao = p.getEscalao();
        this.probdesistencia = p.getProbDesist();
        this.ritmo = p.getRitmo();
        this.tempodeprova = p.getTimeTime();
        this.sumritmos = p.getSumRitmos();
    }

    //Métodos de instãncia
    //gets & sets
    public String getEmail(){return this.email;}
    public String getNome(){return this.nome;}
    public String getGenero(){return this.genero;}
    public int getIdade() {return this.idade;}
    public int getProbDesist(){return this.probdesistencia;}
    public String getEscalao(){return this.escalao;}
    public double getRitmo(){return this.ritmo;}
    public double getSumRitmos(){return this.sumritmos;}
    public Time getTimeTime(){return this.tempodeprova.clone();}
    public double getTime(){return (double)this.tempodeprova.duracaoEmMinutos();}
    public void setTime(int h,int m,int s){ this.tempodeprova = new Time(h,m,s);}
    public void setRitmo(double r){this.ritmo=r;}

    /**
     * Método que acumula os ritmos dos vários kms da prova numa var de instãncia do Objeto do participante
     */
    public void incRitmos(double r){this.sumritmos+=r;}

    /**
     * Método que incrementa o tempo de prova a cada km é atualizado
     */
    public void addTempoDoKm (double min){this.tempodeprova.addTime(min);}

    /**
     * Método que permite atualizar probabilidade de desistencia do participante dado o km da prova.
     */
    public void atualizaProbDesist(int km){
        if(km>0 && km<7) this.probdesistencia++;
        else if(km>13 && km<16) this.probdesistencia+=2; //zona crÃ­tica da prova
        else if(km >18) this.probdesistencia--; // nos Ãºltimos 2 kms a probabilidade dimui

        if(this.genero.equals("F")) this.probdesistencia++;
    }

    //equals, clone e toString
    @Override
    public boolean equals(Object o){
        if(this==o) return true;

        else if(o==null || this.getClass() != o.getClass()) return false;

        else{

            Participante p = (Participante) o;

            return (this.email.equals(p.getEmail()) && this.nome.equals(p.getNome()) && this.idade==p.getIdade());
        }
    }

    @Override
    public Participante clone(){
        return new Participante(this);
    }

    @Override
    public String toString(){
        DecimalFormat f = new DecimalFormat("##.00"); //formator o double nas strings
        if(this.ritmo>2){
            return ("Nome: "+this.nome+" Ritmo: "+f.format(this.ritmo)+" km/h    Tempo: "+this.tempodeprova.toString()+"\n");
        }
        else{
            return("Nome: "+this.getNome()+"(DESISTIU)\n");
        }
    }

    
    
    /**
     * Método de classe que dada a idade do utilizador devolve um String com o escalão étário do mesmo
     */
    private static String escalaoDadoIdade(int idade){
        if(idade>=10 && idade<=12) return "Infantil";
        else if(idade>12 && idade<=14) return "Iniciado";
        else if(idade>14 && idade<=17) return "Juvenil";
        else if(idade>17 && idade<=19) return "Junior";
        else if(idade>19 && idade<=45) return "Senior";
        else if(idade>50) return "Veterano";
        else return null;
    }

    /**
     * Método de classe que calcula probabilidade de desistência em função do escalão etá¡rio e do género
     */
    private static int calculaProbabilidadeDeDesistencia(String escalao, String gen){

        switch(escalao){

            case "Infantil":
            if(gen.equals("F")) return 5;
            else return 4;
            case"Iniciado":
            if(gen.equals("F")) return 4;
            else return 3;
            case"Juvenil":
            if(gen.equals("F")) return 4;
            else return 3;
            case"Junior":
            if(gen.equals("F")) return 5;
            else return 4;
            case"Senior":
            if(gen.equals("F")) return 6;
            else return 5;
            case"Veterano":
            if(gen.equals("F")) return 8;
            else return 7;

            default:
            return 0;
        }

    }  

}
