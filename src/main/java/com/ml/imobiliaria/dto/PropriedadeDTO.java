package com.ml.imobiliaria.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

public class PropriedadeDTO {
    @NotEmpty(message = "O nome da propriedade não pode estar vazio.")
    @Pattern(regexp = "^[A-Z].*", message = "O nome da propriedade deve começar com uma letra maiúscula.")
    @Size(max = 30, message = "O comprimento do nome não pode exceder 30 caracteres.")
    private String nome;

    @NotEmpty(message = "O bairro não pode estar vazio.")
    @Size(max = 45, message = "O comprimento do bairro não pode exceder 45 caracteres.")
    private String bairro;

    @Valid
    Set<ComodoDTO> comodos;

    public PropriedadeDTO() { }

    public PropriedadeDTO(String nome, String bairro, Set<ComodoDTO> comodos) {
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

    public Set<ComodoDTO> getComodos() {
        return comodos;
    }

    public void setComodos(Set<ComodoDTO> comodos) {
        this.comodos = comodos;
    }
}
