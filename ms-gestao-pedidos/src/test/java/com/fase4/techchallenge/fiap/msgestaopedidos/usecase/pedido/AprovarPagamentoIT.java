package com.fase4.techchallenge.fiap.msgestaopedidos.usecase.pedido;

import com.fase4.techchallenge.fiap.msgestaopedidos.entity.gateway.PedidoGateway;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Pedido;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.exception.BussinessErrorException;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.pedido.AprovarPagamento;
import com.fase4.techchallenge.fiap.msgestaopedidos.utils.PedidoHelper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
class AprovarPagamentoIT {

    @Autowired
    AprovarPagamento aprovarPagamento;
    @Autowired
    PedidoGateway pedidoGateway;

    @Test
    void devePermitirAprovarPagamentoPedido() {
        Pedido pedido = PedidoHelper.gerarPedido();
        pedidoGateway.create(pedido);
        var pedidoRetornado = aprovarPagamento.execute(pedido.getIdPedido());

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
                .hasMessage("Pedido Pago.");
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
                .hasMessage("Pedido Entregue.");
    }
}
