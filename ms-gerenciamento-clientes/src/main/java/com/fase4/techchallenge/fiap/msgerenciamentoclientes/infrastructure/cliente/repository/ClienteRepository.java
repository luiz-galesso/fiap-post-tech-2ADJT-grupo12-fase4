package com.fase4.techchallenge.fiap.msgerenciamentoclientes.infrastructure.cliente.repository;

import com.fase4.techchallenge.fiap.msgerenciamentoclientes.entity.cliente.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String> {

}
