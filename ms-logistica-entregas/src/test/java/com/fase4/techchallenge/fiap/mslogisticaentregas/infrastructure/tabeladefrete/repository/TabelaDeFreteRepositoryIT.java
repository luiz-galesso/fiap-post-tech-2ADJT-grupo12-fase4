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
                .isEqualTo(tabelaDeFrete);

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
    class ConsultarTabelaDeFretees {
        @Test
        void devePermitirListarTabelaDeFretees() {

            var tabelaDeFrete1 = TabelaDeFreteHelper.registrarTabelaDeFrete(tabelaDeFreteRepository, TabelaDeFreteHelper.gerarTabelaDeFrete(null));
            var tabelaDeFrete2 = TabelaDeFreteHelper.registrarTabelaDeFrete(tabelaDeFreteRepository, TabelaDeFreteHelper.gerarTabelaDeFrete(null));
            var tabelaDeFrete3 = TabelaDeFreteHelper.registrarTabelaDeFrete(tabelaDeFreteRepository, TabelaDeFreteHelper.gerarTabelaDeFrete(null));
            var resultado = tabelaDeFreteRepository.findAll();

            assertThat(resultado)
                    .hasSize(3);
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

 /*       @Test
        void devePermitirConsultarTabelaDeFretePeloCnpj() {
            var tabelaDeFreteGerado = TabelaDeFreteHelper.gerarTabelaDeFrete(null);
            var tabelaDeFrete = TabelaDeFreteHelper.registrarTabelaDeFrete(tabelaDeFreteRepository, tabelaDeFreteGerado);

            var cnpj = tabelaDeFrete.getCnpj();

            var tabelaDeFreteOptional = tabelaDeFreteRepository.findByCnpj(cnpj);
            assertThat(tabelaDeFreteOptional)
                    .isPresent()
                    .containsSame(tabelaDeFrete);

            tabelaDeFreteOptional.ifPresent(tabelaDeFreteArmazenado -> {
                assertThat(tabelaDeFreteArmazenado)
                        .usingRecursiveComparison()
                        .isEqualTo(tabelaDeFrete);
            });
        }

*/
    }
}
