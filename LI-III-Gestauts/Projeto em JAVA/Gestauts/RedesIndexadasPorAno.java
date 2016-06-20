 

/**
 *
 * @author jdc
 * @version 23/05/2014
 */

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
public class RedesIndexadasPorAno implements Serializable {
    
    //Variáveis de instâcia
    private String nomefich;                                    //nome do ficheiro base para criação da rede de autores
    private int minYEAR;                                        //ano mais antigo de uma publicação
    private int maxYEAR;                                        //ano mais recente de uma publicação
    private int totalpublicacoes;                               //o nº total de artigos publicados no ficheiro
    private int totalNuncaASolo;
    private int totalnomeslidos;                                //o nº total de nomes lidos do ficheiro
    private int totalnomesdistintos;                            //o nº total de nomes diferentes no ficheiro(nº total de autores)

    private TreeMap<Integer,RedeAnualDeAutores> redes;         //Map de redes anuais de autores, a cada ano corresponde uma RedeAnual
    
    
    //Construtores
    public RedesIndexadasPorAno(String nomefich){
        this.nomefich = nomefich;
        this.minYEAR = 3000;
        this.maxYEAR = 0;
        this.totalpublicacoes = 0;
        this.totalnomesdistintos = 0;
        this.totalnomeslidos = 0;
        this.totalNuncaASolo=0;
        this.redes = new TreeMap<>();
    }
    
    public RedesIndexadasPorAno(String nomefich, int minYEAR, int maxYEAR, int tot, int nomesdist, int nomeslidos, TreeMap<Integer,RedeAnualDeAutores> rede){
        
        this.nomefich = nomefich;
        this.minYEAR = minYEAR;
        this.maxYEAR = maxYEAR;
        this.totalpublicacoes = tot;
        this.totalnomeslidos = nomeslidos;
        this.totalnomesdistintos = nomesdist;       

        
        this.redes = new TreeMap<>();
        for(Map.Entry<Integer,RedeAnualDeAutores> entry : rede.entrySet())
            this.redes.put(entry.getKey(),entry.getValue().clone());
    }
    
    public RedesIndexadasPorAno(RedesIndexadasPorAno ripa){
        this(ripa.getNomeFicheiro(),ripa.getMinYear(),ripa.getMaxYear(),ripa.getTotalPubls(),ripa.getTotalNomesDistintos(),ripa.getTotalNomesLidos(),ripa.getRedes());
    }
    
    
    //Métodos de instância
    
    //gets e sets
    
    public String getNomeFicheiro(){return this.nomefich;}
    public int getMinYear(){return this.minYEAR;}
    public int getMaxYear(){return this.maxYEAR;}
    public int getTotalPubls(){return this.totalpublicacoes;}
    public int getTotalNomesDistintos(){return this.totalnomesdistintos;}
    public int getTotalNomesLidos(){return this.totalnomeslidos;}
    public void setTotalDeNomesDistintos(int t){this.totalnomesdistintos=t;}
    public void setTotalNuncaASolo(int n){this.totalNuncaASolo=n;}
    
    public TreeMap<Integer,RedeAnualDeAutores> getRedes(){
        TreeMap<Integer,RedeAnualDeAutores> aux = new TreeMap<>();
        
        for(Map.Entry<Integer,RedeAnualDeAutores> entry : this.redes.entrySet())
            aux.put(entry.getKey(),entry.getValue().clone());
        
        return aux;
    }
    
    
    
    //Método que devolve dados de um dado autor de um dado ano da rede
    public Autor getAutorDoAno(String autor,int ano){
        return this.redes.get(ano).getAutor(autor);
    }
    
    
    
    //equals, clone e toString
    @Override
    public boolean equals(Object o){
        if(this==o) return true;
        
        else if(o==null || o.getClass() != this.getClass()) return false;
        
        else{
              RedesIndexadasPorAno ripa = (RedesIndexadasPorAno) o;
              
              if(this.minYEAR!=ripa.getMinYear() || this.maxYEAR!=ripa.getMaxYear() || this.totalpublicacoes!=this.totalpublicacoes) return false;
              
              else{
                    for(Map.Entry<Integer,RedeAnualDeAutores> entry : this.redes.entrySet())
                        if(!ripa.redes.containsKey(entry.getKey()))
                            return false;
                    
                    return true;
              }
        }
    }
    
