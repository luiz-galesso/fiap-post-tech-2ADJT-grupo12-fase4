package com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.entregador;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.gateway.EntregadorGateway;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.model.Entregador;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entregador.controller.dto.EntregadorInsertDTO;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entregador.utils.EntregadorHelper;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.exception.BusinessErrorException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CadastrarEntregadorTest {
    @Mock
    EntregadorGateway entregadorGateway;
    AutoCloseable openMocks;
    private CadastrarEntregador cadastrarEntregador;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        cadastrarEntregador = new CadastrarEntregador(entregadorGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class CriacaoEntregador {
        @Test
        void deveExecutarCriacaoDeEntregador() {
            Entregador entregador = EntregadorHelper.gerarEntregador(null);
            var entregadorInsertDTO = new EntregadorInsertDTO(entregador.getNome(),
                    entregador.getCnpj(),
                    entregador.getSituacao(),
                    entregador.getQuantidadeRecursosDisponiveis());

            when(entregadorGateway.create(any(Entregador.class))).thenReturn(entregador);

            var entregadorInserido = cadastrarEntregador.execute(entregadorInsertDTO);

            assertThat(entregadorInserido)
                    .isNotNull()

                    .isInstanceOf(Entregador.class);

            assertThat(entregadorInserido)
                    .usingRecursiveComparison()
                    .isEqualTo(entregador);

            verify(entregadorGateway, times(1)).create(any(Entregador.class));
        }

        @Test
        void deveCadastrarEntregadorAtivoQuandoSituacaoNula() {
            Entregador entregador = EntregadorHelper.gerarEntregador(null);

            var entregadorInsertDTO = new EntregadorInsertDTO(entregador.getNome(),
                    entregador.getCnpj(),
                    null,
                    entregador.getQuantidadeRecursosDisponiveis());

            when(entregadorGateway.create(any(Entregador.class))).thenReturn(entregador);

            var entregadorInserido = cadastrarEntregador.execute(entregadorInsertDTO);

            assertThat(entregadorInserido.getSituacao()).isEqualTo("ATIVO");
            assertThat(entregadorInsertDTO.getSituacao()).isNotEqualTo(entregadorInserido.getSituacao());

        }

        @Test
        void deveCadastrarEntregadorAtivoQuandoSituacaoDiferenteDeAtivo() {
            Entregador entregador = EntregadorHelper.gerarEntregador(null);

            var entregadorInsertDTO = new EntregadorInsertDTO(entregador.getNome(),
                    entregador.getCnpj(),
                    "INATIVO",
                    entregador.getQuantidadeRecursosDisponiveis());

            when(entregadorGateway.create(any(Entregador.class))).thenReturn(entregador);

            var entregadorInserido = cadastrarEntregador.execute(entregadorInsertDTO);

            assertThat(entregadorInserido.getSituacao()).isEqualTo("ATIVO");
            assertThat(entregadorInsertDTO.getSituacao()).isNotEqualTo(entregadorInserido.getSituacao());
        }

        @Test
        void deveGerarExcecaoQuandoQuantidadeDeRecursosMenorOuIgualAZero() {
            Entregador entregador = EntregadorHelper.gerarEntregador(null);

            var entregadorInsertDTO = new EntregadorInsertDTO(entregador.getNome(),
                    entregador.getCnpj(),
                    "INATIVO",
                    0L);
            assertThatThrownBy(() -> cadastrarEntregador.execute(entregadorInsertDTO))
                    .isInstanceOf(BusinessErrorException.class)
                    .message().isEqualTo("Quantidade de Recursos deve ser maior que zero");

        }

        @Test
        void deveGerarExcecaoQuandoJaExistirCnpjCadastrado() {
            Entregador entregador = EntregadorHelper.gerarEntregador(null);

            var entregadorInsertDTO = new EntregadorInsertDTO(entregador.getNome(),
                    entregador.getCnpj(),
                    "INATIVO",
                    entregador.getQuantidadeRecursosDisponiveis());

            when(entregadorGateway.findByCnpj(any(Long.class))).thenReturn(Optional.of(entregador));
            assertThatThrownBy(() -> cadastrarEntregador.execute(entregadorInsertDTO))
                    .isInstanceOf(BusinessErrorException.class)
                    .message().isEqualTo("Já existe um entregador com o cnpj informado");

        }
    }

}
