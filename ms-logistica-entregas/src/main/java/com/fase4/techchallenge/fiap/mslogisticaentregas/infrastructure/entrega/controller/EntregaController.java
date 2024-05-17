package com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entrega.controller;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entrega.model.Entrega;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.model.Entregador;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.tabeladefrete.model.TabelaDeFrete;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entrega.controller.dto.EntregaInsertDTO;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entrega.controller.dto.EntregaUpdateDTO;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.tabeladefrete.controller.dto.TabelaDeFreteInsertDTO;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.tabeladefrete.controller.dto.TabelaDeFreteUpdateDTO;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.entrega.CadastraEntrega;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.entregador.*;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.tabeladefrete.AtualizarTabelaDeFrete;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.tabeladefrete.CadastrarTabelaDeFrete;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.tabeladefrete.ObterTabelaDeFretePeloIdEntregador;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entregas")
@AllArgsConstructor
@Tag(name = "Entregas", description = "Serviços para manipular as entregas")
public class EntregaController {

    private final CadastraEntrega cadastraEntrega;
    //private final AtualizarEntregador atualizarEntregador;
    /*private final ObterEntregadorPeloId obterEntregadorPeloId;
    private final AlocarRecursoEntregador alocarRecursoEntregador;
    private final DesalocarRecursoEntregador desalocarRecursoEntregador;
    private final CadastrarTabelaDeFrete cadastrarTabelaDeFrete;
    private final AtualizarTabelaDeFrete atualizarTabelaDeFrete;
    private final ObterTabelaDeFretePeloIdEntregador obterTabelaDeFretePeloIdEntregador;*/


    @Operation(summary = "Realiza o cadastro de um entrega", description = "Serviço utilizado para cadastro de Entrega")
    @PostMapping(produces = "application/json")
    @Transactional
    public ResponseEntity<?> create(@RequestBody EntregaInsertDTO entregaInsertDTO) {
        Entrega entrega = cadastraEntrega.execute(entregaInsertDTO);
        return new ResponseEntity<>(entrega, HttpStatus.CREATED);
    }

    /*@Operation(summary = "Alterar os dados do entregador", description = "Serviço utilizado para alterar os dados de entregador.")
    @PutMapping(value = "/{id}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody EntregaUpdateDTO entregadorUpdateDTO) {
        var entregadorRetorno = atualizarEntregador.execute(id, entregadorUpdateDTO);
        return new ResponseEntity<>(entregadorRetorno, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Busca um entregador pelo Id", description = "Serviço utilizado para buscar um entregador pelo Id.")
    @GetMapping(value = "/{id}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> findById(@PathVariable Long id) {
        var entregador = obterEntregadorPeloId.execute(id);
        return new ResponseEntity<>(entregador, HttpStatus.OK);
    }

    @Operation(summary = "Alocar um recurso de um entregador", description = "Serviço utilizado para alocar recursos de entregador.")
    @PutMapping(value = "/{id}/alocar", produces = "application/json")
    @Transactional
    public ResponseEntity<?> alocar(@PathVariable Long id) {
        var alocarRecursoEntregadorRetorno = alocarRecursoEntregador.execute(id);
        return new ResponseEntity<>(alocarRecursoEntregadorRetorno, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Desalocar um recurso de um entregador", description = "Serviço utilizado para desalocar recursos de entregador.")
    @PutMapping(value = "/{id}/desalocar", produces = "application/json")
    @Transactional
    public ResponseEntity<?> desalocar(@PathVariable Long id) {
        var desalocarRecursoEntregadorRetorno = desalocarRecursoEntregador.execute(id);
        return new ResponseEntity<>(desalocarRecursoEntregadorRetorno, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Realiza o cadastro de uma nova tabela de frete para o Entregador", description = "Serviço utilizado para cadastro de tabela de frete para um Entregador")
    @PostMapping(value = "/{idEntregador}/tabeladefrete", produces = "application/json")
    @Transactional
    public ResponseEntity<?> create(@PathVariable Long idEntregador, @RequestBody TabelaDeFreteInsertDTO tabelaDeFreteInsertDTO) {
        TabelaDeFrete tabelaDeFrete = cadastrarTabelaDeFrete.execute(idEntregador, tabelaDeFreteInsertDTO);
        return new ResponseEntity<>(tabelaDeFrete, HttpStatus.CREATED);
    }

    @Operation(summary = "Altera os dados da tabela de frete do Entregador", description = "Serviço utilizado para alterar os dados da tabela de frete do Entregador.")
    @PutMapping(value = "/{idEntregador}/tabeladefrete/{idTabelaFrete}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> update(@PathVariable Long idEntregador, @PathVariable Long idTabelaFrete, @RequestBody TabelaDeFreteUpdateDTO tabelaDeFreteUpdateDTO) {
        var tabelaDeFreteRetorno = atualizarTabelaDeFrete.execute(idEntregador, idTabelaFrete, tabelaDeFreteUpdateDTO);
        return new ResponseEntity<>(tabelaDeFreteRetorno, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Busca um entregador pelo Id", description = "Serviço utilizado para buscar um entregador pelo Id.")
    @GetMapping(value = "/{idEntregador}/tabeladefrete", produces = "application/json")
    @Transactional
    public ResponseEntity<?> listTabelasDeFrete(@PathVariable Long idEntregador) {
        List<TabelaDeFrete> tabelasDeFrete = obterTabelaDeFretePeloIdEntregador.execute(idEntregador);
        return new ResponseEntity<>(tabelasDeFrete, HttpStatus.OK);
    }*/

}
