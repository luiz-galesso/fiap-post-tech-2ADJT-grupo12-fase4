package com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.tabeladefrete.repository;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.tabeladefrete.model.TabelaDeFrete;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.tabeladefrete.utils.TabelaDeFreteHelper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
class TabelaDeFreteRepositoryIT {
    @Autowired
    private TabelaDeFreteRepository tabelaDeFreteRepository;

    @Test
    void devePermitirCriarTabelaTabelaDeFrete() {
        Long totalTabelasCriadas = tabelaDeFreteRepository.count();
        assertThat(totalTabelasCriadas).isNotNegative();
    }

    @Test
    void devePermitirRegistrarTabelaDeFrete() {
        Long id = 1L;
        var tabelaDeFrete = TabelaDeFreteHelper.gerarTabelaDeFrete(id);
        var tabelaDeFreteArmazenado = tabelaDeFreteRepository.save(tabelaDeFrete);

        assertThat(tabelaDeFreteArmazenado)
                .isInstanceOf(TabelaDeFrete.class)
                .isNotNull();

        assertThat(tabelaDeFreteArmazenado)
                .usingRecursiveComparison()
                .ignoringFields("entregador.cnpj")
                .isEqualTo(tabelaDeFrete)
        ;

        assertThat(tabelaDeFreteArmazenado.getId())
                .isNotNull();
    }

    @Test
    void devePermitirApagarTabelaDeFrete() {
        var tabelaDeFreteGerado = TabelaDeFreteHelper.gerarTabelaDeFrete(null);
        var tabelaDeFrete = TabelaDeFreteHelper.registrarTabelaDeFrete(tabelaDeFreteRepository, tabelaDeFreteGerado);
        var id = tabelaDeFrete.getId();

        tabelaDeFreteRepository.deleteById(id);

        var tabelaDeFreteOptional = tabelaDeFreteRepository.findById(id);

        assertThat(tabelaDeFreteOptional)
                .isEmpty();
    }


    @Nested
    class ConsultarTabelaDeFretes {
        @Test
        void devePermitirListarTabelaDeFretes() {

            var tabelaDeFrete1 = TabelaDeFreteHelper.registrarTabelaDeFrete(tabelaDeFreteRepository, TabelaDeFreteHelper.gerarTabelaDeFrete(null));
            var tabelaDeFrete2 = TabelaDeFreteHelper.registrarTabelaDeFrete(tabelaDeFreteRepository, TabelaDeFreteHelper.gerarTabelaDeFrete(null));
            var tabelaDeFrete3 = TabelaDeFreteHelper.registrarTabelaDeFrete(tabelaDeFreteRepository, TabelaDeFreteHelper.gerarTabelaDeFrete(null));
            var resultado = tabelaDeFreteRepository.findAll();

            assertThat(resultado)
                    .hasSize(23);
        }

        @Test
        void devePermitirConsultarTabelaDeFrete() {
            var tabelaDeFreteGerado = TabelaDeFreteHelper.gerarTabelaDeFrete(null);
            var tabelaDeFrete = TabelaDeFreteHelper.registrarTabelaDeFrete(tabelaDeFreteRepository, tabelaDeFreteGerado);

            var id = tabelaDeFrete.getId();

            var tabelaDeFreteOptional = tabelaDeFreteRepository.findById(id);

            assertThat(tabelaDeFreteOptional)
                    .isPresent()
                    .containsSame(tabelaDeFrete);

            tabelaDeFreteOptional.ifPresent(tabelaDeFreteArmazenado -> {
                assertThat(tabelaDeFreteArmazenado)
                        .usingRecursiveComparison()
                        .isEqualTo(tabelaDeFrete);
            });

        }

        @Test
        void devePermitirBuscarTabelaDeFretePorCepOrigemERangeCepDestinoEEntregador() {
            var tabelaDeFrete = TabelaDeFreteHelper.registrarTabelaDeFrete(tabelaDeFreteRepository, TabelaDeFreteHelper.gerarTabelaDeFrete(null));

            var tabelaDeFreteOptional = tabelaDeFreteRepository.findTabelaDeFreteByCepOrigemAndCepDestinoInicialAndCepDestinoFinalAndEntregador(tabelaDeFrete.getCepOrigem(), tabelaDeFrete.getCepDestinoInicial(), tabelaDeFrete.getCepDestinoFinal(), tabelaDeFrete.getEntregador());

            assertThat(tabelaDeFreteOptional)
                    .isPresent()
                    .containsSame(tabelaDeFrete);
            tabelaDeFreteOptional.ifPresent(tabelaDeFreteObtido -> {
                assertThat(tabelaDeFreteObtido)
                        .usingRecursiveComparison()
                        .isEqualTo(tabelaDeFrete);
            });
        }

