package br.com.jmpaj.cont.dao;

import br.com.jmpaj.cont.domain.Credor;

import java.util.List;

public interface CredorDAO {
    void insert(Credor credor);
    void update(Credor credor);
    void delete(Credor credor);
    Credor findById(Integer id);
    Credor findByCpfCnpj(String cpfCnpj);
    List<Credor> findAll();
}
