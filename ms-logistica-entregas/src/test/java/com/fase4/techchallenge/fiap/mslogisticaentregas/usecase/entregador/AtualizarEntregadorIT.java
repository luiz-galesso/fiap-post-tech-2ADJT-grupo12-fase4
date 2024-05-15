package com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.entregador;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.model.Entregador;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entregador.controller.dto.EntregadorUpdateDTO;
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
public class AtualizarEntregadorIT {
    @Autowired
    EntregadorRepository entregadorRepository;

    @Autowired
    AtualizarEntregador atualizarEntregador;

    @Nested
    class AtualizarEntregadorTeste {
        @Test
        void deveAtualizarEntregador() {
            var entregadorDesatualizado = EntregadorHelper.registrarEntregador(entregadorRepository, EntregadorHelper.gerarEntregador(null));
            var entregadorDesatualizadoSituacao = entregadorDesatualizado.getSituacao();
            var entregadorUpdateDTO = new EntregadorUpdateDTO(entregadorDesatualizado.getNome()
                    , entregadorDesatualizado.getCnpj()
                    , "INATIVO"
            );

            var resultado = atualizarEntregador.execute(entregadorDesatualizado.getId(), entregadorUpdateDTO);

            assertThat(resultado)
                    .isNotNull()
                    .isInstanceOf(Entregador.class)
                    .usingRecursiveComparison()
                    .isEqualTo(entregadorDesatualizado);

            assertThat(resultado.getSituacao())
                    .isNotEqualTo(entregadorDesatualizadoSituacao)
                    .isNotNull();
        }

        @Test
        void deveGerarExcecaoSeNaoEncontrarEntregador() {

            var entregadorDesatualizado = EntregadorHelper.gerarEntregador(1L);
            var entregadorUpdateDTO = new EntregadorUpdateDTO(entregadorDesatualizado.getNome()
                    , entregadorDesatualizado.getCnpj()
                    , entregadorDesatualizado.getSituacao()
            );

            assertThatThrownBy(() -> atualizarEntregador.execute(1L, entregadorUpdateDTO))
                    .isInstanceOf(BusinessErrorException.class)
                    .message().isEqualTo("Não foi encontrado o entregador com o Id informado");
        }

    }

}
