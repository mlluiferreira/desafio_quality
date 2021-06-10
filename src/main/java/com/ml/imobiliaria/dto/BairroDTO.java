package com.ml.imobiliaria.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class BairroDTO {

    private String nome;

    private BigDecimal valorPorMQuadrado;

    public BairroDTO() { }

    public BairroDTO(String nome, BigDecimal valorPorMQuadrado) {
        this.nome = nome;
        this.valorPorMQuadrado = valorPorMQuadrado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValorPorMQuadrado() {
        return valorPorMQuadrado;
    }

    public void setValorPorMQuadrado(BigDecimal valorPorMQuadrado) {
        this.valorPorMQuadrado = valorPorMQuadrado;
    }
}