    @Override
    public RedesIndexadasPorAno clone(){
        return new RedesIndexadasPorAno(this);
    }
    
    
    /**
     * @return String com relatório
     * toString é o relatório a imprimir após término da leitura do ficheiro
     */
    public String toSring(){
        return("\n#############################RELATORIO#############################\n"
               +"Nome do ficheiro lido: "+this.nomefich+"\n"
               +"Total de artigos publicados: "+this.totalpublicacoes+"\n"
               +"Total de nomes lidos: "+(this.totalnomeslidos+3)+"\n"
               +"Total de nomes distintos: "+this.totalnomesdistintos+"\n"
               +"Intervalo de anos das publicações: ["+this.minYEAR+","+this.maxYEAR+"]\n");
    }


    
    /**
     * 
     * @return String com tabela
     * Método que devolve String com tabela de publicações descriminadas por ano. 
     */
    public String tabelaDePublicacoesString(){
        StringBuilder sb = new StringBuilder();
        
        sb.append("\n##################TABELA########################\n");
        for(Map.Entry<Integer,RedeAnualDeAutores> rede : this.redes.entrySet())
            sb.append("Ano: ").append(rede.getKey()).append("         Publicações: ").append(rede.getValue().getTotal()).append("\n");
        
        sb.append("\t\t\tTOTAL: ").append(this.totalpublicacoes).append("\n");
        
        return sb.toString();
    }
    
    
    /**
     * 
     * @param autores, Set de autores recolhidos de uma linha do ficheiro
     * @param ano, o ano da publicação
     * Método que alimenta a estrutura de dados com informação de mais uma publicação
     */
    public void addPublicacao(HashSet<String> autores, int ano){
        
        RedeAnualDeAutores rededoano;
        this.totalpublicacoes++; //incremento ao total de publicações
        this.totalnomeslidos+=autores.size(); //incremento no total de nomes lidos
        
        
        if(this.redes.containsKey(ano)){//ano já se encontra na estrutura de dados
           rededoano = this.redes.get(ano);
           rededoano.incTotalPublicacoesDoAno();
           rededoano.addAutoresRedeDoAno(autores);
        }
        
        else{//Um novo ano surge

            //Determinar intervalo de anos
            if(ano < this.minYEAR) this.minYEAR = ano;
            if(ano > this.maxYEAR) this.maxYEAR = ano;
            
            RedeAnualDeAutores rede = new RedeAnualDeAutores(ano);//alocar nova rede para ano 
            rede.addAutoresRedeDoAno(autores);
            this.redes.put(ano,rede);
        }
        
    } 

    
    
    //-------------------------------------------------------------------------------------------------------------------//
    //---------------------------------------------ESTATÍSTICAS----------------------------------------------------------//
    //-------------------------------------------------------------------------------------------------------------------//    
    
