package com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.entregador;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.gateway.EntregadorGateway;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.model.Entregador;
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

public class AlocarRecursoEntregadorTest {
    @Mock
    EntregadorGateway entregadorGateway;
    AutoCloseable openMocks;
    private AlocarRecursoEntregador alocarRecursoEntregador;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        alocarRecursoEntregador = new AlocarRecursoEntregador(entregadorGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class AtualizarEntregadorTestes {
        @Test
        void devePermitirAlocarRecursoEntregador() {
            var entregadorDesatualizado = EntregadorHelper.gerarEntregador(1L);
            var entregadorDesatualizadoQuantidadeRecursos = entregadorDesatualizado.getQuantidadeRecursosDisponiveis();
            var entregadorAtualizado = EntregadorHelper.gerarEntregador(1L);
            entregadorAtualizado.setQuantidadeRecursosDisponiveis(entregadorDesatualizado.getQuantidadeRecursosDisponiveis() - 1L);

            when(entregadorGateway.findById(any(Long.class))).thenReturn(Optional.of(entregadorDesatualizado));

            when(entregadorGateway.update(any(Entregador.class))).thenReturn(entregadorAtualizado);

            var resultado = alocarRecursoEntregador.execute(entregadorDesatualizado.getId());

            assertThat(resultado)
                    .isInstanceOf(Entregador.class)
                    .isNotNull()
                    .usingRecursiveComparison()
                    .isEqualTo(entregadorAtualizado);
            assertThat(resultado.getQuantidadeRecursosDisponiveis())
                    .isNotEqualTo(entregadorDesatualizadoQuantidadeRecursos)
                    .isNotNull();

            verify(entregadorGateway, times(1))
                    .findById(any(Long.class));
            verify(entregadorGateway, times(1))
                    .update(any(Entregador.class));


        }

        @Test
        void deveGerarExcecaoSeNaoEncontrarEntregador() {
            when(entregadorGateway.findById(any(Long.class))).thenReturn(Optional.empty());

            assertThatThrownBy(() -> alocarRecursoEntregador.execute(1L))
                    .isInstanceOf(BusinessErrorException.class)
                    .message().isEqualTo("Entregador não localizado");

            verify(entregadorGateway, times(1))
                    .findById(any(Long.class));
            verify(entregadorGateway, never())
                    .update(any(Entregador.class));

        }

        @Test
        void deveGerarExcecaoSeRecursosInsuficientes() {
            var entregadorDesatualizado = EntregadorHelper.gerarEntregador(1L);
            entregadorDesatualizado.setQuantidadeRecursosDisponiveis(0L);

            when(entregadorGateway.findById(any(Long.class))).thenReturn(Optional.of(entregadorDesatualizado));

            assertThatThrownBy(() -> alocarRecursoEntregador.execute(1L))
                    .isInstanceOf(BusinessErrorException.class)
                    .message().isEqualTo("Entregador sem recurso disponivel para alocação");

            verify(entregadorGateway, times(1))
                    .findById(any(Long.class));
            verify(entregadorGateway, never())
                    .update(any(Entregador.class));

        }
    }
}
