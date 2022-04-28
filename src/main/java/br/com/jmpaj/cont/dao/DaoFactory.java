package br.com.jmpaj.cont.dao;

import br.com.jmpaj.cont.db.DB;

public class DaoFactory {
    public static CredorDAO createCredorDAO() {
        return new CredorDAOImpl(DB.getConnection());
    }

    public static CreditoDAO createCreditoDAO() {
        return new CreditoDAOImpl(DB.getConnection());
    }

    public static ControleHabDAO createControleHabDAO() {
        return new ControleHabDAOImpl(DB.getConnection());
    }

}
