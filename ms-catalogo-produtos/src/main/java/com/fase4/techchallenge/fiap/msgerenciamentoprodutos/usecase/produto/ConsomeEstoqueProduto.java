package com.fase4.techchallenge.fiap.msgerenciamentoprodutos.usecase.produto;

import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.entity.produto.gateway.ProdutoGateway;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.entity.produto.model.Produto;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.usecase.exception.BussinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ConsomeEstoqueProduto {

    private final ProdutoGateway produtoGateway;

    public Produto execute(Long id, Long quantidade) {

        Produto produto = produtoGateway.findById(id).orElseThrow(() -> new BussinessErrorException("Produto com id " + id + "não foi encontrado."));

        if (quantidade <= 0) {
            throw new BussinessErrorException("Quantidade inválida.");
        }

        if (produto.getQuantidade() - quantidade < 0) {
            throw new BussinessErrorException("Estoque insuficiente.");
        }

        produto.setQuantidade(produto.getQuantidade() - quantidade);
        produto.setDataAtualizacao(LocalDateTime.now());

        return this.produtoGateway.update(produto);
    }
}
