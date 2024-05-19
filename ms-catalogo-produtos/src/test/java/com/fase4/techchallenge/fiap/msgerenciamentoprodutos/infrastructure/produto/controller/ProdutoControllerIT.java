package com.fase4.techchallenge.fiap.msgerenciamentoprodutos.infrastructure.produto.controller;

import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.entity.produto.model.Produto;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.infrastructure.produto.controller.dto.EstoqueDTO;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.usecase.produto.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static com.fase4.techchallenge.fiap.msgerenciamentoprodutos.utils.ProdutoHelper.gerarProduto;
import static com.fase4.techchallenge.fiap.msgerenciamentoprodutos.utils.ProdutoHelper.gerarProdutoUpdate;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProdutoControllerIT
{
    @Autowired
    private CadastrarProduto cadastrarProduto;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = this.port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.filters(new AllureRestAssured());
    }

    @Test
    void devePermitirCadastrarProduto() {
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(gerarProduto())
        .when().post("/ms-catalogo-produtos/produtos")
        .then()
                .statusCode(HttpStatus.CREATED.value())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/produto.schema.json"));
    }

    @Test
    void devePermitirAtualizarProduto() {

        Produto produto = cadastrarProduto.execute(gerarProduto());

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(gerarProdutoUpdate(gerarProduto()))
        .when()
                .put("/ms-catalogo-produtos/produtos/{id}", produto.getCodProduto())
        .then()
                .statusCode(HttpStatus.ACCEPTED.value())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/produto.schema.json"));
    }

    @Test
    void devePermitirBuscarPeloId() {
        Produto produto = cadastrarProduto.execute(gerarProduto());
        when()
                .get("/ms-catalogo-produtos/produtos/{id}", produto.getCodProduto())
        .then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/produto.schema.json"))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void devePermitirApagarProduto() {
        Produto produto = cadastrarProduto.execute(gerarProduto());
        when()
                .delete("/ms-catalogo-produtos/produtos/{id}", produto.getCodProduto())
        .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void devePermitirAumentarEstoqueDeUmProduto(){
        Produto produto = cadastrarProduto.execute(gerarProduto());
        EstoqueDTO estoqueDTO = new EstoqueDTO(10L);

        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(estoqueDTO)
        .when()
            .put("/ms-catalogo-produtos/produtos/{id}/aumenta-estoque", produto.getCodProduto())
        .then()
            .statusCode(HttpStatus.OK.value())
            .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/produto.schema.json"));
    }

    @Test
    void devePermitirConsumirEstoqueDeUmProduto(){
        Produto produto = cadastrarProduto.execute(gerarProduto());
        EstoqueDTO estoqueDTO = new EstoqueDTO(10L);

        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(estoqueDTO)
        .when()
            .put("/ms-catalogo-produtos/produtos/{id}/consome-estoque", produto.getCodProduto())
        .then()
            .statusCode(HttpStatus.OK.value())
            .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/produto.schema.json"));
    }
}
