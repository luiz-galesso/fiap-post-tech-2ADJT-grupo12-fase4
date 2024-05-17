package com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.entregador;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.model.Entregador;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entregador.repository.EntregadorRepository;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entregador.utils.EntregadorHelper;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.exception.BusinessErrorException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class AlocarRecursoEntregadorIT {
    @Autowired
    EntregadorRepository entregadorRepository;

    @Autowired
    AlocarRecursoEntregador alocarRecursoEntregador;

    @Nested
    class AtualizarEntregadorTeste {
        @Test
        void devePermitirAlocarRecursoEntregador() {
            var entregadorDesatualizado = EntregadorHelper.registrarEntregador(entregadorRepository, EntregadorHelper.gerarEntregador(null));
            var entregadorDesatualizadoQuantidadeRecursos = entregadorDesatualizado.getQuantidadeRecursosDisponiveis();
            var resultado = alocarRecursoEntregador.execute(entregadorDesatualizado.getId());

            assertThat(resultado)
                    .isNotNull()
                    .isInstanceOf(Entregador.class)
                    .usingRecursiveComparison()
                    .isEqualTo(entregadorDesatualizado);

            assertThat(resultado.getQuantidadeRecursosDisponiveis())
                    .isLessThan(entregadorDesatualizadoQuantidadeRecursos)
                    .isNotNull();
        }

        @Test
        void deveGerarExcecaoSeNaoEncontrarEntregador() {
            assertThatThrownBy(() -> alocarRecursoEntregador.execute(1L))
                    .isInstanceOf(BusinessErrorException.class)
                    .message().isEqualTo("Entregador não localizado");
        }

        @Test
        void deveGerarExcecaoSeRecursosInsuficientes() {
            var entregador = EntregadorHelper.gerarEntregador(null);
            entregador.setQuantidadeRecursosDisponiveis(0L);
            entregador = EntregadorHelper.registrarEntregador(entregadorRepository, entregador);
            Entregador entregadorGravado = entregador;
            assertThatThrownBy(() -> alocarRecursoEntregador.execute(entregadorGravado.getId()))
                    .isInstanceOf(BusinessErrorException.class)
                    .message().isEqualTo("Entregador sem recurso disponivel para alocação");
        }

    }
}
