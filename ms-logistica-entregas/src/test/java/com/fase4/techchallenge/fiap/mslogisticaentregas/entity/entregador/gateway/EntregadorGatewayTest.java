package com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.gateway;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.model.Entregador;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entregador.repository.EntregadorRepository;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entregador.utils.EntregadorHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EntregadorGatewayTest {
    AutoCloseable openMocks;
    private EntregadorGateway entregadorGateway;
    @Mock
    private EntregadorRepository entregadorRepository;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        entregadorGateway = new EntregadorGateway(entregadorRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirCriarEntregador() {
        Entregador entregador = EntregadorHelper.gerarEntregador(null);

        when(entregadorRepository.save(any(Entregador.class))).thenReturn(entregador);

        var resultado = entregadorGateway.create(entregador);

        verify(entregadorRepository, times(1)).save(entregador);
        assertThat(resultado)
                .isInstanceOf(Entregador.class)
                .isNotNull();
        assertThat(resultado)
                .usingRecursiveComparison()
                .isEqualTo(entregador);
    }

    @Test
    void devePermitirAlterarEntregador() {
        Entregador entregadorDesatualizado = EntregadorHelper.gerarEntregador(1L);
        var entregadorAtualizado = EntregadorHelper.gerarEntregador(1L);
        entregadorAtualizado.setNome("Trovão Entregas");

        when(entregadorRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(entregadorDesatualizado));
        when(entregadorRepository.save(any(Entregador.class)))
                .thenAnswer(i -> i.getArgument(0));

        var resultado = entregadorGateway.update(entregadorAtualizado);

        assertThat(resultado)
                .isInstanceOf(Entregador.class)
                .isNotNull();

        assertNotEquals(resultado.getNome(), entregadorDesatualizado.getNome());

        assertThat(resultado)
                .usingRecursiveComparison()
                .isEqualTo(entregadorAtualizado);
        verify(entregadorRepository, times(1)).save(any(Entregador.class));
    }

    @Nested
    class ListarEntregador {
        @Test
        void devePermitirBuscarEntregador() {
            Entregador entregador = EntregadorHelper.gerarEntregador(1L);
            when(entregadorRepository.findById(any(Long.class)))
                    .thenReturn(Optional.of(entregador));

            var resultado = entregadorGateway.findById(any(Long.class));

            verify(entregadorRepository, times(1))
                    .findById(any(Long.class));
            assertThat(resultado)
                    .isPresent()
                    .isInstanceOf(Optional.class)
                    .isNotNull();

            assertEquals(entregador, resultado.get());

            assertThat(resultado)
                    .usingRecursiveComparison()
                    .isEqualTo(Optional.of(entregador));
        }

        @Test
        void devePermitirListarEntregadorPorCnpj() {
            Entregador entregador = EntregadorHelper.gerarEntregador(1L);

            when(entregadorRepository.findByCnpj(any(Long.class)))
                    .thenReturn(Optional.of(entregador));

            var resultado = entregadorGateway.findByCnpj(entregador.getCnpj());

            verify(entregadorRepository, times(1))
                    .findByCnpj(any(Long.class));
            assertThat(resultado)
                    .isPresent()
                    .isInstanceOf(Optional.class)
                    .isInstanceOf(Optional.class)
                    .isNotNull();

            assertEquals(entregador, resultado.get());

            assertThat(resultado)
                    .usingRecursiveComparison()
                    .isEqualTo(Optional.of(entregador));
        }
    }

}
