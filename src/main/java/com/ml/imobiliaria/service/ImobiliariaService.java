package com.ml.imobiliaria.service;

import com.ml.imobiliaria.dto.BairroDTO;
import com.ml.imobiliaria.dto.ComodoDTO;
import com.ml.imobiliaria.dto.ComodoAreaDTO;
import com.ml.imobiliaria.dto.PropriedadeDTO;
import com.ml.imobiliaria.dto.AreaPropriedadeDTO;
import com.ml.imobiliaria.dto.ValorPropriedadeDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImobiliariaService {

    private final BairroService bairroService;

    public ImobiliariaService(BairroService bairroService) {
        this.bairroService = bairroService;
    }

    public AreaPropriedadeDTO calcularAreaPropriedade(PropriedadeDTO propriedadeDTO) {
        Double area = propriedadeDTO.getComodos().stream().mapToDouble(this::calculaArea).sum();
        return new AreaPropriedadeDTO(area);
    }

    public ValorPropriedadeDTO calcularValorPropriedade(PropriedadeDTO propriedadeDTO) {
        BairroDTO bairroDTO = bairroService.buscar(propriedadeDTO.getBairro());
        AreaPropriedadeDTO areaPropriedadeDTO = calcularAreaPropriedade(propriedadeDTO);
        return new ValorPropriedadeDTO(bairroDTO.getValorPorMQuadrado().multiply(BigDecimal.valueOf(areaPropriedadeDTO.getArea())));
    }

    public ComodoDTO obterMaiorComodo(PropriedadeDTO propriedadeDTO) {
        List<ComodoDTO> comodosOrdenados = propriedadeDTO.getComodos().stream().sorted(Comparator.comparing(this::calculaArea)).collect(Collectors.toList());
        return comodosOrdenados.get(comodosOrdenados.size() - 1);
    }

    public List<ComodoAreaDTO> obterAreaComodos(PropriedadeDTO propriedadeDTO) {
        return propriedadeDTO.getComodos().stream().map(comodo -> new ComodoAreaDTO(comodo.getNome(), comodo.getLargura(), comodo.getComprimento())).collect(Collectors.toList());
    }

    private Double calculaArea(ComodoDTO comodo) {
        return comodo.getLargura() * comodo.getComprimento();
    }
}
