package com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "pedidos", url = "${feign.ms-gestao-pedidos.url}")
public interface PedidosClient {
    @RequestMapping(method = RequestMethod.PUT, value = "/pedidos/{idPedido}/confirmacao-entrega")
    Object confirmaEntrega(@PathVariable(value = "idPedido") Long idPedido);

}