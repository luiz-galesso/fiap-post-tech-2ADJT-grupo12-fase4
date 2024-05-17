package com.fase4.techchallenge.fiap.msgestaopedidos.usecase.pedido;

import com.fase4.techchallenge.fiap.msgestaopedidos.entity.gateway.PedidoGateway;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Pedido;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.exception.EntityNotFoundException;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.pedido.ObterPedidoPeloId;
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
class ObterPedidoPeloIdIT {

    @Autowired
    ObterPedidoPeloId obterPedidoPeloId;
    @Autowired
    PedidoGateway pedidoGateway;

    @Test
    void devePermitirBuscarPedido() {
        Pedido pedido = PedidoHelper.gerarPedido();
        pedidoGateway.create(pedido);
        var pedidoRetornado = obterPedidoPeloId.execute(pedido.getIdPedido());

        assertThat(pedidoRetornado).isInstanceOf(Pedido.class);
        assertThat(pedidoRetornado).isNotNull();
        assertThat(pedidoRetornado.getIdPedido()).isNotNull();
        assertThat(pedidoRetornado).isEqualTo(pedido);
    }

    @Test
    void deveGerarExcecao_QuandoBuscarPedido_IdNaoExiste() {
        long id = 10;

        assertThatThrownBy(() -> obterPedidoPeloId.execute(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Pedido não localizado");
    }
}
