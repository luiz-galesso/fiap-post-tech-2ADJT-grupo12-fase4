package com.fase4.techchallenge.fiap.msgerenciamentoprodutos.infrastructure.produto.controller;

import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.entity.produto.gateway.ProdutoGateway;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.entity.produto.model.Produto;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.infrastructure.produto.controller.dto.EstoqueDTO;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.infrastructure.produto.controller.dto.ProdutoInsertDTO;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.infrastructure.produto.controller.dto.ProdutoUpdateDTO;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.usecase.produto.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static com.fase4.techchallenge.fiap.msgerenciamentoprodutos.utils.ProdutoHelper.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProdutoControllerTest {

    @Mock
    CadastrarProduto cadastrarProduto;

    @Mock
    AtualizarProduto atualizarProduto;

    @Mock
    ObterProdutoPeloId obterProdutoPeloId;

    @Mock
    RemoverProdutoPeloId removerProdutoPeloId;

    @Mock
    AumentaEstoqueProduto aumentaEstoqueProduto;

    @Mock
    ConsomeEstoqueProduto consomeEstoqueProduto;

    @Mock
    ConsomeEstoquesMassivamente consomeEstoquesMassivamente;

    @Mock
    AumentaEstoquesMassivamente aumentaEstoquesMassivamente;

    @Mock
    ObtemListaProdutosComEstoque obtemListaProdutosComEstoque;
    @Mock
    ProdutoGateway produtoGateway;


    MockMvc mockMvc;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        ProdutoController produtoController = new ProdutoController(
                cadastrarProduto,
                atualizarProduto,
                obterProdutoPeloId,
                removerProdutoPeloId,
                aumentaEstoqueProduto,
                consomeEstoqueProduto,
                consomeEstoquesMassivamente,
                aumentaEstoquesMassivamente,
                obtemListaProdutosComEstoque
        );
        mockMvc = MockMvcBuilders.standaloneSetup(produtoController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    void devePermitirCadastrarProduto() throws Exception {
        //arrange
        ProdutoInsertDTO produtoInsertDTO = gerarProduto();
        Produto produto = produtoGerado(gerarProduto(), 1L);
        when(cadastrarProduto.execute(any(ProdutoInsertDTO.class))).thenReturn(produto);

        //act
        mockMvc.perform(
                post("/produtos")
                        .content(asJsonString(produtoInsertDTO))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());

        //assert
        verify(cadastrarProduto, times(1)).execute(any(ProdutoInsertDTO.class));
    }

    @Test
    void devePermitirAtualizarProduto() throws Exception {
        //arrange
        ProdutoUpdateDTO produtoUpdateDTO = gerarProdutoUpdate(gerarProduto());
        Produto produto = produtoGerado(gerarProduto(), 1L);
        when(atualizarProduto.execute(any(Long.class), any(ProdutoUpdateDTO.class)))
                .thenReturn(produto);
        //act
        mockMvc.perform(
                put("/produtos/{id}", 1L)
                        .content(asJsonString(produtoUpdateDTO))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isAccepted());

        //assert
        verify(atualizarProduto, times(1))
                .execute(any(Long.class), any(ProdutoUpdateDTO.class));
    }

    @Test
    void devePermitirBuscarPeloId() throws Exception {
        //arrange
        Produto produto = produtoGerado(gerarProduto(), 1L);
        when(obterProdutoPeloId.execute(any(Long.class))).thenReturn(produto);

        //act
        mockMvc.perform(
                get("/produtos/{id}", 1L)
        ).andExpect(status().isOk());

        //assert
        verify(obterProdutoPeloId, times(1)).execute(any(Long.class));
    }

    @Test
    void devePermitirApagarProduto() throws Exception {
        //arrange
        when(removerProdutoPeloId.execute(any(Long.class))).thenReturn(true);

        //act
        mockMvc.perform(
                delete("/produtos/{id}", 1L)
        ).andExpect(status().isOk());

        //assert
        verify(removerProdutoPeloId, times(1)).execute(any(Long.class));
    }

    @Test
    void devePermitirAumentarEstoqueDeUmProduto() throws Exception {
        //arrange
        Produto produto = produtoGerado(gerarProduto(), 1L);
        when(produtoGateway.findById(1L)).thenReturn(Optional.of(produto));
        when(aumentaEstoqueProduto.execute(
                any(java.lang.Long.class),
                any(java.lang.Long.class)
        )).thenReturn(produto);
        when(aumentaEstoqueProduto
                .execute(any(Long.class), any(Long.class))).thenReturn(produto);

        EstoqueDTO estoqueDTO = new EstoqueDTO(10L);

        //act
        mockMvc.perform(
                put("/produtos/{id}/aumenta-estoque", 1L)
                        .content(asJsonString(estoqueDTO))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        //assert
        verify(aumentaEstoqueProduto, times(1)).execute(any(Long.class), any(Long.class));
    }

    @Test
    void devePermitirConsumirEstoqueDeUmProduto() throws Exception {
        //arrange
        Produto produto = produtoGerado(gerarProduto(), 1L);
        when(produtoGateway.findById(1L)).thenReturn(Optional.of(produto));
        when(consomeEstoqueProduto.execute(
                any(java.lang.Long.class),
                any(java.lang.Long.class)
        )).thenReturn(produto);
        when(consomeEstoqueProduto
                .execute(any(Long.class), any(Long.class))).thenReturn(produto);

        EstoqueDTO estoqueDTO = new EstoqueDTO(10L);

        //act
        mockMvc.perform(
                put("/produtos/{id}/consome-estoque", 1L)
                        .content(asJsonString(estoqueDTO))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        //assert
        verify(consomeEstoqueProduto, times(1)).execute(any(Long.class), any(Long.class));
    }
}
