package com.fase4.techchallenge.fiap.msgestaopedidos.usecase.pedido;

import com.fase4.techchallenge.fiap.msgestaopedidos.entity.gateway.PedidoGateway;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Pedido;
import com.fase4.techchallenge.fiap.msgestaopedidos.infrastructure.pedido.controller.dto.PedidoInsertDTO;
import com.fase4.techchallenge.fiap.msgestaopedidos.infrastructure.pedido.controller.dto.PedidoUpdateDTO;
import com.fase4.techchallenge.fiap.msgestaopedidos.infrastructure.pedido.repository.PedidoRepository;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.exception.BussinessErrorException;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.pedido.AprovarPagamento;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.pedido.CadastrarPedido;
import com.fase4.techchallenge.fiap.msgestaopedidos.utils.PedidoHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class AprovarPagamentoTest {

    @Mock
    PedidoGateway pedidoGateway;
    AprovarPagamento aprovarPagamento;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        aprovarPagamento = new AprovarPagamento(pedidoGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirAprovarPagamentoPedido() {
        Pedido pedido = PedidoHelper.gerarPedido();
        pedido.setIdPedido(1L);

        when(pedidoGateway.findById(any(Long.class))).thenReturn(Optional.of(pedido));
        when(pedidoGateway.update(any(Pedido.class))).thenReturn(pedido);

        var pedidoRetornado = aprovarPagamento.execute(pedido.getIdPedido());
        pedidoRetornado.setStatus("PAGAMENTO_APROVADO");

        assertThat(pedidoRetornado).isNotNull();
        assertThat(pedidoRetornado.getIdPedido()).isEqualTo(pedido.getIdPedido());
        assertThat(pedidoRetornado.getStatus()).isEqualToIgnoringCase("PAGAMENTO_APROVADO");
        verify(pedidoGateway, times(1)).findById(any(Long.class));
        verify(pedidoGateway, times(1)).update(any(Pedido.class));
    }

    @Test
    void deveGerarExcecao_QuandoAprovarPagamentoPedido_IdNaoCadastrado() {
        long id = 10;
        when(pedidoGateway.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> aprovarPagamento.execute(id))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Pedido Informado não Cadastrado.");
        verify(pedidoGateway, times(1)).findById(any(Long.class));
        verify(pedidoGateway, never()).update(any(Pedido.class));
    }

    @Test
    void deveGerarExcecao_QuandoAprovarPagamentoPedido_PedidoPago() {
        Pedido pedido = PedidoHelper.gerarPedido();
        pedido.setIdPedido(10L);
        pedido.setStatus("PAGAMENTO_APROVADO");
        pedido.setDataPagamento(LocalDateTime.now());

        when(pedidoGateway.findById(any(Long.class))).thenReturn(Optional.of(pedido));
        when(pedidoGateway.update(any(Pedido.class))).thenReturn(pedido);

        assertThatThrownBy(() -> aprovarPagamento.execute(pedido.getIdPedido()))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Pedido Pago.");
        verify(pedidoGateway, times(1)).findById(any(Long.class));
        verify(pedidoGateway, never()).update(any(Pedido.class));
    }

    @Test
    void deveGerarExcecao_QuandoAprovarPagamentoPedido_PedidoEntregue() {
        Pedido pedido = PedidoHelper.gerarPedido();
        pedido.setIdPedido(10L);
        pedido.setStatus("ENTREGUE");
        pedido.setDataPagamento(LocalDateTime.now());
        pedido.setDataEntrega(LocalDateTime.now());

        when(pedidoGateway.findById(any(Long.class))).thenReturn(Optional.of(pedido));
        when(pedidoGateway.update(any(Pedido.class))).thenReturn(pedido);

        assertThatThrownBy(() -> aprovarPagamento.execute(pedido.getIdPedido()))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Pedido Entregue.");
        verify(pedidoGateway, times(1)).findById(any(Long.class));
        verify(pedidoGateway, never()).update(any(Pedido.class));
    }
}
