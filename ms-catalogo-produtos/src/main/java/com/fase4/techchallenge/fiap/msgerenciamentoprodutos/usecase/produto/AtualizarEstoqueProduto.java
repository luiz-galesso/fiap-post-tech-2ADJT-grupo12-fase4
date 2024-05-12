package com.fase4.techchallenge.fiap.msgerenciamentoprodutos.usecase.produto;

import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.entity.produto.gateway.ProdutoGateway;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.entity.produto.model.Produto;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.usecase.exception.BussinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AtualizarEstoqueProduto {

    private final ProdutoGateway produtoGateway;

    public Produto execute(Long id, Long quantidade) {
        Optional<Produto> produtoOptional = produtoGateway.findById(id);

        if (produtoOptional.isEmpty()) {
            throw new BussinessErrorException("Não foi encontrado o produto cadastrado com o identificador informado.");
        }

        produtoOptional.get().setQuantidade(produtoOptional.get().getQuantidade() - quantidade);

        Produto produto = new Produto(id,
                produtoOptional.get().getDescricaoProduto(),
                produtoOptional.get().getMarca(),
                produtoOptional.get().getCategoria(),
                produtoOptional.get().getQuantidade(),
                LocalDateTime.now());

        return this.produtoGateway.update(produto);
    }
}
