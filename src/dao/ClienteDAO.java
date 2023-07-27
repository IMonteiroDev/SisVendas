/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import beans.Cliente;
import conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rafael
 */
public class ClienteDAO {
    private Connection conn;
    
    public ClienteDAO() {
        Conexao c = new Conexao();
        this.conn = c.getConexao();
    }
    
    public void inserir(Cliente c) throws Exception {
        String sql = "INSERT INTO clientes(nomecliente) VALUES (?)";
        
        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, c.getNomecliente());
            stmt.execute();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    
    public void editar(Cliente c) throws Exception {
        String sql = "UPDATE clientes SET nomecliente = ? WHERE id = ?";
        
        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, c.getNomecliente());
            stmt.setInt(2, c.getId());
            stmt.execute();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    
    public void excluir(int id) throws Exception {
        String sql = "DELETE FROM clientes WHERE id = ?";
        
        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    
    public List<Cliente> getClientes() throws Exception {
        List<Cliente> lista = new ArrayList();
        
        String sql = "SELECT * FROM clientes";
        
        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                Cliente c = new Cliente();
                
                c.setId(rs.getInt("id"));
                c.setNomecliente(rs.getString("nomecliente"));
                
                lista.add(c);
            }
            return lista;
        } catch (Exception e) {
            throw new Exception("Erro ao buscar clientes: " + e.getMessage());
        }
    }
    
    public Cliente getCliente(int id) throws Exception {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        
        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.first();
            
            Cliente c = new Cliente();                
            c.setId(rs.getInt("id"));
            c.setNomecliente(rs.getString("nomecliente"));
            
            return c;
        } catch (Exception e) {
            throw new Exception("Erro ao buscar cliente: " + e.getMessage());
        }
    }
}