    /**
     * 
     * @param autor, uma String com o nome do autor
     * @return nº total de publicações desse autor
     * Método que retorna o nº total de publicações para um dado autor
     */
    public int NumeroTotalDeArtigosDoAutor(String autor){
        int contador=0;
        
        for(Map.Entry<Integer,RedeAnualDeAutores> entry : this.redes.entrySet()){
            contador += entry.getValue().publicacoesDoAutorNoAno(autor);
        }
        
        return contador;
    }
    
    
    /**
     * @return inteiro com o total de autores que sempre e apenas publicaram a solo
     * Método que calcula nº total de autores que apenas publicaram a solo
     */
    public int totalASolo(){
        HashSet<String> solo = new HashSet<>();
        HashSet<String> eliminados = new HashSet<>();
        
        for(RedeAnualDeAutores raa : this.redes.values()){
            for(Autor a : raa.getAutoresDoAno().values()){
                if(!eliminados.contains(a.getNome()) && a.getNSolo()==0){
                    solo.add(a.getNome());
                }
                else if(a.getNSolo()!=0){
                    solo.remove(a.getNome());
                    eliminados.add(a.getNome());
                }
            }
        }
        
        return solo.size();
    }
    
    
    /**
     * @return int, com o total de autores que nunca publicaram a solo
     * Método que devolve total de autores que nunca publicaram a solo
     */
    public int totalNuncaASolo(){
        return this.totalNuncaASolo;
    }
    
    
    /**
     * @param X, limite mínimo de artigos para contabilizar o autor
     * @return int, nº de autores que publicaram mais de X artigos
     * Método que que determina nº de autores que publicaram mais de X artigos.
     */
    public int maisDeXArtigos(int X){
        
        int total=0;
        TreeMap<String,Integer> totalautores = new TreeMap<>();
        
        for(RedeAnualDeAutores raa : this.redes.values()){
            for(Autor a : raa.getAutoresDoAno().values()){
                if(totalautores.containsKey(a.getNome())){
                    totalautores.put(a.getNome(),totalautores.get(a.getNome())+a.getTotal());
                }
                else{ totalautores.put(a.getNome(),a.getTotal()); }
            }
        }
        
        for(Map.Entry<String,Integer> entry : totalautores.entrySet()){
            if(entry.getValue() > X){ total++; }
        }
        
        return total;
    }
    
    //-------------------------------------------------------------------------------------------------------------------//
    //-------------------------------------------ESTATÍSTICAS-FIM--------------------------------------------------------//
    //-------------------------------------------------------------------------------------------------------------------//
    
    
    
    
    
    
    
    //-------------------------------------------------------------------------------------------------------------------//
    //-----------------------------------------CONSULTAS INDEXADAS POR ANO-----------------------------------------------//
    //-------------------------------------------------------------------------------------------------------------------//
    
    /**
     * @param X, tamanho da lista de autores
     * @param min, ano mínimo
     * @param max, ano máx
     * @return TreeSet<String>, os X autores que mais publicaram no intervalo de anos dado
     */
    public TreeSet<String> XAutoresQueMaisPubNoIntervalo(int X, int min, int max){
        
        TreeMap<String,Integer> totalautores = new TreeMap();
        TreeSet<String> xautores = new TreeSet<>();
        
        for(RedeAnualDeAutores raa : this.redes.values()){
            if(raa.getAno()>=min && raa.getAno()<=max){//se estamos no intervalo de anos fechado
                for(Autor a : raa.getAutoresDoAno().values()){
                    if(totalautores.containsKey(a.getNome())){
                        totalautores.put(a.getNome(),totalautores.get(a.getNome())+a.getTotal());
                    }
                    else{ totalautores.put(a.getNome(),a.getTotal()); }
                }   
            }
        }
        
        TreeSet<Autor> totalord = new TreeSet<>(new AutorPubsComparator());
        for(Map.Entry<String,Integer> entry : totalautores.entrySet())
            totalord.add(new Autor(entry.getKey(),0,entry.getValue()));
            
        
        int counter=0;
        Iterator<Autor> it = totalord.iterator();
        
        while(it.hasNext() && counter<X){           
            xautores.add(it.next().getNome());
            counter++;
        }
        
        return xautores;
    }
    
    
    
    
    /**
     * @param min, o ano mínimo
     * @param max, o ano máximo
     * @return TreeSet<ParAutPub>, um tree set ordenado por maior número de publicações do par de autores associado
     */
    public TreeSet<ParAutPub> XParesComMaisArtigosPub(int min, int max){
        
        HashMap<ParAutPub,Integer> aux = new HashMap<>();
        
        for(RedeAnualDeAutores raa : this.redes.values()){
            if(raa.getAno()>=min && raa.getAno()<=max){//se estamos no intervalo de anos fechado
                for(Autor a : raa.getAutoresDoAno().values()){
                    for(Map.Entry<String,Integer> entry : a.getCoAutores().entrySet()){
                        ParAutPub pa = new ParAutPub(a.getNome(),entry.getKey(),0);
                        if(aux.containsKey(pa)){
                            aux.put(pa,aux.get(pa)+entry.getValue()); //atualizar contador associado ao par                            
                        }
                        else{
                            aux.put(pa,entry.getValue());
                        }
                    }
                }   
           }
        }
        

        TreeSet<ParAutPub> pares = new TreeSet<>(new ParAutPubComparator());
        
        for(Map.Entry<ParAutPub,Integer> entry : aux.entrySet()){
            ParAutPub par = new ParAutPub(entry.getKey());
            par.setCoautorias(entry.getValue());
            pares.add(par);
        }
        
        return pares;
    }
    
    
    
    
    
