package com.fase4.techchallenge.fiap.msgerenciamentoclientes.bdd;

import br.com.fiap.api.model.Mensagem;
import br.com.fiap.api.utils.MensagemHelper;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class DefinicaoPassos {

    private Response response;
    private Mensagem mensagemResposta;
    private final String ENDPOINT_API_MENSAGENS = "http://localhost:8080/mensagens";

    @Quando("registrar uma nova mensagem")
    public Mensagem registrar_uma_nova_mensagem() {
        var mensagemRequest = MensagemHelper.gerarMensagem();
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(mensagemRequest)
                .when()
                .post(ENDPOINT_API_MENSAGENS);
        return response.then().extract().as(Mensagem.class);
    }

    @Entao("a mensagem é registrada com sucesso")
    public void a_mensagem_é_registrada_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Entao("deve ser apresentada")
    public void deve_ser_apresentada() {
        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/mensagem_schema.json"));
    }

    @Dado("que uma mensagem ja foi publicada")
    public void que_uma_mensagem_ja_foi_publicada() {
        mensagemResposta = registrar_uma_nova_mensagem();
    }

    @Quando("efetuar a busca da mensagem")
    public void efetuar_a_busca_da_mensagem() {
        response = when()
                .get(ENDPOINT_API_MENSAGENS + "/{id}", mensagemResposta.getId());
    }

    @Entao("a mensagem é exibida com sucesso")
    public void a_mensagem_é_exibida_com_sucesso() {
        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/mensagem_schema.json"));
    }

    @Quando("efetuar requisição para alteração da mensagem")
    public void efetuar_requisição_para_alteração_da_mensagem() {
        mensagemResposta.setConteudo("ABC 123");
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(mensagemResposta)
                .when()
                .put(ENDPOINT_API_MENSAGENS + "/{id}", mensagemResposta.getId());
    }

    @Entao("a mensagem é atualizada com sucesso")
    public void a_mensagem_é_atualizada_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.ACCEPTED.value());
    }

    @Quando("requisitar a remoção da mensagem")
    public void requisitar_a_remoção_da_mensagem() {
        response = when()
                .delete(ENDPOINT_API_MENSAGENS + "/{id}", mensagemResposta.getId());
    }

    @Entao("a mensagem é removida com sucesso")
    public void a_mensagem_é_removida_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.OK.value());
    }
}
