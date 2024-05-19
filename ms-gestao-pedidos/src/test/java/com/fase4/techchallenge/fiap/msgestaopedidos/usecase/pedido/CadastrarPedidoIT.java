package com.fase4.techchallenge.fiap.msgestaopedidos.usecase.pedido;

import com.fase4.techchallenge.fiap.msgestaopedidos.entity.gateway.PedidoGateway;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Pedido;
import com.fase4.techchallenge.fiap.msgestaopedidos.infrastructure.pedido.controller.dto.PedidoInsertDTO;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.cliente.ObtemClientePeloEmail;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.endereco.ObtemEnderecoPeloIdClienteEIdEndereco;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.produto.ConsomeEstoques;
import com.fase4.techchallenge.fiap.msgestaopedidos.utils.PedidoHelper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
class CadastrarPedidoIT {

    @Autowired
    CadastrarPedido cadastrarPedido;

    @Autowired
    PedidoGateway pedidoGateway;

    @Test
    void devePermitirBuscarPedido() {
        Pedido pedido = PedidoHelper.gerarPedido();
        pedidoGateway.create(pedido);
        PedidoInsertDTO pedidoInsertDTO = new PedidoInsertDTO(pedido.getEmailCliente(), pedido.getIdEnderecoCliente(), pedido.getValorFrete(), pedido.getMeioPagamento(), pedido.getProdutos());

        var pedidoRetornado = cadastrarPedido.execute(pedidoInsertDTO);

        assertThat(pedidoRetornado).isNotNull();
    }

}
