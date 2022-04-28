package br.com.jmpaj.cont.dao;

import br.com.jmpaj.cont.domain.ControleHab;

import java.util.List;

public interface ControleHabDAO {
    void insert(ControleHab hab);
    void update(ControleHab hab);
    void delete(ControleHab hab);
    ControleHab findById(Integer id);
    List<ControleHab> findAllByNomeCredor(String nomeCredor);
    List<ControleHab> findAll();
}
