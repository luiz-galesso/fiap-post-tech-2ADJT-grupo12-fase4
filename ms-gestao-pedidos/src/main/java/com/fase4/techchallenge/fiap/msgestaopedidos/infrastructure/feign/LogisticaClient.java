package com.fase4.techchallenge.fiap.msgestaopedidos.infrastructure.feign;

import com.fase4.techchallenge.fiap.msgestaopedidos.infrastructure.feign.dto.EntregaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "logistica", url = "${feign.ms-logistica-entregas.url}")
public interface LogisticaClient {
    @RequestMapping(method = RequestMethod.POST, value = "/entregas")
    Object enviaEntrega(@RequestBody EntregaDTO entrega);

}