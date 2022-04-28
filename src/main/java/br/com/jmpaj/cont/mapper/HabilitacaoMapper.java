package br.com.jmpaj.cont.mapper;

import br.com.jmpaj.cont.HelloController;
import br.com.jmpaj.cont.domain.Habilitacao;

public class HabilitacaoMapper {

    public static Habilitacao toDomain(HelloController controller) {
        return Habilitacao.builder()
                .numeroProcesso(controller.getNumeroProcesso().getText())
                .credor(controller.getCredor().getText())
                .credito(controller.getCredito().getText())
                .procurador(controller.getProcurador().getText())
                .creditoProcurador(controller.getCreditoProcurador().getText())
                .reclamatoria(controller.getReclamatoria().getText())
                .substituicao(controller.getSubstituicao().isSelected())
                .calculoCerto(controller.getCalculoCerto().isSelected())
                .documentacaoCerta(controller.getDocumentacaoCerta().isSelected())
                .build();
    }
}
