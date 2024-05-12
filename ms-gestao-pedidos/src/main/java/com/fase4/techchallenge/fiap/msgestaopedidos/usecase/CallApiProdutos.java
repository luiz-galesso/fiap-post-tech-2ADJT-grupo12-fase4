package com.fase4.techchallenge.fiap.msgestaopedidos.usecase;

import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Produto;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.exception.BussinessErrorException;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.exception.EntityNotFoundException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@AllArgsConstructor
public class CallApiProdutos {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String url = "http://ms-catalogo-produtos:8081/ms-gerenciamento-produtos/";

    public void validaProdutoAjustaEstoque(List<Produto> produtos) {
        produtos.stream().forEach(p -> {
            try {
                ResponseEntity<String> responseEntity = restTemplate.getForEntity(url + "produtos/{idProduto}", String.class, p.getCodProduto());

                if (responseEntity.getStatusCode().is2xxSuccessful()) {
                    JsonNode jsonRetorno = objectMapper.readTree((responseEntity.getBody()));
                    long quantidadeEstoque = jsonRetorno.get("quantidade").asLong();

                    if (p.getQuantidade() > quantidadeEstoque) {
                        throw new BussinessErrorException("O Produto " + jsonRetorno.get("descricaoProduto").asText() + " não possui quantidade de estoque para atender o Pedido.");
                    } else {
                        ajustarEstoque(p.getCodProduto(), p.getQuantidade());
                    }
                }
            } catch (HttpClientErrorException e) {
                if (e.getStatusCode().value() == 404) {
                    throw new EntityNotFoundException("Produto não Localizado");
                }
            } catch (BussinessErrorException e) {
                throw new BussinessErrorException(e.getMessage());
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        });
    }

    private void ajustarEstoque(long codProduto, long quantidade) {
        ResponseEntity<String> resposta = restTemplate.exchange(url + "produtos/atualiza-estoque/{id}/{quantidade}",
                HttpMethod.PUT, new HttpEntity<>(null, null), String.class, codProduto, quantidade
        );
    }
}
