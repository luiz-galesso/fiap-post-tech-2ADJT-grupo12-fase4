package com.fase4.techchallenge.fiap.msgestaopedidos.usecase.logistica;

import com.fase4.techchallenge.fiap.msgestaopedidos.entity.enums.PedidoStatus;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.gateway.PedidoGateway;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Pedido;
import com.fase4.techchallenge.fiap.msgestaopedidos.infrastructure.feign.LogisticaClient;
import com.fase4.techchallenge.fiap.msgestaopedidos.infrastructure.feign.dto.EnderecoDestinoDTO;
import com.fase4.techchallenge.fiap.msgestaopedidos.infrastructure.feign.dto.EntregaDTO;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.exception.BussinessErrorException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EnviaPedidosPagamentoAprovado {

    private final LogisticaClient logisticaClient;

    private final PedidoGateway pedidoGateway;

    public void execute() {
        try {
            List<Pedido> pedidoList = pedidoGateway.findByStatus(PedidoStatus.PAGAMENTO_APROVADO.toString());
            pedidoList.forEach(this::envia);
        }
        catch( Exception e){
            throw new BussinessErrorException(e.getMessage());
        }
    }

    @Transactional
    public void envia(Pedido pedido){
        try {
            EntregaDTO entrega =
                    new EntregaDTO(pedido.getIdPedido(),
                            pedido.getEmailCliente(),
                            new EnderecoDestinoDTO(pedido.getEndereco().getLogradouro(),pedido.getEndereco().getNumero(),pedido.getEndereco().getBairro(),pedido.getEndereco().getComplemento(),pedido.getEndereco().getCep(),pedido.getEndereco().getCidade(),pedido.getEndereco().getEstado(),pedido.getEndereco().getReferencia()),
                            pedido.getDataCriacao()
                    );
            Object response = logisticaClient.enviaEntrega(entrega);
            pedido.setStatus(PedidoStatus.EM_LOGISTICA.toString());
            pedidoGateway.update(pedido);
        }
        catch( Exception e){
            throw new BussinessErrorException(e.getMessage());
        }
    }
}


