package com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entregador.controller;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.model.Entregador;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.tabeladefrete.model.TabelaDeFrete;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entregador.controller.dto.EntregadorInsertDTO;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entregador.controller.dto.EntregadorUpdateDTO;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.tabeladefrete.controller.dto.TabelaDeFreteInsertDTO;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.tabeladefrete.controller.dto.TabelaDeFreteUpdateDTO;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.entregador.*;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.exception.BusinessErrorException;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.exception.EntityNotFoundException;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.tabeladefrete.AtualizarTabelaDeFrete;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.tabeladefrete.CadastrarTabelaDeFrete;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.tabeladefrete.ObterTabelaDeFretePeloIdEntregador;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entregador")
@Tag(name = "Entregadores", description = "Serviços para manipular os entregadores")
public class EntregadorController {
    private final CadastrarEntregador cadastrarEntregador;
    private final AtualizarEntregador atualizarEntregador;
    private final ObterEntregadorPeloId obterEntregadorPeloId;
    private final AlocarRecursoEntregador alocarRecursoEntregador;
    private final DesalocarRecursoEntregador desalocarRecursoEntregador;
    private final CadastrarTabelaDeFrete cadastrarTabelaDeFrete;
    private final AtualizarTabelaDeFrete atualizarTabelaDeFrete;
    private final ObterTabelaDeFretePeloIdEntregador obterTabelaDeFretePeloIdEntregador;

    public EntregadorController(CadastrarEntregador cadastrarEntregador, AtualizarEntregador atualizarEntregador, ObterEntregadorPeloId obterEntregadorPeloId, AlocarRecursoEntregador alocarRecursoEntregador, DesalocarRecursoEntregador desalocarRecursoEntregador, CadastrarTabelaDeFrete cadastrarTabelaDeFrete, AtualizarTabelaDeFrete atualizarTabelaDeFrete, ObterTabelaDeFretePeloIdEntregador obterTabelaDeFretePeloIdEntregador) {
        this.cadastrarEntregador = cadastrarEntregador;
        this.atualizarEntregador = atualizarEntregador;
        this.obterEntregadorPeloId = obterEntregadorPeloId;
        this.alocarRecursoEntregador = alocarRecursoEntregador;
        this.desalocarRecursoEntregador = desalocarRecursoEntregador;
        this.cadastrarTabelaDeFrete = cadastrarTabelaDeFrete;
        this.atualizarTabelaDeFrete = atualizarTabelaDeFrete;
        this.obterTabelaDeFretePeloIdEntregador = obterTabelaDeFretePeloIdEntregador;
    }

    @Operation(summary = "Realiza o cadastro de um entregador", description = "Serviço utilizado para cadastro de Entregador")
    @PostMapping(produces = "application/json")
    @Transactional
    public ResponseEntity<?> create(@RequestBody EntregadorInsertDTO entregadorInsertDTO){
        try {
            Entregador entregador = cadastrarEntregador.execute(entregadorInsertDTO);
            return new ResponseEntity<>(entregador, HttpStatus.CREATED);
        } catch (BusinessErrorException businessErrorException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(businessErrorException.getMessage());
        }
    }

    @Operation(summary = "Alterar os dados do entregador", description = "Serviço utilizado para alterar os dados de entregador.")
    @PutMapping(value = "/{id}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody EntregadorUpdateDTO entregadorUpdateDTO){
        try {
            var entregadorRetorno = atualizarEntregador.execute(id, entregadorUpdateDTO);
            return new ResponseEntity<>(entregadorRetorno, HttpStatus.ACCEPTED);
        } catch (BusinessErrorException businessErrorException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(businessErrorException.getMessage());
        }
    }

    @Operation(summary = "Busca um entregador pelo Id", description =  "Serviço utilizado para buscar um entregador pelo Id.")
    @GetMapping(value = "/{id}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> findById(@PathVariable Long id){
        try {
            var entregador = obterEntregadorPeloId.execute(id);
            return new ResponseEntity<>(entregador,HttpStatus.OK);
        } catch (EntityNotFoundException entityNotFoundException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(entityNotFoundException.getMessage());
        }
    }

    @Operation(summary = "Alocar um recurso de um entregador", description = "Serviço utilizado para alocar recursos de entregador.")
    @PutMapping(value = "/{id}/alocar", produces = "application/json")
    @Transactional
    public ResponseEntity<?> alocar(@PathVariable Long id){
        try {
            var alocarRecursoEntregadorRetorno = alocarRecursoEntregador.execute(id);
            return new ResponseEntity<>(alocarRecursoEntregadorRetorno, HttpStatus.ACCEPTED);
        } catch (BusinessErrorException businessErrorException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(businessErrorException.getMessage());
        }
    }

    @Operation(summary = "Desalocar um recurso de um entregador", description = "Serviço utilizado para desalocar recursos de entregador.")
    @PutMapping(value = "/{id}/desalocar", produces = "application/json")
    @Transactional
    public ResponseEntity<?> desalocar(@PathVariable Long id){
        try {
            var desalocarRecursoEntregadorRetorno = desalocarRecursoEntregador.execute(id);
            return new ResponseEntity<>(desalocarRecursoEntregadorRetorno, HttpStatus.ACCEPTED);
        } catch (BusinessErrorException businessErrorException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(businessErrorException.getMessage());
        }
    }

    @Operation(summary = "Realiza o cadastro de uma nova tabela de frete para o Entregador",description = "Serviço utilizado para cadastro de tabela de frete para um Entregador")
    @PostMapping(value = "/{idEntregador}/tabeladefrete", produces = "application/json")
    @Transactional
    public ResponseEntity<?> create(@PathVariable Long idEntregador, @RequestBody TabelaDeFreteInsertDTO tabelaDeFreteInsertDTO){
        try {
            TabelaDeFrete tabelaDeFrete = cadastrarTabelaDeFrete.execute(idEntregador,tabelaDeFreteInsertDTO);
            return new ResponseEntity<>(tabelaDeFrete, HttpStatus.CREATED);
        } catch (BusinessErrorException businessErrorException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(businessErrorException.getMessage());
        }
    }

    @Operation(summary = "Altera os dados da tabela de frete do Entregador", description = "Serviço utilizado para alterar os dados da tabela de frete do Entregador.")
    @PutMapping(value = "/{idEntregador}/tabeladefrete/{idTabelaFrete}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> update(@PathVariable Long idEntregador, @PathVariable Long idTabelaFrete, @RequestBody TabelaDeFreteUpdateDTO tabelaDeFreteUpdateDTO) {
        try {
            var tabelaDeFreteRetorno = atualizarTabelaDeFrete.execute(idEntregador,idTabelaFrete,tabelaDeFreteUpdateDTO);
            return new ResponseEntity<>(tabelaDeFreteRetorno,HttpStatus.ACCEPTED);
        } catch( BusinessErrorException businessErrorException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(businessErrorException.getMessage());
        }
    }

    @Operation(summary = "Busca um entregador pelo Id", description =  "Serviço utilizado para buscar um entregador pelo Id.")
    @GetMapping(value = "/{idEntregador}/tabeladefrete", produces = "application/json")
    @Transactional
    public ResponseEntity<?> listTabelasDeFrete(@PathVariable Long idEntregador){
        List<TabelaDeFrete> tabelasDeFrete = obterTabelaDeFretePeloIdEntregador.execute(idEntregador);
            return new ResponseEntity<>(tabelasDeFrete,HttpStatus.OK);
    }

}
