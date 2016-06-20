 

/**Classe leitura permite isolar leitura para detação de erros, organização do código e medição do tempo de carregamento do ficheiro em mem central.
 *
 * @author jdc
 * @version 23/05/2014
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;

public class Leitura implements Serializable {
   
    
    //Construtor
    public Leitura(){
    }
    
    
    //Métodos de instância
    public RedesIndexadasPorAno lerFicheiro(String filename){
        
        HashSet<String> autoressemduplicados;
        
        System.out.println("\nA ler o ficheiro ...");
        
        BufferedReader reader = null;
        
        RedesIndexadasPorAno ripa = new RedesIndexadasPorAno(filename);
        
        try{
            autoressemduplicados = new HashSet<>();
            FileReader file = new FileReader(filename);
            reader = new BufferedReader(file); 
            String linha = null;
            HashSet<String> solos = new HashSet<>();
        
            //ler linha a linha do ficheiro fazendo devido tratamento da informação
            while( (linha = reader.readLine()) != null ){
                
               if( !linha.equals("") ){ //Testar se a linha do ficheiro está vazia
                    HashSet<String> autores = new HashSet<>();
                    int ano = 0;
                    int nautores = 0;
                
                    String[] linha_partes = linha.split(",");
                    nautores = (linha_partes.length)-1;
                    
                    int isdata = 0;
                    for (String autor : linha_partes) {
                        if(isdata==nautores){
                            ano = Integer.parseInt( autor.trim() );
                        }
                        else{
                            autores.add(autor.trim());
                            autoressemduplicados.add(autor.trim());
                            isdata++;
                        }
                    }                  
                    if(autores.size()==1) solos.add(autores.iterator().next());
                    
                    //Neste ponto temos um HashSet com .size() autores e o ano da publicação (var ano)              
                    
                    //-----------------Armazenar informação relevante na na rede de autores indexados por ano-------------
                    ripa.addPublicacao(autores,ano);
                    //-----------------------------------------------------------------------------------------------------
                
               }
            }
            ripa.setTotalDeNomesDistintos(autoressemduplicados.size());
            ripa.setTotalNuncaASolo(autoressemduplicados.size()-solos.size());
        }
        catch (IOException e){
            System.out.println("\nERRO A LER O FICHEIRO!\n");
        }
        
        
        return ripa; // retorna uma rede de autores indexada por ano construída a partir da leitura do ficheiro
    }
    
}
