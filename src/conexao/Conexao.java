/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexao;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author ifsp
 */
public class Conexao {
    
    public Connection getConexao()
    {
        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dbvendas?serverTimezone=UTC", 
                "root", 
                ""
            );
            return conn;
        } catch (Exception e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
            return null;
        }
    }
    
}
