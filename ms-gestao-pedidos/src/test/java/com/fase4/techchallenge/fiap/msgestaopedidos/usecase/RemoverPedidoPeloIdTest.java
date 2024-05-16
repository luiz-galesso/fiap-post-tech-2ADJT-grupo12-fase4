package com.fase4.techchallenge.fiap.msgestaopedidos.usecase;

import com.fase4.techchallenge.fiap.msgestaopedidos.entity.gateway.PedidoGateway;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Pedido;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.exception.BussinessErrorException;
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


class RemoverPedidoPeloIdTest {
    @Mock
    PedidoGateway pedidoGateway;
    RemoverPedidoPeloId removerPedidoPeloId;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        removerPedidoPeloId = new RemoverPedidoPeloId(pedidoGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirRemoverPedido() {
        long id = 1;
        Pedido pedido = PedidoHelper.gerarPedido();
        pedido.setIdPedido(id);
        pedidoGateway.create(pedido);
        when(pedidoGateway.findById(id)).thenReturn(Optional.of(pedido));
        doNothing().when(pedidoGateway).remove(id);

        var retorno = removerPedidoPeloId.execute(id);

        assertThat(retorno).isTrue();
        verify(pedidoGateway, times(1)).findById(any(Long.class));
        verify(pedidoGateway, times(1)).remove(any(Long.class));
    }

    @Test
    void deveGerarExcecao_QuandoRemoverPedido_IdNaoExiste() {
        long id = 1;
        when(pedidoGateway.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> removerPedidoPeloId.execute(id))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Pedido Informado não Cadastrado.");
        verify(pedidoGateway, times(1)).findById(any(Long.class));
        verify(pedidoGateway, never()).remove(any(Long.class));
    }
}
