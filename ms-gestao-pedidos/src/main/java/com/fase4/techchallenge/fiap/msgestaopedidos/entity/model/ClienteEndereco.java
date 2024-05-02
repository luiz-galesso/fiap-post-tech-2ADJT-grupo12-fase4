package com.fase4.techchallenge.fiap.msgestaopedidos.entity.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClienteEndereco {

    private Cliente cliente;
    private Endereco endereco;

}
