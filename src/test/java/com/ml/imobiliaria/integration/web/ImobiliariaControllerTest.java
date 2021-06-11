package com.ml.imobiliaria.integration.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ml.imobiliaria.dto.ComodoDTO;
import com.ml.imobiliaria.dto.PropriedadeDTO;
import com.ml.imobiliaria.error.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class ImobiliariaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private PropriedadeDTO propriedadeDTO;

    private final ComodoDTO maiorComodo = new ComodoDTO("Area de Lazer", 25d, 33d);

    @BeforeEach
    void configurarPropriedade() {
        propriedadeDTO = new PropriedadeDTO();
        propriedadeDTO.setNome("Propriedade Teste");
        propriedadeDTO.setBairro("mangabeira");

        List<ComodoDTO> comodos = List.of(
                new ComodoDTO("Quarto", 5d, 2d), //  Area = 10
                new ComodoDTO("Cozinha", 5d, 5d), // Area 25
                new ComodoDTO("Area de servico", 1d, 1d), // Area 1
                new ComodoDTO("Sala", 3d, 3d), // Area 9
                maiorComodo
        ); // Area total  = 870

        propriedadeDTO.setComodos(comodos);
    }

    @Test
    public void deve_retornar_400_ao_enviar_propriedade_sem_nome() throws Exception {
        propriedadeDTO.setNome(null);
        ResultMatcher expected = jsonPath("$.nome").value(Message.NOME_DA_PROPRIEDADE_VAZIO);
        sendAllEndpoints(propriedadeDTO, expected);
    }

    @Test
    public void deve_retornar_400_ao_enviar_propriedade_com_nome_iniciando_com_letra_minuscula() throws Exception {
        propriedadeDTO.setNome("propriedade teste");
        ResultMatcher expected = jsonPath("$.nome").value(Message.NOME_DA_PROPRIEDADE_DEVE_INICIAR_COM_LETRA_MAIUSCULA);
        sendAllEndpoints(propriedadeDTO, expected);
    }

    @Test
    public void deve_retornar_400_ao_enviar_propriedade_com_nome_maior_que_30() throws Exception {
        propriedadeDTO.setNome("A" + "b".repeat(30));
        ResultMatcher expected = jsonPath("$.nome").value(Message.TAMANHO_DA_PROPRIEDADE_NAO_PODE_SER_MAIOR_QUE_30);
        sendAllEndpoints(propriedadeDTO, expected);
    }

    @Test
    public void deve_retornar_400_ao_enviar_propriedade_com_bairro_vazio() throws Exception {
        propriedadeDTO.setBairro(null);
        ResultMatcher expected = jsonPath("$.bairro").value(Message.NOME_DO_BAIRRO_VAZIO);
        sendAllEndpoints(propriedadeDTO, expected);
    }

    @Test
    public void deve_retornar_400_ao_enviar_propriedade_com_bairro_maior_que_45() throws Exception {
        propriedadeDTO.setBairro("b".repeat(46));
        ResultMatcher expected = jsonPath("$.bairro").value(Message.TAMANHO_DO_NOME_DO_BAIRRO_NAO_PODE_SER_MAIOR_QUE_45);
        sendAllEndpoints(propriedadeDTO, expected);
    }

    @Test
    public void deve_retornar_400_ao_enviar_propriedade_com_nome_do_comodo_vazio() throws Exception {
        propriedadeDTO.setComodos(List.of(new ComodoDTO(null, 1d, 1d)));
        ResultMatcher expected = jsonPath(".['comodos[0].nome']").value(Message.NOME_DO_COMODO_VAZIO);
        sendAllEndpoints(propriedadeDTO, expected);
    }

    @Test
    public void deve_retonar_400_ao_enviar_propriedade_com_nome_do_comodo_iniciando_com_letra_minuscula() throws Exception {
        propriedadeDTO.setComodos(List.of(new ComodoDTO("quarto", 1d, 1d)));
        ResultMatcher expected = jsonPath(".['comodos[0].nome']").value(Message.NOME_DO_COMODO_DEVE_INICIAR_COM_LETRA_MAIUSCULA);
        sendAllEndpoints(propriedadeDTO, expected);
    }

    @Test
    public void deve_retornar_400_ao_enviar_propriedade_com_nome_do_comodo_maior_que_30() throws Exception {
        propriedadeDTO.setComodos(List.of(new ComodoDTO("A" + "b".repeat(30), 1d, 1d)));
        ResultMatcher expected = jsonPath(".['comodos[0].nome']").value(Message.TAMANHO_DO_NOME_DO_COMODO_NAO_PODE_SER_MAIOR_QUE_30);
        sendAllEndpoints(propriedadeDTO, expected);
    }

    @Test
    public void deve_retornar_400_ao_enviar_propriedade_com_comodo_de_largura_vazia() throws Exception {
        propriedadeDTO.setComodos(List.of(new ComodoDTO("Quarto", null, 1d)));
        ResultMatcher expected = jsonPath(".['comodos[0].largura']").value(Message.LARGURA_DO_COMODO_NAO_PODE_SER_VAZIA);
        sendAllEndpoints(propriedadeDTO, expected);
    }

    @Test
    public void deve_retornar_400_ao_enviar_propriedade_com_comodo_de_largura_maior_que_25() throws Exception {
        propriedadeDTO.setComodos(List.of(new ComodoDTO("Quarto", 25.1, 1d)));
        ResultMatcher expected = jsonPath(".['comodos[0].largura']").value(Message.LARGURA_DO_COMODO_NAO_PODE_SER_MAIOR_QUE_25);
        sendAllEndpoints(propriedadeDTO, expected);
    }

    @Test
    public void deve_retornar_400_ao_enviar_propriedade_com_comodo_de_comprimento_vazio() throws Exception {
        propriedadeDTO.setComodos(List.of(new ComodoDTO("Quarto", 1d, null)));
        ResultMatcher expected = jsonPath(".['comodos[0].comprimento']").value(Message.COMPRIMENTO_DO_COMODO_NAO_PODE_SER_VAZIO);
        sendAllEndpoints(propriedadeDTO, expected);
    }

    @Test
    public void deve_retornar_400_ao_enviar_propriedade_com_comodo_de_comprimento_maior_que_33() throws Exception {
        propriedadeDTO.setComodos(List.of(new ComodoDTO("Quarto", 1d, 33.1)));
        ResultMatcher expected = jsonPath(".['comodos[0].comprimento']").value(Message.COMPRIMENTO_DO_COMODO_NAO_PODE_SER_MAIOR_QUE_33);
        sendAllEndpoints(propriedadeDTO, expected);
    }

    @Test
    public void deve_retornar_a_area_da_propriedade() throws Exception {
        ResultMatcher expected = jsonPath("$.area").value(870);
        sendPostRequest("/propriedade/area", propriedadeDTO, expected);
    }

    @Test
    public void deve_retornar_o_valor_da_propriedade() throws Exception {
        ResultMatcher expected = jsonPath("$.valor").value(870000);
        sendPostRequest("/propriedade/valor", propriedadeDTO, expected);
    }

    @Test
    public void deve_retornar_o_maior_comodo_da_propriedade() throws Exception {
        ResultMatcher []expected = {
                jsonPath(".nome").value("Area de Lazer"),
                jsonPath(".largura").value(25.0),
                jsonPath(".comprimento").value(33.0),
        };
        sendPostRequest("/propriedade/maiorComodo", propriedadeDTO, expected);
    }

    @Test
    public void deve_retornar_a_area_de_cada_comodo() throws Exception {
        List<ResultMatcher> expecteds = new ArrayList<>();

        IntStream.range(0, propriedadeDTO.getComodos().size()).forEach(i -> {
            ComodoDTO comodoDTO = propriedadeDTO.getComodos().get(i);
            expecteds.add(jsonPath(String.format("[%d].nome", i)).value(comodoDTO.getNome()));
            expecteds.add(jsonPath(String.format("[%d].largura", i)).value(comodoDTO.getLargura()));
            expecteds.add(jsonPath(String.format("[%d].comprimento", i)).value(comodoDTO.getComprimento()));
            expecteds.add(jsonPath(String.format("[%d].area", i)).value(comodoDTO.getComprimento() * comodoDTO.getLargura()));
        });

        sendPostRequest("/propriedade/areaComodos", propriedadeDTO, expecteds.toArray(ResultMatcher[]::new));
    }

    private void sendAllEndpoints(PropriedadeDTO propriedadeDTO, ResultMatcher expected) throws Exception {
        sendPostRequest("/propriedade/area", propriedadeDTO, expected);
        sendPostRequest("/propriedade/valor", propriedadeDTO, expected);
        sendPostRequest("/propriedade/maiorComodo", propriedadeDTO, expected);
        sendPostRequest("/propriedade/areaComodos", propriedadeDTO, expected);
    }

    private void sendPostRequest(String path, Object content, ResultMatcher... expected) throws Exception {
        ResultActions action = mockMvc.perform(
                MockMvcRequestBuilders
                        .post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(content))
        )
                .andDo(MockMvcResultHandlers.print());

        for (ResultMatcher matcher : expected)
            action.andExpect(matcher);
    }
}
