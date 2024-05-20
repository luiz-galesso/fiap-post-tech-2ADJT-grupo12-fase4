package com.fase4.techchallenge.fiap.msgerenciamentoclientes.endereco.infrastructure;

import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.cliente.model.Cliente;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.endereco.gateway.EnderecoGateway;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.endereco.model.Endereco;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.infrastructure.cliente.controller.dto.ClienteInsertDTO;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.infrastructure.endereco.controller.dto.EnderecoInsertDTO;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.infrastructure.endereco.controller.dto.EnderecoUpdateDTO;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.usecase.cliente.CadastrarCliente;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.usecase.endereco.CadastrarEndereco;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.utils.ClienteHelper;
import com.fase4.techchallenge.fiap.msgerenciamentoclientes.utils.EnderecoHelper;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureTestDatabase
public class EnderecoControllerIT {

    @Autowired
    CadastrarCliente cadastrarCliente;
    @Autowired
    CadastrarEndereco cadastrarEndereco;
    @Autowired
    EnderecoGateway enderecoGateway;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void devePermitirCadastrarEndereco() {
        EnderecoInsertDTO enderecoInsertDTO = EnderecoHelper.gerarEnderecoInsert();
        Cliente cliente = ClienteHelper.gerarCliente();//cadastrarCliente.execute(ClienteHelper.gerarClienteInsert());

        given()
                .filter(new AllureRestAssured())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(enderecoInsertDTO)
                .when()
                .post("/ms-gerenciamento-clientes/clientes/{idCliente}/enderecos", cliente.getEmail())
                .then()
                .log().all()
                .statusCode(HttpStatus.CREATED.value())
                .body(matchesJsonSchemaInClasspath("./schemas/endereco_schema.json"));
    }


    @Test
    void devePermitirAtualizarEndereco() {
        EnderecoInsertDTO enderecoInsertDTO = EnderecoHelper.gerarEnderecoInsert();
        Cliente cliente = ClienteHelper.gerarCliente();//cadastrarCliente.execute(ClienteHelper.gerarClienteInsert());
        Endereco endereco = cadastrarEndereco.execute(cliente.getEmail(), enderecoInsertDTO);
        EnderecoUpdateDTO enderecoUpdateDTO = EnderecoHelper.gerarEnderecoUpdate();

        given()
                .filter(new AllureRestAssured())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(enderecoUpdateDTO)
                .when()
                .put("/ms-gerenciamento-clientes/clientes/{idCliente}/enderecos/{idEndereco}", cliente.getEmail(), endereco.getId())
                .then()
                .log().all()
                .statusCode(HttpStatus.ACCEPTED.value())
                .body(matchesJsonSchemaInClasspath("./schemas/endereco_schema.json"));
    }


    @Test
    void devePermitirObterEnderecoPorId() {
        EnderecoInsertDTO enderecoInsertDTO = EnderecoHelper.gerarEnderecoInsert();
        Cliente cliente = ClienteHelper.gerarCliente();//cadastrarCliente.execute(ClienteHelper.gerarClienteInsert());
        Endereco endereco = cadastrarEndereco.execute(cliente.getEmail(), enderecoInsertDTO);

        given()
                .filter(new AllureRestAssured())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/ms-gerenciamento-clientes/clientes/{idCliente}/enderecos/{idEndereco}", cliente.getEmail(), endereco.getId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/endereco_schema.json"));
    }

    @Test
    void devePermitirObterEnderecosPorCliente() {
        EnderecoInsertDTO enderecoInsertDTO = EnderecoHelper.gerarEnderecoInsert();
        Cliente cliente = ClienteHelper.gerarCliente();//cadastrarCliente.execute(ClienteHelper.gerarClienteInsert());
        Endereco endereco = cadastrarEndereco.execute(cliente.getEmail(), enderecoInsertDTO);

        given()
                .filter(new AllureRestAssured())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/ms-gerenciamento-clientes/clientes/{idCliente}/enderecos", cliente.getEmail())
                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/endereco_list_schema.json"));
    }


    @Test
    void devePermitirRemoverEndereco() {
        EnderecoInsertDTO enderecoInsertDTO = EnderecoHelper.gerarEnderecoInsert();
        ClienteInsertDTO clienteInsertDTO = ClienteHelper.gerarClienteInsert();
        clienteInsertDTO.setEmail(ClienteHelper.generateRandomEmail());
        Cliente cliente = cadastrarCliente.execute(clienteInsertDTO);
        Endereco endereco = cadastrarEndereco.execute(cliente.getEmail(), enderecoInsertDTO);


        given()
                .filter(new AllureRestAssured())
                .when().delete("/ms-gerenciamento-clientes/clientes/{idCliente}/enderecos/{idEndereco}", cliente.getEmail(), endereco.getId())
                .then();
    }

}
