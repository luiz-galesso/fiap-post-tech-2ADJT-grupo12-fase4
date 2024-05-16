package com.fase4.techchallenge.fiap.msgestaopedidos.infrastructure.feign;

import com.fase4.techchallenge.fiap.msgestaopedidos.infrastructure.feign.dto.ProdutoEstoqueDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "produtos", url = "${feign.ms-catalogo-produtos.url}")
public interface ProdutosClient {
    @RequestMapping(method = RequestMethod.PUT, value = "/produtos/estoque-massivo")
    void consomeEstoques(@RequestBody List<ProdutoEstoqueDTO> produtoEstoqueList);

}