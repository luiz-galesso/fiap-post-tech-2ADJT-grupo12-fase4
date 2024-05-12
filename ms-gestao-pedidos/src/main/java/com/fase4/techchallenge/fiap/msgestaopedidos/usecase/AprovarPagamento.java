package com.fase4.techchallenge.fiap.msgestaopedidos.usecase;

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
public class AprovarPagamento {
    private final PedidoGateway pedidoGateway;

    public Pedido execute(Long id) {

        Optional<Pedido> pedidoOptional = pedidoGateway.findById(id);

        if (pedidoOptional.isEmpty()) {
            throw new BussinessErrorException("Pedido Informado não Cadastrado.");
        }

        if (pedidoOptional.get().getStatus().equalsIgnoreCase("PAGAMENTO_APROVADO")) {
            throw new BussinessErrorException("Pedido já Pago.");
        } else if (pedidoOptional.get().getStatus().equalsIgnoreCase("ENTREGUE")) {
            throw new BussinessErrorException("Pedido já Entregue.");
        }

        Pedido pedido = new Pedido(pedidoOptional.get().getIdPedido(),
                PedidoStatus.PAGAMENTO_APROVADO.toString(),
                pedidoOptional.get().getMeioPagamento(),
                pedidoOptional.get().getValorPedido(),
                pedidoOptional.get().getCliente(),
                pedidoOptional.get().getProdutos(),
                LocalDateTime.now(),
                null,
                pedidoOptional.get().getDataCriacao());

        return this.pedidoGateway.update(pedido);
    }

}