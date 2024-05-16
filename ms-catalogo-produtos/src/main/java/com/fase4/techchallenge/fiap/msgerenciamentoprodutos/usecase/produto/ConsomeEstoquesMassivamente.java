package com.fase4.techchallenge.fiap.msgerenciamentoprodutos.usecase.produto;

import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.entity.produto.gateway.ProdutoGateway;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.entity.produto.model.Produto;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.infrastructure.produto.controller.dto.ProdutoEstoqueDTO;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.usecase.exception.BussinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ConsomeEstoquesMassivamente {

    private final ProdutoGateway produtoGateway;
    private final ConsomeEstoqueProduto consomeEstoqueProduto;

    public void execute(List<ProdutoEstoqueDTO> produtoEstoqueList){

        produtoEstoqueList.forEach( p -> {
            Produto produto = produtoGateway.findById(p.codProduto()).orElseThrow(() -> new BussinessErrorException("O produto "+ p.codProduto()+ " não foi encontrado."));
            consomeEstoqueProduto.execute(produto.getCodProduto(), p.quantidade());
        });

    }
}
