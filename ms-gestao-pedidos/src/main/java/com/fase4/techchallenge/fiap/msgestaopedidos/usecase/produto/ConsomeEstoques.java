package com.fase4.techchallenge.fiap.msgestaopedidos.usecase.produto;


import com.fase4.techchallenge.fiap.msgestaopedidos.infrastructure.feign.ProdutosClient;
import com.fase4.techchallenge.fiap.msgestaopedidos.infrastructure.feign.dto.ProdutoEstoqueDTO;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.exception.BussinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ConsomeEstoques {

    private final ProdutosClient produtosClient;

    public void execute(List<ProdutoEstoqueDTO> produtoEstoqueList) {
        try {
            this.produtosClient.consomeEstoques(produtoEstoqueList);
        }
        catch( Exception e){
            throw new BussinessErrorException(e.getMessage());
        }
    }
}
