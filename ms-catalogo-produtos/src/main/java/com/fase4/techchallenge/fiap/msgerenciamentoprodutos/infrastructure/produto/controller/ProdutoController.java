package com.fase4.techchallenge.fiap.msgerenciamentoprodutos.infrastructure.produto.controller;

import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.entity.produto.model.Produto;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.infrastructure.produto.controller.dto.ProdutoInsertDTO;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.infrastructure.produto.controller.dto.ProdutoUpdateDTO;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.usecase.produto.AtualizarProduto;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.usecase.produto.CadastrarProduto;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.usecase.produto.ObterProdutoPeloId;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.usecase.produto.RemoverProdutoPeloId;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.usecase.exception.BussinessErrorException;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.usecase.exception.EntityNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produtos")
@Tag(name = "Produtos", description = "Serviços para manipular os produtos")
public class ProdutoController {

    private final CadastrarProduto cadastrarProduto;
    private final AtualizarProduto atualizarProduto;
    private final ObterProdutoPeloId obterProdutoPeloId;
    private final RemoverProdutoPeloId removerProdutoPeloId;

    public ProdutoController(CadastrarProduto cadastrarProduto, AtualizarProduto atualizarProduto, ObterProdutoPeloId obterProdutoPeloId, RemoverProdutoPeloId removerProdutoPeloId) {
        this.cadastrarProduto = cadastrarProduto;
        this.atualizarProduto = atualizarProduto;
        this.obterProdutoPeloId = obterProdutoPeloId;
        this.removerProdutoPeloId = removerProdutoPeloId;
    }

    @Operation(summary = "Realiza um novo cadastro de produto", description = "Serviço utilizado para cadastro do produto.")
    @PostMapping(produces = "application/json")
    @Transactional
    public ResponseEntity<?> create(@RequestBody ProdutoInsertDTO produtoInsertDTO) {
        try {
            Produto produto = cadastrarProduto.execute(produtoInsertDTO);
            return new ResponseEntity<>(produto, HttpStatus.CREATED);
        } catch (BussinessErrorException bussinessErrorException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bussinessErrorException.getMessage());
        }
    }

    @Operation(summary = "Altera os dados do produto", description = "Serviço utilizado para alterar os dados do produto.")
    @PutMapping(value = "/{id}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody ProdutoUpdateDTO produtoUpdateDTO) {
        try {
            var produtoRetorno = atualizarProduto.execute(id, produtoUpdateDTO);
            return new ResponseEntity<>(produtoRetorno, HttpStatus.ACCEPTED);
        } catch (BussinessErrorException bussinessErrorException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bussinessErrorException.getMessage());
        }
    }

    @Operation(summary = "Busca o produto pelo Id", description = "Serviço utilizado para buscar o produto pelo Id.")
    @GetMapping(value = "/{id}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> findById(@PathVariable String id) {
        try {
            var produto = obterProdutoPeloId.execute(id);
            return new ResponseEntity<>(produto, HttpStatus.OK);
        } catch (EntityNotFoundException entityNotFoundException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(entityNotFoundException.getMessage());
        }
    }

    @Operation(summary = "Remove o produto pelo Id", description = "Serviço utilizado para remover o produto pelo Id.")
    @DeleteMapping(value = "/{id}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable String id) {
        try {
            var produto = removerProdutoPeloId.execute(id);
            return new ResponseEntity<>("Produto Removido", HttpStatus.OK);
        } catch (BussinessErrorException bussinessErrorException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bussinessErrorException.getMessage());
        }
    }

}
