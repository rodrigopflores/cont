package br.com.jmpaj.cont.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ControleHab {
    private String processo;
    private String credor;
    private String situacao;
    private String sentenca;

    @Override
    public String toString() {
        return String.format("%-26.26s | %-30.30s | %-10.10s | %-10.10s", processo, credor, situacao, sentenca);
    }
}
