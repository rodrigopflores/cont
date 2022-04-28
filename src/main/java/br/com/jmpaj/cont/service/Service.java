package br.com.jmpaj.cont.service;

import br.com.jmpaj.cont.dao.ControleHabDAO;
import br.com.jmpaj.cont.dao.CreditoDAO;
import br.com.jmpaj.cont.dao.DaoFactory;
import br.com.jmpaj.cont.domain.ControleHab;
import br.com.jmpaj.cont.domain.Credito;
import br.com.jmpaj.cont.domain.Habilitacao;
import br.com.jmpaj.cont.exception.ValidacaoCamposException;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

@Slf4j
public class Service {

    private static final CreditoDAO creditoDAO = DaoFactory.createCreditoDAO();
    private static final ControleHabDAO controleHabDAO = DaoFactory.createControleHabDAO();

    public static List<ControleHab> checarSePossuiHabilitacao(String credor) {
        log.info("Checando se possui habilitação.");
        return controleHabDAO.findAllByNomeCredor(credor);
    }

    public static List<Credito> checarSeEstaNaRelacaoDeCredores(String credor) {
        log.info("Checando se está na relação de credores.");
        return creditoDAO.findAllByNomeCredor(credor);
    }

    public static void criarPastaEArquivoDoWord(Habilitacao habilitacao) throws ValidacaoCamposException, IOException {
        validarCampos(habilitacao);
        formatarCampos(habilitacao);
        WordService.criarArquivoWordDoModelo(habilitacao, criarPasta(habilitacao));
    }

    private static String criarPasta(Habilitacao habilitacao) {
        String pastaPath = "./src/main/resources/diretoriosCriados/" + habilitacao.getNumeroProcesso() +
                " - " + habilitacao.getCredor();

        log.info("Criando pasta {}", pastaPath);

        if (!habilitacao.getProcurador().isEmpty())
            pastaPath += " e Outros";

        File file = new File(pastaPath);
        file.mkdirs();
        return pastaPath;
    }

    private static void validarCampos(Habilitacao habilitacao) {
        log.info("Validando campos");
        if (haCamposVazios(habilitacao))
            throw new ValidacaoCamposException("Os campos não podem ser vazios.");

        if (valoresNaoSaoValidos(habilitacao))
            throw new ValidacaoCamposException("Valor inválido");

        if (processosNaoTemFormatoValido(habilitacao))
            throw new ValidacaoCamposException("Número processo inválido");
    }

    private static boolean valoresNaoSaoValidos(Habilitacao habilitacao) {
        Pattern pattern = Pattern.compile("^(R\\$[ ]?)?\\d{0,3}((\\.\\d{3})*|(\\d)*)(\\,\\d{2})?$");
        return !pattern.matcher(habilitacao.getCredito()).matches() ||
                (
                    !habilitacao.getProcurador().isEmpty() &&
                    !pattern.matcher(habilitacao.getCreditoProcurador()).matches()
                );
    }

    private static boolean processosNaoTemFormatoValido(Habilitacao habilitacao) {
        Pattern pattern = Pattern.compile("\\d{7}[-]\\d{2}[.]\\d{4}[.]\\d[.]\\d{2}[.]\\d{4}");
        return  !pattern.matcher(habilitacao.getReclamatoria()).matches() ||
                !pattern.matcher(habilitacao.getNumeroProcesso()).matches();
    }

    private static boolean haCamposVazios(Habilitacao habilitacao) {
        return  habilitacao.getCredito().isEmpty() ||
                habilitacao.getNumeroProcesso().isEmpty() ||
                habilitacao.getReclamatoria().isEmpty() ||
                habilitacao.getCredor().isEmpty();
    }

    private static void formatarCampos(Habilitacao habilitacao) {
        log.info("Formatando campos");
        habilitacao.setCredor(formatarNomeProprio(habilitacao.getCredor()));
        habilitacao.setProcurador(formatarNomeProprio(habilitacao.getProcurador()));
        try {
            String creditoFormatado = formatarValor(habilitacao.getCredito());
            String creditoProcurador = habilitacao.getCreditoProcurador();
            String creditoProcFormatado = creditoProcurador.isEmpty() ? "" : formatarValor(creditoProcurador);
            habilitacao.setCredito(creditoFormatado);
            habilitacao.setCreditoProcurador(creditoProcFormatado);
        } catch (NumberFormatException e) {
            log.error("Não foi possível formatar o valor: {}", e.getMessage());
        }
    }

    private static String formatarValor(String valor) {
        String valorSoComPontoDecimal = valor.replaceAll("[R$.]", "").replace(",", ".");
        Double valorDouble = Double.parseDouble(valorSoComPontoDecimal);
        return NumberFormat.getCurrencyInstance(new Locale("pt","BR")).format(valorDouble);
    }

    private static String formatarNomeProprio(String credor) {
        List<String> excecoes = List.of("do","da","dos","de");
        StringBuilder nomeAjustado = new StringBuilder();
        String[] nomes = credor.trim().split(" ");
        for (String nome : nomes) {
            if (nome.isEmpty())
                continue;
            nome = nome.toLowerCase();
            if (!excecoes.contains(nome)) {
                nome = nome.substring(0,1).toUpperCase() + nome.substring(1);
            }
            nomeAjustado.append(nome);
            nomeAjustado.append(" ");
        }
        return nomeAjustado.toString().trim();
    }
}
