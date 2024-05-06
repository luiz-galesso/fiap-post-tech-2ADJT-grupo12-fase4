package com.fase4.techchallenge.fiap.msgerenciamentoprodutos.usecase.produto;

import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.entity.produto.gateway.ProdutoGateway;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.entity.produto.model.Produto;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.infrastructure.produto.controller.dto.ProdutoUpdateDTO;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.usecase.exception.BussinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AtualizarProduto {

    private final ProdutoGateway produtoGateway;

    public Produto execute(Long codProduto, ProdutoUpdateDTO produtoUpdateDTO) {

        Optional<Produto> produtoOptional = produtoGateway.findById(codProduto);

        if (produtoOptional.isEmpty()) {
            throw new BussinessErrorException("Não foi encontrado o produto cadastrado com o identificador informado.");
        }

        Produto produto = new Produto(codProduto,
                produtoUpdateDTO.descricaoProduto(),
                produtoUpdateDTO.marca(),
                produtoUpdateDTO.categoria(),
                produtoUpdateDTO.quantidade(),
                LocalDateTime.now()
        );
        return this.produtoGateway.update(produto);
    }
}
