package com.ml.imobiliaria.integration;

import com.ml.imobiliaria.dto.AreaPropriedadeDTO;
import com.ml.imobiliaria.dto.ComodoDTO;
import com.ml.imobiliaria.dto.PropriedadeDTO;
import com.ml.imobiliaria.dto.ValorPropriedadeDTO;
import com.ml.imobiliaria.exception.BairroNaoExisteException;
import com.ml.imobiliaria.service.BairroService;
import com.ml.imobiliaria.service.ImobiliariaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@Import({ImobiliariaService.class, BairroService.class})
public class ImobiliariaServiceTest {

    @Autowired
    private ImobiliariaService imobiliariaService;

    private PropriedadeDTO propriedadeDTO;

    private ComodoDTO maiorComodo = new ComodoDTO("Area de Lazer", 25d, 33d);

    @BeforeEach
    public void inicializarPropriedadeDTO() {
        propriedadeDTO = new PropriedadeDTO();
        propriedadeDTO.setNome("Propriedade Teste");
        propriedadeDTO.setBairro("mangabeira");

        Set<ComodoDTO> comodos = Set.of(
                new ComodoDTO("Quarto", 5d, 2d), //  Area = 10
                new ComodoDTO("Cozinha", 5d, 5d), // Area 25
                new ComodoDTO("Area de servico", 1d, 1d), // Area 1
                new ComodoDTO("sala", 3d, 3d), // Area 9
                maiorComodo
        ); // Area total  = 870

        propriedadeDTO.setComodos(comodos);
    }

    @Test
    public void deve_retornar_area_da_propriedade() {
        AreaPropriedadeDTO area = imobiliariaService.calcularAreaPropriedade(propriedadeDTO);
        assertNotNull(area);
        assertEquals(870d, area.getArea());
    }

    @Test
    public void deve_retornar_maior_comodo() {
        ComodoDTO comodoDTO = imobiliariaService.obterMaiorComodo(propriedadeDTO);
        assertNotNull(comodoDTO);
        assertRoom(maiorComodo, comodoDTO);
    }

    @Test
    public void deve_retornar_area_por_comodo() {
        imobiliariaService.obterAreaComodos(propriedadeDTO).forEach(comodo -> {
            Double correctArea = comodo.getLargura() * comodo.getComprimento();
            assertEquals(correctArea, comodo.getArea());
        });
    }

    @Test
    public void deve_lancar_exception_se_o_bairro_nao_existir() {
        propriedadeDTO.setBairro("barra funda");
        assertThrows(BairroNaoExisteException.class, () -> {
           imobiliariaService.calcularValorPropriedade(propriedadeDTO);
        });
    }

    @Test
    public void deve_retornar_valor_da_propriedade() {
        ValorPropriedadeDTO valorPropriedadeDTO = imobiliariaService.calcularValorPropriedade(propriedadeDTO);
        assertEquals(BigDecimal.valueOf(870000d), valorPropriedadeDTO.getValor());
    }

    private void assertRoom(ComodoDTO expected, ComodoDTO current) {
        assertEquals(expected.getNome(), current.getNome());
        assertEquals(expected.getLargura(), current.getLargura());
        assertEquals(expected.getComprimento(), current.getComprimento());
    }
}
