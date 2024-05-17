package com.fase4.techchallenge.fiap.msgestaopedidos.usecase.pedido;

import com.fase4.techchallenge.fiap.msgestaopedidos.entity.enums.PedidoStatus;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.gateway.PedidoGateway;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Pedido;
import com.fase4.techchallenge.fiap.msgestaopedidos.infrastructure.feign.dto.ProdutoEstoqueDTO;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.exception.BussinessErrorException;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.produto.AumentaEstoques;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CancelaPedido {

    private final PedidoGateway pedidoGateway;
    private final AumentaEstoques aumentaEstoques;

    public Pedido execute(Long id) {

        List<ProdutoEstoqueDTO> produtoEstoqueList = new ArrayList<>();

        Pedido pedido = pedidoGateway.findById(id).orElseThrow(() -> new BussinessErrorException("Pedido Informado não Cadastrado."));

        pedido.getProdutos().forEach( p -> {
            produtoEstoqueList.add(new ProdutoEstoqueDTO(p.getId(),p.getQuantidade()));
        });

        aumentaEstoques.execute(produtoEstoqueList);

        pedido.setStatus(PedidoStatus.CANCELADO.toString());

        return this.pedidoGateway.update(pedido);
    }
}
