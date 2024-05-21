package com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.endereco.model;

import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.cliente.model.Cliente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "tb_endereco")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "endereco_generator")
  @SequenceGenerator(name = "endereco_generator", sequenceName = "endereco_sequence", allocationSize = 1)
  private Integer id;

  @ManyToOne
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
