/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import beans.Produto;
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
public class ProdutoDAO {
    private Connection conn;
    
    public ProdutoDAO() {
        Conexao c = new Conexao();
        this.conn = c.getConexao();
    }
    
    public void inserir(Produto p) throws Exception {
        String sql = "INSERT INTO produtos(nomeproduto, valor, estoque) VALUES (?, ?, ?)";
        
        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, p.getNomeproduto());
            stmt.setDouble(2, p.getValor());
            stmt.setInt(3, p.getEstoque());
            stmt.execute();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    
    public void editar(Produto p) throws Exception {
        String sql = "UPDATE produtos SET nomeproduto = ?, valor = ?, estoque = ? WHERE id = ?";
        
        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, p.getNomeproduto());
            stmt.setDouble(2, p.getValor());
            stmt.setInt(3, p.getEstoque());
            stmt.setInt(4, p.getId());
            stmt.execute();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    
    public void excluir(int id) throws Exception {
        String sql = "DELETE FROM produtos WHERE id = ?";
        
        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    
    //Recebe o "conn" como parâmetro porque vamos usar a mesma instancia da conexão para
    //inserir a venda, inserir os itens e alterar o estoque.
    public void alterarEstoque(int id, int quantidade, Connection conn) throws Exception{
        String sql = "UPDATE produtos SET estoque = estoque - ? WHERE id = ?";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, quantidade);
            stmt.setInt(2, id);
            stmt.execute();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    
    public List<Produto> getProdutos() throws Exception {
        List<Produto> lista = new ArrayList();
        
        String sql = "SELECT * FROM produtos";
        
        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                Produto p = new Produto();
                
                p.setId(rs.getInt("id"));
                p.setNomeproduto(rs.getString("nomeproduto"));
                p.setValor(rs.getDouble("valor"));
                p.setEstoque(rs.getInt("estoque"));
                
                lista.add(p);
            }
            return lista;
        } catch (Exception e) {
            throw new Exception("Erro ao buscar produtos: " + e.getMessage());
        }
    }
    
    public Produto getProduto(int id) throws Exception {
        String sql = "SELECT * FROM produtos WHERE id = ?";
        
        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.first();
            
            Produto p = new Produto();
                
                p.setId(rs.getInt("id"));
                p.setNomeproduto(rs.getString("nomeproduto"));
                p.setValor(rs.getDouble("valor"));
                p.setEstoque(rs.getInt("estoque"));
            
            return p;
        } catch (Exception e) {
            throw new Exception("Erro ao buscar produto: " + e.getMessage());
        }
    }
}
