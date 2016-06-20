package data_access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe que faz gestão da conexão à base de dados.
 * @author jdc
 * @version 2014.12.29
 */

public class MySQLManager {  
    
    public MySQLManager(){
    }
   
    public Connection getConnection() throws ConnectionErrorException, SQLException{  
         Connection con = null;  
         try{  
               Class.forName("com.mysql.jdbc.Driver");  
               con = DriverManager.getConnection("jdbc:mysql://localhost/Habitat?useUnicode=yes&characterEncoding=UTF-8", "root", "root");
         }catch(ClassNotFoundException e){  
               try{
                    throw new ConnectionErrorException(e.getMessage()+"\n"+"Driver Não Encontrado");  
               }catch(ConnectionErrorException exe){  
                      throw new ConnectionErrorException("Erro de conexão");  
               }  
          }catch(SQLException exe){  
               throw new ConnectionErrorException("Erro ao tentar conectar");  
          }
         return con;
    }
    
    public void CloseConexao(Connection Con)throws ConnectionErrorException, SQLException{  
        try{  
           Con.close();  
        }catch(SQLException exe){  
             throw new ConnectionErrorException("Erro ao tentar fechar a conexão");  
        }  
    }
    
    public void CloseStatement(Statement Stim)throws ConnectionErrorException, SQLException{  
        Stim.close();  
    }
}    
