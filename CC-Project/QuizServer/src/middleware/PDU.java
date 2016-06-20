package middleware;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import middleware.Protocol;

/**
 *
 * @author carlosmorais
 */

public class PDU implements Serializable {
    
    private int versao = 0; //acho que pode/deve ser atribuida aqui
    private int seg = 0; //acho que pode/deve ser atribuida aqui
    private int label;
    private int tipo;
    private int nCampos;
    private int tamanho;
    
    private byte[][][] buffer; 

    public PDU(int label) {        
        this.label = label;
        this.buffer = new byte[256][][];
        this.tamanho = 0;
        this.nCampos = 0;
    }

    //Creators -> ORDENAR...
    
    // 7 LIST_CHALLENGE
    public void creatListChallenge(){
        this.tipo = Protocol.LIST_CHALLENGE;
    }        
    
    public void creatReply(){
        this.tipo = Protocol.REPLY;
    }
    
    //é utilizado para todas as mensagens Reply, que não devolvem erro
    public void replyOK(){
        //basta um byete a 0 ->valor por defeito é 0
        this.buffer[0] = new byte[1][1];
        this.buffer[0][0][0] = (byte)0;
    }
    
    public boolean isOK() { 
        return (this.tipo==Protocol.REPLY); 
    }
    
    public void replyErro(String erro){
        this.buffer[255] = new byte[1][erro.length()];
        this.buffer[255][0] = erro.getBytes();       
    }
    
    public void creatHello(){
       this.tipo = Protocol.HELLO;
    }
    
    public void creatRegister(String nome, String alcunha, String secInfo){
        this.tipo = Protocol.REGISTER;
        
        //necessário???
        this.tamanho = nome.length()+alcunha.length()+secInfo.length();

        buffer[1] = new byte[1][nome.length()];
        buffer[1][0] = nome.getBytes();
        
        buffer[2] = new byte[1][alcunha.length()];
        buffer[2][0] = alcunha.getBytes();
        
        buffer[3] = new byte[1][secInfo.length()];
        buffer[3][0] = secInfo.getBytes();        
    }
    
    public void creatLogin(String alcunha, String secInfo){
        this.tipo = Protocol.LOGIN;
        
        this.tamanho = alcunha.length()+secInfo.length();
        
        buffer[2] = new byte[1][alcunha.length()];
        buffer[2][0] = alcunha.getBytes();
        
        buffer[3] = new byte[1][secInfo.length()];
        buffer[3][0] = secInfo.getBytes();        
    }
    
    
    public void creatLogout(){
        this.tipo = Protocol.LOGOUT;
    }
    
    public void creatInfo(){
        this.tipo = Protocol.INFO;
    }
    
    public void creatQuit(){
        this.tipo = Protocol.QUIT;
    }
    
    public void creatEnd(){
        this.tipo = Protocol.END;
    }
    
    public void creatRetransmit(){
        this.tipo = Protocol.RETRANSMIT;
    }
    
    public void creatListRanking(){
        this.tipo = Protocol.LIST_RANKING;
    }
    
    
  
    public void creatMakeChallenge(String challenge, GregorianCalendar gc){
        this.tipo = Protocol.MAKE_CHALLENGE;        
        this.setDesafio(challenge);                
        this.addGregorianCalendar(gc);
    }
    
    public void addGregorianCalendar(GregorianCalendar gc){
        this.setData(gc.get(GregorianCalendar.YEAR), gc.get(GregorianCalendar.MONTH), gc.get(GregorianCalendar.DAY_OF_MONTH));        
        this.setHora(gc.get(GregorianCalendar.HOUR_OF_DAY), gc.get(GregorianCalendar.MINUTE), gc.get(GregorianCalendar.SECOND));
    }
    
    
    public List<GregorianCalendar> getListGregorianCalendar(){
        List<GregorianCalendar> res = new ArrayList<>();
        
        for(int i=0; i<this.buffer[4].length; i++){
            GregorianCalendar gc = new GregorianCalendar(this.getIndiceDataAno(i),this.getIndiceDataMes(i),this.getIndiceDataDia(i),this.getIndiceDataHora(i),this.getIndiceDataMinuto(i),this.getIndiceDataMinuto(i));
            res.add(gc);
        }
        
        return res;
    }
    
