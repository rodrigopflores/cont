package br.com.jmpaj.cont.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class Credor {
    private int id;
    private String nome;
    private String endereco;
    private String bairro;
    private String cidade;
    private String cep;
    private String cpfCnpj;
}
