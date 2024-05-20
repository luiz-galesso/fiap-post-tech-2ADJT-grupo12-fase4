package com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entregador.repository;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.model.Entregador;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entregador.utils.EntregadorHelper;
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
class EntregadorRepositoryIT {

    @Autowired
    private EntregadorRepository entregadorRepository;

    @Test
    void devePermitirCriarTabelaEntregador() {
        Long totalTabelasCriadas = entregadorRepository.count();
        assertThat(totalTabelasCriadas).isNotNegative();
    }

    @Test
    void devePermitirRegistrarEntregador() {
        Long id = 1L;
        var entregador = EntregadorHelper.gerarEntregador(id);
        var entregadorArmazenado = entregadorRepository.save(entregador);

        assertThat(entregadorArmazenado)
                .isInstanceOf(Entregador.class)
                .isNotNull();

        assertThat(entregadorArmazenado)
                .usingRecursiveComparison()
                .isEqualTo(entregador);

        assertThat(entregadorArmazenado.getId())
                .isNotNull();
    }

    @Test
    void devePermitirApagarEntregador() {
        var entregadorGerado = EntregadorHelper.gerarEntregador(null);
        var entregador = EntregadorHelper.registrarEntregador(entregadorRepository, entregadorGerado);
        var id = entregador.getId();

        entregadorRepository.deleteById(id);

        var entregadorOptional = entregadorRepository.findById(id);

        assertThat(entregadorOptional)
                .isEmpty();
    }


    @Nested
    class ConsultarEntregadores {
        @Test
        void devePermitirListarEntregadores() {

            var entregador1 = EntregadorHelper.registrarEntregador(entregadorRepository, EntregadorHelper.gerarEntregador(null));
            var entregador2 = EntregadorHelper.registrarEntregador(entregadorRepository, EntregadorHelper.gerarEntregador(null));
            var entregador3 = EntregadorHelper.registrarEntregador(entregadorRepository, EntregadorHelper.gerarEntregador(null));
            var resultado = entregadorRepository.findAll();

            assertThat(resultado)
                    .hasSize(4);
        }

        @Test
        void devePermitirConsultarEntregador() {
            var entregadorGerado = EntregadorHelper.gerarEntregador(null);
            var entregador = EntregadorHelper.registrarEntregador(entregadorRepository, entregadorGerado);

            var id = entregador.getId();

            var entregadorOptional = entregadorRepository.findById(id);

            assertThat(entregadorOptional)
                    .isPresent()
                    .containsSame(entregador);

            entregadorOptional.ifPresent(entregadorArmazenado -> {
                assertThat(entregadorArmazenado)
                        .usingRecursiveComparison()
                        .isEqualTo(entregador);
            });

        }

        @Test
        void devePermitirConsultarEntregadorPeloCnpj() {
            var entregadorGerado = EntregadorHelper.gerarEntregador(null);
            var entregador = EntregadorHelper.registrarEntregador(entregadorRepository, entregadorGerado);

            var cnpj = entregador.getCnpj();

            var entregadorOptional = entregadorRepository.findByCnpj(cnpj);
            assertThat(entregadorOptional)
                    .isPresent()
                    .containsSame(entregador);

            entregadorOptional.ifPresent(entregadorArmazenado -> {
                assertThat(entregadorArmazenado)
                        .usingRecursiveComparison()
                        .isEqualTo(entregador);
            });
        }


    }

}
