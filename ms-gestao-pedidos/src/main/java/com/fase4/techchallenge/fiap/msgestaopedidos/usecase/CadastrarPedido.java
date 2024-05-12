package com.fase4.techchallenge.fiap.msgestaopedidos.usecase;

import com.fase4.techchallenge.fiap.msgestaopedidos.entity.enums.PedidoStatus;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.gateway.PedidoGateway;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Pedido;
import com.fase4.techchallenge.fiap.msgestaopedidos.infrastructure.dto.ClienteEnderecoDTO;
import com.fase4.techchallenge.fiap.msgestaopedidos.infrastructure.pedido.controller.dto.PedidoInsertDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CadastrarPedido {

    private final PedidoGateway pedidoGateway;
    private final CallApiClientes callApiClientes;
    private final CallApiProdutos callApiProdutos;

    public Pedido execute(PedidoInsertDTO pedidoInsertDTO) {
        ClienteEnderecoDTO clienteEndereco = callApiClientes.retornaDadosCliente(pedidoInsertDTO.emailCliente(), pedidoInsertDTO.idEndereco());
        callApiProdutos.validaProdutoAjustaEstoque(pedidoInsertDTO.produtos());

        Pedido pedido = new Pedido(clienteEndereco.getCliente(),
                pedidoInsertDTO.produtos(),
                pedidoInsertDTO.valorPedido(),
                PedidoStatus.GERADO.toString(),
                pedidoInsertDTO.meioPagamento(),
                LocalDateTime.now());
        return this.pedidoGateway.create(pedido);
    }

}
