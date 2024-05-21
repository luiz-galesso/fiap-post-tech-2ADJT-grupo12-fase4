package com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entregador.controller;

import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entregador.controller.dto.EntregadorInsertDTO;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entregador.utils.EntregadorHelper;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.entregador.*;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.tabeladefrete.AtualizarTabelaDeFrete;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.tabeladefrete.CadastrarTabelaDeFrete;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.tabeladefrete.ObterTabelaDeFretePeloIdEntregador;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EntregadorControllerTest {
    @Mock
    AlocarRecursoEntregador alocarRecursoEntregador;
    @Mock
    DesalocarRecursoEntregador desalocarRecursoEntregador;
    AutoCloseable mock;
    @Mock
    private CadastrarEntregador cadastrarEntregador;
    @Mock
    private AtualizarEntregador atualizarEntregador;
    @Mock
    private ObterEntregadorPeloId obterEntregadorPeloId;
    @Mock
    private CadastrarTabelaDeFrete cadastrarTabelaDeFrete;
    @Mock
    private AtualizarTabelaDeFrete atualizarTabelaDeFrete;
    @Mock
    private ObterTabelaDeFretePeloIdEntregador obterTabelaDeFretePeloIdEntregador;
    private MockMvc mockMvc;

    public static String asJsonString(final Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return objectMapper.writeValueAsString(object);
    }

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        EntregadorController entregadorController = new EntregadorController(cadastrarEntregador,
                atualizarEntregador,
                obterEntregadorPeloId,
                alocarRecursoEntregador,
                desalocarRecursoEntregador,
                cadastrarTabelaDeFrete,
                atualizarTabelaDeFrete,
                obterTabelaDeFretePeloIdEntregador
        );
        mockMvc = MockMvcBuilders.standaloneSetup(entregadorController)
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }).build();
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class CriacaoEntregador {
        @Test
        void devePermitirCriarEntregador() throws Exception {
            var entregador = EntregadorHelper.gerarEntregador(null);
            var entregadorInsertDTO = new EntregadorInsertDTO(entregador.getNome()
                    , entregador.getCnpj()
                    , entregador.getQuantidadeRecursosDisponiveis()
            );

            when(cadastrarEntregador.execute(any(EntregadorInsertDTO.class))).thenReturn(entregador);

            mockMvc.perform(post("/entregador").content(asJsonString(entregadorInsertDTO)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());

            verify(cadastrarEntregador, times(1)).execute(any(EntregadorInsertDTO.class));
        }

   /*     @Test
        void deveRetornarErroQuandoCnpjJaExiste() throws Exception {
            var entregador = EntregadorHelper.gerarEntregador(null);
            var entregadorInsertDTO = new EntregadorInsertDTO(entregador.getNome(), entregador.getCnpj(), entregador.getQuantidadeRecursosDisponiveis());
            when(cadastrarEntregador.execute(any(EntregadorInsertDTO.class))).thenThrow(new BusinessErrorException("Já existe um entregador com o cnpj informado"));

            mockMvc.perform(post("/entregador").content(asJsonString(entregadorInsertDTO)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
        }*/
    }

        @Nested
        class BuscarEntregador {
            @Test
            void devePermitirBuscarEntregadorPeloId() throws Exception {
                var entregador = EntregadorHelper.gerarEntregador(1L);
                when(obterEntregadorPeloId.execute(any(Long.class))).thenReturn(entregador);

                mockMvc.perform(get("/entregador/{id}", entregador.getId())).andExpect(status().isOk());

                verify(obterEntregadorPeloId, times(1)).execute(any(Long.class));
            }
        }
        }


 /*       @Test
        void deveGerarExcecaoAoBuscarEntregadorPorIdQueNaoExiste() throws Exception {
            when(obterEntregadorPeloId.execute(any(Long.class))).thenThrow(new EntityNotFoundException("Entregador não localizado"));

            mockMvc.perform(get("/entregador/{id}", any(Long.class)).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(content().string("Entregador não localizado"));
            verify(obterEntregadorPeloId, times(1)).execute(any(Long.class));
        }
    }

  */
    /*
    @Nested
    class AtualizarEntregador {
        @Test
        void devePermitirAlterarEntregador() throws Exception {
            var entregador = EntregadorHelper.gerarEntregador(1L);

            when(atualizarEntregador.execute(any(Long.class), any(EntregadorUpdateDTO.class))).thenReturn(entregador);

            mockMvc.perform(put("/entregador/{id}", 1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(entregador))).andExpect(status().isOk());

        }*/




