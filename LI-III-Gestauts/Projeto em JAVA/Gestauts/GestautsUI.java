 

/**Classe main que contém so métodos de controlo de I/O, leitura e armazenamento dos dados relevantes numa rede de autores
 *
 * @author jdc
 * @version 19/05/2014
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.Map;
import java.util.HashMap;
import java.io.*;
public class GestautsUI {
    
    
    //Método main
    public static void main(String[] args) throws IOException, ClassNotFoundException {        

        RedesIndexadasPorAno ripa;
        Leitura read = null;
        int user_key=0;
        String filename=null;
        
        printPainelDeEntrada();  
        
        user_key = Input.lerInt();
        if(user_key==0) return;
        
        if(user_key!=5){ //Ler ficheiro texto e carregar dados em memória
            filename = getFileName(user_key);
            read = new Leitura();
            
            //Medição do tempo de leitura do ficheiro
            Crono.start();
            ripa = read.lerFicheiro(filename);
            Crono.stop();
            System.out.print('\u000C');
            System.out.println("\nTempo leitura do ficheiro e população da estrutura de dados: "+Crono.print()+" segundos\n");
        }
        else{
            //Carregar Objecto publicx.obj
            Crono.start();
            ripa = open();
            Crono.stop();
            if(ripa==null){
                System.out.println("\nO ficheiro publicx.obj é inexistente, por favor escolha um ficheiro para carregar em memória\n");
                return;
            }
            System.out.print('\u000C');
            System.out.println("\nTempo carregar ficheiro objecto: "+Crono.print()+" segundos\n");
        }
        
        
        //No final da leitura e armazenamento dos dados imprimimos ao relatório correspondente ao ponto 1.1
        System.out.println(ripa.toSring());
        
        
        
        //Queries interativas
        user_key=-1;
        
        do{
                printMenuGestauts();
                user_key = Input.lerInt();
                    
                switch(user_key){
                
                    case 11:
                        System.out.print('\u000C');
                        Querie11(ripa);
                        break;
                    
                    case 12:
                        System.out.print('\u000C');
                        Querie12(ripa);
                        break;
                
                    case 13:
                        System.out.print('\u000C');
                        Querie13(ripa);
                        break;

                    case 14:
                        System.out.print('\u000C');
                        Querie14(ripa);
                        break;
                    
                    case 15:
                        System.out.print('\u000C');
                        Querie15(ripa);
                        break;
                    
                    case 21:
                        System.out.print('\u000C');
                        Querie21(ripa);
                        break;
                   
                    case 22:
                        System.out.print('\u000C');
                        Querie22(ripa);
                        break;    
                    
                    case 23:
                        System.out.print('\u000C');
                        Querie23(ripa);
                        break;
               
                    case 24:
                        System.out.print('\u000C');
                        Querie24(ripa);
                        break;
                
                    case 31:
                        System.out.print('\u000C');
                        lerLinhasDuplicado(filename);
                        break;                    
                    
                    case 32:
                        System.out.print('\u000C');
                        Querie32(ripa);
                        break;
                        
                    case 33:
                        System.out.println('\u000C');
                        Querie33(ripa);
                        break;
                    
                    case 34:
                        System.out.print('\u000C');
                        Querie34(ripa);
                        break;
                    
                    case 4://Guardar estado do programa
                        System.out.print('\u000C');
                        Crono.start();
                        save(ripa);
                        Crono.stop();
                        System.out.println("\nTempo de exec: "+Crono.print()+" segundos\n");
                        break;
                    
                    case 0:
                        break;                   
           }
                
        }
        while(user_key!=0);    
    }
    

    
    //-----------------------------------------------------------------------------------------------------------------//
    //-----------------------------------------------I/O FORMAT--------------------------------------------------------//
    //-----------------------------------------------------------------------------------------------------------------//
    
    //Método que imprime o menu interativo da API GESTAUTS
    public static void printMenuGestauts(){
        System.out.print("\n\n###############################################################\n");
        System.out.printf("##                       GESTAUTS  Menu                      ##\n");
        System.out.println("###############################################################\n");
        System.out.println("-I-Consultas Estastícas-");
        System.out.println("11 - Total de artigos publicados por um dado autor");
        System.out.println("12 - Total de autores que apenas publicaram a solo");
        System.out.println("13 - Total de autores que nunca publicaram a solo");
        System.out.println("14 - Total de autores que publicaram mais de X artigos no intervalo de anos");
        System.out.println("15 - Consultar tabela de publicações\n");
        
        System.out.println("-II-Consultas indexadas por ano ou anos-");
        System.out.println("21 - Nomes dos X autores que mais publicaram");
        System.out.println("22 - X pares de autores com mais coautorias");
        System.out.println("23 - Coautores comuns a todos os autores de uma dada lista");
        System.out.println("24 - Listar todos os autores que publicaram num dado intervalo de anos\n");
        
        System.out.println("-III-Consultas globais especiais-");
        System.out.println("31 - Número de linhas em duplicado no ficheiro");
        System.out.println("32 - Nomes de todos os autores começados por uma determinada letra");
        System.out.println("33 - Determinar coautorias dado um autor e um ano");
        System.out.println("34 - Todos os coautores de um dado autor\n");

        System.out.println("4 - Gravar em ficheiro o estado da aplicação\n");
        System.out.println("0 - Sair\n");
        System.out.print("> ");    
    }
    
    
    //Método que imprime o painel de entrada da API
    public static void printPainelDeEntrada(){
        System.out.println("####################- BEM-VINDO AO GESTAUTS -####################");
        System.out.println("\nO GESTAUTS é uma aplicação interativa  de criação, gestão e consulta de um catálogo de publicações indexado por ano.");
        System.out.println("@by: Tiago Cunha a67741, J. Daniel Caldas a67691, Xavier Rodrigues a67676 (Grupo 12)");
        System.out.println("\nPor favor selecione uma das opções:\n");
        System.out.println("1 - publicx.txt\n2 - publicx_x4.txt\n3 - publicx_x6.txt\n4 - Inserir nome do ficheiro manualmente (***.txt)");
        System.out.println("5 - (Alternativa) Carregar objecto publicx.obj");
        System.out.println("6 - Sair\n");
        System.out.print("> ");
    }
    
    
    //Dado o índice escolhido pelo utilizador devolve uma String com o nome do ficheiro correspondente
    public static String getFileName(int i){
        String s = null;
        
        switch(i){
            case 1:
                s = "publicx.txt";
                break;
            case 2:
                s = "publicx_x4.txt";
                break;
            case 3:
                s = "publicx_x6.txt";
                break;
            case 4:
                System.out.print("\nNome do ficheiro: ");
                s = Input.lerString();
                break;
        }
        
        return s;
    }
   
 
    
    
    
    
    
    /*Método que permite paginar conteúdo a visualizar seja para Strings ou Pares de Autores ou Autores*/
    public static void printPages (TreeSet<?> list) {
        
        int user_key = -1;
        int counter  = 0;
        int pagtot = (list.size()/24)+1;
        int pag = 1;
        
        Iterator<?> it = list.iterator();
        
        while( user_key != 0){
          System.out.println("Tamanho da lista: "+list.size()+"\n");
          
          if(list.size() <=24){
              
              while(it.hasNext() && counter < 24 ){
                  System.out.println(" - "+it.next().toString());
                  counter++;
                  
              }
              
                System.out.println("Página: 1/1");
            
                System.out.print("\n  0 - Sair:   ");
                user_key = Input.lerInt();
                System.out.print('\u000C');
              
            }
          
          else {
              
              while( it.hasNext() && counter < 24) {
                  
                  System.out.println(" - "+it.next().toString());
                  counter++;
                  
                   }
            
              if (pag != pagtot){
                
            
                System.out.println("\nPágina: "+pag+"/"+pagtot);
            
                System.out.print("\n  1 - Página seguinte    0 - Sair:   ");
                user_key = Input.lerInt();
                System.out.print('\u000C');
                pag++;
                counter = 0;
                }
            else {
                
                System.out.println("Página: "+pag+"/"+pagtot);
            
                System.out.print("\n  0 - Sair:   ");
                user_key = Input.lerInt();
                System.out.print('\u000C');          
                
            }    
                  
              
          }
      }
    }
    
    //-----------------------------------------------------------------------------------------------------------------//
    //-------------------------------------------FIM-I/O FORMAT--------------------------------------------------------//
    //-----------------------------------------------------------------------------------------------------------------//
    
    
    
    
    
    
    
    
    
    
    //-----------------------------------------------------------------------------------------------------------------//
    //------------------------------------------QUERIES INTERATIVAS----------------------------------------------------//
    //-----------------------------------------------------------------------------------------------------------------//
    
    
    public static void Querie11 (RedesIndexadasPorAno ripa){                
        String aux; 
        
         System.out.print("\nNome do autor: ");
         aux = Input.lerString();
         Crono.start();
         System.out.println("\nTotal artigos publicados por "+aux+" :"+ripa.NumeroTotalDeArtigosDoAutor(aux));
         Crono.stop();
         System.out.println("\nTempo de exec: "+Crono.print()+" segundos\n");
    }
    
    
    public static void Querie12 (RedesIndexadasPorAno ripa){
        Crono.start();
        System.out.println("\nTotal de autores que apenas publicaram a solo: "+ripa.totalASolo());
        System.out.println("Tempo de exec: "+Crono.print()+" segundos\n");
        Crono.stop();        
    }
    
    
    public static void Querie13 (RedesIndexadasPorAno ripa){
        Crono.start();
        System.out.println("\nTotal de autores que nunca publicaram a solo: "+ripa.totalNuncaASolo());
        System.out.println("Tempo de exec: "+Crono.print()+" segundos\n");
        Crono.stop();
    }
    
    
    public static void Querie14 (RedesIndexadasPorAno ripa){
        int X;
        
        System.out.print("\nMínimo de publicações X: ");
        X = Input.lerInt();
        Crono.start();
        System.out.println("\nTotal de autores que publicaram mais de X artigos: "+ripa.maisDeXArtigos(X));
        Crono.stop();
        System.out.println("\nTempo de exec: "+Crono.print()+" segundos\n");
    }
    
    
    public static void Querie15 (RedesIndexadasPorAno ripa){
        Crono.start();
        System.out.println(ripa.tabelaDePublicacoesString());
        Crono.stop();
        System.out.println("\nTempo de exec: "+Crono.print()+" segundos\n");
    }
    
    
    public static void Querie21 (RedesIndexadasPorAno ripa){
        int min, max, X;
        
        System.out.print("\nAno mínimo: ");
        min = Input.lerInt();
        System.out.print("Ano máximo: ");
        max = Input.lerInt();
        
        if( !anosValidos(min,max,ripa.getMinYear(),ripa.getMaxYear()) ){
            System.out.println("\nIntervalo de anos inválido!\n");
            return;
        }
        
        System.out.print("\nNúmero de autores na lista: ");
        X = Input.lerInt();
        Crono.start();
        TreeSet<String> r21 = ripa.XAutoresQueMaisPubNoIntervalo(X,min,max);
        Crono.stop();
        System.out.println("\nTempo de exec: "+Crono.print()+" segundos\n");
                    
        printPages(r21);
        System.out.print('\u000C');
    }
    
    
    public static void Querie22 (RedesIndexadasPorAno ripa){
        int min, max, X;
        TreeSet<ParAutPub> pares;
        
        System.out.print("\nAno mínimo: ");
        min = Input.lerInt();
        System.out.print("Ano máximo: ");
        max = Input.lerInt();
        
        if( !anosValidos(min,max,ripa.getMinYear(),ripa.getMaxYear()) ){
            System.out.println("\nIntervalo de anos inválido!\n");
            return;
        }
        
        
        System.out.print("\nNúmero de pares de autores na lista: ");
        X = Input.lerInt();
                    
        Crono.start();
        pares = ripa.XParesComMaisArtigosPub(min,max);
        Crono.stop();
        System.out.println("\nTempo de exec: "+Crono.print()+" segundos\n");
        
        
        int counter=0;
        TreeSet<ParAutPub> aux = new TreeSet<>(new ParAutPubComparator());        
        
        Iterator<ParAutPub> it = pares.iterator();
        while(it.hasNext() && counter<X){
            aux.add(it.next());
            counter++;
        }
        
        System.out.println("\n######### "+X+" pares de autores com mais publicações entre "+min+" e "+max+" #########\n"); //header       
        
        //páginas-----------------------------------!
        printPages(aux);
    }
    
    
    public static void Querie23 (RedesIndexadasPorAno ripa){    
        int min, max;
        HashSet<String> autores;
        TreeSet<String> nomes;
        String aux;
        
        System.out.print("Ano mínimo: ");
        min = Input.lerInt();
        System.out.print("Ano máximo: ");
        max = Input.lerInt();
                    
        if( !anosValidos(min,max,ripa.getMinYear(),ripa.getMaxYear()) ){
            System.out.println("\nIntervalo de anos inválido!\n");
            return;
        }       
        
        autores = new HashSet<>();
        aux="";
        int c=0;
                    
        while(c!=3){
            System.out.print("Autor (escreva sair para sair): ");
            aux = Input.lerString();
            if(aux.equals("sair")) break;
            autores.add(aux);
            c++;
        }
                    
        Crono.start();
        nomes = ripa.listaDeCoAutoresComuns(min,max,autores);
        Crono.stop();
        System.out.println("\nTempo de exec: "+Crono.print()+" segundos\n");
                    
        //páginas-----------------------------------!
        System.out.print("Coautores comuns a: ");
        for(String h : autores) System.out.print(h+"   ");
            System.out.println();
        
        printPages(nomes);
    }
    
    
    
    public static void Querie24 (RedesIndexadasPorAno ripa){
        int min, max;
        TreeSet<String> nomes;
        
        System.out.print("Ano mínimo: ");
        min = Input.lerInt();
        System.out.print("Ano máximo: ");
        max = Input.lerInt();
                    
        if( !anosValidos(min,max,ripa.getMinYear(),ripa.getMaxYear()) || min==max ){ //nesta querie em particular rejeitamos pedidos para o mesmo ano
            System.out.println("\nIntervalo de anos inválido!\n");
            return;
        }
        else{
            nomes = new TreeSet<>();
            Crono.start();
            nomes = ripa.listarAutoresQuePublicaramNoIntervalo(min,max);
            Crono.stop();
            System.out.println("\nTempo de exec: "+Crono.print()+" segundos\n");
                    
            System.out.println("\nTOTAL: "+nomes.size());
            
            printPages(nomes);
        }
    }
    
    
    public static void Querie32 (RedesIndexadasPorAno ripa){
       TreeSet<String> nomes;
        
       char letra;
       System.out.println("Insira uma letra: ");
       letra = Input.lerString().toUpperCase().charAt(0);
       
       Crono.start();
       nomes = ripa.autoresComecadosPelaLetra(letra);
       Crono.stop();
       System.out.println("\nTempo de exec: "+Crono.print()+" segundos\n");
                    
       //páginas-----------------------------------!
       System.out.println("\n######### Lista de nomes começados por: "+letra+" #########");
       System.out.println("TOTAL: "+nomes.size());
       
       printPages(nomes);
    }
  
    
    
    public static void Querie33(RedesIndexadasPorAno ripa){
        String aux;
        int ano;
    
        System.out.print("Nome do autor: ");
        aux = Input.lerString();
        System.out.print("Ano: ");
        ano = Input.lerInt();
        
        if(ano<ripa.getMinYear() && ano>ripa.getMaxYear()){
            System.out.println("\nAno inválido!!\n");
        }
        else{
            Crono.start();
            Autor autor = ripa.getAutorDoAno(aux,ano);
            
            if(autor==null){
                System.out.println("\n"+aux+" não publicou artigos em "+ano+"\n");
                return;
            }
            
            //-----------------------------------------------------------------------------
            //Neste momento temos a certeza que o autor existe e publicou no ano em questão
            StringBuilder sb = new StringBuilder();
            int sum=0;
            sb.append("######### CO AUTORIAS DE "+autor.getNome()+" no ano "+ano+" #########\n\n");
            for(Map.Entry<String,Integer> entry : autor.getCoAutores().entrySet()){
                sb.append("Coautor: "+entry.getKey()+"        Coautorias: "+entry.getValue()+"\n");
                sum+=entry.getValue();
            }
            sb.append("\nSomatório das coautorias: "+sum+"\n");
            
            try{
                PrintStream file = new PrintStream(new FileOutputStream("querie33.txt"));
                file.println(sb.toString());
                file.flush();
                Crono.stop();
            }
            catch(Exception e){
                System.out.println("\nERRO!\n");
            }
            
            System.out.print('\u000C');

            System.out.println("\nTempo de exec: "+Crono.print()+" segundos\n");
            System.out.println("\nPor favor consulte o ficheiro queire33.txt para consultar as coautorias de "+autor.getNome()+" em "+ano+"\n");
            
        }
        
    }
    
    public static void Querie34 (RedesIndexadasPorAno ripa){  
       String aux;
       TreeSet<String> nomes;
        
       System.out.print("Nome do autor: ");
       aux = Input.lerString();
       Crono.start();
       nomes = ripa.todosOsCoAutoresDeUmAutor(aux);
       Crono.stop();
       System.out.println("\nTempo de exec: "+Crono.print()+" segundos\n");
                    
       System.out.println("\nTOTAL: "+nomes.size());
       //páginas-----------------------------------!
       printPages(nomes);
    }
                    
    
    //-----------------------------------------------------------------------------------------------------------------//
    //------------------------------------------FIM/-QUERIES INTERATIVAS-----------------------------------------------//
    //-----------------------------------------------------------------------------------------------------------------// 
    
    
    
 
    
    //-----------------------------------------------------------------------------------------------------------------//
    //------------------------------------------CONSULTAS GLOBAIS ESPECIAIS--------------------------------------------//
    //-----------------------------------------------------------------------------------------------------------------//   
    
    /**
     * 
     * @param filename, o nome do ficheiro sobre o qual estamos a trabalhar
     * @throws FileNotFoundException 
     * Método que relê ficheiro e calcula o nº de linhas lidas e linhas em duplicado
     */
    public static void lerLinhasDuplicado(String filename) throws FileNotFoundException{        
        ArrayList<String> linhas = new ArrayList<String>();
        Crono.start();
        FileReader file = new FileReader(filename);
        BufferedReader reader;
        
        try{
            String linha;
            reader = new BufferedReader(file); 
            while(null != (linha=reader.readLine()) ){
                linhas.add(linha);
            }
        }
        catch(IOException e){System.out.println("\nERRO! AO LER FICHEIRO!\n"); }
        
        HashSet<String> noDupRecs = new HashSet<String>(linhas);
        System.out.println("\nNúmero de linhas lidas: "+linhas.size());
        System.out.println("Número de linhas em duplicado: "+ (linhas.size()-noDupRecs.size()) );
        Crono.stop();
        System.out.println("Tempo de exec: "+Crono.print()+" segundos");    
    }     
    
    //----------------------------------------------------------------------------------------------------------------//
    
    
    
    /*Método auxiliar para validação da entrada de um intervalo de anos*/
    public static boolean anosValidos(int min, int max, int themin, int themax){
        if(min>max) return false;
        else if(themin>min) return false;
        else if(themax<max) return false;
        else return true;
    }    
      
    
    
    
    //-----------------------------------------------------------------------------------------------------------------//
    //------------------------------------------SALVAGUARDAR ESTADO DA API---------------------------------------------//
    //-----------------------------------------------------------------------------------------------------------------//    
    
    
    //Funções que permitem salvaguardar o estado da API e posteriormente recuperar esses dados para criação de um ambiente de teste
    public static RedesIndexadasPorAno open() throws IOException, ClassNotFoundException{
        try{
            System.out.println("\nA carregar objeto rede anual de autores . . .\n");
            String workingDir = System.getProperty("user.dir");
            FileInputStream fin=new FileInputStream(workingDir+"publicx.obj");
            ObjectInputStream oin=new ObjectInputStream(fin);
            return (RedesIndexadasPorAno)oin.readObject();
        }
        catch (IOException e){
            System.out.println("\nERROR OPENING FILE!\n");
            return null;
        }  
    }
    
    
    public static void save(RedesIndexadasPorAno ripa) throws IOException{
        String workingDir = System.getProperty("user.dir");
        FileOutputStream fos = new FileOutputStream(workingDir+"publicx.obj");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        try{
            System.out.println("\nA guardar rede anual de autores . . .\n");
            oos.writeObject(ripa);
            oos.close();
        }
        catch (IOException e){
            System.out.println("\nERROR SAVING FILE!\n");
        }
    }
}