    public void addListGregorianCalendar(List<GregorianCalendar> list){
        this.buffer[4] = new byte[list.size()][6];  
        this.buffer[5] = new byte[list.size()][6]; 
        
        int i=0;
        for(GregorianCalendar gc : list){                  
            BigInteger bigInt = BigInteger.valueOf(gc.get(GregorianCalendar.YEAR));      
            byte[] aux =  bigInt.toByteArray();

            //Data
            this.buffer[4][i][0] = aux[0];
            this.buffer[4][i][1] = aux[1];

            this.buffer[4][i][2] = (byte) 0;  //este byte não é necessário -> fiaca a 0
            this.buffer[4][i][3] = (byte) gc.get(GregorianCalendar.MONTH);

            this.buffer[4][i][4] = (byte) 0;
            this.buffer[4][i][5] = (byte) gc.get(GregorianCalendar.DAY_OF_MONTH);
            
            //Hora
            this.buffer[5][i][0] = (byte) 0;
            this.buffer[5][i][1] = (byte) gc.get(GregorianCalendar.HOUR_OF_DAY);

            this.buffer[5][i][2] = (byte) 0;
            this.buffer[5][i][3] = (byte) gc.get(GregorianCalendar.MINUTE);

            this.buffer[5][i][4] = (byte) 0;
            this.buffer[5][i][5] = (byte) gc.get(GregorianCalendar.SECOND);
         
            i++;
        }
    }
    
    public void creatAcceptChallenge(String challenge){
        this.tipo = Protocol.ACCEPT_CHALLENGE;
        
        this.buffer[7] = new byte[1][challenge.length()];
        this.buffer[7][0] = challenge.getBytes();
    }
    
    public void creatdeleteChallenge(String challenge){
        this.tipo = Protocol.DELETE_CHALLEHNGE;
        
        this.buffer[7] = new byte[1][challenge.length()];
        this.buffer[7][0] = challenge.getBytes();
    }
    
    public void creatAnswer(){
        this.tipo = Protocol.ANSWER;
    }
    
    public boolean getOK(){
        if ((this.buffer[0] != null) && (this.buffer[0][0] != null))
            return true;        
        return false;
    }
    
    public String getErro(){
        return new String(buffer[255][0]);
    }
    
    //Campo 1
    public String getNome(){
        return new String(buffer[1][0]);
    }
    
    public void setNome(String nome){
        buffer[1] = new byte[1][nome.length()];
        buffer[1][0] = nome.getBytes();
    }  
    
    public List<String> getListNome(){
        List<String> res = new ArrayList<>();
        int l = this.buffer[1].length;
        
        for(int i=0; i<l; i++)
            res.add(new String(buffer[1][i]));            
        
        return res;
    }
    
    public void setListNome(List<String> list){
        buffer[1] = new byte[list.size()][];
        
        int i=0;
        for(String challenge : list){
            buffer[1][i] = new byte[challenge.length()];
            buffer[1][i] = challenge.getBytes();
            i++;
        }        
    }
    
    //Campo 2
    public String getAlcunha(){
        return new String(buffer[2][0]);
    }
    
    public void setAlcunha(String alcunha){
        buffer[2] = new byte[1][alcunha.length()];
        buffer[2][0] = alcunha.getBytes();
    }
    
    public List<String> getListAlcunha(){
        List<String> res = new ArrayList<>();
        int l = this.buffer[2].length;
        
        for(int i=0; i<l; i++)
            res.add(new String(buffer[2][i]));            
        
        return res;
    }
    
    public void setListAlcunha(List<String> list){
        buffer[2] = new byte[list.size()][];
        
        int i=0;
        for(String challenge : list){
            buffer[2][i] = new byte[challenge.length()];
            buffer[2][i] = challenge.getBytes();
            i++;
        }        
    }
        
    //Campo 3
    public String getSecInfo(){
        return new String(buffer[3][0]); 
    }        
    
    //Campo 4
    public String getData(){
        return new String(buffer[4][0]);
    }
    
    public void setData(int ano, int mes, int dia){
        this.buffer[4] = new byte[1][6];  
        
        BigInteger bigInt = BigInteger.valueOf(ano);      
        byte[] aux =  bigInt.toByteArray();
                      
        this.buffer[4][0][0] = aux[0];
        this.buffer[4][0][1] = aux[1];
        
       
        this.buffer[4][0][2] = (byte) 0;  //este byte não é necessário -> fiaca a 0
        this.buffer[4][0][3] = (byte) mes;
              
        this.buffer[4][0][4] = (byte) 0;
        this.buffer[4][0][5] = (byte) dia;
    }
    
