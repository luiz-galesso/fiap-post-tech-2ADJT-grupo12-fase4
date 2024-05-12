package com.fase4.techchallenge.fiap.msgerenciamentoprodutosbatch;

import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDateTime;
import java.util.logging.Logger;

public class ProdutoProcessor implements ItemProcessor<Produto, Produto>
{
    @Override
    public Produto process(Produto item) throws Exception {
        item.setDataAtualizacao(LocalDateTime.now());
        return item;
    }
}
