package com.fase4.techchallenge.fiap.msgestaopedidos.usecase.pedido;

import com.fase4.techchallenge.fiap.msgestaopedidos.entity.gateway.PedidoGateway;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Pedido;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.exception.EntityNotFoundException;
import com.fase4.techchallenge.fiap.msgestaopedidos.utils.PedidoHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class ObterPedidoPeloClienteTest {
    @Mock
    PedidoGateway pedidoGateway;
    ObtemPedidosPeloCliente obtemPedidosPeloCliente;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        obtemPedidosPeloCliente = new ObtemPedidosPeloCliente(pedidoGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirBuscarPedido() {
        Pedido pedido = PedidoHelper.gerarPedido();

        pedido.setIdPedido(1L);
        pedidoGateway.create(pedido);
        when(pedidoGateway.findByEmailCliente(anyString())).thenReturn(Arrays.asList(pedido));
        var pedidoRetornado = obtemPedidosPeloCliente.execute("john@email.com");

        assertThat(pedidoRetornado).isNotNull();
        assertThat(pedidoRetornado).hasSize(1);
        verify(pedidoGateway, times(1)).findByEmailCliente(anyString());
    }
}