    public int getDataAno(){
        byte[][] aux = new byte[1][2];
        aux[0][0] = buffer[4][0][0];
        aux[0][1] = buffer[4][0][1];
        
        return (new BigInteger(aux[0]).intValue());       
    }
    
    public int getIndiceDataAno(int i){
        byte[][] aux = new byte[1][2];
        aux[0][0] = buffer[4][i][0];
        aux[0][1] = buffer[4][i][1];
        
        return (new BigInteger(aux[0]).intValue());       
    }
    
    public int getDataMes(){
        byte[][] aux = new byte[1][2];
        aux[0][0] = buffer[4][0][2];
        aux[0][1] = buffer[4][0][3];
        
        return (new BigInteger(aux[0]).intValue());  
    }
    
    public int getIndiceDataMes(int i){
        byte[][] aux = new byte[1][2];
        aux[0][0] = buffer[4][i][2];
        aux[0][1] = buffer[4][i][3];
        
        return (new BigInteger(aux[0]).intValue());  
    }
    
    public int getDataDia(){
        byte[][] aux = new byte[1][2];
        aux[0][0] = buffer[4][0][4];
        aux[0][1] = buffer[4][0][5];
        
        return (new BigInteger(aux[0]).intValue());  
    }
    
    public int getIndiceDataDia(int i){
        byte[][] aux = new byte[1][2];
        aux[0][0] = buffer[4][i][4];
        aux[0][1] = buffer[4][i][5];
        
        return (new BigInteger(aux[0]).intValue());  
    } 
    
    //Campo 5
    public String getHora(){
        return new String(buffer[5][0]);
    }
    
    public void setHora(int hora, int minuto, int segundo){
        this.buffer[5] = new byte[1][6]; 
        
        this.buffer[5][0][0] = (byte) 0;
        this.buffer[5][0][1] = (byte) hora;
        
        this.buffer[5][0][2] = (byte) 0;
        this.buffer[5][0][3] = (byte) minuto;
         
        this.buffer[5][0][4] = (byte) 0;
        this.buffer[5][0][5] = (byte) segundo;
    }
    
    public int getDataHora(){
        byte[][] aux = new byte[1][2];
        aux[0][0] = buffer[5][0][0];
        aux[0][1] = buffer[5][0][1];
        
        return (new BigInteger(aux[0]).intValue());
    }
    
    public int getIndiceDataHora(int i){
        byte[][] aux = new byte[1][2];
        aux[0][0] = buffer[5][i][0];
        aux[0][1] = buffer[5][i][1];
        
        return (new BigInteger(aux[0]).intValue());
    }
    
    public int getDataMinuto(){
        byte[][] aux = new byte[1][2];
        aux[0][0] = buffer[5][0][2];
        aux[0][1] = buffer[5][0][3];
        
        return (new BigInteger(aux[0]).intValue());
    }
    
    public int getIndiceDataMinuto(int i){
        byte[][] aux = new byte[1][2];
        aux[0][0] = buffer[5][i][2];
        aux[0][1] = buffer[5][i][3];
        
        return (new BigInteger(aux[0]).intValue());
    }
    
    public int getDataSegundo(){
        byte[][] aux = new byte[1][2];
        aux[0][0] = buffer[5][0][4];
        aux[0][1] = buffer[5][0][5];
        
        return (new BigInteger(aux[0]).intValue());
    }
    
    public int getIndiceDataSegundo(int i){
        byte[][] aux = new byte[1][2];
        aux[0][0] = buffer[5][i][4];
        aux[0][1] = buffer[5][i][5];
        
        return (new BigInteger(aux[0]).intValue());
    }
    
    //Campo 6
    public int getEscolha(){
        return (this.buffer[6][0][0]);
    }
    
    public void setEscolha(int escolha){
        this.buffer[6] = new byte[1][1];
        this.buffer[6][0][0] = (byte)escolha;
    }
    
    //Campo 7
    public String getDesafio(){
        return new String(buffer[7][0]);
    }
    
