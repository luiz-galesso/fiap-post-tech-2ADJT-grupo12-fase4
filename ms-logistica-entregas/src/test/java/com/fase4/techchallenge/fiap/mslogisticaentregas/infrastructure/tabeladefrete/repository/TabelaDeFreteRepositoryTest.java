package com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.tabeladefrete.repository;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.model.Entregador;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.tabeladefrete.model.TabelaDeFrete;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.tabeladefrete.utils.TabelaDeFreteHelper;
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

public class TabelaDeFreteRepositoryTest {
    AutoCloseable openMocks;
    @Mock
    private TabelaDeFreteRepository tabelaDeFreteRepository;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirRegistrarTabelaDeFrete() {
        TabelaDeFrete tabelaDeFrete = TabelaDeFreteHelper.gerarTabelaDeFrete(null);
        when(tabelaDeFreteRepository.save(any(TabelaDeFrete.class))).thenAnswer(a -> a.getArguments()[0]);

        var tabelaDeFreteArmazenado = tabelaDeFreteRepository.save(tabelaDeFrete);
        verify(tabelaDeFreteRepository, times(1)).save(tabelaDeFrete);
        assertThat(tabelaDeFreteArmazenado)
                .isInstanceOf(TabelaDeFrete.class)
                .isNotNull();
        assertThat(tabelaDeFreteArmazenado)
                .usingRecursiveComparison()
                .isEqualTo(tabelaDeFrete);

    }

    @Test
    void devePermitirApagarTabelaDeFrete() {
        Long id = new Random().nextLong();
        doNothing().when(tabelaDeFreteRepository).deleteById(id);

        tabelaDeFreteRepository.deleteById(id);

        verify(tabelaDeFreteRepository, times(1)).deleteById(id);

    }

    @Nested
    class ConsultarTabelasDeFrete {
        @Test
        void devePermitirConsultarTabelaDeFrete() {

            Long id = 1L;

            var tabelaDeFrete = TabelaDeFreteHelper.gerarTabelaDeFrete(id);

            when(tabelaDeFreteRepository.findById(any(Long.class))).thenReturn(Optional.of(tabelaDeFrete));

            var tabelaDeFreteOptional = tabelaDeFreteRepository.findById(id);

            verify(tabelaDeFreteRepository, times(1)).findById(id);
            assertThat(tabelaDeFreteOptional)
                    .isPresent()
                    .containsSame(tabelaDeFrete)
            ;
            tabelaDeFreteOptional.ifPresent(tabelaDeFreteArmazenado -> {
                assertThat(tabelaDeFreteArmazenado)
                        .usingRecursiveComparison()
                        .isEqualTo(tabelaDeFrete);
            });
        }

        @Test
        void devePermitirBuscarTabelaDeFretePorCepOrigemERangeCepDestinoEEntregador() {
            var tabelaDeFrete1 = TabelaDeFreteHelper.gerarTabelaDeFrete(1L);

            when(tabelaDeFreteRepository.findTabelaDeFreteByCepOrigemAndCepDestinoInicialAndCepDestinoFinalAndEntregador(anyLong(), anyLong(), anyLong(), any(Entregador.class))).thenReturn(Optional.of(tabelaDeFrete1));

            var tabelaDeFreteOptional = tabelaDeFreteRepository.findTabelaDeFreteByCepOrigemAndCepDestinoInicialAndCepDestinoFinalAndEntregador(tabelaDeFrete1.getEntregador().getId(), tabelaDeFrete1.getCepOrigem(), tabelaDeFrete1.getCepDestinoInicial(), tabelaDeFrete1.getEntregador());

            verify(tabelaDeFreteRepository, times(1)).findTabelaDeFreteByCepOrigemAndCepDestinoInicialAndCepDestinoFinalAndEntregador(anyLong(), anyLong(), anyLong(), any(Entregador.class));
            assertThat(tabelaDeFreteOptional)
                    .isPresent()
                    .containsSame(tabelaDeFrete1)
            ;
            tabelaDeFreteOptional.ifPresent(tabelaDeFreteArmazenado -> {
                assertThat(tabelaDeFreteArmazenado)
                        .usingRecursiveComparison()
                        .isEqualTo(tabelaDeFrete1);
            });


        }

