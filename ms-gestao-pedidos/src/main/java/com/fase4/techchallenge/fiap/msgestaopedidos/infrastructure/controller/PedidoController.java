package com.fase4.techchallenge.fiap.msgestaopedidos.infrastructure.controller;

import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Pedido;
import com.fase4.techchallenge.fiap.msgestaopedidos.infrastructure.controller.dto.PedidoInsertDTO;
import com.fase4.techchallenge.fiap.msgestaopedidos.infrastructure.controller.dto.PedidoUpdateDTO;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.AtualizarPedido;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.CadastrarPedido;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.ObterPedidoPeloId;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.RemoverPedidoPeloId;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.exception.BussinessErrorException;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.exception.EntityNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
@Tag(name = "Pedidos", description = "Serviços para manipular os pedidos")
public class PedidoController {

    private final CadastrarPedido cadastrarPedido;
    private final AtualizarPedido atualizarPedido;
    private final ObterPedidoPeloId obterPedidoPeloId;
    private final RemoverPedidoPeloId removerPedidoPeloId;

    public PedidoController(CadastrarPedido cadastrarPedido, AtualizarPedido atualizarPedido, ObterPedidoPeloId obterPedidoPeloId, RemoverPedidoPeloId removerPedidoPeloId) {
        this.cadastrarPedido = cadastrarPedido;
        this.atualizarPedido = atualizarPedido;
        this.obterPedidoPeloId = obterPedidoPeloId;
        this.removerPedidoPeloId = removerPedidoPeloId;
    }

    @Operation(summary = "Realiza o cadastro de um novo Pedido", description = "Serviço utilizado para cadastro do Pedido.")
    @PostMapping(produces = "application/json")
    @Transactional
    public ResponseEntity<?> create(@RequestBody PedidoInsertDTO pedidoInsertDTO) {
        try {
            Pedido pedido = cadastrarPedido.execute(pedidoInsertDTO);
            return new ResponseEntity<>(pedido, HttpStatus.CREATED);
        } catch (BussinessErrorException bussinessErrorException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bussinessErrorException.getMessage());
        }
    }

    @Operation(summary = "Altera os dados do pedido", description = "Serviço utilizado para alterar os dados do pedido.")
    @PutMapping(value = "/{id}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody PedidoUpdateDTO pedidoUpdateDTO) {
        try {
            var pedidoRetorno = atualizarPedido.execute(id, pedidoUpdateDTO);
            return new ResponseEntity<>(pedidoRetorno, HttpStatus.ACCEPTED);
        } catch (BussinessErrorException bussinessErrorException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bussinessErrorException.getMessage());
        }
    }

    @Operation(summary = "Busca o pedido pelo Id", description = "Serviço utilizado para buscar o pedido pelo Id.")
    @GetMapping(value = "/{id}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            var pedido = obterPedidoPeloId.execute(id);
            return new ResponseEntity<>(pedido, HttpStatus.OK);
        } catch (EntityNotFoundException entityNotFoundException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(entityNotFoundException.getMessage());
        }
    }

    @Operation(summary = "Remove o pedido pelo Id", description = "Serviço utilizado para remover o pedido pelo Id.")
    @DeleteMapping(value = "/{id}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            var pedido = removerPedidoPeloId.execute(id);
            return new ResponseEntity<>("Pedido Removido", HttpStatus.OK);
        } catch (BussinessErrorException bussinessErrorException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bussinessErrorException.getMessage());
        }
    }

}
