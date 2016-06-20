package middleware;

/**
 * Armazena e encapsula por forma a proteger e tornar mais
 * legíveis os códigos que constituem o nosso protocolo de comunicação (Classe visível por toda a aplicação).
 * 
 * @author jdc
 * @version 2015.04.13
 */

public class Protocol {

    // Tipos de pedidos
    public static byte HELLO = 1;
    public static byte REGISTER = 2;
    public static byte LOGIN = 3;
    public static byte LOGOUT = 4;
    public static byte QUIT = 5;
    public static byte END = 6;
    public static byte LIST_CHALLENGE = 7;
    public static byte MAKE_CHALLENGE = 8;
    public static byte ACCEPT_CHALLENGE = 9;
    public static byte DELETE_CHALLEHNGE = 10;
    public static byte ANSWER = 11;
    public static byte RETRANSMIT = 12;
    public static byte LIST_RANKING = 13;
    public static byte INFO = 14;
    
    // Código 00 é reservado para respostas do servidor
    public static byte REPLY = 0;
    
    //Tamanho máximo para os campos de Dados
    public static int MAX_BYTE = 48000;
}