    public List<String> getListDesafio(){
        List<String> res = new ArrayList<>();
        int l = this.buffer[7].length;
        
        for(int i=0; i<l; i++)
            res.add(new String(buffer[7][i]));            
        
        return res;
    }
    
    public void setDesafio(String challenge){
        buffer[7] = new byte[1][challenge.length()];
        buffer[7][0] = challenge.getBytes();
    }
    
    public void setListDesafio(List<String> list){
        buffer[7] = new byte[list.size()][];
        
        int i=0;
        for(String challenge : list){
            buffer[7][i] = new byte[challenge.length()];
            buffer[7][i] = challenge.getBytes();
            i++;
        }        
    }    
    
    //Campo 10
    public int getNumQuestao(){
        return (this.buffer[10][0][0]);
    }
    
    public void setNumQuestao(int number){
        this.buffer[10] = new byte[1][1];
        this.buffer[10][0][0] = (byte)number;
    }
        
    public boolean isQuestao(){
        if (this.buffer[10] != null)
            return true;        
        return false;
    }
    
    //Campo 11
    public String getQuestao(){
        return new String(buffer[11][0]);
    }
    
    public void setQuestao(String q){
        buffer[11] = new byte[1][q.length()];
        buffer[11][0] = q.getBytes();
    }
    
    
    //INFO
    public List<String> getListQuestao(){
        List<String> res = new ArrayList<>();
        int l = this.buffer[11].length;
        
        for(int i=0; i<l; i++)
            res.add(new String(buffer[11][i]));            
        
        return res;
    }
    
    //INFO
    public void setListQuestao(List<String> list){
        buffer[11] = new byte[list.size()][];
        
        int i=0;
        for(String quest : list){
            buffer[11][i] = new byte[quest.length()];
            buffer[11][i] = quest.getBytes();
            i++;
        }        
    }
    
    //Campo 12
    public int getNumResposta(){
        return (this.buffer[12][0][0]);
    }
    
    public void setNumResposta(int number){
        this.buffer[12] = new byte[1][1];
        this.buffer[12][0][0] = (byte)number;
    }
    
    //Campo 13
    public String getResposta(){
        return new String(buffer[13][0]);
    }
    
    public void setResposta(String r){
        this.buffer[13] = new byte[1][r.length()];
        this.buffer[13][0] = r.getBytes();
    }
    
        
    //INFO
    public List<String> getListResposta(){
        List<String> res = new ArrayList<>();
        int l = this.buffer[13].length;
        
        for(int i=0; i<l; i++)
            res.add(new String(buffer[13][i]));            
        
        return res;
    }
    
    //INFO
    public void setListResposta(List<String> list){
        buffer[13] = new byte[list.size()][];
        
        int i=0;
        for(String resp : list){
            buffer[13][i] = new byte[resp.length()];
            buffer[13][i] = resp.getBytes();
            i++;
        }        
    }
    
    //Campo 14
    public boolean getCerta(){
        if (this.buffer[14][0][0] == 1)
            return true;        
        else 
            return false;
    }
    
    public void setCerta(boolean res){
        this.buffer[14] = new byte[1][1];
        if (res)
            this.buffer[14][0][0] = (byte) 1;
        else
            this.buffer[14][0][0] = (byte) 0;            
    }
    
    
    //INFO
    public List<Integer> getListCerta(){
        List<Integer> res = new ArrayList<>();
        int l = this.buffer[14].length;
        
        for(int i=0; i<l; i++)
            res.add((int)this.buffer[14][i][0]);            
        
        return res;
    }
    
    //INFO
    public void setLisCerta(List<Integer> list){
        buffer[14] = new byte[list.size()][];
        
        int i=0;
        for(Integer score : list){
            buffer[14][i] = new byte[1];
            buffer[14][i][0] = (byte)score.intValue();           
        }        
    }

    
    //Campo 15
    public int getPontos(){
        return Integer.parseInt(new String(buffer[15][0]));
    }
    
    public void setPontos(int pontos){
        this.buffer[15] = new byte[1][1];
        this.buffer[15][0][0] = (byte) pontos;
    }
    
    //Campo 16
    public byte[] getImagem(){
        return this.buffer[16][0];
    }
    
    public boolean hasImagem(){
        if ((this.buffer[16] != null)&&(this.buffer[16][0] != null))
            return true;        
        return false;
    }
    
    public int getLenghtImagem(){
        return this.buffer[16][0].length;
    }
    
