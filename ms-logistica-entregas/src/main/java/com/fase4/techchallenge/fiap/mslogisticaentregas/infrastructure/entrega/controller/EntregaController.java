package com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entrega.controller;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entrega.model.Entrega;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entrega.controller.dto.EntregaInsertDTO;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entrega.controller.dto.RecebedorDTO;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.entrega.CadastraEntrega;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.entrega.EmRotaDeEntrega;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.entrega.ObtemTodasEntregas;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.entrega.RealizaEntrega;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/entregas")
@AllArgsConstructor
@Tag(name = "Entregas", description = "Serviços para manipular as entregas")
public class EntregaController {

    private final CadastraEntrega cadastraEntrega;
    private final EmRotaDeEntrega emRotaDeEntrega;
    private final RealizaEntrega realizaEntrega;
    private final ObtemTodasEntregas obtemTodasEntregas;

    @Operation(summary = "Realiza o cadastro de um entrega", description = "Serviço utilizado para cadastro de Entrega")
    @PostMapping(produces = "application/json")
    @Transactional
    public ResponseEntity<?> create(@RequestBody EntregaInsertDTO entregaInsertDTO) {
        Entrega entrega = cadastraEntrega.execute(entregaInsertDTO);
        return new ResponseEntity<>(entrega, HttpStatus.CREATED);
    }

    @Operation(summary = "Atualiza o status da entrega para em rota de entrega", description = "Serviço utilizado para atualizar o status da entrega para em rota de entrega.")
    @PutMapping(value = "/{id}/em-rota-de-entrega", produces = "application/json")
    @Transactional
    public ResponseEntity<?> emRotaDeEntrega(@PathVariable Long id) {
        var entrega = emRotaDeEntrega.execute(id);
        return new ResponseEntity<>(entrega, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Atualiza o status da entrega para entregue", description = "Serviço utilizado para atualizar o status da entrega para Entregue.")
    @PutMapping(value = "/{id}/entrega-realizada", produces = "application/json")
    @Transactional
    public ResponseEntity<?> realizaEntrega(@PathVariable Long id, @RequestBody RecebedorDTO recebedorDTO) {
        var entrega = realizaEntrega.execute(id, recebedorDTO.getNomeRecebedor());
        return new ResponseEntity<>(entrega, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Obtem todas entregas", description = "Serviço utilizado para obter todas entregas.")
    @GetMapping(produces = "application/json")
    @Transactional
    public ResponseEntity<?> obtemTodasEntregas() {
        var entregas = obtemTodasEntregas.execute();
        return new ResponseEntity<>(entregas, HttpStatus.OK);
    }

}
