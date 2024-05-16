package com.fase4.techchallenge.fiap.msgestaopedidos.usecase;

import com.fase4.techchallenge.fiap.msgestaopedidos.entity.gateway.PedidoGateway;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Pedido;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.exception.BussinessErrorException;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.pedido.EntregarPedido;
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
class EntregarPedidoIT {

    @Autowired
    EntregarPedido entregarPedido;
    @Autowired
    PedidoGateway pedidoGateway;

    @Test
    void devePermitirEntregarPedido() {
        long id = 1;
        Pedido pedido = PedidoHelper.gerarPedido();
        pedido.setStatus("EM_TRANSPORTE");
        pedidoGateway.create(pedido);
        var pedidoRetornado = entregarPedido.execute(pedido.getIdPedido());

        assertThat(pedidoRetornado).isInstanceOf(Pedido.class);
        assertThat(pedidoRetornado.getDataPagamento()).isNotNull();
        assertThat(pedidoRetornado.getStatus()).isEqualToIgnoringCase("ENTREGUE");
    }

    @Test
    void deveGerarExcecao_QuandoEntregarPedido_IdNaoCadastrado() {
        long id = 1;

        assertThatThrownBy(() -> entregarPedido.execute(id))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Pedido Informado não Cadastrado.");
    }

    @Test
    void deveGerarExcecao_QuandoEntregarPedido_PedidoEntregue() {
        Pedido pedido = PedidoHelper.gerarPedido();
        pedido.setStatus("ENTREGUE");
        pedido.setDataPagamento(LocalDateTime.now());
        pedido.setDataEntrega(LocalDateTime.now());
        pedidoGateway.create(pedido);

        assertThatThrownBy(() -> entregarPedido.execute(pedido.getIdPedido()))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Pedido Entregue.");
    }

    @Test
    void deveGerarExcecao_QuandoEntregarPedido_PedidoNaoTransporte() {
        Pedido pedido = PedidoHelper.gerarPedido();
        pedido.setStatus("EM_SEPARACAO");
        pedido.setDataPagamento(LocalDateTime.now());
        pedido.setDataEntrega(LocalDateTime.now());
        pedidoGateway.create(pedido);

        assertThatThrownBy(() -> entregarPedido.execute(pedido.getIdPedido()))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Pedido não está em Transporte.");
    }

}
