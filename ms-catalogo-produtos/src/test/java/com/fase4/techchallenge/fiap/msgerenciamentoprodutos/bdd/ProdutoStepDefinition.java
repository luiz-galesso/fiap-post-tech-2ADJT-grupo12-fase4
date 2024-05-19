package com.fase4.techchallenge.fiap.msgerenciamentoprodutos.bdd;

import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.entity.produto.model.Produto;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.infrastructure.produto.controller.dto.ProdutoInsertDTO;
import com.fase4.techchallenge.fiap.msgerenciamentoprodutos.infrastructure.produto.controller.dto.ProdutoUpdateDTO;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import jakarta.transaction.Transactional;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.util.Random;

import static com.fase4.techchallenge.fiap.msgerenciamentoprodutos.utils.ProdutoHelper.gerarProduto;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ActiveProfiles("test")
public class ProdutoStepDefinition
{
    private Response response;

    private Produto produto;

    private ProdutoInsertDTO produtoInsertDTO;

    private ProdutoUpdateDTO produtoUpdateDTO;

    private static final String ENDPOINT_API_PRODUTO = "http://localhost:8080/ms-gerenciamento-produtos/produtos";

    /** CADASTRAR **/
    @Dado("que tenha um produto para cadastrar")
    public void que_tenha_um_produto_para_cadastrar() {
        produtoInsertDTO = gerarProduto("Produto " + new Random().nextLong());
    }

    @Quando("eu cadastro o produto")
    public Produto cadastro_o_produto() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(produtoInsertDTO)
                .when().post(ENDPOINT_API_PRODUTO);

        produto = response.then().extract().as(Produto.class);
        return produto;
    }

    @Entao("o produto é cadastrado")
    public void o_produto_é_cadastrado() {
        response.then().statusCode(HttpStatus.CREATED.value());
    }

    @Entao("deverá ser apresentado")
    public void deverá_ser_apresentado() {
        response.then().body(
            JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/produto.schema.json")
        );
    }

    /** OBTER PRODUTO PELO ID **/
    @Dado("que o produto já foi cadastrado")
    public void que_o_produto_já_foi_cadastrado() {
        que_tenha_um_produto_para_cadastrar();
        produto = cadastro_o_produto();
    }
    @Quando("efetuar a busca pelo ID")
    public void efetuar_a_busca_pelo_id() {
        response = when()
                .get(ENDPOINT_API_PRODUTO+ "/{id}", produto.getCodProduto());
    }
    @Então("exibir produto")
    public void exibir_produto() {
        response.then().body(
          JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/produto.schema.json")
        );
    }

    /** ATUALIZAR **/
    @Dado("que eu deseje atualizar os dados desse produto")
    public void que_eu_desejo_atualizar_os_dados_desse_produto() {
        produtoUpdateDTO = new ProdutoUpdateDTO(
                produto.getDescricaoProduto(),
                produto.getMarca(),
                produto.getCategoria(),
                new Random().nextLong()
        );

        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(produtoInsertDTO)
                .when().put(ENDPOINT_API_PRODUTO + "/{id}", produto.getCodProduto());
    }
    @Quando("eu atualizo o produto com novas informações")
    public void eu_atualizo_o_produto_com_novas_informações() {
        response.then().statusCode(HttpStatus.ACCEPTED.value());
    }
    @Então("o produto deve ser atualizado com sucesso")
    public void o_produto_deve_ser_atualizado_com_sucesso() {
        response.then().body(
                JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/produto.schema.json")
        );
    }

    /**REMOVER PRODUTO**/
    @Quando("deletar o produto")
    public void deletar_o_produto() {
        response = when()
                .delete(ENDPOINT_API_PRODUTO + "/{id}", produto.getCodProduto());
    }

    @Entao("o produto deve ser deletado")
    public void o_produto_deve_ser_deletado() {
        response.then().statusCode(HttpStatus.OK.value());
    }
}
