package com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.pedido;

import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.feign.PedidosClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConfirmaEntrega {

    private final PedidosClient pedidosClient;
    public void execute(Long idPedido){
        Object object = pedidosClient.confirmaEntrega(idPedido);
    }
}
