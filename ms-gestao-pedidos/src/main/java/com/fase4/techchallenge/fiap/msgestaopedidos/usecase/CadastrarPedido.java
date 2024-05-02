package com.fase4.techchallenge.fiap.msgestaopedidos.usecase;

import com.fase4.techchallenge.fiap.msgestaopedidos.entity.enums.PedidoStatus;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.gateway.PedidoGateway;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Cliente;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Endereco;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Pedido;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.ClienteEndereco;
import com.fase4.techchallenge.fiap.msgestaopedidos.infrastructure.pedido.controller.dto.PedidoInsertDTO;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.exception.EntityNotFoundException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CadastrarPedido {

    private final PedidoGateway pedidoGateway;
    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    public Pedido execute(PedidoInsertDTO pedidoInsertDTO) {
        ClienteEndereco clienteEndereco = validaClienteEnderecoApiClientes(pedidoInsertDTO.emailCliente(), pedidoInsertDTO.idEndereco());

        Pedido pedido = new Pedido(clienteEndereco.getCliente(),
                pedidoInsertDTO.produtos(),
                pedidoInsertDTO.valorPedido(),
                PedidoStatus.GERADO.toString(),
                pedidoInsertDTO.meioPagamento(),
                LocalDateTime.now());

        return this.pedidoGateway.create(pedido);
    }

    public ClienteEndereco validaClienteEnderecoApiClientes(String idEmail, Integer idEndereco) {
        ClienteEndereco clienteEndereco = null;
        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:8082/ms-gerenciamento-clientes/clientes/{idCliente}/enderecos/{idEndereco}", String.class, idEmail, idEndereco);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                JsonNode jsonRetorno = objectMapper.readTree((responseEntity.getBody()));
                JsonNode clienteJson = jsonRetorno.get("cliente");

                Endereco endereco = new Endereco(jsonRetorno.get("logradouro").asText(), jsonRetorno.get("numero").asText(), jsonRetorno.get("bairro").asText(), jsonRetorno.get("complemento").asText(),
                        jsonRetorno.get("cep").asLong(), jsonRetorno.get("cidade").asText(), jsonRetorno.get("estado").asText(), jsonRetorno.get("referencia").asText());
                Cliente cliente = new Cliente(clienteJson.get("email").asText(), clienteJson.get("nome").asText(), endereco);

                ClienteEndereco clienteRetorno = new ClienteEndereco(cliente, endereco);
                clienteEndereco = clienteRetorno;
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 404) {
                throw new EntityNotFoundException("Cliente/Endereço não Localizado");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return clienteEndereco;
    }

}
