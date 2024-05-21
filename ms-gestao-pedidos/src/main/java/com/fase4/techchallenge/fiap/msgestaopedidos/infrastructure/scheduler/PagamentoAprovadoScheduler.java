package com.fase4.techchallenge.fiap.msgestaopedidos.infrastructure.scheduler;

import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.logistica.EnviaPedidosPagamentoAprovado;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PagamentoAprovadoScheduler {

    private final EnviaPedidosPagamentoAprovado enviaPedidosPagamentoAprovado;

    @Scheduled(fixedRate = 60000)
    public void processa() {
        enviaPedidosPagamentoAprovado.execute();
    }

}
