package br.com.jmpaj.cont.dao;

import br.com.jmpaj.cont.db.DB;
import br.com.jmpaj.cont.domain.ControleHab;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControleHabDAOImpl implements ControleHabDAO {

    private Connection conn;

    public ControleHabDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(ControleHab hab) {
        PreparedStatement insertStatement = null;
        try {
            insertStatement = conn.prepareStatement(
                    "INSERT INTO tbhabs " +
                            "(Processo, Credor, Situacao, Sentenca) " +
                            "VALUES " +
                            "(?, ?, ?, ?)"
            );
            insertStatement.setString(1, hab.getProcesso());
            insertStatement.setString(2,hab.getCredor());
            insertStatement.setString(3,hab.getSituacao());
            insertStatement.setString(4,hab.getSentenca());
            insertStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(insertStatement);
        }
    }

    @Override
    public void update(ControleHab hab) {

    }

    @Override
    public void delete(ControleHab hab) {

    }

    @Override
    public ControleHab findById(Integer id) {
        return null;
    }

    @Override
    public List<ControleHab> findAllByNomeCredor(String nomeCredor) {
        PreparedStatement selectStatement = null;
        ResultSet resultSet = null;
        List<ControleHab> habs = new ArrayList<>();
        try {
            selectStatement = conn.prepareStatement(
                    " SELECT * \n" +
                            " FROM tbhabs m\n" +
                            " WHERE credor LIKE ? "
            );
            selectStatement.setString(1, "%"+nomeCredor+"%");
            resultSet = selectStatement.executeQuery();
            while(resultSet.next()) {
                ControleHab hab = ControleHab.builder()
                        .processo(resultSet.getString("Processo"))
                        .credor(resultSet.getString("Credor"))
                        .situacao(resultSet.getString("Situacao"))
                        .sentenca(resultSet.getString("Sentenca"))
                        .build();
                habs.add(hab);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(selectStatement);
        }
        return habs;
    }

    @Override
    public List<ControleHab> findAll() {
        return null;
    }
}
