package com.fase4.techchallenge.fiap.msgestaopedidos.usecase.pedido;

import com.fase4.techchallenge.fiap.msgestaopedidos.entity.enums.PedidoStatus;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.gateway.PedidoGateway;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Cliente;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Endereco;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Pedido;
import com.fase4.techchallenge.fiap.msgestaopedidos.infrastructure.feign.dto.ProdutoEstoqueDTO;
import com.fase4.techchallenge.fiap.msgestaopedidos.infrastructure.pedido.controller.dto.PedidoInsertDTO;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.cliente.ObtemClientePeloEmail;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.endereco.ObtemEnderecoPeloIdClienteEIdEndereco;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.produto.ConsomeEstoques;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CadastrarPedido {

    private final PedidoGateway pedidoGateway;
    private final ObtemClientePeloEmail obtemClientePeloEmail;
    private final ObtemEnderecoPeloIdClienteEIdEndereco obtemEnderecoPeloIdClienteEIdEndereco;
    private final ConsomeEstoques consomeEstoques;

    public Pedido execute(PedidoInsertDTO pedidoInsertDTO) {

        Cliente cliente = obtemClientePeloEmail.execute(pedidoInsertDTO.emailCliente());

        Endereco endereco = obtemEnderecoPeloIdClienteEIdEndereco.execute(cliente.getEmail(), pedidoInsertDTO.idEndereco());

        List<ProdutoEstoqueDTO> produtoEstoqueList = new ArrayList<>();
        final double[] valorTotalPedido = {0L};

        pedidoInsertDTO.produtos().forEach( p -> {
            valorTotalPedido[0] = valorTotalPedido[0] + (p.getValorUnitario() * p.getQuantidade());
            produtoEstoqueList.add(new ProdutoEstoqueDTO(p.getId(),p.getQuantidade()));
        });

        valorTotalPedido[0] = valorTotalPedido[0] + pedidoInsertDTO.valorFrete();

        consomeEstoques.execute(produtoEstoqueList);

        Pedido pedido = new Pedido(cliente.getEmail(),
                endereco,
                pedidoInsertDTO.produtos(),
                valorTotalPedido[0],
                pedidoInsertDTO.valorFrete(),
                PedidoStatus.GERADO.toString(),
                pedidoInsertDTO.meioPagamento(),
                LocalDateTime.now());

        return this.pedidoGateway.create(pedido);
    }

}
