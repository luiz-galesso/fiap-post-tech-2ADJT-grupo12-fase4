package com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.entregador;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.model.Entregador;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entregador.controller.dto.EntregadorInsertDTO;
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
public class CadastrarEntregadorIT {
    @Autowired
    EntregadorRepository entregadorRepository;

    @Autowired
    CadastrarEntregador cadastrarEntregador;

    @Nested
    class CriacaoEntregador {
        @Test
        void deveExecutarCriacaoDeEntregador() {
            var entregador = EntregadorHelper.gerarEntregador(null);
            EntregadorInsertDTO entregadorInsertDTO = new EntregadorInsertDTO(entregador.getNome()
                    , entregador.getCnpj()
                    , entregador.getSituacao()
                    , entregador.getQuantidadeRecursosDisponiveis());

            var entregadorCriado = cadastrarEntregador.execute(entregadorInsertDTO);

            assertThat(entregadorCriado)
                    .isInstanceOf(Entregador.class)
                    .isNotNull();
            assertThat(entregadorCriado)
                    .usingRecursiveComparison()
                    .ignoringFields( "id")
                    .isEqualTo(entregador);
            assertThat(entregadorCriado.getId())
                    .isNotNull();

        }
        @Test
        void deveCadastrarEntregadorAtivoQuandoSituacaoNula() {
            var entregador = EntregadorHelper.gerarEntregador(null);
            EntregadorInsertDTO entregadorInsertDTO = new EntregadorInsertDTO(entregador.getNome()
                    , entregador.getCnpj()
                    , null
                    , entregador.getQuantidadeRecursosDisponiveis());

            var entregadorCriado = cadastrarEntregador.execute(entregadorInsertDTO);

            assertThat(entregadorCriado.getSituacao())
                    .isEqualTo("ATIVO");

        }
        @Test
        void deveCadastrarEntregadorAtivoQuandoSituacaoDiferenteDeAtivo() {
            var entregador = EntregadorHelper.gerarEntregador(null);
            EntregadorInsertDTO entregadorInsertDTO = new EntregadorInsertDTO(entregador.getNome()
                    , entregador.getCnpj()
                    , "INATIVO"
                    , entregador.getQuantidadeRecursosDisponiveis());

            var entregadorCriado = cadastrarEntregador.execute(entregadorInsertDTO);

            assertThat(entregadorCriado.getSituacao())
                    .isEqualTo("ATIVO")
                    .isNotEqualTo(entregadorInsertDTO.getSituacao());
        }
        @Test
        void deveGerarExcecaoQuandoQuantidadeDeRecursosMenorOuIgualAZero(){
            var entregador = EntregadorHelper.gerarEntregador(null);
            EntregadorInsertDTO entregadorInsertDTO = new EntregadorInsertDTO(entregador.getNome()
                    , entregador.getCnpj()
                    , entregador.getSituacao()
                    , 0L);

            assertThatThrownBy(() -> cadastrarEntregador.execute(entregadorInsertDTO))
                    .isInstanceOf(BusinessErrorException.class)
                    .hasMessage("Quantidade de Recursos deve ser maior que zero");

        }
        @Test
        void deveGerarExcecaoQuandoJaExistirCnpjCadastrado(){
            var entregador = EntregadorHelper.gerarEntregador(null);
            EntregadorInsertDTO entregadorInsertDTO = new EntregadorInsertDTO(entregador.getNome()
                    , entregador.getCnpj()
                    , entregador.getSituacao()
                    , entregador.getQuantidadeRecursosDisponiveis());

            entregador = cadastrarEntregador.execute(entregadorInsertDTO);

            assertThatThrownBy(() -> cadastrarEntregador.execute(entregadorInsertDTO))
                    .isInstanceOf(BusinessErrorException.class)
                    .hasMessage("Já existe um entregador com o cnpj informado");

        }
    }

}