        @Test
        void devePermitirListarTabelasDeFreteDeUmaOrigemERangeDestino() {
            TabelaDeFrete tabelaDeFrete1 = TabelaDeFreteHelper.gerarTabelaDeFrete(1L);
            TabelaDeFrete tabelaDeFrete2 = TabelaDeFreteHelper.gerarTabelaDeFrete(2L);
            var tabelaDeFreteList = Arrays.asList(tabelaDeFrete1, tabelaDeFrete2);

            when(tabelaDeFreteRepository.findByCepOrigemAndCepDestinoInicialLessThanEqualAndCepDestinoFinalGreaterThan(anyLong(), anyLong(), anyLong()))
                    .thenReturn(tabelaDeFreteList);

            var resultado = tabelaDeFreteRepository.findByCepOrigemAndCepDestinoInicialLessThanEqualAndCepDestinoFinalGreaterThan(tabelaDeFrete1.getCepOrigem(), tabelaDeFrete1.getCepDestinoInicial(),tabelaDeFrete1.getCepDestinoFinal());

            verify(tabelaDeFreteRepository, times(1))
                    .findByCepOrigemAndCepDestinoInicialLessThanEqualAndCepDestinoFinalGreaterThan(anyLong(), anyLong(), anyLong());

            assertThat(resultado)
                    .hasSize(2)
                    .containsExactlyInAnyOrder(tabelaDeFrete1, tabelaDeFrete2);
        }
/*
        @Test
        void devePermitirListarTabelasDeFreteDoEntregador() {
            var tabelaDeFrete1 = TabelaDeFreteHelper.gerarTabelaDeFrete(null);
            var tabelaDeFrete2 = TabelaDeFreteHelper.gerarTabelaDeFrete(null);
            var tabelaDeFreteList = Arrays.asList(tabelaDeFrete1, tabelaDeFrete2);

            when(tabelaDeFreteRepository.findAllByEntregador_Id(any(Long.class))).thenReturn(tabelaDeFreteList);

            var resultado = tabelaDeFreteRepository.findAllByEntregador_Id(tabelaDeFrete1.getEntregador().getId());

            verify(tabelaDeFreteRepository, times(1)).findAllByEntregador_Id(any(Long.class));

            assertThat(resultado)
                    .hasSize(2)
                    .containsExactlyInAnyOrder(tabelaDeFrete1, tabelaDeFrete2);
        }

        @Test
        void devePermitirBuscarOutrasTabelasDeFreteComMesmaOrigemDestinoDoMesmoEntregador() {
            var tabelaDeFrete1 = TabelaDeFreteHelper.gerarTabelaDeFrete(1L);

            when(tabelaDeFreteRepository.findTabelaDeFreteByEntregador_IdAndCepOrigemAndCepDestinoAndIdNot(any(Long.class), any(Long.class), any(Long.class), any(Long.class))).thenReturn(Optional.of(tabelaDeFrete1));

            var tabelaDeFreteOptional = tabelaDeFreteRepository.findTabelaDeFreteByEntregador_IdAndCepOrigemAndCepDestinoAndIdNot(tabelaDeFrete1.getEntregador().getId(), tabelaDeFrete1.getCepOrigem(), tabelaDeFrete1.getCepDestinoInicial(), tabelaDeFrete1.getId());

            verify(tabelaDeFreteRepository, times(1)).findTabelaDeFreteByEntregador_IdAndCepOrigemAndCepDestinoAndIdNot(tabelaDeFrete1.getEntregador().getId(), tabelaDeFrete1.getCepOrigem(), tabelaDeFrete1.getCepDestinoInicial(), tabelaDeFrete1.getId());
            assertThat(tabelaDeFreteOptional)
                    .isPresent()
                    .containsSame(tabelaDeFrete1)
            ;
            tabelaDeFreteOptional.ifPresent(tabelaDeFreteArmazenado -> {
                assertThat(tabelaDeFreteArmazenado)
                        .usingRecursiveComparison()
                        .isEqualTo(tabelaDeFrete1);
            });

        }

        @Test
        void devePermitirListarTabelaDeFreteDoEntregadorParaOrigemEDestino() {
            var tabelaDeFrete1 = TabelaDeFreteHelper.gerarTabelaDeFrete(null);
            when(tabelaDeFreteRepository.findTabelaDeFreteByCepOrigemAndCepDestinoAndEntregador_Id(any(Long.class), any(Long.class), any(Long.class))).thenReturn(Optional.of(tabelaDeFrete1));

            var tabelaDeFreteOptional = tabelaDeFreteRepository.findTabelaDeFreteByCepOrigemAndCepDestinoAndEntregador_Id(tabelaDeFrete1.getCepOrigem(), tabelaDeFrete1.getCepDestinoInicial(), tabelaDeFrete1.getEntregador().getId());

            verify(tabelaDeFreteRepository, times(1)).findTabelaDeFreteByCepOrigemAndCepDestinoAndEntregador_Id(tabelaDeFrete1.getCepOrigem(), tabelaDeFrete1.getCepDestinoInicial(), tabelaDeFrete1.getEntregador().getId());
            assertThat(tabelaDeFreteOptional)
                    .isPresent()
                    .containsSame(tabelaDeFrete1)
            ;
            tabelaDeFreteOptional.ifPresent(tabelaDeFreteArmazenado -> {
                assertThat(tabelaDeFreteArmazenado)
                        .usingRecursiveComparison()
                        .isEqualTo(tabelaDeFrete1);
            });
        }

        @Test
        void devePermitirBuscarOpcoesDisponiveisDeFreteConformeOrigemEDestino() {
            var tabelaDeFrete1 = TabelaDeFreteHelper.gerarTabelaDeFrete(null);
            var tabelaDeFrete2 = TabelaDeFreteHelper.gerarTabelaDeFrete(null);
            var tabelaDeFreteList = Arrays.asList(tabelaDeFrete1, tabelaDeFrete2);

            when(tabelaDeFreteRepository.findAllByCepOrigemAndCepDestinoAndEntregador_QuantidadeRecursosDisponiveisGreaterThanAndEntregador_SituacaoOrderByValorFrete(any(Long.class), any(Long.class), any(Long.class), any(String.class))).thenReturn(tabelaDeFreteList);

            var resultado = tabelaDeFreteRepository.findAllByCepOrigemAndCepDestinoAndEntregador_QuantidadeRecursosDisponiveisGreaterThanAndEntregador_SituacaoOrderByValorFrete(tabelaDeFrete1.getCepOrigem(),
                    tabelaDeFrete1.getCepDestinoInicial(),
                    1L,
                    "ATIVO");

            verify(tabelaDeFreteRepository, times(1)).findAllByCepOrigemAndCepDestinoAndEntregador_QuantidadeRecursosDisponiveisGreaterThanAndEntregador_SituacaoOrderByValorFrete(any(Long.class), any(Long.class), any(Long.class), any(String.class));

            assertThat(resultado)
                    .hasSize(2)
                    .containsExactlyInAnyOrder(tabelaDeFrete1, tabelaDeFrete2);

        }*/
    }
}
