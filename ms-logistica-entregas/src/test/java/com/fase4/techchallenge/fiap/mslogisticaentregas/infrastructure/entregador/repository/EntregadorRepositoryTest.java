package com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entregador.repository;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.model.Entregador;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entregador.utils.EntregadorHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EntregadorRepositoryTest {
    AutoCloseable openMocks;
    @Mock
    private EntregadorRepository entregadorRepository;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirRegistrarEntregador() {
        Entregador entregador = EntregadorHelper.gerarEntregador(null);
        when(entregadorRepository.save(any(Entregador.class))).thenAnswer(a -> a.getArguments()[0]);

        var entregadorArmazenado = entregadorRepository.save(entregador);
        verify(entregadorRepository, times(1)).save(entregador);
        assertThat(entregadorArmazenado)
                .isInstanceOf(Entregador.class)
                .isNotNull();
        assertThat(entregadorArmazenado)
                .usingRecursiveComparison()
                .isEqualTo(entregador);

    }

    @Test
    void devePermitirApagarEntregador() {
        Long id = new Random().nextLong();
        doNothing().when(entregadorRepository).deleteById(id);

        entregadorRepository.deleteById(id);

        verify(entregadorRepository, times(1)).deleteById(id);

    }

    @Nested
    class ConsultarEntregadores {
        @Test
        void devePermitirConsultarEntregador() {

            Long id = 1L;

            var entregador = EntregadorHelper.gerarEntregador(id);

            when(entregadorRepository.findById(any(Long.class))).thenReturn(Optional.of(entregador));

            var entregadorOptional = entregadorRepository.findById(id);

            verify(entregadorRepository, times(1)).findById(id);
            assertThat(entregadorOptional)
                    .isPresent()
                    .containsSame(entregador)
            ;
            entregadorOptional.ifPresent(entregadorArmazenado -> {
                assertThat(entregadorArmazenado)
                        .usingRecursiveComparison()
                        .isEqualTo(entregador);
            });
        }

        @Test
        void devePermitirListarEntregadores() {
            var entregador1 = EntregadorHelper.gerarEntregador(null);
            var entregador2 = EntregadorHelper.gerarEntregador(null);
            var entregadorList = Arrays.asList(entregador1, entregador2);

            when(entregadorRepository.findAll()).thenReturn(entregadorList);

            var resultado = entregadorRepository.findAll();

            verify(entregadorRepository, times(1)).findAll();

            assertThat(resultado)
                    .hasSize(2)
                    .containsExactlyInAnyOrder(entregador1, entregador2);
        }

        @Test
        void devePermitirConsultarEntregadorPorCnpj() {
            var entregador = EntregadorHelper.gerarEntregador(null);
            var cnpj = 52123949000182L;

            when(entregadorRepository.findByCnpj(any(Long.class))).thenReturn(Optional.of(entregador));

            var entregadorOptional = entregadorRepository.findByCnpj(cnpj);

            verify(entregadorRepository, times(1)).findByCnpj(cnpj);
            assertThat(entregadorOptional)
                    .isPresent()
                    .containsSame(entregador)
            ;
            entregadorOptional.ifPresent(entregadorArmazenado -> {
                assertThat(entregadorArmazenado)
                        .usingRecursiveComparison()
                        .isEqualTo(entregador);
            });

        }
    }
}
