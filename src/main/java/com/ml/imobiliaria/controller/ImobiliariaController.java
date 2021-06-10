package com.ml.imobiliaria.controller;

import com.ml.imobiliaria.dto.PropriedadeDTO;
import com.ml.imobiliaria.service.ImobiliariaService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/propriedade")
public class ImobiliariaController {

    private final ImobiliariaService imobiliariaService;

    public ImobiliariaController(ImobiliariaService imobiliariaService) {
        this.imobiliariaService = imobiliariaService;
    }

    @PostMapping("/area")
    public ResponseEntity<?> calculaArea(@RequestBody @Valid PropriedadeDTO propriedadeDTO) {
        return ResponseEntity.ok(imobiliariaService.calcularAreaPropriedade(propriedadeDTO));
    }

    @PostMapping("/valor")
    public ResponseEntity<?> calcularValor(@RequestBody @Valid PropriedadeDTO propriedadeDTO) {
        return ResponseEntity.ok(imobiliariaService.calcularValorPropriedade(propriedadeDTO));
    }

    @PostMapping("/maiorComodo")
    public ResponseEntity<?> maiorComodo(@RequestBody @Valid PropriedadeDTO propriedadeDTO) {
        return ResponseEntity.ok(imobiliariaService.obterMaiorComodo(propriedadeDTO));
    }

    @PostMapping("/areaComodos")
    public ResponseEntity<?> obterAreaComodos(@RequestBody @Valid PropriedadeDTO propriedadeDTO) {
        return ResponseEntity.ok(imobiliariaService.obterAreaComodos(propriedadeDTO));
    }
}