        @Test
        void devePermitirListarTabelasDeFreteDeUmaOrigemERangeDestino() {
            var tabelaDeFrete1 = TabelaDeFreteHelper.registrarTabelaDeFrete(tabelaDeFreteRepository, TabelaDeFreteHelper.gerarTabelaDeFrete(null));
            var tabelaDeFrete2 = TabelaDeFreteHelper.registrarTabelaDeFrete(tabelaDeFreteRepository, TabelaDeFreteHelper.gerarTabelaDeFrete(null));

            var resultado = tabelaDeFreteRepository.findByCepOrigemAndCepDestinoInicialLessThanEqualAndCepDestinoFinalGreaterThan(tabelaDeFrete1.getCepOrigem(), tabelaDeFrete1.getCepDestinoInicial(),tabelaDeFrete1.getCepDestinoFinal()-1L);

            assertThat(resultado)
                    .hasSize(2)
                    .containsExactlyInAnyOrder(tabelaDeFrete1, tabelaDeFrete2);

        }

        /*@Test
        void devePermitirListarTabelasDeFreteDoEntregador() {
            var tabelaDeFrete1 = TabelaDeFreteHelper.registrarTabelaDeFrete(tabelaDeFreteRepository, TabelaDeFreteHelper.gerarTabelaDeFrete(null));
            var tabelaDeFrete2 = TabelaDeFreteHelper.registrarTabelaDeFrete(tabelaDeFreteRepository, TabelaDeFreteHelper.gerarTabelaDeFrete(null));

            var resultado = tabelaDeFreteRepository.findAllByEntregador_Id(tabelaDeFrete1.getEntregador().getId());

            assertThat(resultado)
                    .hasSize(2)
                    .containsExactlyInAnyOrder(tabelaDeFrete1, tabelaDeFrete2);
        }*/

        /*@Test
        void devePermitirBuscarOutrasTabelasDeFreteComMesmaOrigemDestinoDoMesmoEntregador() {
            var tabelaDeFrete1 = TabelaDeFreteHelper.registrarTabelaDeFrete(tabelaDeFreteRepository, TabelaDeFreteHelper.gerarTabelaDeFrete(null));
            var tabelaDeFrete2 = TabelaDeFreteHelper.registrarTabelaDeFrete(tabelaDeFreteRepository, TabelaDeFreteHelper.gerarTabelaDeFrete(null));

            var tabelaDeFreteOptional = tabelaDeFreteRepository.findTabelaDeFreteByEntregador_IdAndCepOrigemAndCepDestinoAndIdNot(tabelaDeFrete1.getEntregador().getId(), tabelaDeFrete1.getCepOrigem(), tabelaDeFrete1.getCepDestinoInicial(), tabelaDeFrete1.getId());
            assertThat(tabelaDeFreteOptional)
                    .isPresent()
                    .containsSame(tabelaDeFrete2);

            tabelaDeFreteOptional.ifPresent(tabelaDeFrete -> {
                assertThat(tabelaDeFrete)
                        .usingRecursiveComparison()
                        .isEqualTo(tabelaDeFrete2);
            });
        }*/

        /*@Test
        void devePermitirListarTabelaDeFreteDoEntregadorParaOrigemEDestino() {
            var tabelaDeFrete = TabelaDeFreteHelper.registrarTabelaDeFrete(tabelaDeFreteRepository, TabelaDeFreteHelper.gerarTabelaDeFrete(null));

            var tabelaDeFreteOptional = tabelaDeFreteRepository.findTabelaDeFreteByCepOrigemAndCepDestinoAndEntregador_Id(tabelaDeFrete.getCepOrigem(), tabelaDeFrete.getCepDestinoInicial(), tabelaDeFrete.getEntregador().getId());
            assertThat(tabelaDeFreteOptional)
                    .isPresent()
                    .containsSame(tabelaDeFrete);

            tabelaDeFreteOptional.ifPresent(tabelaDeFreteGerada -> {
                assertThat(tabelaDeFreteGerada)
                        .usingRecursiveComparison()
                        .isEqualTo(tabelaDeFrete);
            });


        }*/
        /*@Test
        void devePermitirBuscarOpcoesDisponiveisDeFreteConformeOrigemEDestino() {
            var tabelaDeFrete1 = TabelaDeFreteHelper.registrarTabelaDeFrete(tabelaDeFreteRepository, TabelaDeFreteHelper.gerarTabelaDeFrete(null));
            var tabelaDeFrete2 = TabelaDeFreteHelper.registrarTabelaDeFrete(tabelaDeFreteRepository, TabelaDeFreteHelper.gerarTabelaDeFrete(null));
            var tabelaDeFreteList = Arrays.asList(tabelaDeFrete1, tabelaDeFrete2);

            var resultado = tabelaDeFreteRepository.findAllByCepOrigemAndCepDestinoAndEntregador_QuantidadeRecursosDisponiveisGreaterThanAndEntregador_SituacaoOrderByValorFrete(tabelaDeFrete1.getCepOrigem(),
                    tabelaDeFrete1.getCepDestinoInicial(),
                    1L,
                    "ATIVO");

            assertThat(resultado)
                    .hasSize(2)
                    .containsExactlyInAnyOrder(tabelaDeFrete1, tabelaDeFrete2);
        }*/
    }
}
