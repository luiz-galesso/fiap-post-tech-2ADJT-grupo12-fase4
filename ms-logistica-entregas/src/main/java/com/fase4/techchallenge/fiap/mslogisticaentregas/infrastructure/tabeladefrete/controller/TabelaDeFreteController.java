package com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.tabeladefrete.controller;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.tabeladefrete.model.TabelaDeFrete;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.tabeladefrete.ObterTabelaDeFretePeloCepOrigemECepDestinoDisponiveis;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.tabeladefrete.ObterTabelaDeFretePeloId;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.tabeladefrete.RemoverTabelaDeFretePeloId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tabelasdefrete")
@Tag(name = "Tabelas de Frete", description = "Serviços para manipular as tabelas de frete")
public class TabelaDeFreteController {
    private final ObterTabelaDeFretePeloId obterTabelaDeFretePeloId;
    private final ObterTabelaDeFretePeloCepOrigemECepDestinoDisponiveis obterTabelaDeFretePeloCepOrigemECepDestinoDisponiveis;
    private final RemoverTabelaDeFretePeloId removerTabelaDeFretePeloId;

    public TabelaDeFreteController(ObterTabelaDeFretePeloId obterTabelaDeFretePeloId
            , ObterTabelaDeFretePeloCepOrigemECepDestinoDisponiveis obterTabelaDeFretePeloCepOrigemECepDestinoDisponiveis
            , RemoverTabelaDeFretePeloId removerTabelaDeFretePeloId) {
        this.obterTabelaDeFretePeloId = obterTabelaDeFretePeloId;
        this.obterTabelaDeFretePeloCepOrigemECepDestinoDisponiveis = obterTabelaDeFretePeloCepOrigemECepDestinoDisponiveis;
        this.removerTabelaDeFretePeloId = removerTabelaDeFretePeloId;
    }

    @Operation(summary = "Busca tabela de frete pelo Id", description = "Serviço utilizado para buscar uma tabela de frete pelo Id.")
    @GetMapping(value = "/{id}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> findById(@PathVariable Long id) {
        var tabelaDeFrete = obterTabelaDeFretePeloId.execute(id);
        return new ResponseEntity<>(tabelaDeFrete, HttpStatus.OK);
    }

    @Operation(summary = "Remove tabela de frete pelo Id", description = "Serviço utilizado para remover uma tabela de frete pelo Id.")
    @DeleteMapping(value = "/{id}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id) {
        var removeuTabelaDeFrete = removerTabelaDeFretePeloId.execute(id);
        return new ResponseEntity<>("Tabela de Frete Removida", HttpStatus.OK);
    }

    @Operation(summary = "Obter tabelas de frete disponíveis para Origem x Destino", description = "Serviço utilizado para listar as tabelas de frete disponíveis")
    @GetMapping(value = "/cotar", produces = "application/json")
    @Transactional
    public ResponseEntity<?> listarTabelasDeFreteDisponiveisPorRota(@RequestParam("cepOrigem") String cepOrigem, @RequestParam("cepDestino") String cepDestino) {
        List<TabelaDeFrete> tabelasDisponiveis = this.obterTabelaDeFretePeloCepOrigemECepDestinoDisponiveis.execute(cepOrigem, cepDestino);
        return new ResponseEntity<>(tabelasDisponiveis, HttpStatus.OK);
    }
}
