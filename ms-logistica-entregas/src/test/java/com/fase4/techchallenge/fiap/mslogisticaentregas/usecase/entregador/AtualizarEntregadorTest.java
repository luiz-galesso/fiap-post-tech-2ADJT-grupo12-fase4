package com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.entregador;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.gateway.EntregadorGateway;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.model.Entregador;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entregador.controller.dto.EntregadorUpdateDTO;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entregador.utils.EntregadorHelper;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.exception.BusinessErrorException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AtualizarEntregadorTest {
    @Mock
    EntregadorGateway entregadorGateway;
    AutoCloseable openMocks;
    private AtualizarEntregador atualizarEntregador;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        atualizarEntregador = new AtualizarEntregador(entregadorGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class AtualizarEntregadorTestes {
        @Test
        void devePermitirAtualizarEntregador() {
            var entregadorDesatualizado = EntregadorHelper.gerarEntregador(1L);
            var entregadorDesatualizadoSituacao = entregadorDesatualizado.getSituacao();
            var entregadorAtualizado = EntregadorHelper.gerarEntregador(1L);
            entregadorAtualizado.setSituacao("INATIVO");
            var entregadorUpdateDTO = new EntregadorUpdateDTO(entregadorDesatualizado.getNome()
                    , entregadorDesatualizado.getCnpj()
                    , entregadorAtualizado.getSituacao()
            );

            when(entregadorGateway.findById(any(Long.class))).thenReturn(Optional.of(entregadorDesatualizado));

            when(entregadorGateway.update(any(Entregador.class))).thenReturn(entregadorAtualizado);

            var resultado = atualizarEntregador.execute(entregadorDesatualizado.getId(), entregadorUpdateDTO);

            assertThat(resultado)
                    .isInstanceOf(Entregador.class)
                    .isNotNull()
                    .usingRecursiveComparison()
                    .isEqualTo(entregadorAtualizado);
            assertThat(resultado.getSituacao())
                    .isNotEqualTo(entregadorDesatualizadoSituacao)
                    .isNotNull();

            verify(entregadorGateway, times(1))
                    .findById(any(Long.class));
            verify(entregadorGateway, times(1))
                    .update(any(Entregador.class));


        }

        @Test
        void deveGerarExcecaoSeNaoEncontrarEntregador() {
            var entregadorDesatualizado = EntregadorHelper.gerarEntregador(1L);
            var entregadorUpdateDTO = new EntregadorUpdateDTO(entregadorDesatualizado.getNome()
                    , entregadorDesatualizado.getCnpj()
                    , entregadorDesatualizado.getSituacao()
            );

            when(entregadorGateway.findById(any(Long.class))).thenReturn(Optional.empty());

            assertThatThrownBy(() -> atualizarEntregador.execute(1L, entregadorUpdateDTO))
                    .isInstanceOf(BusinessErrorException.class)
                    .message().isEqualTo("Não foi encontrado o entregador com o Id informado");

            verify(entregadorGateway, times(1))
                    .findById(any(Long.class));
            verify(entregadorGateway, never())
                    .update(any(Entregador.class));

        }
    }
}
