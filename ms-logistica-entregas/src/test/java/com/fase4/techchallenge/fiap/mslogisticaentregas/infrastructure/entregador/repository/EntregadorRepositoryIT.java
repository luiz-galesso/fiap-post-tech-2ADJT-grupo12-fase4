package com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entregador.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class EntregadorRepositoryIT {

    @Autowired
    private EntregadorRepository entregadorRepository;
    @Test
    void devePermitirCriarTabelaEntregador() {
        Long totalTabelasCriadas = entregadorRepository.count();
        assertThat(totalTabelasCriadas).isNotNegative();
    }
}