    public void setImagem(byte[] img){
        this.buffer[16] = new byte[1][img.length];
        this.buffer[16][0] = img;
    }
    
        //metodo para INFO
    public void addListImage(List<byte[]> imgs){
        this.buffer[16] = new byte[imgs.size()][];
        
        int i=0;
        for(byte[] img : imgs){
            this.buffer[16][i] = new byte[img.length];
            this.buffer[16][i] = img;
        }
    }
    
    //metodo para INFO
    public List<byte[]> getListImagem(){
        List<byte[]> res = new ArrayList<byte[]>();
        
        int l = this.buffer[16].length;
        
        for(int i=0; i<l; i++)
            res.add(this.buffer[16][i]); 
        
        return res;
    }
    
    //Campo 17 -> BLOCO da Questao (nao da musica!)
    public int getBloco(){
        return (this.buffer[17][0][0]);
    }
    
    public void setBloco(int number){
        this.buffer[17] = new byte[1][1];
        this.buffer[17][0][0] = (byte)number; 
    }
    
    //Campo 18
    public byte[] getAudio(){
        return this.buffer[18][0];
    }
    
    public void setAudio(byte[] song){
        this.buffer[18] = new byte[1][song.length];
        this.buffer[18][0] = song;
    }
    
    //metodo para INFO
    public void addListAudio(List<byte[]> songs){
        this.buffer[18] = new byte[songs.size()][];
        
        int i=0;
        for(byte[] song : songs){
            this.buffer[18][i] = new byte[song.length];
            this.buffer[18][i] = song;
        }
    }
    
    //metodo para INFO
    public List<byte[]> getListAudio(){
        List<byte[]> res = new ArrayList<byte[]>();
        
        int l = this.buffer[18].length;
        
        for(int i=0; i<l; i++)
            res.add(this.buffer[18][i]); 
        
        return res;
    }
    
    public boolean hasAudio(){
        if ((this.buffer[18] != null) && (this.buffer[18][0] != null))
            return true;        
        return false;
    }
    
    public int getLenghtAudio(){
        return this.buffer[18].length;
    }
    
    //Campo 20
    
    public int getScore(){
        return (this.buffer[20][0][0]);
    }
    
    public void setScore(int number){
        this.buffer[20] = new byte[1][1];
        this.buffer[20][0][0] = (byte)number; //is that?
    }
    
    public List<Integer> getListScore(){
        List<Integer> res = new ArrayList<>();
        int l = this.buffer[20].length;
        
        for(int i=0; i<l; i++)
            res.add((int)this.buffer[20][i][0]);            
        
        return res;
    }
    
    public void setListScore(List<Integer> list){
        buffer[20] = new byte[list.size()][];
        
        int i=0;
        for(Integer score : list){
            buffer[20][i] = new byte[1];
            buffer[20][i][0] = (byte)score.intValue();
            i++;
        }        
    }
    //campo 30
    public String getIPServer(){
        return new String(this.buffer[30][0]);
    }  
    public void setIPServer(String ip){
        this.buffer[30][0] = new byte[ip.length()];
        this.buffer[30][0] = ip.getBytes();
    }
    //campo  31
    public String getPort(){
        return new String(this.buffer[31][0]);
    }
    public void setServerPort(String Port){
        this.buffer[31][0] = new byte[Port.length()];
        this.buffer[31][0] = Port.getBytes();
    }
    
    //campo 254
    public boolean hasNextPDU(){
        if ((this.buffer[254]!=null) && (this.buffer[254][0]!=null))
            return true;        
        return false;
    }
    
    public void willBeNextPDU(){
        this.buffer[254] = new byte[1][1];
        this.buffer[254][0][0] = (byte)1;
    }
    

    public int getVersao() {
        return versao;
    }

    public int getSeg() {
        return seg;
    }

    public int getLabel() {
        return label;
    }

    public int getTipo() {
        return tipo;
    }

    public int getnCampos() {
        return nCampos;
    }

    public int getTamnho() {
        return tamanho;
    }

    public byte[][][] getBuffer() {
        return buffer;
    }
    
    @Override
    public String toString() {
        return "Message{" + "versao=" + versao + ", seg=" + seg + ", label=" + label + ", tipo=" + tipo + ", nCampos=" + nCampos + ", tamnho=" + tamanho + "}";
    }  
    
}
