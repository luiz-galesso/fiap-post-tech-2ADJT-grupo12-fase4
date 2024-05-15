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
public class DesalocarRecursoEntregadorIT {
    @Autowired
    EntregadorRepository entregadorRepository;

    @Autowired
    DesalocarRecursoEntregador desalocarRecursoEntregador;

    @Nested
    class AtualizarEntregadorTeste {
        @Test
        void devePermitirDesalocarRecursoEntregador() {
            var entregadorDesatualizado = EntregadorHelper.registrarEntregador(entregadorRepository, EntregadorHelper.gerarEntregador(null));
            var entregadorDesatualizadoQuantidadeRecursos = entregadorDesatualizado.getQuantidadeRecursosDisponiveis();
            var resultado = desalocarRecursoEntregador.execute(entregadorDesatualizado.getId());

            assertThat(resultado)
                    .isNotNull()
                    .isInstanceOf(Entregador.class)
                    .usingRecursiveComparison()
                    .isEqualTo(entregadorDesatualizado);

            assertThat(resultado.getQuantidadeRecursosDisponiveis())
                    .isGreaterThan(entregadorDesatualizadoQuantidadeRecursos)
                    .isNotNull();
        }

        @Test
        void deveGerarExcecaoSeNaoEncontrarEntregador() {
            assertThatThrownBy(() -> desalocarRecursoEntregador.execute(1L))
                    .isInstanceOf(BusinessErrorException.class)
                    .message().isEqualTo("Entregador não localizado");
        }

    }
}
