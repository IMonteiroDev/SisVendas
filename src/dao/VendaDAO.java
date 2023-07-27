/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import beans.Itens;
import beans.Venda;
import conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author ifsp
 */
public class VendaDAO {
    private Connection conn;
    
    public VendaDAO(){
        Conexao c = new Conexao();
        this.conn = c.getConexao();
    }
    
    public void inserir(Venda v) throws Exception {
        String sql = "INSERT INTO vendas(datavenda, valortotal, formapagamento, clienteid)"
                + " VALUES (?, ?, ?, ?)";
        
        try {
            //desabilitar o autocommit
            this.conn.setAutoCommit(false);
            //Statement.RETURN_GENERATED_KEYS é para retornar a chave primária AUTOINC que será gerada
            PreparedStatement stmt = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, v.getDatavenda());
            stmt.setDouble(2, v.getValortotal());
            stmt.setString(3, v.getFormapagamento());
            stmt.setInt(4, v.getCliente().getId());
            stmt.execute();
            
            //pegar o ID da venda que foi gerado automaticamente no INSERT
            ResultSet rs = stmt.getGeneratedKeys();
            rs.first();
            v.setId(rs.getInt(1));
            
            //"setar" o "v" nos itens da Venda
            for(Itens item : v.getItens()){
                item.setVenda(v);
            }
            
            //salva os itens
            ItensDAO itensDAO = new ItensDAO();
            itensDAO.setConn(conn);
            itensDAO.inserir(v.getItens());
            
            this.conn.commit();//confirma pq tudo deu certo!
        } catch (Exception e) {
            //se deu erro, desfaz tudo
            this.conn.rollback();
            throw new Exception(e.getMessage());
        }
    }
}
