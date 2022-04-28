package br.com.jmpaj.cont.service;

import br.com.jmpaj.cont.domain.Habilitacao;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
public class WordService {

    private static final String modeloComProcurador = "./src/main/resources/br/com/jmpaj/cont/word/comprocurador.docx";
    private static final String modeloSemProcurador = "./src/main/resources/br/com/jmpaj/cont/word/semprocurador.docx";

    public static void criarArquivoWordDoModelo(Habilitacao habilitacao, String outputPath) throws IOException {
        log.info("Criando arquivo do Word.");
        String pathModelo = habilitacao.getProcurador().isEmpty() ? modeloSemProcurador : modeloComProcurador;
        try (XWPFDocument doc = new XWPFDocument(Files.newInputStream(Paths.get(pathModelo)))) {
            preencherModeloComInformacoesDaHabilitacao(habilitacao, doc);
            salvarArquivoPreenchido(doc, outputPath + "/01 - Manifestação.docx");
        }
    }

    private static void preencherModeloComInformacoesDaHabilitacao(Habilitacao habilitacao, XWPFDocument doc) {
        List<XWPFParagraph> paragrafos = doc.getParagraphs();
        for (XWPFParagraph paragrafo : paragrafos) {
            for (XWPFRun run : paragrafo.getRuns()) {
                fazerSubstituicoes(habilitacao, run);
            }
        }
    }

    private static void fazerSubstituicoes(Habilitacao habilitacao, XWPFRun run) {
        String docText = run.getText(0);
        if (docText == null)
            return;
        docText = docText.replace("${numeroProcesso}", habilitacao.getNumeroProcesso());
        docText = docText.replace("${autores}", habilitacao.getCredor().toUpperCase());
        docText = docText.replace("${creditoCredor}", habilitacao.getCredito());
        docText = docText.replace("${creditoProcurador}", habilitacao.getCreditoProcurador());
        docText = docText.replace("${credor}", habilitacao.getCredor());
        docText = docText.replace("${reclamatoria}", habilitacao.getReclamatoria());
        docText = docText.replace("${documentacaoCerta}", substituirDocumentacaoCerta(habilitacao));
        docText = docText.replace("${apresentada}", habilitacao.isDocumentacaoCerta() ? "apresentada" : "levantada");
        docText = docText.replace("${valorCerto}", substituirValorCerto(habilitacao));
        docText = docText.replace("${issoPosto}", substituirIssoPosto(habilitacao));
        run.setText(docText, 0);
    }

    private static String substituirDocumentacaoCerta(Habilitacao habilitacao) {
        return habilitacao.isDocumentacaoCerta() ?
                ""
                :
                "Embora a petição inicial seja omissa em relação a parte da documentação necessária, foi possível " +
                "a obtenção desses documentos por meio do sistema PJe do TRT da 4ª Região, prestigiando-se, assim, " +
                "os princípios da economia e celeridade processual. ";
    }

    private static String substituirValorCerto(Habilitacao habilitacao) {
        return habilitacao.isCalculoCerto() ?
                "igualmente, foi corretamente atualizado até a data do pedido de recuperação, de modo que devido " +
                "o seu reconhecimento"
                :
                "todavia, não foi atualizado até a data do pedido de recuperação, conforme determina a LRF";
    }

    private static String substituirIssoPosto(Habilitacao habilitacao) {
        return habilitacao.isCalculoCerto() ? comCalculoCorreto(habilitacao) : comCalculoErrado(habilitacao);
    }

    private static String comCalculoCorreto(Habilitacao habilitacao) {
        StringBuilder issoPosto = new StringBuilder();
        issoPosto.append("opina o administrador judicial pela procedência da presente habilitação, " +
                        "para reconhecer-se o crédito de ")
                .append(habilitacao.getCredito())
                .append(" em favor de ")
                .append(habilitacao.getCredor().toUpperCase());

        if (habilitacao.isSubstituicao())
            issoPosto.append(", em substituição ao já reconhecido no edital");
        else
            issoPosto.append(", de classe trabalhista");

        if (!habilitacao.getProcurador().isEmpty())
            incluirIssoPostoComProcurador(habilitacao, issoPosto);

        issoPosto.append(".");

        return issoPosto.toString();
    }

    private static void incluirIssoPostoComProcurador(Habilitacao habilitacao, StringBuilder issoPosto) {
        issoPosto.append(", bem como o crédito de ")
                .append(habilitacao.getCreditoProcurador())
                .append(" em favor de ");
        if (habilitacao.getProcurador().equals("baka"))
            issoPosto.append("MAITE ALEXANDRA BAKALARCZYK CORREA, CHARLES LEONEL BAKALARCZYK e " +
                    "DIONES RODRIGO FERNANDES OLIVEIRA");
        else
            issoPosto.append(habilitacao.getProcurador().toUpperCase());

        issoPosto.append(", de classe trabalhista, a ser incluído na relação de credores");
    }

    private static String comCalculoErrado(Habilitacao habilitacao) {
        return  "requer seja a parte autora intimada a apresentar nova certidão de cálculo emitida pela Vara do " +
                "Trabalho de origem com a atualização dos valores até 31/08/2015, ou memória de cálculo equivalente.";
    }

    public static void salvarArquivoPreenchido(XWPFDocument doc, String outputPath) throws IOException {
        try (FileOutputStream out = new FileOutputStream(outputPath)) {
            doc.write(out);
        }
    }
}
