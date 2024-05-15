package com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.entregador;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.gateway.EntregadorGateway;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.model.Entregador;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entregador.utils.EntregadorHelper;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.exception.EntityNotFoundException;
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

public class ObterEntregadorPeloIdTest {
    @Mock
    EntregadorGateway entregadorGateway;
    AutoCloseable openMocks;
    private ObterEntregadorPeloId obterEntregadorPeloId;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        obterEntregadorPeloId = new ObterEntregadorPeloId(entregadorGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class ObterEntregador {
        @Test
        void deveObterEntregadorPorId() {
            var entregador = EntregadorHelper.gerarEntregador(null);

            when(entregadorGateway.findById(any(Long.class))).thenReturn(Optional.of(entregador));

            var entregadorObtido = obterEntregadorPeloId.execute(any(Long.class));

            assertThat(entregadorObtido)
                    .isNotNull()
                    .isInstanceOf(Entregador.class)
                    .usingRecursiveComparison()
                    .isEqualTo(entregador);
        }

        @Test
        void deveGerarErroQuandoNaoEncontrarEntregadorPorId() {
            when(entregadorGateway.findById(any(Long.class))).thenReturn(Optional.empty());
            assertThatThrownBy(() -> obterEntregadorPeloId.execute(any(Long.class)))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage("Entregador não localizado");
            verify(entregadorGateway, times(1)).findById(any(Long.class));
        }
    }
}
