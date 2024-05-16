package com.fase4.techchallenge.fiap.msgestaopedidos.infrastructure.feign;

import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Cliente;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Endereco;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "clientes", url = "${feign.ms-gerenciamento-clientes.url}")
public interface ClientesClient {
    @RequestMapping(method = RequestMethod.GET, value = "/clientes/{email}")
    Cliente getCliente(@PathVariable(value = "email") String email);

    @RequestMapping(method = RequestMethod.GET, value = "/clientes/{email}/enderecos/{idEndereco}")
    Endereco getEnderecoCliente(@PathVariable(value = "email") String email, @PathVariable(value = "idEndereco") Integer idEndereco);
}