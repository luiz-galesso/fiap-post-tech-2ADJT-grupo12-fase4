package com.fase4.techchallenge.fiap.msgerenciamentoprodutos.infrastructure.produto.controller;

import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.entity.produto.model.Produto;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.infrastructure.produto.controller.dto.EstoqueDTO;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.infrastructure.produto.controller.dto.ProdutoEstoqueDTO;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.infrastructure.produto.controller.dto.ProdutoInsertDTO;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.infrastructure.produto.controller.dto.ProdutoUpdateDTO;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.infrastructure.util.DefaultResponse;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.usecase.produto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/produtos")
@AllArgsConstructor
@Tag(name = "Produtos", description = "Serviços para manipular os produtos")
public class ProdutoController {

    private final CadastrarProduto cadastrarProduto;
    private final AtualizarProduto atualizarProduto;
    private final ObterProdutoPeloId obterProdutoPeloId;
    private final RemoverProdutoPeloId removerProdutoPeloId;
    private final AumentaEstoqueProduto aumentaEstoqueProduto;
    private final ConsomeEstoqueProduto consomeEstoqueProduto;
    private final ConsomeEstoquesMassivamente consomeEstoquesMassivamente;
    private final AumentaEstoquesMassivamente aumentaEstoquesMassivamente;
    private final ObtemListaProdutosComEstoque obtemListaProdutosComEstoque;

    @Operation(summary = "Realiza um novo cadastro de produto", description = "Serviço utilizado para cadastro do produto.")
    @PostMapping(produces = "application/json")
    @Transactional
    public ResponseEntity<?> create(@RequestBody ProdutoInsertDTO produtoInsertDTO) {
          Produto produto = cadastrarProduto.execute(produtoInsertDTO);
          return new ResponseEntity<>(produto, HttpStatus.CREATED);
    }

    @Operation(summary = "Altera os dados do produto", description = "Serviço utilizado para alterar os dados do produto.")
    @PutMapping(value = "/{id}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProdutoUpdateDTO produtoUpdateDTO) {
         var produtoRetorno = atualizarProduto.execute(id, produtoUpdateDTO);
         return new ResponseEntity<>(produtoRetorno, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Busca o produto pelo Id", description = "Serviço utilizado para buscar o produto pelo Id.")
    @GetMapping(value = "/{id}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> findById(@PathVariable Long id) {
        var produto = obterProdutoPeloId.execute(id);
        return new ResponseEntity<>(produto, HttpStatus.OK);
    }

    @Operation(summary = "Remove o produto pelo Id", description = "Serviço utilizado para remover o produto pelo Id.")
    @DeleteMapping(value = "/{id}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id) {
         var produto = removerProdutoPeloId.execute(id);
         return new ResponseEntity<>(new DefaultResponse(Instant.now(),"OK","Produto removido."), HttpStatus.OK);
    }

    @Operation(summary = "Aumenta o estoque de um produto", description = "Serviço utilizado para aumentar o estoque de um produto.")
    @PutMapping(value = "/{id}/aumenta-estoque", produces = "application/json")
    @Transactional
    public ResponseEntity<?> aumentaEstoque(@PathVariable Long id, @RequestBody EstoqueDTO estoque) {
        var produto = aumentaEstoqueProduto.execute(id, estoque.quantidade());
        return new ResponseEntity<>(produto, HttpStatus.OK);
    }

    @Operation(summary = "Consome o estoque de um produto", description = "Serviço utilizado para consumir o estoque de um produto.")
    @PutMapping(value = "/{id}/consome-estoque", produces = "application/json")
    @Transactional
    public ResponseEntity<?> consomeEstoque(@PathVariable Long id, @RequestBody EstoqueDTO estoque) {
        var produto = consomeEstoqueProduto.execute(id, estoque.quantidade());
        return new ResponseEntity<>(produto, HttpStatus.OK);
    }

    @Operation(summary = "Consome estoques massivamente", description = "Serviço utilizado para consumir o estoque de varios produtos de forma simultanea.")
    @PutMapping(value = "/estoque-massivo/consumo", produces = "application/json")
    @Transactional
    public ResponseEntity<?> consomeEstoques(@RequestBody List<ProdutoEstoqueDTO> produtoEstoqueList) {
         consomeEstoquesMassivamente.execute(produtoEstoqueList);
         return new ResponseEntity<> (new DefaultResponse(Instant.now(),"OK","Estoques consumidos com sucesso."), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Aumenta estoques massivamente", description = "Serviço utilizado para aumentar o estoque de varios produtos de forma simultanea.")
    @PutMapping(value = "/estoque-massivo/aumento", produces = "application/json")
    @Transactional
    public ResponseEntity<?> aumentaEstoques(@RequestBody List<ProdutoEstoqueDTO> produtoEstoqueList) {
        aumentaEstoquesMassivamente.execute(produtoEstoqueList);
        return new ResponseEntity<> (new DefaultResponse(Instant.now(),"OK","Estoques foram aumentados com sucesso."), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Obtem todos produtos com estoque positivo", description = "Serviço utilizado para obter todos produtos com estoque positivo.")
    @GetMapping(value = "/estoque-positivo", produces = "application/json")
    @Transactional
    public ResponseEntity<?> buscaProdutosDisponiveis() {
        List<Produto> produtoList = obtemListaProdutosComEstoque.execute();
        return new ResponseEntity<> (produtoList, HttpStatus.OK);
    }
}
