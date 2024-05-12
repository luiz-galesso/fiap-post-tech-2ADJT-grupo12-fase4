package com.fase4.techchallenge.fiap.msgerenciamentoprodutos.usecase.produto;

import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.entity.produto.gateway.ProdutoGateway;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.entity.produto.model.Produto;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.infrastructure.produto.controller.dto.ProdutoInsertDTO;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.usecase.exception.BussinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CadastrarProduto {

    private final ProdutoGateway produtoGateway;

    public Produto execute(ProdutoInsertDTO produtoDTO) {

            Produto produto =
                    new Produto(
                            produtoDTO.descricaoProduto(),
                            produtoDTO.marca(),
                            produtoDTO.categoria(),
                            produtoDTO.quantidade(),
                            LocalDateTime.now()
                    );
            return this.produtoGateway.create(produto);
    }
}
