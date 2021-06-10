package com.ml.imobiliaria.service;

import com.ml.imobiliaria.dto.BairroDTO;
import com.ml.imobiliaria.exception.BairroNaoExisteException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class BairroService {
    Map<String, BairroDTO> bairros = new HashMap<>();

    public BairroDTO buscar(String nome) {
        if(!bairros.containsKey(nome)) throw new BairroNaoExisteException(String.format("Bairro %s nao existe", nome));
        return bairros.get(nome);
    }

    @PostConstruct
    public void popularBairros() {
        bairros.put("mangabeira", new BairroDTO("Mangabeira", BigDecimal.valueOf(1000)));
        bairros.put("bancarios", new BairroDTO("Bancarios", BigDecimal.valueOf(1200)));
        bairros.put("centro", new BairroDTO("Centro", BigDecimal.valueOf(10000)));
    }
}
