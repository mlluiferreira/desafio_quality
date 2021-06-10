package com.ml.imobiliaria.dto;

import java.math.BigDecimal;

public class ValorPropriedadeDTO {
    private BigDecimal valor;

    private String moeda = "R$";

    public ValorPropriedadeDTO() { }

    public ValorPropriedadeDTO(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getMoeda() {
        return moeda;
    }

    public void setMoeda(String moeda) {
        this.moeda = moeda;
    }
}
