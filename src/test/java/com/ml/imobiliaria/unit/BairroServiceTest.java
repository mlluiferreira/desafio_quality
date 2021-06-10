package com.ml.imobiliaria.unit;

import com.ml.imobiliaria.exception.BairroNaoExisteException;
import com.ml.imobiliaria.service.BairroService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Import(BairroService.class)
@ExtendWith(SpringExtension.class)
public class BairroServiceTest {

    @Autowired
    private BairroService bairroService;

    @Test
    public void deve_lancar_exception_ao_buscar_bairro_que_nao_existe() {
        assertThrows(BairroNaoExisteException.class, () -> {
            bairroService.buscar(UUID.randomUUID().toString());
        });
    }

    @Test
    public void deve_retonar_bairro_ao_buscar_por_nome() {
        assertNotNull(bairroService.buscar("mangabeira"));
    }

}
