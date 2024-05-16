package com.fase4.techchallenge.fiap.msgestaopedidos.usecase;

import com.fase4.techchallenge.fiap.msgestaopedidos.entity.gateway.PedidoGateway;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Pedido;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.exception.BussinessErrorException;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.pedido.RemoverPedidoPeloId;
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
class RemoverPedidoPeloIdIT {

    @Autowired
    RemoverPedidoPeloId removerPedidoPeloId;
    @Autowired
    PedidoGateway pedidoGateway;

    @Test
    void devePermitirRemoverPedido() {
        Pedido pedido = PedidoHelper.gerarPedido();
        pedidoGateway.create(pedido);

        var retorno = removerPedidoPeloId.execute(pedido.getIdPedido());
        assertThat(retorno).isTrue();
    }

    @Test
    void deveGerarExcecao_QuandoRemoverPedido_IdNaoExiste() {
        long id = 1;
        assertThatThrownBy(() -> removerPedidoPeloId.execute(id))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Pedido Informado não Cadastrado.");
    }
}
