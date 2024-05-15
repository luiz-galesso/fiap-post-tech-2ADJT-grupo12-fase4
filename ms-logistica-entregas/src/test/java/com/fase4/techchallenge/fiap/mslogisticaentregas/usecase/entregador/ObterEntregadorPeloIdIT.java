package com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.entregador;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.model.Entregador;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entregador.repository.EntregadorRepository;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entregador.utils.EntregadorHelper;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.exception.EntityNotFoundException;
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
public class ObterEntregadorPeloIdIT {
    @Autowired
    EntregadorRepository entregadorRepository;

    @Autowired
    ObterEntregadorPeloId obterEntregadorPeloId;

    @Nested
    class ObterEntregador {
        @Test
        void deveObterEntregadorPeloId() {
            var entregador = EntregadorHelper.registrarEntregador(entregadorRepository, EntregadorHelper.gerarEntregador(null));

            var entregadorObtido = obterEntregadorPeloId.execute(entregador.getId());

            assertThat(entregadorObtido)
                    .isNotNull()
                    .isInstanceOf(Entregador.class)
                    .usingRecursiveComparison()
                    .isEqualTo(entregador);
        }

        @Test
        void deveGerarErroQuandoNaoEncontrarEntregadorPorId(){
            assertThatThrownBy(() -> obterEntregadorPeloId.execute(-1L))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage("Entregador não localizado");
        }

    }
}
