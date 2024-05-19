package com.fase4.techchallenge.fiap.mslogisticaentregas.entity.tabeladefrete.gateway;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.tabeladefrete.model.TabelaDeFrete;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.tabeladefrete.repository.TabelaDeFreteRepository;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.tabeladefrete.utils.TabelaDeFreteHelper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
class TabelaDeFreteGatewayIT {

    @Autowired
    private TabelaDeFreteRepository tabelaDeFreteRepository;

    @Autowired
    private TabelaDeFreteGateway tabelaDeFreteGateway;

    @Test
    void devePermitirCriarTabelaDeFrete() {
        var tabelaDeFrete = TabelaDeFreteHelper.gerarTabelaDeFrete(null);

        var tabelaDeFreteArmazenado = tabelaDeFreteGateway.create(tabelaDeFrete);

        assertThat(tabelaDeFreteArmazenado)
                .isNotNull()
                .isInstanceOf(TabelaDeFrete.class);

        assertThat(tabelaDeFreteArmazenado)
                .usingRecursiveComparison()
                .isEqualTo(tabelaDeFrete);

        assertThat(tabelaDeFreteArmazenado.getId())
                .isNotNull();
    }

    @Test
    void devePermitirAlterarTabelaDeFrete() {

        var tabelaDeFreteDesatualizado = TabelaDeFreteHelper.registrarTabelaDeFrete(tabelaDeFreteRepository, TabelaDeFreteHelper.gerarTabelaDeFrete(null));
        var valorFreteAntigo = tabelaDeFreteDesatualizado.getValorFrete();
        var tabelaDeFreteAtualizado = tabelaDeFreteDesatualizado.toBuilder().valorFrete(180.01).build();

        var resultado = tabelaDeFreteGateway.update(tabelaDeFreteAtualizado);

        assertThat(resultado)
                .isInstanceOf(TabelaDeFrete.class)
                .isNotNull();

        assertNotEquals(resultado.getValorFrete(), valorFreteAntigo);
        assertEquals(resultado.getId(), tabelaDeFreteDesatualizado.getId());

        assertThat(resultado)
                .usingRecursiveComparison()
                .isEqualTo(tabelaDeFreteAtualizado);
    }

    @Nested
    class ListarTabelaDeFrete {
        @Test
        void devePermitirBuscarTabelaDeFrete() {
            var tabelaDeFrete = TabelaDeFreteHelper.registrarTabelaDeFrete(tabelaDeFreteRepository, TabelaDeFreteHelper.gerarTabelaDeFrete(null));

            var tabelaDeFreteOptional = tabelaDeFreteGateway.findById(tabelaDeFrete.getId());

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
        void devePermitirBuscarTabelaDeFreteDoEntregadorPorOrigemDestino() {
            var tabelaDeFrete = TabelaDeFreteHelper.registrarTabelaDeFrete(tabelaDeFreteRepository, TabelaDeFreteHelper.gerarTabelaDeFrete(null));

            var tabelaDeFreteOptional = tabelaDeFreteGateway.findTabelaDeFreteByCepOrigemAndCepDestinoAndEntregador(tabelaDeFrete.getCepOrigem(), tabelaDeFrete.getCepDestino(), tabelaDeFrete.getEntregador().getId());

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
        void devePermitirBuscarTabelaDeFreteDeOutrosEntregadorPorOrigemDestino() {
            var tabelaDeFrete1 = TabelaDeFreteHelper.registrarTabelaDeFrete(tabelaDeFreteRepository, TabelaDeFreteHelper.gerarTabelaDeFrete(null));
            var tabelaDeFrete2 = TabelaDeFreteHelper.registrarTabelaDeFrete(tabelaDeFreteRepository, TabelaDeFreteHelper.gerarTabelaDeFrete(null));

            var tabelaDeFreteOptional = tabelaDeFreteGateway.findTabelaDeFreteByIdEntregadorAndCepOrigemAndCepDestinoAndIdTabelaNot(tabelaDeFrete1.getEntregador().getId(), tabelaDeFrete1.getCepOrigem(), tabelaDeFrete1.getCepDestino(), tabelaDeFrete1.getId());

            assertThat(tabelaDeFreteOptional)
                    .isPresent()
                    .containsSame(tabelaDeFrete2);
            tabelaDeFreteOptional.ifPresent(tabelaDeFreteObtido -> {
                assertThat(tabelaDeFreteObtido)
                        .usingRecursiveComparison()
                        .isEqualTo(tabelaDeFrete2);
            });
        }

        @Test
        void devePermitirListarTabelasDeFreteDeUmEntregador() {
            var tabelaDeFrete1 = TabelaDeFreteHelper.registrarTabelaDeFrete(tabelaDeFreteRepository, TabelaDeFreteHelper.gerarTabelaDeFrete(null));
            var tabelaDeFrete2 = TabelaDeFreteHelper.registrarTabelaDeFrete(tabelaDeFreteRepository, TabelaDeFreteHelper.gerarTabelaDeFrete(null));

            var resultado = tabelaDeFreteGateway.findAllByIdEntregador(tabelaDeFrete1.getEntregador().getId());

            assertThat(resultado)
                    .hasSize(2)
                    .containsExactlyInAnyOrder(tabelaDeFrete1, tabelaDeFrete2);

        }

        @Test
        void devePermitirListarTabelasDeFreteDeUmaOrigemDestinoDisponivel() {
            var tabelaDeFrete1 = TabelaDeFreteHelper.registrarTabelaDeFrete(tabelaDeFreteRepository, TabelaDeFreteHelper.gerarTabelaDeFrete(null));
            var tabelaDeFrete2 = TabelaDeFreteHelper.registrarTabelaDeFrete(tabelaDeFreteRepository, TabelaDeFreteHelper.gerarTabelaDeFrete(null));

            var resultado = tabelaDeFreteGateway.obterTabelaDeFretePeloCepOrigemECepDestinoDisponiveis(tabelaDeFrete1.getCepOrigem(), tabelaDeFrete1.getCepDestino());

            assertThat(resultado)
                    .hasSize(2)
                    .containsExactlyInAnyOrder(tabelaDeFrete1, tabelaDeFrete2);

        }

        @Test
        void devePermitirDeletar() {
            var tabelaDeFrete = TabelaDeFreteHelper.registrarTabelaDeFrete(tabelaDeFreteRepository, TabelaDeFreteHelper.gerarTabelaDeFrete(null));

            tabelaDeFreteGateway.remove(tabelaDeFrete.getId());
            var tabelaDeFreteOptional = tabelaDeFreteGateway.findById(tabelaDeFrete.getId());
            assertThat(tabelaDeFreteOptional)
                    .isEmpty();
        }
    }
}
