package server;

/**
 * Classe que agrupa/organiza mensagens de todos os tipos, desde erros a outros tipos de informações.
 * @author jdc
 * @version 2015.04.18
 */

public class Messages {
    public static String NICK_ALREADY_EXISTS = "Já existe um utilizador com esta alcunha. Por favor utilize outra alcunha.";
    public static String NO_USER = "Utilizador inexistente.";
    public static String CHALLENGE_ALREADY_EXIST = "Já existe um desafio com este nome!.";
    public static String USER_ALREADY_PAYING = "o jogador já se encontra inscrito num desafio.";
    public static String CHALLENGE_UNAVAILABLE = "o desafio não se encontra disponivel.";
    public static String ALREADY_LOGGED = "O utilizador já tem sessão iniciada.";
    public static String WRONG_PASSWORD = "Password errada.";
}
