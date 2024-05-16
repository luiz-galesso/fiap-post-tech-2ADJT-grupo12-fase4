package com.fase4.techchallenge.fiap.msgestaopedidos.usecase;

import com.fase4.techchallenge.fiap.msgestaopedidos.entity.gateway.PedidoGateway;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Pedido;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.exception.BussinessErrorException;
import com.fase4.techchallenge.fiap.msgestaopedidos.utils.PedidoHelper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
class EntregarPedidoIT {

    @Autowired
    AprovarPagamento aprovarPagamento;
    @Autowired
    PedidoGateway pedidoGateway;

    @Test
    void devePermitirAprovarPagamentoPedido() {
        long id = 1;
        Pedido pedido = PedidoHelper.gerarPedido();
        pedidoGateway.create(pedido);
        var pedidoRetornado = aprovarPagamento.execute(id);

        assertThat(pedidoRetornado).isInstanceOf(Pedido.class);
        assertThat(pedidoRetornado.getDataPagamento()).isNotNull();
        assertThat(pedidoRetornado.getStatus()).isEqualToIgnoringCase("PAGAMENTO_APROVADO");
    }

    @Test
    void deveGerarExcecao_QuandoAprovarPagamentoPedido_IdNaoCadastrado() {
        long id = 1;

        assertThatThrownBy(() -> aprovarPagamento.execute(id))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Pedido Informado não Cadastrado.");
    }

    @Test
    void deveGerarExcecao_QuandoAprovarPagamentoPedido_PedidoPago() {
        Pedido pedido = PedidoHelper.gerarPedido();
        pedido.setStatus("PAGAMENTO_APROVADO");
        pedido.setDataPagamento(LocalDateTime.now());
        pedidoGateway.create(pedido);

        assertThatThrownBy(() -> aprovarPagamento.execute(pedido.getIdPedido()))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Pedido já Pago.");
    }

    @Test
    void deveGerarExcecao_QuandoAprovarPagamentoPedido_PedidoEntregue() {
        Pedido pedido = PedidoHelper.gerarPedido();
        pedido.setStatus("ENTREGUE");
        pedido.setDataPagamento(LocalDateTime.now());
        pedido.setDataEntrega(LocalDateTime.now());
        pedidoGateway.create(pedido);

        assertThatThrownBy(() -> aprovarPagamento.execute(pedido.getIdPedido()))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Pedido já Entregue.");
    }
}
