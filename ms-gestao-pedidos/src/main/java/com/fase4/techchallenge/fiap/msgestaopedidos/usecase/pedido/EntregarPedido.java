package com.fase4.techchallenge.fiap.msgestaopedidos.usecase.pedido;

import com.fase4.techchallenge.fiap.msgestaopedidos.entity.enums.PedidoStatus;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.gateway.PedidoGateway;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Pedido;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.exception.BussinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EntregarPedido {
    private final PedidoGateway pedidoGateway;

    public Pedido execute(Long id) {

        Optional<Pedido> pedidoOptional = pedidoGateway.findById(id);

        if (pedidoOptional.isEmpty()) {
            throw new BussinessErrorException("Pedido Informado não Cadastrado.");
        }

        if (pedidoOptional.get().getStatus().equalsIgnoreCase("ENTREGUE")) {
            throw new BussinessErrorException("Pedido Entregue.");
        } else if (!pedidoOptional.get().getStatus().equalsIgnoreCase("EM_TRANSPORTE")) {
            throw new BussinessErrorException("Pedido não está em Transporte.");
        }

        Pedido pedido = new Pedido(pedidoOptional.get().getIdPedido(),
                pedidoOptional.get().getEmailCliente(),
                pedidoOptional.get().getIdEnderecoCliente(),
                pedidoOptional.get().getProdutos(),
                pedidoOptional.get().getValorPedido(),
                pedidoOptional.get().getValorFrete(),
                PedidoStatus.ENTREGUE.toString(),
                pedidoOptional.get().getMeioPagamento(),
                pedidoOptional.get().getDataCriacao(),
                pedidoOptional.get().getDataPagamento(),
                LocalDateTime.now());

        return this.pedidoGateway.update(pedido);
    }
}
