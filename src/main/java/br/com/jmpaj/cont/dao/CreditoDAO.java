package br.com.jmpaj.cont.dao;

import br.com.jmpaj.cont.domain.Credito;

import java.util.List;

public interface CreditoDAO {
    void insert(Credito credito);
    void update(Credito credito);
    void delete(Credito credito);
    Credito findById(Integer id);
    List<Credito> findAllByNomeCredor(String nomeCredor);
    List<Credito> findAll();
}
