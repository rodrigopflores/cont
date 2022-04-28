package br.com.jmpaj.cont.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@Builder
@ToString
public class Impugnacao {
    private int id;
    private String numero;
    private LocalDate transitoJulg;
}
