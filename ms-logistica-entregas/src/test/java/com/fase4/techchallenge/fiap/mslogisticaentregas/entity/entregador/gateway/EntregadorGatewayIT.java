package com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.gateway;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.model.Entregador;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entregador.repository.EntregadorRepository;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entregador.utils.EntregadorHelper;
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
class EntregadorGatewayIT {

    @Autowired
    private EntregadorRepository entregadorRepository;

    @Autowired
    private EntregadorGateway entregadorGateway;

    @Test
    void devePermitirCriarEntregador() {
        var entregador = EntregadorHelper.gerarEntregador(null);

        var entregadorArmazenado = entregadorGateway.create(entregador);

        assertThat(entregadorArmazenado)
                .isNotNull()
                .isInstanceOf(Entregador.class);

        assertThat(entregadorArmazenado)
                .usingRecursiveComparison()
                .isEqualTo(entregador);

        assertThat(entregadorArmazenado.getId())
                .isNotNull();
    }

    @Test
    void devePermitirAlterarEntregador() {

        var entregadorDesatualizado = EntregadorHelper.registrarEntregador(entregadorRepository, EntregadorHelper.gerarEntregador(null));
        var nomeAntigo = entregadorDesatualizado.getNome();
        var entregadorAtualizado = entregadorDesatualizado.toBuilder().nome("Jardins Grill Prime").build();

        var resultado = entregadorGateway.update(entregadorAtualizado);

        assertThat(resultado)
                .isInstanceOf(Entregador.class)
                .isNotNull();

        assertNotEquals(resultado.getNome(), nomeAntigo);
        assertEquals(resultado.getId(), entregadorDesatualizado.getId());

        assertThat(resultado)
                .usingRecursiveComparison()
                .isEqualTo(entregadorAtualizado);
    }

    @Nested
    class ListarEntregador {
        @Test
        void devePermitirBuscarEntregador() {
            var entregador = EntregadorHelper.registrarEntregador(entregadorRepository, EntregadorHelper.gerarEntregador(null));

            var entregadorOptional = entregadorGateway.findById(entregador.getId());

            assertThat(entregadorOptional)
                    .isPresent()
                    .containsSame(entregador);
            entregadorOptional.ifPresent(entregadorObtido -> {
                assertThat(entregadorObtido)
                        .usingRecursiveComparison()
                        .isEqualTo(entregador);
            });
        }

        @Test
        void devePermitirListarEntregadorPorCnpj() {
            var entregador = EntregadorHelper.registrarEntregador(entregadorRepository, EntregadorHelper.gerarEntregador(null));

            var entregadorOptional = entregadorGateway.findByCnpj(entregador.getCnpj());

            assertThat(entregadorOptional)
                    .isPresent()
                    .containsSame(entregador);
            entregadorOptional.ifPresent(entregadorObtido -> {
                assertThat(entregadorObtido)
                        .usingRecursiveComparison()
                        .isEqualTo(entregador);
            });

        }

    }
}