    /**
     * @param min, ano mínimo do intevalo
     * @param max, ano máximo do intervalo
     * @param autores, set de no máximo três autores
     * @return um Set de autores que são coautores comuns aos autores da lista passada como parâmetro
     * Método que para uma lista de autores determina os coautores comuns
     */
    public TreeSet<String> listaDeCoAutoresComuns(int min, int max, HashSet<String> autores){
        
        TreeSet<String> comuns = new TreeSet<>();
        
        for(RedeAnualDeAutores raa : this.redes.values()){
            if(raa.getAno()>=min && raa.getAno()<=max){//se estamos no intervalo de anos fechado
                for(Autor a : raa.getAutoresDoAno().values()){
                    
                    if(!autores.contains(a.getNome())){//só faz sentido procurar relações em autores que não sejam os da lista de entrada
                        boolean temtodos = true;
                    
                        TreeSet<String> aux = a.getNomesCoAutores();
                    
                        Iterator<String> it = autores.iterator();
                    
                        while(it.hasNext() && temtodos){
                            if(!aux.contains(it.next())) temtodos=false;
                        }
                        if(temtodos) comuns.add(a.getNome());
                    }   
                }
            }
        }
        
        return comuns;
    }
    
    
    /**
     * 
     * @param min, ano mínimo
     * @param max, ano máximo
     * @return TreeSet com nomes de autores que publicaram em todos os anos do intervalo dado
     */
    public TreeSet<String> listarAutoresQuePublicaramNoIntervalo(int min, int max){
         HashSet<String> autores = new HashSet<>(); //optamos pelo tempo constante das ops básicas ins remove do HashSet
         HashSet<String> elimindos = new HashSet<>();
         
        for(RedeAnualDeAutores raa : this.redes.values()){
            if(raa.getAno()>=min && raa.getAno()<=max){//se estamos no intervalo de anos fechado
                
                if(raa.getAno()==min){
                    autores = raa.getNomesDeAutores();
                }
                else{
                      HashSet<String> doano = raa.getNomesDeAutores();
                      
                      Iterator<String> it = autores.iterator();
                      while(it.hasNext()){
                          String nome = it.next();
                          if(!doano.contains(nome))
                              it.remove();
                      }
                }
            }
        }
        
        TreeSet<String> ret = new TreeSet<>(autores);
            
        return ret;
    }
  
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //-------------------------------------------------------------------------------------------------------------------//
    //-----------------------------------------CONSULTAS GLOBAIS ESPECIAIS-----------------------------------------------//
    //-------------------------------------------------------------------------------------------------------------------//
    
    /**
     * 
     * @param autor, nome de um autor
     * @return TreeSet dos coautores do autor passado como parâmetro
     */
    public TreeSet<String> todosOsCoAutoresDeUmAutor(String autor){
        
        TreeSet<String> coautores = new TreeSet<>();
        
        for(RedeAnualDeAutores raa : this.redes.values()){
            for(Autor a : raa.getAutoresDoAno().values()){
                if(!a.getNome().equals(autor))
                    if(a.getNomesCoAutores().contains(autor))
                        coautores.add(a.getNome());
            }
        }
        
        return coautores;
    }
    
    
    /**
     * 
     * @param first, letra pela qual queremos que os nomes da lista começem
     * @return lista de autores começados pela letra passada como parâmetro
     */
    public TreeSet<String> autoresComecadosPelaLetra(char first){
        
        TreeSet<String> nomes = new TreeSet<>();
        
        for(RedeAnualDeAutores raa : this.redes.values()){
            for(Autor a : raa.getAutoresDoAno().values()){
                if(a.getNome().charAt(0) == first)
                    nomes.add(a.getNome());
            }
        }
        
        return nomes;        
    }
    
    
    
    
}

