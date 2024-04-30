package com.fase4.techchallenge.fiap.msgerenciamentoprodutos.usecase.produto;

import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.entity.produto.gateway.ProdutoGateway;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.entity.produto.model.Produto;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.usecase.exception.BussinessErrorException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RemoverProdutoPeloId {
    private final ProdutoGateway produtoGateway;

    public RemoverProdutoPeloId(ProdutoGateway produtoGateway) {
        this.produtoGateway = produtoGateway;
    }

    public boolean execute(Long id) {
        Optional<Produto> produtoOptional = produtoGateway.findById(id);

        if (produtoOptional.isEmpty()) {
            throw new BussinessErrorException("Não foi encontrado o produto cadastrado com o email informado.");
        }

        produtoGateway.remove(id);
        return true;
    }

}