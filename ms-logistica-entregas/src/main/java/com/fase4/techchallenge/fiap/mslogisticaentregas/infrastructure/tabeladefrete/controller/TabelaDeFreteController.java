package com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.tabeladefrete.controller;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.tabeladefrete.model.TabelaDeFrete;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.tabeladefrete.controller.dto.TabelaDeFreteInsertDTO;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.tabeladefrete.controller.dto.TabelaDeFreteUpdateDTO;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.exception.BusinessErrorException;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.exception.EntityNotFoundException;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.tabeladefrete.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tabelasdefrete")
@Tag(name = "Tabelas de Frete", description = "Serviços para manipular as tabelas de frete")
public class TabelaDeFreteController {
    private final CadastrarTabelaDeFrete cadastrarTabelaDeFrete;
    private final AtualizarTabelaDeFrete atualizarTabelaDeFrete;
    private final ObterTabelaDeFretePeloId obterTabelaDeFretePeloId;
    private final ObterTabelaDeFretePeloCepOrigemECepDestino obterTabelaDeFretePeloCepOrigemECepDestino;
    private final RemoverTabelaDeFretePeloId removerTabelaDeFretePeloId;

    public TabelaDeFreteController( CadastrarTabelaDeFrete cadastrarTabelaDeFrete
    , AtualizarTabelaDeFrete atualizarTabelaDeFrete
    , ObterTabelaDeFretePeloId obterTabelaDeFretePeloId
    , ObterTabelaDeFretePeloCepOrigemECepDestino obterTabelaDeFretePeloCepOrigemECepDestino
    , RemoverTabelaDeFretePeloId removerTabelaDeFretePeloId) {
        this.cadastrarTabelaDeFrete = cadastrarTabelaDeFrete;
        this.atualizarTabelaDeFrete = atualizarTabelaDeFrete;
        this.obterTabelaDeFretePeloId = obterTabelaDeFretePeloId;
        this.obterTabelaDeFretePeloCepOrigemECepDestino = obterTabelaDeFretePeloCepOrigemECepDestino;
        this.removerTabelaDeFretePeloId = removerTabelaDeFretePeloId;
    }

    @Operation(summary = "Realiza o cadastro de uma nova tabela de frete",description = "Serviço utilizado para cadastro de tabela de frete")
    @PostMapping(produces = "application/json")
    @Transactional
    public ResponseEntity<?> create(@RequestBody TabelaDeFreteInsertDTO tabelaDeFreteInsertDTO){
        try {
            TabelaDeFrete tabelaDeFrete = cadastrarTabelaDeFrete.execute(tabelaDeFreteInsertDTO);
            return new ResponseEntity<>(tabelaDeFrete, HttpStatus.CREATED);
        } catch (BusinessErrorException businessErrorException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(businessErrorException.getMessage());
        }
    }

    @Operation(summary = "Altera os dados da tabela de frete", description = "Serviço utilizado para alterar os dados da tabela de frete.")
    @PutMapping(value = "/{id}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody TabelaDeFreteUpdateDTO tabelaDeFreteUpdateDTO) {
        try {
            var tabelaDeFreteRetorno = atualizarTabelaDeFrete.execute(id,tabelaDeFreteUpdateDTO);
            return new ResponseEntity<>(tabelaDeFreteRetorno,HttpStatus.ACCEPTED);
        } catch( BusinessErrorException businessErrorException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(businessErrorException.getMessage());
        }
    }

    @Operation(summary = "Busca tabela de frete pelo Id", description = "Serviço utilizado para buscar uma tabela de frete pelo Id.")
    @GetMapping(value = "/{id}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            var tabelaDeFrete = obterTabelaDeFretePeloId.execute(id);
            return new ResponseEntity<>(tabelaDeFrete, HttpStatus.OK);
        } catch (EntityNotFoundException entityNotFoundException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(entityNotFoundException.getMessage());
        }
    }

    @Operation(summary = "Remove tabela de frete pelo Id", description = "Serviço utilizado para remover uma tabela de frete pelo Id.")
    @DeleteMapping(value = "/{id}", produces =  "application/json")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id){
        try {
            var removeuTabelaDeFrete = removerTabelaDeFretePeloId.execute(id);
            return new ResponseEntity<>("Tabela de Frete Removida", HttpStatus.OK);
        } catch (BusinessErrorException businessErrorException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(businessErrorException.getMessage());
        }
    }
}
