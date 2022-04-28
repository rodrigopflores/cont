package br.com.jmpaj.cont.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Credito {
    private int id;
    private Credor credor;
    private int classe;
    private String origem;
    private double valor;
    private Impugnacao impugnacao;

    @Override
    public String toString() {
        return String.format("%-35.35s | %d | %-35.35s | R$ %8.2f | %-16.16s | %-25.25s | %-12s", credor.getNome(), classe, origem, valor, credor.getCpfCnpj(), impugnacao.getNumero(), impugnacao.getTransitoJulg());
    }
}
