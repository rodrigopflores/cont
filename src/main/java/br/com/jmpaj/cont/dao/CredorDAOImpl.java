package br.com.jmpaj.cont.dao;

import br.com.jmpaj.cont.db.DB;
import br.com.jmpaj.cont.domain.Credor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class CredorDAOImpl implements CredorDAO {

    private Connection conn;

    public CredorDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Credor credor) {
        PreparedStatement insertStatement = null;
        PreparedStatement selectStatement = null;
        ResultSet resultSet = null;
        try {
            insertStatement = conn.prepareStatement(
                    "INSERT INTO tbcredor " +
                            "(Nome, Endereco, Bairro, Cidade, CpfCnpj, CEP) " +
                            "VALUES " +
                            "(?, ?, ?, ?, ?, ?)"
            );

            selectStatement = conn.prepareStatement(
                    "SELECT CpfCnpj FROM tbcredor " +
                            "WHERE CpfCnpj = ? "
            );

            selectStatement.setString(1, credor.getCpfCnpj());
            resultSet = selectStatement.executeQuery();
            if (!resultSet.next() ||
                    (resultSet.getString(1).equals("") && credor.getNome() != null && !credor.getNome().isEmpty())) {
                insertStatement.setString(1, credor.getNome());
                insertStatement.setString(2, credor.getEndereco());
                insertStatement.setString(3, credor.getBairro());
                insertStatement.setString(4, credor.getCidade());
                insertStatement.setString(5, credor.getCpfCnpj());
                insertStatement.setString(6, credor.getCep());
                insertStatement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(resultSet);
            DB.closeStatement(insertStatement);
            DB.closeStatement(selectStatement);
        }
    }

    @Override
    public void update(Credor credor) {

    }

    @Override
    public void delete(Credor credor) {

    }

    @Override
    public Credor findById(Integer id) {
        return null;
    }

    @Override
    public Credor findByCpfCnpj(String cpfCnpj) {
        return null;
    }

    @Override
    public List<Credor> findAll() {
        return null;
    }
}
