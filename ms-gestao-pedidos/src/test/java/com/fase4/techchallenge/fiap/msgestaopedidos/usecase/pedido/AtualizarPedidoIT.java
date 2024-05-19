package com.fase4.techchallenge.fiap.msgestaopedidos.usecase.pedido;

import com.fase4.techchallenge.fiap.msgestaopedidos.entity.gateway.PedidoGateway;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Pedido;
import com.fase4.techchallenge.fiap.msgestaopedidos.infrastructure.pedido.controller.dto.PedidoUpdateDTO;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.exception.BussinessErrorException;
import com.fase4.techchallenge.fiap.msgestaopedidos.utils.PedidoHelper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
class AtualizarPedidoIT {

    @Autowired
    AtualizarPedido atualizarPedido;

    @Autowired
    PedidoGateway pedidoGateway;

    @Test
    void devePermitirAlterarPedido() {
        Pedido pedido = PedidoHelper.gerarPedido();
        pedidoGateway.create(pedido);
        PedidoUpdateDTO pedidoUpdateDTO = new PedidoUpdateDTO(pedido.getEmailCliente()
                , pedido.getEndereco().getId()
                , 100.00
                , 9.58
                , "CARTAO_CREDITO"
                , pedido.getProdutos());

        var pedidoRetornado = atualizarPedido.execute(pedido.getIdPedido(), pedidoUpdateDTO);

        assertThat(pedidoRetornado).isNotNull();
        assertThat(pedidoRetornado.getIdPedido()).isEqualTo(pedido.getIdPedido());
    }

    @Test
    void deveGerarExcecao_QuandoAlterarPedido_IdNaoCadastrado() {
        long id = 1;

        assertThatThrownBy(() -> atualizarPedido.execute(id,new PedidoUpdateDTO("jony@email.com",10,220,12,"PIX",null)))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Pedido Informado não Cadastrado.");
    }

    @Test
    void deveGerarExcecao_QuandoAlterarPedido_CampoNaoPodeAlterar() {
        Pedido pedido = PedidoHelper.gerarPedido();
        pedido.setStatus("EM_SEPARACAO");
        pedidoGateway.create(pedido);
        PedidoUpdateDTO pedidoUpdateDTO = new PedidoUpdateDTO(pedido.getEmailCliente()
                , pedido.getEndereco().getId()
                , 100.00
                , 9.58
                , "CARTAO_CREDITO"
                , pedido.getProdutos());

        assertThatThrownBy(() -> atualizarPedido.execute(pedido.getIdPedido(), pedidoUpdateDTO))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Não pode ser alterado o meio de Pagamento do Pedido. Status:" + pedido.getStatus());
    }
}
