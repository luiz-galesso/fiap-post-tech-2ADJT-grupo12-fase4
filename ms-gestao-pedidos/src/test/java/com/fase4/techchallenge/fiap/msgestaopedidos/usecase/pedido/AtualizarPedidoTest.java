package com.fase4.techchallenge.fiap.msgestaopedidos.usecase.pedido;

import com.fase4.techchallenge.fiap.msgestaopedidos.entity.gateway.PedidoGateway;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Pedido;
import com.fase4.techchallenge.fiap.msgestaopedidos.infrastructure.pedido.controller.dto.PedidoUpdateDTO;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.exception.BussinessErrorException;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.exception.EntityNotFoundException;
import com.fase4.techchallenge.fiap.msgestaopedidos.utils.PedidoHelper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AtualizarPedidoTest {

    @Mock
    PedidoGateway pedidoGateway;
    AtualizarPedido atualizarPedido;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        atualizarPedido = new AtualizarPedido(pedidoGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirAlterarPedido() {
        Pedido pedido = PedidoHelper.gerarPedido();
        pedido.setIdPedido(1L);

        when(pedidoGateway.findById(any(Long.class))).thenReturn(Optional.of(pedido));
        when(pedidoGateway.update(any(Pedido.class))).thenReturn(pedido);
        PedidoUpdateDTO pedidoUpdateDTO = new PedidoUpdateDTO(pedido.getEmailCliente()
                , pedido.getIdEnderecoCliente()
                , 100.00
                , 9.58
                , "CARTAO_CREDITO"
                , pedido.getProdutos());

        var pedidoRetornado = atualizarPedido.execute(pedido.getIdPedido(), pedidoUpdateDTO);

        assertThat(pedidoRetornado).isNotNull();
        assertThat(pedidoRetornado.getIdPedido()).isEqualTo(pedido.getIdPedido());
        verify(pedidoGateway, times(1)).findById(any(Long.class));
        verify(pedidoGateway, times(1)).update(any(Pedido.class));
    }

    @Test
    void deveGerarExcecao_QuandoAlterarPedido_IdNaoCadastrado() {
        long id = 1;
        when(pedidoGateway.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> atualizarPedido.execute(id, new PedidoUpdateDTO("jony@email.com", 10, 220, 12, "PIX", null)))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Pedido Informado não Cadastrado.");
        verify(pedidoGateway, times(1)).findById(any(Long.class));
        verify(pedidoGateway, never()).update(any(Pedido.class));
    }

    @Test
    void deveGerarExcecao_QuandoAlterarPedido_CampoNaoPodeAlterar() {
        Pedido pedido = PedidoHelper.gerarPedido();
        pedido.setIdPedido(1L);
        pedido.setStatus("EM_SEPARACAO");

        when(pedidoGateway.findById(any(Long.class))).thenReturn(Optional.of(pedido));
        when(pedidoGateway.update(any(Pedido.class))).thenReturn(pedido);
        PedidoUpdateDTO pedidoUpdateDTO = new PedidoUpdateDTO(pedido.getEmailCliente()
                , pedido.getIdEnderecoCliente()
                , 100.00
                , 9.58
                , "CARTAO_CREDITO"
                , pedido.getProdutos());

        assertThatThrownBy(() -> atualizarPedido.execute(pedido.getIdPedido(), pedidoUpdateDTO))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Não pode ser alterado o meio de Pagamento do Pedido. Status:" + pedido.getStatus());

        verify(pedidoGateway, times(1)).findById(any(Long.class));
        verify(pedidoGateway, never()).update(any(Pedido.class));
    }
}
