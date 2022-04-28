package br.com.jmpaj.cont.dao;

import br.com.jmpaj.cont.db.DB;
import br.com.jmpaj.cont.domain.Credito;
import br.com.jmpaj.cont.domain.Credor;
import br.com.jmpaj.cont.domain.Impugnacao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CreditoDAOImpl implements CreditoDAO {

    private Connection conn;

    public CreditoDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Credito credito) {

    }

    @Override
    public void update(Credito credito) {

    }

    @Override
    public void delete(Credito credito) {

    }

    @Override
    public Credito findById(Integer id) {
        return null;
    }

    @Override
    public List<Credito> findAllByNomeCredor(String nomeCredor) {
        PreparedStatement selectStatement = null;
        ResultSet resultSet = null;
        List<Credito> creditos = new ArrayList<>();
        try {
            selectStatement = conn.prepareStatement(
                    " SELECT m.Id, classeId, c.Nome, c.CpfCnpj, m.valor, m.origem, i.numero, i.transitojulg\n" +
                            " FROM tbcredito m\n" +
                            " INNER JOIN tbcredor c\n" +
                            " ON c.id = m.credorId\n" +
                            " LEFT JOIN tbimpugnacao i\n" +
                            " ON i.id = m.impugnacaoId\n" +
                            " WHERE c.Nome LIKE ? "
            );
            selectStatement.setString(1, "%"+nomeCredor+"%");
            resultSet = selectStatement.executeQuery();
            while(resultSet.next()) {
                Credor credor = Credor.builder()
                        .nome(resultSet.getString("Nome"))
                        .cpfCnpj(resultSet.getString("CpfCnpj"))
                        .build();
                Date date = resultSet.getDate("transitojulg");
                Impugnacao impugnacao = Impugnacao.builder()
                        .numero(resultSet.getString("numero"))
                        .transitoJulg(date != null ? date.toLocalDate() : null)
                        .build();
                Credito credito = Credito.builder()
                        .id(resultSet.getInt("Id"))
                        .credor(credor)
                        .impugnacao(impugnacao)
                        .classe(resultSet.getInt("classeId"))
                        .valor(resultSet.getDouble("valor"))
                        .origem(resultSet.getString("origem"))
                        .build();
                creditos.add(credito);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(selectStatement);
        }
        return creditos;
    }

    @Override
    public List<Credito> findAll() {
        return null;
    }
}
