package com.fase4.techchallenge.fiap.msgestaopedidos.usecase;

import com.fase4.techchallenge.fiap.msgestaopedidos.entity.gateway.PedidoGateway;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Pedido;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.exception.EntityNotFoundException;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.pedido.ObterPedidoPeloId;
import com.fase4.techchallenge.fiap.msgestaopedidos.utils.PedidoHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;


class ObterPedidoPeloIdTest {
    @Mock
    PedidoGateway pedidoGateway;
    ObterPedidoPeloId obterPedidoPeloId;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        obterPedidoPeloId = new ObterPedidoPeloId(pedidoGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirBuscarPedido() {
        long id = 1;
        Pedido pedido = PedidoHelper.gerarPedido();
        pedido.setIdPedido(id);
        pedidoGateway.create(pedido);
        when(pedidoGateway.findById(id)).thenReturn(Optional.of(pedido));
        var pedidoRetornado = obterPedidoPeloId.execute(id);

        assertThat(pedidoRetornado).isInstanceOf(Pedido.class);
        assertThat(pedidoRetornado).isNotNull();
        assertThat(pedidoRetornado.getIdPedido()).isNotNull();
        assertThat(pedidoRetornado).isEqualTo(pedido);
        verify(pedidoGateway, times(1)).findById(any(Long.class));
    }

    @Test
    void deveGerarExcecao_QuandoBuscarPedido_IdNaoExiste() {
        long id = 1;

        when(pedidoGateway.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> obterPedidoPeloId.execute(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Pedido não localizado");
        verify(pedidoGateway, times(1)).findById(any(Long.class));

    }
}
