package br.com.jmpaj.cont.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class Habilitacao {
    private String credor;
    private String numeroProcesso;
    private String credito;
    private String procurador;
    private String creditoProcurador;
    private String reclamatoria;
    private boolean documentacaoCerta;
    private boolean calculoCerto;
    private boolean substituicao;
}
