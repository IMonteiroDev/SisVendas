/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import beans.Itens;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

/**
 *
 * @author ifsp
 */
public class ItensDAO {
    private Connection conn;

    public void setConn(Connection conn) {
        this.conn = conn;
    }
    
    public void inserir(List<Itens> itens) throws Exception {
        String sql = "INSERT INTO itens(vendaid, produtoid, quantidade) VALUES (?, ?, ?)";
        ProdutoDAO produtoDAO = new ProdutoDAO();
        
        try {
            for(Itens item : itens){
                PreparedStatement stmt = this.conn.prepareStatement(sql);
                stmt.setInt(1, item.getVenda().getId());
                stmt.setInt(2, item.getProduto().getId());
                stmt.setInt(3, item.getQuantidade());
                
                stmt.execute();
                
                //chama o método para alterar o estoque
                produtoDAO.alterarEstoque(item.getProduto().getId(), item.getQuantidade(), conn);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
