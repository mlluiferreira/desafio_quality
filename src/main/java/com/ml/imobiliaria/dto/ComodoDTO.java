package com.ml.imobiliaria.dto;

import com.ml.imobiliaria.annotation.FirstLetterIsUpperCase;
import com.ml.imobiliaria.error.Message;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ComodoDTO {
    @NotEmpty(message = Message.NOME_DO_COMODO_VAZIO)
    @Size(max = 30, message = Message.TAMANHO_DO_NOME_DO_COMODO_NAO_PODE_SER_MAIOR_QUE_30)
    @FirstLetterIsUpperCase(message = Message.NOME_DO_COMODO_DEVE_INICIAR_COM_LETRA_MAIUSCULA)
    private String nome;

    @NotNull(message = Message.LARGURA_DO_COMODO_NAO_PODE_SER_VAZIA)
    @Max(value = 25, message = Message.LARGURA_DO_COMODO_NAO_PODE_SER_MAIOR_QUE_25)
    private Double largura;

    @NotNull(message = Message.COMPRIMENTO_DO_COMODO_NAO_PODE_SER_VAZIO)
    @Max(value = 33, message = Message.COMPRIMENTO_DO_COMODO_NAO_PODE_SER_MAIOR_QUE_33)
    private Double comprimento;

    public ComodoDTO() {
    }

    public ComodoDTO(String nome, Double largura, Double comprimento) {
        this.nome = nome;
        this.largura = largura;
        this.comprimento = comprimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getLargura() {
        return largura;
    }

    public void setLargura(Double largura) {
        this.largura = largura;
    }

    public Double getComprimento() {
        return comprimento;
    }

    public void setComprimento(Double comprimento) {
        this.comprimento = comprimento;
    }
}
