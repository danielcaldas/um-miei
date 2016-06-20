import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import jregex.Matcher;
import jregex.Pattern;
import se.sics.jasper.SICStus;
import se.sics.jasper.SPException;


/**
 * Classe de comunicação com o SICStus Prolog. Contém métodos que efetuam pré-validação de querys, verificação e parse de resultados,
 * contém mecanismos de detação de erros e excepção por forma a conseguir reproduzir de forma credível a resposta dada pelo SICStus.
 * 
 * @author Daniel Caldas, José Cortez, Susana Mendes, Xavier Rodrigues
 * @version 2015.06.08
 */

public class PrologStub {
	
	// Variáveis de classe
	public static String OK = "OK_CODE_MESSAGE";
	
	// Variáveis de instância
	private SICStus sp; //Objeto de interação com a máquina virtual do SICStus
    private String sessionfile;
    private boolean sessionON;
    
    /**
     * Construtor.
     * @param file do ficheiro com código em PROLOG que queremos carregar.
     */
    public PrologStub(String file){
    	this.sessionfile=file;
    	this.sessionON=false;
    	try {
    		sp = new SICStus();
			loadSICStus();
		} catch (SPException e) {
			this.sessionON=false;
		}
    }
    
    /**
     * Instanciar objeto de comunicação e carregar ficheiro com o código.
     * @param args
     * @throws SPException
     */
    private void loadSICStus() throws SPException {
        sp.load(this.sessionfile); // Carregar o ficheiro no SICStus
        this.sessionON=true;
    }
    
    /**
     * Verificar se código foi carregado sem problemas.
     * @return booleano
     */
    public boolean loadOK(){ return this.sessionON; }
    
    /**
     * Pré-verificação de erros de sintaxe.
     * @param s uma query em PROLOG
     * @return String com mensagem de confirmação ou erro
     */
	public String checkError(String s){
		s=s.trim();
		// Erro 1: Falta "." no final da query
		if(s.charAt(s.length()-1)!='.'){
			return ("   Erro de sintaxe. Falta caracter de término da query \".\" .\n");
		}
		// Erro 2: Número de parentisis esquerdos diferente de número de parentisis direitos.
		else if(countChar(s,'(')!=countChar(s,')')) {
			return ("   Erro de sintaxe. Verificar parentisis \".\" .\n");
		}
		return OK;
	}
	
	/**
	 * Conta o numero de ocorrencias de t em s e devolve esse valor.
	 * @param s string
	 * @param t carater
	 * @return inteiro
	 */
	private int countChar(String s, char t){
		int counter=0;
		for(char c : s.toCharArray()){
			if(c==t) counter++;
		}
		return counter;
	}
    
	/**
	 * Função que chama o sicstus para intrepertar uma dada query.
	 * @param queryS query em PROLOG.
	 * @return String com resposta.
	 */
    public String interpret(String queryS) {
        System.out.println();
	
        @SuppressWarnings("rawtypes")
		HashMap map = new HashMap();
        se.sics.jasper.Query query=null;
        
        int x = queryS.lastIndexOf(',');
        char respVariavel = queryS.charAt(x+1);
        String rv = queryS.substring(x+1, x+1);
        
        StringBuilder sb = new StringBuilder();
        String r="";
        
        boolean flag=false;
        try{
        	System.out.println("["+queryS+"]");
        	query = sp.openPrologQuery(queryS,map);
        	
        	try{
        		while (query.nextSolution()) {
        			flag=true;
		        	// Não gostamos das chavetas portanto retiram-se
		        	r = map.toString();
		        	System.out.println(r);
		        	
		        	if(r.equals("{}")){
		        		sb.append("   yes.\n");
		        	}		        	
		        	else if(r.contains(".(") && !r.contains("_")){ // Estamos na presença de uma lista de valores
		        		r=parseLista(r);
		        		sb.append("   "+respVariavel+" = "+r+"\n");
		        	} else {		        	
			        	r = r.substring(1, r.length()-1);
			        	if(!r.contains("=")){
			        		System.out.println(respVariavel+" = "+r+"\n");
			        		if(!r.contains(rv)) sb.append("   "+respVariavel+" = "+r+"\n");
			        		else{ sb.append("   "+r+"\n"); }
			        	}
			        	else if(!r.contains("_")){
			        		System.out.println(respVariavel+"="+r+"\n");
			        		if(!r.contains(rv)) sb.append("   "+respVariavel+"="+r+"\n");
			        		else{ sb.append("   "+r+"\n");}
			        	}
		        	}
		        }
        		if(flag==false){ // SICStus respondeu no.
        			sb.append("   no.\n");
        		}
        	} finally {
        		((se.sics.jasper.Query)query).close();
        	}
        } catch (SPException ex) {
        	System.out.println(r); System.out.println("yes");
            Logger.getLogger(PrologStub.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        } catch (InterruptedException ex) {
        	System.out.println(r); System.out.println("no");
            Logger.getLogger(PrologStub.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        } catch (Exception ex) {
        	System.out.println(r);
            Logger.getLogger(PrologStub.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        
        return sb.toString();
    }
    
    
    // Frases exemplo de respostas mal formatadas do SICStus
    // tios(erico,L).
    // .(raimundo,.(constanca,.(afonsoII,.(pedro,.(fernando,.(branca,[]))))))
    // .(afonsoI,.(mafalda,.(ramon,.(petronila,.(sancho,.(dulce,_494))))))
    
    /**
     * Método que efetua parse de respostas mal formatadas através da libraria REGEX.
     * @param answ reposta devidamente formatada após aplicado a expressão regular de filtragem adequada
     * @return String com resposta
     */
    private String parseLista(String answ){
    	ArrayList<String> respostas = new ArrayList<String>();
    	StringBuilder sb = new StringBuilder();
    	Pattern p=new Pattern("\\((.*?),"); // Padrão para uma palavra
        
        Matcher m=p.matcher(answ);
        while(m.find()){
            respostas.add( m.toString().substring(1, m.toString().length()-1) );
        }
        
        sb.append("[");
        for(String s : respostas){
        	sb.append(s+", ");
        }
        sb.replace(sb.length()-2, sb.length(), "]");        
        
        return sb.toString();    
    }
}
