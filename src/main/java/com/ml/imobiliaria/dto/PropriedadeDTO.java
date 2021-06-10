package com.ml.imobiliaria.dto;

import com.ml.imobiliaria.error.Message;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public class PropriedadeDTO {
    @NotEmpty(message = Message.NOME_DA_PROPRIEDADE_VAZIO)
    @Pattern(regexp = "^[A-Z].*", message = Message.NOME_DA_PROPRIEDADE_DEVE_INICIAR_COM_LETRA_MAIUSCULA)
    @Size(max = 30, message = Message.TAMANHO_DA_PROPRIEDADE_NAO_PODE_SER_MAIOR_QUE_30)
    private String nome;

    @NotEmpty(message = Message.NOME_DO_BAIRRO_VAZIO)
    @Size(max = 45, message = Message.TAMANHO_DO_NOME_DO_BAIRRO_NAO_PODE_SER_MAIOR_QUE_45)
    private String bairro;

    @Valid
    List<ComodoDTO> comodos;

    public PropriedadeDTO() { }

    public PropriedadeDTO(String nome, String bairro, List<ComodoDTO> comodos) {
        this.nome = nome;
        this.bairro = bairro;
        this.comodos = comodos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public List<ComodoDTO> getComodos() {
        return comodos;
    }

    public void setComodos(List<ComodoDTO> comodos) {
        this.comodos = comodos;
    }
}
