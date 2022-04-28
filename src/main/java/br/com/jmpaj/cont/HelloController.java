package br.com.jmpaj.cont;

import br.com.jmpaj.cont.exception.ValidacaoCamposException;
import br.com.jmpaj.cont.mapper.HabilitacaoMapper;
import br.com.jmpaj.cont.service.Service;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Slf4j
public class HelloController {

    public TextField credor;
    public TextField numeroProcesso;
    public TextField credito;
    public TextField procurador;
    public TextField creditoProcurador;
    public TextField reclamatoria;
    public CheckBox substituicao;
    public CheckBox documentacaoCerta;
    public CheckBox calculoCerto;
    public Label status;
    public ListView<Label> listaHabilitacoes;
    public ListView<Label> listaQGC;

    public void onProcurarButton(ActionEvent actionEvent) {

        status.setText("Buscando...");
        String credorString = credor.getText();

        List<Label> habs = Service.checarSePossuiHabilitacao(credorString)
                .stream()
                .map(c -> new Label(c.toString()))
                .collect(Collectors.toList());

        List<Label> creds = Service.checarSeEstaNaRelacaoDeCredores(credorString)
                .stream()
                .map(c -> new Label(c.toString()))
                .collect(Collectors.toList());

        listaHabilitacoes.getItems().clear();
        for (Label l : habs) {
            l.setStyle("-fx-font-family: 'monospaced';");
            listaHabilitacoes.getItems().add(l);
        }

        listaQGC.getItems().clear();
        for (Label c : creds) {
            c.setStyle("-fx-font-family: 'monospaced';");
            listaQGC.getItems().add(c);
        }
        status.setText("Pronto");
    }

    public void onCriarArquivoWord(ActionEvent actionEvent) {
        status.setText("Criando arquivo");
        try {
            Service.criarPastaEArquivoDoWord(HabilitacaoMapper.toDomain(this));
            status.setText("Arquivo criado");
        } catch (ValidacaoCamposException e) {
            log.warn("Campo inválido: {}", e.getMessage());
            status.setText(e.getMessage());
        } catch (IOException e) {
            log.error("Não foi possível criar o arquivo");
            e.printStackTrace();
        }
    }
}