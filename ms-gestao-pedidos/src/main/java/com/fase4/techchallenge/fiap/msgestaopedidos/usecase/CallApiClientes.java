package com.fase4.techchallenge.fiap.msgestaopedidos.usecase;

import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Cliente;
import com.fase4.techchallenge.fiap.msgestaopedidos.infrastructure.dto.ClienteEnderecoDTO;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Endereco;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.exception.EntityNotFoundException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CallApiClientes {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String url = "http://ms-gerenciamento-clientes:8082/ms-gerenciamento-clientes/";

    public ClienteEnderecoDTO retornaDadosCliente(String idEmail, Integer idEndereco) {
        ClienteEnderecoDTO clienteEnderecoDTO = null;
        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url + "clientes/{idCliente}/enderecos/{idEndereco}", String.class, idEmail, idEndereco);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                JsonNode jsonRetorno = objectMapper.readTree((responseEntity.getBody()));
                JsonNode clienteJson = jsonRetorno.get("cliente");

                Endereco endereco = new Endereco(jsonRetorno.get("logradouro").asText(), jsonRetorno.get("numero").asText(), jsonRetorno.get("bairro").asText(), jsonRetorno.get("complemento").asText(),
                        jsonRetorno.get("cep").asLong(), jsonRetorno.get("cidade").asText(), jsonRetorno.get("estado").asText(), jsonRetorno.get("referencia").asText());
                Cliente cliente = new Cliente(clienteJson.get("email").asText(), clienteJson.get("nome").asText(), endereco);

                ClienteEnderecoDTO clienteRetorno = new ClienteEnderecoDTO(cliente, endereco);
                clienteEnderecoDTO = clienteRetorno;
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 404) {
                throw new EntityNotFoundException("Cliente/Endereço não Localizado");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return clienteEnderecoDTO;
    }

}
