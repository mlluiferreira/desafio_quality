package com.ml.imobiliaria.dto;

public class ComodoAreaDTO extends ComodoDTO {
    public ComodoAreaDTO() { }

    public ComodoAreaDTO(String nome, Double largura, Double comprimento) {
        super(nome, largura, comprimento);
    }

    public Double getArea() {
        return this.getLargura() * this.getComprimento();
    }
}
