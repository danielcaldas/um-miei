
import java.io.Serializable;

/**
 * Classe utilizador onde agrupamos os dados de uma entidade que interage com o servidor.
 * @author Jorge Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
 * @version 2014.12.13 
 */

public class User implements Serializable {
    
    private String nickname;	
    private String password;
    private boolean ativo;
        

    public User() {
        this.nickname = "";
        this.password = "";
        this.ativo = false;
    }

    public User(String nick, String pw) {
        this.nickname = nick;
	this.password = pw;
        this.ativo = false;
    }

    public User(User c) {
	this.nickname = c.getNickname();
	this.password = c.getPassword();
        this.ativo = c.isAtivo();
    }

    public String getNickname() { return this.nickname; }

    public String getPassword() { return this.password; }
    
    public boolean isAtivo() { return this.ativo; }
       
    public void setAtivo(boolean a) { this.ativo = a; } 
        
    @Override
    public User clone() { return new User(this); }

    @Override   
    public String toString() {
	StringBuilder s = new StringBuilder(" --- Cliente --- \n");
        s.append(" - Utilizador: ").append(this.getNickname()).append("\n");
	s.append(" - Password ").append(this.getPassword()).append("\n");
        return s.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
	if ((o == null) || (o.getClass() != this.getClass()))
            return false;
	else {
            User c = (User) o;
            return (this.getNickname().equals(c.getNickname()));
	}
    }
}
