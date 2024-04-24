package com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.endereco.model;

import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.cliente.model.Cliente;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@Table(name = "tb_endereco")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

  private Integer id;

  private Cliente cliente;

  private String logradouro;

  private String numero;

  private String bairro;

  private String complemento;

  private Long cep;

  private String cidade;

  private String estado;

  private String referencia;

}
