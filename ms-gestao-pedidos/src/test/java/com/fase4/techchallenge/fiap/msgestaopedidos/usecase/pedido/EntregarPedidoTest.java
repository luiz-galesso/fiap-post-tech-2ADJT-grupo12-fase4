package com.fase4.techchallenge.fiap.msgestaopedidos.usecase.pedido;

import com.fase4.techchallenge.fiap.msgestaopedidos.entity.gateway.PedidoGateway;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Pedido;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.exception.BussinessErrorException;
import com.fase4.techchallenge.fiap.msgestaopedidos.utils.PedidoHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;


class EntregarPedidoTest {

    @Mock
    PedidoGateway pedidoGateway;
    EntregarPedido entregarPedido;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        entregarPedido = new EntregarPedido(pedidoGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirEntregarPedidoPedido() {
        Pedido pedido = PedidoHelper.gerarPedido();
        pedido.setIdPedido(1L);
        pedido.setStatus("EM_TRANSPORTE");

        when(pedidoGateway.findById(any(Long.class))).thenReturn(Optional.of(pedido));
        when(pedidoGateway.update(any(Pedido.class))).thenReturn(pedido);

        var pedidoRetornado = entregarPedido.execute(pedido.getIdPedido());
        pedido.setStatus("ENTREGUE");

        assertThat(pedidoRetornado).isNotNull();
        assertThat(pedidoRetornado.getIdPedido()).isEqualTo(pedido.getIdPedido());
        assertThat(pedidoRetornado.getStatus()).isEqualToIgnoringCase("ENTREGUE");
        verify(pedidoGateway, times(1)).findById(any(Long.class));
        verify(pedidoGateway, times(1)).update(any(Pedido.class));
    }

    @Test
    void deveGerarExcecao_QuandoEntregarPedidoPedido_IdNaoCadastrado() {
        long id = 10;
        when(pedidoGateway.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> entregarPedido.execute(id))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Pedido Informado não Cadastrado.");
        verify(pedidoGateway, times(1)).findById(any(Long.class));
        verify(pedidoGateway, never()).update(any(Pedido.class));
    }

    @Test
    void deveGerarExcecao_QuandoEntregarPedidoPedido_PedidoPago() {
        Pedido pedido = PedidoHelper.gerarPedido();
        pedido.setIdPedido(10L);
        pedido.setStatus("ENTREGUE");
        pedido.setDataEntrega(LocalDateTime.now());

        when(pedidoGateway.findById(any(Long.class))).thenReturn(Optional.of(pedido));
        when(pedidoGateway.update(any(Pedido.class))).thenReturn(pedido);

        assertThatThrownBy(() -> entregarPedido.execute(pedido.getIdPedido()))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Pedido Entregue.");
        verify(pedidoGateway, times(1)).findById(any(Long.class));
        verify(pedidoGateway, never()).update(any(Pedido.class));
    }

    @Test
    void deveGerarExcecao_QuandoEntregarPedidoPedido_PedidoEntregue() {
        Pedido pedido = PedidoHelper.gerarPedido();
        pedido.setIdPedido(10L);
        pedido.setStatus("EM_SEPARACAO");

        when(pedidoGateway.findById(any(Long.class))).thenReturn(Optional.of(pedido));
        when(pedidoGateway.update(any(Pedido.class))).thenReturn(pedido);

        assertThatThrownBy(() -> entregarPedido.execute(pedido.getIdPedido()))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Pedido não está em Transporte.");
        verify(pedidoGateway, times(1)).findById(any(Long.class));
        verify(pedidoGateway, never()).update(any(Pedido.class));
    }
}
