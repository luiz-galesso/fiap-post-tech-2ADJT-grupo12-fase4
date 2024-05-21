package com.fase4.techchallenge.fiap.msgerenciamentoprodutos.performance;

import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.infrastructure.produto.controller.dto.EstoqueDTO;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.infrastructure.produto.controller.dto.ProdutoInsertDTO;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.infrastructure.produto.controller.dto.ProdutoUpdateDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.gatling.javaapi.core.ActionBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import java.time.Duration;
import java.util.Random;

import static com.fase4.techchallenge.fiap.msgerenciamentoprodutos.utils.ProdutoHelper.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class ProdutoPerformanceTest extends Simulation
{
    String port = System.getenv("PORT");

    private final HttpProtocolBuilder httpProtocolBuilder
            = http.baseUrl("http://localhost:"+ (port == null ? "8092" : port) + "/ms-catalogo-produtos")
            .header("Content-Type", "application/json");

    ActionBuilder cadastrarProduto = http("Cadastrar produto")
            .post("/produtos")
            .body(StringBody(session -> {
                ProdutoInsertDTO produtoInsertDTO = gerarProduto("Produto " + new Random().nextLong());
                try {
                    return asJsonString(produtoInsertDTO);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }))
            .asJson()
            .check(status().is(HttpStatus.CREATED.value()))
            .check(jsonPath("$.codProduto").saveAs("id"));

    ActionBuilder atualizarProduto = http("Atualizar produto")
            .put("/produtos/#{id}")
            .body(StringBody(session -> {
                ProdutoUpdateDTO updateDTO = gerarProdutoUpdate(gerarProduto("Produto Update " + new Random().nextLong()));
                try {
                    return asJsonString(updateDTO);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }))
            .checkIf((response, session) -> {
                return session.getString("id") != null;
            })
            .then(status().is(HttpStatus.ACCEPTED.value()));

    ActionBuilder buscarProduto = http("Buscar produto")
            .get("/produtos/#{id}")
            .checkIf((response, session) -> {
                return session.getString("id") != null;
            })
            .then(status().is(HttpStatus.OK.value()));

    ActionBuilder aumentarEstoque = http("Aumentar estoque")
            .put("/produtos/#{id}/aumenta-estoque")
            .body(StringBody(session -> {
                EstoqueDTO estoqueDTO = new EstoqueDTO(10L);
                try {
                    return asJsonString(estoqueDTO);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }))
            .checkIf((response, session) -> {
                return session.getString("id") != null;
            })
            .then(status().is(HttpStatus.OK.value()));

    ActionBuilder consumirEstoque = http("Consumir estoque")
            .put("/produtos/#{id}/consome-estoque")
            .body(StringBody(session -> {
                EstoqueDTO estoqueDTO = new EstoqueDTO(10L);
                try {
                    return asJsonString(estoqueDTO);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }))
            .checkIf((response, session) -> {
                return session.getString("id") != null;
            })
            .then(status().is(HttpStatus.OK.value()));

    ScenarioBuilder cenarioCadastrarProduto = scenario("Cadastar produto")
            .exec(cadastrarProduto);

    ScenarioBuilder cenarioAtualizarProduto = scenario("Atualizar produto")
            .exec(cadastrarProduto)
            .exec(atualizarProduto);

    ScenarioBuilder cenarioBuscarProduto = scenario("Buscar produto")
            .exec(cadastrarProduto)
            .exec(buscarProduto);

    ScenarioBuilder cenarioAumentarEstoque = scenario("Aumentar estoque")
            .exec(cadastrarProduto)
            .exec(aumentarEstoque);

    ScenarioBuilder cenarioConsumirEstoque = scenario("Consumir estoque")
            .exec(cadastrarProduto)
            .exec(consumirEstoque);

    {
        setUp(
                cenarioCadastrarProduto.injectOpen(
                        rampUsersPerSec(2)
                                .to(20)
                                .during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10)
                                .during(Duration.ofSeconds(30)),
                        rampUsersPerSec(10)
                                .to(1)
                                .during(Duration.ofSeconds(10))
                ),
                cenarioAtualizarProduto.injectOpen(
                        rampUsersPerSec(2)
                                .to(10)
                                .during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10)
                                .during(Duration.ofSeconds(30)),
                        rampUsersPerSec(10)
                                .to(1)
                                .during(Duration.ofSeconds(10))
                ),
                cenarioBuscarProduto.injectOpen(
                                rampUsersPerSec(2)
                                        .to(100)
                                        .during(Duration.ofSeconds(21)),
                                constantUsersPerSec(20)
                                        .during(Duration.ofSeconds(60)),
                                rampUsersPerSec(10)
                                        .to(1)
                                        .during(Duration.ofSeconds(20))
                ),
                cenarioAumentarEstoque.injectOpen(
                        rampUsersPerSec(2)
                                .to(10)
                                .during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10)
                                .during(Duration.ofSeconds(20)),
                        rampUsersPerSec(10)
                                .to(1)
                                .during(Duration.ofSeconds(10))
                ),
                cenarioConsumirEstoque.injectOpen(
                        rampUsersPerSec(10)
                                .to(10)
                                .during(Duration.ofSeconds(25)),
                        constantUsersPerSec(16)
                                .during(Duration.ofSeconds(40)),
                        rampUsersPerSec(10)
                                .to(10)
                                .during(Duration.ofSeconds(10))
                )
        )
                .protocols(httpProtocolBuilder)
                .assertions(
                        global().responseTime().max().lt(1000)
                );
    }
}
