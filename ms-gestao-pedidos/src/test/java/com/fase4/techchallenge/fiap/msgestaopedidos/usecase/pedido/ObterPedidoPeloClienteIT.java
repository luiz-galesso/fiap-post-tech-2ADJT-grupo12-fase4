package com.fase4.techchallenge.fiap.msgestaopedidos.usecase.pedido;

import com.fase4.techchallenge.fiap.msgestaopedidos.entity.gateway.PedidoGateway;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Pedido;
import com.fase4.techchallenge.fiap.msgestaopedidos.utils.PedidoHelper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
class ObterPedidoPeloClienteIT {

    @Autowired
    ObtemPedidosPeloCliente obtemPedidosPeloCliente;
    @Autowired
    PedidoGateway pedidoGateway;

    @Test
    void devePermitirBuscarPedido() {
        Pedido pedido = PedidoHelper.gerarPedido();
        pedidoGateway.create(pedido);
        var pedidoRetornado = obtemPedidosPeloCliente.execute(pedido.getEmailCliente());

        assertThat(pedidoRetornado).isNotNull();
        assertThat(pedidoRetornado).hasSize(1);
        assertThat(pedidoRetornado.get(0).getIdPedido().equals(pedido.getIdPedido()));
    }
}
