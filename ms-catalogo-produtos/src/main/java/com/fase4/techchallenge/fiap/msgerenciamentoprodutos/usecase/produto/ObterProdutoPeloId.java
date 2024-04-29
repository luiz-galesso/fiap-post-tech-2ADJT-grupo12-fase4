package com.fase4.techchallenge.fiap.msgerenciamentoprodutos.usecase.produto;

import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.entity.produto.gateway.ProdutoGateway;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.entity.produto.model.Produto;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.usecase.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ObterProdutoPeloId {
    private final ProdutoGateway produtoGateway;

    public ObterProdutoPeloId(ProdutoGateway produtoGateway) {
        this.produtoGateway = produtoGateway;
    }

    public Produto execute(String id) {
        return this.produtoGateway.findById(id).orElseThrow(() -> new EntityNotFoundException("Produto não localizado"));
    }


}