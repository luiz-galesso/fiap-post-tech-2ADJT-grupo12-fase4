package com.fase4.techchallenge.fiap.msgestaopedidos.usecase.logistica;

import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Pedido;
import com.fase4.techchallenge.fiap.msgestaopedidos.infrastructure.feign.LogisticaClient;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.exception.BussinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EnviaPedidosPagamentoAprovado {

    private final LogisticaClient logisticaClient;

    public void execute(Pedido pedido) {
        try {
            System.out.println("SA");
            //return this.clientesClient.getCliente(email);
        }
        catch( Exception e){
            throw new BussinessErrorException(e.getMessage());
        }
    }
}
