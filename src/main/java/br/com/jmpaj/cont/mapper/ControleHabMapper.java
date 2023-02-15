package br.com.jmpaj.cont.mapper;

import br.com.jmpaj.cont.domain.ControleHab;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ControleHabMapper {
    public static ControleHab resultSetToDomain(ResultSet resultSet) throws SQLException {
        return ControleHab.builder()
                .processo(resultSet.getString("Processo"))
                .credor(resultSet.getString("Credor"))
                .situacao(resultSet.getString("Situacao"))
                .sentenca(resultSet.getString("Sentenca"))
                .build();
    }
}
