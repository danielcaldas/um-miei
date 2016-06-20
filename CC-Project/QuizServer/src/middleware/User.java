package middleware;

/**
 * Classe utilizador que agrega dados acerca de um utilizador da aplicação.
 * @author carlos, daniel, xavier
 * @version 2015.04.04
 */

public class User {
    
    // Variáveis de instância
    String nome;
    String username;
    String password; 
    boolean logged;
    Integer score;
    
    // Construtores
    public User() {
        this.nome = "";
        this.username = "";
        this.password = ""; 
        this.logged = false;
        this.score = 0;
    }

    public User(String nome, String username, String password) {
        this.nome = nome;
        this.username = username;
        this.password = password;
        this.score = 0;
    }
    
    public User(User u) {
        this.nome = u.getNome();
        this.username = u.getUsername();
        this.password = u.getPassword();
        this.score = u.getScore();
    }
    
    // getters & setters
    public String getNome() { return nome; }
    public int getScore() { return this.score; }
    public Integer getIntegerScore() { return this.score; }
    public boolean isLogged() { return this.logged; }
    public void setNome(String nome) { this.nome = nome; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public void setLogged() { 
       if(this.logged==true)
           this.logged = false;
       else
           this.logged = true;
    }
    
    //são utilizados estes métodos para garantir exc. mutua
    public boolean login(){
        if (!this.logged) {
            this.logged = true;
            return true;
        } else
            return false;        
    }

    public void logout() {
        //se cá chega é pq esta logado->basta isto
        this.logged = false;
    }
    
    public void addScore(int score){this.score += score;}
    
    // equals, clone and toString
    @Override
    public boolean equals(Object o) {
      if(this==o) return true;
      
      else if(o==null || !this.getClass().equals(o.getClass())) return false;
      
      else{
          User u = (User)o;
          
          return (this.username.equals(u.getUsername()));
      }
    }
    
    @Override
    public User clone() {
        return new User(this);
    }
    
    @Override
    public String toString() {
        return ("Nome: "+this.nome+"\nUsername: "+this.username+"\nPassword: "+this.password);
    }
    
}
