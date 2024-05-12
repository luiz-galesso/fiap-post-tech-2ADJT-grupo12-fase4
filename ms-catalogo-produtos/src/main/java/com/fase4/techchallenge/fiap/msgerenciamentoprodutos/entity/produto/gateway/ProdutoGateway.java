package com.fase4.techchallenge.fiap.msgerenciamentoprodutos.entity.produto.gateway;

import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.entity.produto.model.Produto;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.infrastructure.produto.repository.ProdutoRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProdutoGateway {

    private ProdutoRepository produtoRepository;

    public ProdutoGateway(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto create(Produto produto) {
        return this.produtoRepository.save(produto);
    }

    public Produto update(Produto produto) {
        return this.produtoRepository.save(produto);
    }

    public Optional<Produto> findById(Long codProduto) {
        return this.produtoRepository.findById(codProduto);
    }

    public void remove(Long codProduto) {
        produtoRepository.deleteById(codProduto);
    }

}