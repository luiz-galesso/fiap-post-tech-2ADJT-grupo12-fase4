package com.fase4.techchallenge.fiap.mslogisticaentregas.entity.tabeladefrete.gateway;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.model.Entregador;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.tabeladefrete.model.TabelaDeFrete;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.tabeladefrete.repository.TabelaDeFreteRepository;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.tabeladefrete.utils.TabelaDeFreteHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TabelaDeFreteGatewayTest {
    AutoCloseable openMocks;
    private TabelaDeFreteGateway tabelaDeFreteGateway;
    @Mock
    private TabelaDeFreteRepository tabelaDeFreteRepository;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        tabelaDeFreteGateway = new TabelaDeFreteGateway(tabelaDeFreteRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirCriarTabelaDeFrete() {
        TabelaDeFrete tabelaDeFrete = TabelaDeFreteHelper.gerarTabelaDeFrete(null);

        when(tabelaDeFreteRepository.save(any(TabelaDeFrete.class))).thenReturn(tabelaDeFrete);

        var resultado = tabelaDeFreteGateway.create(tabelaDeFrete);

        verify(tabelaDeFreteRepository, times(1)).save(tabelaDeFrete);
        assertThat(resultado)
                .isInstanceOf(TabelaDeFrete.class)
                .isNotNull();
        assertThat(resultado)
                .usingRecursiveComparison()
                .isEqualTo(tabelaDeFrete);
    }

    @Test
    void devePermitirAlterarTabelaDeFrete() {
        TabelaDeFrete tabelaDeFreteDesatualizado = TabelaDeFreteHelper.gerarTabelaDeFrete(1L);
        var tabelaDeFreteAtualizado = TabelaDeFreteHelper.gerarTabelaDeFrete(1L);
        tabelaDeFreteAtualizado.setValorFrete(190.52);

        when(tabelaDeFreteRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(tabelaDeFreteDesatualizado));
        when(tabelaDeFreteRepository.save(any(TabelaDeFrete.class)))
                .thenAnswer(i -> i.getArgument(0));

        var resultado = tabelaDeFreteGateway.update(tabelaDeFreteAtualizado);

        assertThat(resultado)
                .isInstanceOf(TabelaDeFrete.class)
                .isNotNull();

        assertNotEquals(resultado.getValorFrete(), tabelaDeFreteDesatualizado.getValorFrete());

        assertThat(resultado)
                .usingRecursiveComparison()
                .isEqualTo(tabelaDeFreteAtualizado);
        verify(tabelaDeFreteRepository, times(1)).save(any(TabelaDeFrete.class));
    }

    @Nested
    class ListarTabelaDeFrete {
        @Test
        void devePermitirBuscarTabelaDeFrete() {
            TabelaDeFrete tabelaDeFrete = TabelaDeFreteHelper.gerarTabelaDeFrete(1L);
            when(tabelaDeFreteRepository.findById(any(Long.class)))
                    .thenReturn(Optional.of(tabelaDeFrete));

            var resultado = tabelaDeFreteGateway.findById(any(Long.class));

            verify(tabelaDeFreteRepository, times(1))
                    .findById(any(Long.class));
            assertThat(resultado)
                    .isPresent()
                    .isInstanceOf(Optional.class)
                    .isNotNull();

            assertEquals(tabelaDeFrete, resultado.get());

            assertThat(resultado)
                    .usingRecursiveComparison()
                    .isEqualTo(Optional.of(tabelaDeFrete));
        }

        @Test
        void devePermitirBuscarTabelaDeFretePorCepOrigemERangeCepDestinoEEntregador() {
            TabelaDeFrete tabelaDeFrete = TabelaDeFreteHelper.gerarTabelaDeFrete(1L);

            when(tabelaDeFreteRepository.findTabelaDeFreteByCepOrigemAndCepDestinoInicialAndCepDestinoFinalAndEntregador(anyLong(), anyLong(), anyLong(), any(Entregador.class)))
                    .thenReturn(Optional.of(tabelaDeFrete));

            var resultado = tabelaDeFreteGateway.findTabelaDeFreteByCepOrigemAndCepDestinoInicialAndCepDestinoFinalAndEntregador(tabelaDeFrete.getCepOrigem(), tabelaDeFrete.getCepDestinoInicial(), tabelaDeFrete.getCepDestinoFinal(), tabelaDeFrete.getEntregador());

            verify(tabelaDeFreteRepository, times(1))
                    .findTabelaDeFreteByCepOrigemAndCepDestinoInicialAndCepDestinoFinalAndEntregador(anyLong(), anyLong(), anyLong(), any(Entregador.class));
            assertThat(resultado)
                    .isPresent()
                    .isInstanceOf(Optional.class)
                    .isInstanceOf(Optional.class)
                    .isNotNull();

            assertEquals(tabelaDeFrete, resultado.get());

            assertThat(resultado)
                    .usingRecursiveComparison()
                    .isEqualTo(Optional.of(tabelaDeFrete));
        }

        @Test
        void devePermitirListarTabelasDeFreteDeUmaOrigemERangeDestino() {
            TabelaDeFrete tabelaDeFrete1 = TabelaDeFreteHelper.gerarTabelaDeFrete(1L);
            TabelaDeFrete tabelaDeFrete2 = TabelaDeFreteHelper.gerarTabelaDeFrete(2L);
            var tabelaDeFreteList = Arrays.asList(tabelaDeFrete1, tabelaDeFrete2);

            when(tabelaDeFreteRepository.findByCepOrigemAndCepDestinoInicialLessThanEqualAndCepDestinoFinalGreaterThan(anyLong(),anyLong(),anyLong()))
                    .thenReturn(tabelaDeFreteList);

            var resultado = tabelaDeFreteGateway.findByCepOrigemAndCepDestinoInicialLessThanEqualAndCepDestinoFinalGreaterThan(tabelaDeFrete1.getCepOrigem(),tabelaDeFrete1.getCepDestinoInicial());

            verify(tabelaDeFreteRepository, times(1))
                    .findByCepOrigemAndCepDestinoInicialLessThanEqualAndCepDestinoFinalGreaterThan(anyLong(),anyLong(),anyLong());

            assertThat(resultado)
                    .hasSize(2)
                    .containsExactlyInAnyOrder(tabelaDeFrete1, tabelaDeFrete2);
        }

       /* @Test
        void devePermitirBuscarTabelaDeFreteDoEntregadorPorOrigemDestino() {
            TabelaDeFrete tabelaDeFrete = TabelaDeFreteHelper.gerarTabelaDeFrete(1L);

            when(tabelaDeFreteRepository.findTabelaDeFreteByCepOrigemAndCepDestinoAndEntregador_Id(anyLong(), anyLong(), anyLong()))
                    .thenReturn(Optional.of(tabelaDeFrete));

            var resultado = tabelaDeFreteGateway.findTabelaDeFreteByCepOrigemAndCepDestinoAndEntregador(tabelaDeFrete.getCepOrigem(), tabelaDeFrete.getCepDestinoInicial(), tabelaDeFrete.getEntregador().getId());

            verify(tabelaDeFreteRepository, times(1))
                    .findTabelaDeFreteByCepOrigemAndCepDestinoAndEntregador_Id(anyLong(), anyLong(), anyLong());
            assertThat(resultado)
                    .isPresent()
                    .isInstanceOf(Optional.class)
                    .isInstanceOf(Optional.class)
                    .isNotNull();

            assertEquals(tabelaDeFrete, resultado.get());

            assertThat(resultado)
                    .usingRecursiveComparison()
                    .isEqualTo(Optional.of(tabelaDeFrete));
        }*/

       /* @Test
        void devePermitirBuscarTabelaDeFreteDeOutrosEntregadorPorOrigemDestino() {
            TabelaDeFrete tabelaDeFrete = TabelaDeFreteHelper.gerarTabelaDeFrete(1L);

            when(tabelaDeFreteRepository.findTabelaDeFreteByEntregador_IdAndCepOrigemAndCepDestinoAndIdNot(anyLong(), anyLong(), anyLong(), anyLong()))
                    .thenReturn(Optional.of(tabelaDeFrete));

            var resultado = tabelaDeFreteGateway.findTabelaDeFreteByIdEntregadorAndCepOrigemAndCepDestinoAndIdTabelaNot(tabelaDeFrete.getEntregador().getId(), tabelaDeFrete.getCepOrigem(), tabelaDeFrete.getCepDestinoInicial(), tabelaDeFrete.getId());

            verify(tabelaDeFreteRepository, times(1))
                    .findTabelaDeFreteByEntregador_IdAndCepOrigemAndCepDestinoAndIdNot(anyLong(), anyLong(), anyLong(), anyLong());
            assertThat(resultado)
                    .isPresent()
                    .isInstanceOf(Optional.class)
                    .isInstanceOf(Optional.class)
                    .isNotNull();

            assertEquals(tabelaDeFrete, resultado.get());

            assertThat(resultado)
                    .usingRecursiveComparison()
                    .isEqualTo(Optional.of(tabelaDeFrete));
        }*/

        /*@Test
        void devePermitirListarTabelasDeFreteDeUmEntregador() {
            TabelaDeFrete tabelaDeFrete1 = TabelaDeFreteHelper.gerarTabelaDeFrete(1L);
            TabelaDeFrete tabelaDeFrete2 = TabelaDeFreteHelper.gerarTabelaDeFrete(2L);
            var tabelaDeFreteList = Arrays.asList(tabelaDeFrete1, tabelaDeFrete2);

            when(tabelaDeFreteRepository.findAllByEntregador_Id(anyLong()))
                    .thenReturn(tabelaDeFreteList);

            var resultado = tabelaDeFreteGateway.findAllByIdEntregador(tabelaDeFrete1.getEntregador().getId());

            verify(tabelaDeFreteRepository, times(1))
                    .findAllByEntregador_Id(anyLong());

            assertThat(resultado)
                    .hasSize(2)
                    .containsExactlyInAnyOrder(tabelaDeFrete1, tabelaDeFrete2);
        }*/

       /* @Test
        void devePermitirListarTabelasDeFreteDeUmaOrigemDestinoDisponivel() {
            TabelaDeFrete tabelaDeFrete1 = TabelaDeFreteHelper.gerarTabelaDeFrete(1L);
            TabelaDeFrete tabelaDeFrete2 = TabelaDeFreteHelper.gerarTabelaDeFrete(2L);
            var tabelaDeFreteList = Arrays.asList(tabelaDeFrete1, tabelaDeFrete2);

            when(tabelaDeFreteRepository.findAllByCepOrigemAndCepDestinoAndEntregador_QuantidadeRecursosDisponiveisGreaterThanAndEntregador_SituacaoOrderByValorFrete(anyLong(), anyLong(), anyLong(), anyString()))
                    .thenReturn(tabelaDeFreteList);

            var resultado = tabelaDeFreteGateway.obterTabelaDeFretePeloCepOrigemECepDestinoDisponiveis(tabelaDeFrete1.getCepOrigem(), tabelaDeFrete1.getCepDestinoInicial());

            verify(tabelaDeFreteRepository, times(1))
                    .findAllByCepOrigemAndCepDestinoAndEntregador_QuantidadeRecursosDisponiveisGreaterThanAndEntregador_SituacaoOrderByValorFrete(anyLong(), anyLong(), anyLong(), anyString());

            assertThat(resultado)
                    .hasSize(2)
                    .containsExactlyInAnyOrder(tabelaDeFrete1, tabelaDeFrete2);
        }*/

        @Test
        void devePermitirDeletar() {
            TabelaDeFrete tabelaDeFrete = TabelaDeFreteHelper.gerarTabelaDeFrete(1L);
            var id = tabelaDeFrete.getId();
            doNothing().when(tabelaDeFreteRepository).deleteById(id);
            tabelaDeFreteGateway.remove(id);
            var tabelaDeFreteOptional = tabelaDeFreteGateway.findById(id);
            assertThat(tabelaDeFreteOptional)
                    .isEmpty();
            verify(tabelaDeFreteRepository, times(1)).deleteById(id);
        }
    }

}
