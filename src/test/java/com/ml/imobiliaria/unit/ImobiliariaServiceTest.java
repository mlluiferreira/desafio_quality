package com.ml.imobiliaria.unit;

import com.ml.imobiliaria.dto.AreaPropriedadeDTO;
import com.ml.imobiliaria.dto.BairroDTO;
import com.ml.imobiliaria.dto.ComodoDTO;
import com.ml.imobiliaria.dto.PropriedadeDTO;
import com.ml.imobiliaria.dto.ValorPropriedadeDTO;
import com.ml.imobiliaria.exception.BairroNaoExisteException;
import com.ml.imobiliaria.service.BairroService;
import com.ml.imobiliaria.service.ImobiliariaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@Import({ImobiliariaService.class, BairroService.class})
public class ImobiliariaServiceTest {

    @MockBean
    private BairroService bairroService;

    @Autowired
    private ImobiliariaService imobiliariaService;

    private PropriedadeDTO propriedadeDTO;

    private final ComodoDTO maiorComodo = new ComodoDTO("Area de Lazer", 25d, 33d);

    @BeforeEach
    public void configurarTeste() {
        configurarMock();
        configurarPropriedadeDTO();
    }

    private void configurarMock() {
        Mockito.when(bairroService.buscar("mangabeira")).thenReturn(new BairroDTO("Mangabeira", BigDecimal.valueOf(1000)));
        Mockito.when(bairroService.buscar("barra funda")).thenThrow(new BairroNaoExisteException("Bairro barra funda nao existe"));
    }

    private void configurarPropriedadeDTO() {
        propriedadeDTO = new PropriedadeDTO();
        propriedadeDTO.setNome("Propriedade Teste");
        propriedadeDTO.setBairro("mangabeira");

        List<ComodoDTO> comodos = List.of(
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
        assertComodo(maiorComodo, comodoDTO);
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

    private void assertComodo(ComodoDTO expected, ComodoDTO current) {
        assertEquals(expected.getNome(), current.getNome());
        assertEquals(expected.getLargura(), current.getLargura());
        assertEquals(expected.getComprimento(), current.getComprimento());
    }
}
