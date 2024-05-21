package com.fase4.techchallenge.fiap.msgestaopedidos.infrastructure.dto;

import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Cliente;
import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Endereco;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClienteEnderecoDTO {

    private Cliente cliente;
    private Endereco endereco;

}
