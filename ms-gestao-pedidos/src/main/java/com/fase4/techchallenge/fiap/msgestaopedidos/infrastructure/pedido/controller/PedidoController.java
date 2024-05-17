package com.fase4.techchallenge.fiap.msgestaopedidos.infrastructure.pedido.controller;

import com.fase4.techchallenge.fiap.msgestaopedidos.entity.model.Pedido;
import com.fase4.techchallenge.fiap.msgestaopedidos.infrastructure.pedido.controller.dto.PedidoInsertDTO;
import com.fase4.techchallenge.fiap.msgestaopedidos.infrastructure.pedido.controller.dto.PedidoUpdateDTO;
import com.fase4.techchallenge.fiap.msgestaopedidos.usecase.pedido.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
@AllArgsConstructor
@Tag(name = "Pedidos", description = "Serviços para manipular os pedidos")
public class PedidoController {

    private final CadastrarPedido cadastrarPedido;
    private final AtualizarPedido atualizarPedido;
    private final ObterPedidoPeloId obterPedidoPeloId;
    private final RemoverPedidoPeloId removerPedidoPeloId;
    private final AprovarPagamento aprovarPagamento;
    private final EntregarPedido entregarPedido;
    private final ObtemPedidosPeloCliente obtemPedidosPeloCliente;

    @Operation(summary = "Realiza o cadastro de um novo Pedido", description = "Serviço utilizado para cadastro do Pedido.")
    @PostMapping(produces = "application/json")
    @Transactional
    public ResponseEntity<?> create(@RequestBody PedidoInsertDTO pedidoInsertDTO) {
        Pedido pedido = cadastrarPedido.execute(pedidoInsertDTO);
        return new ResponseEntity<>(pedido, HttpStatus.CREATED);
    }

    @Operation(summary = "Altera os dados do pedido", description = "Serviço utilizado para alterar os dados do pedido.")
    @PutMapping(value = "/{idPedido}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> update(@PathVariable Long idPedido, @RequestBody PedidoUpdateDTO pedidoUpdateDTO) {
        var pedidoRetorno = atualizarPedido.execute(idPedido, pedidoUpdateDTO);
        return new ResponseEntity<>(pedidoRetorno, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Busca o pedido pelo Id", description = "Serviço utilizado para buscar o pedido pelo Id.")
    @GetMapping(value = "/{idPedido}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> findById(@PathVariable Long idPedido) {
        var pedido = obterPedidoPeloId.execute(idPedido);
        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }

    @Operation(summary = "Remove o pedido pelo Id", description = "Serviço utilizado para remover o pedido pelo Id.")
    @DeleteMapping(value = "/{idPedido}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long idPedido) {
        var pedido = removerPedidoPeloId.execute(idPedido);
        return new ResponseEntity<>("Pedido Removido", HttpStatus.OK);
    }

    @Operation(summary = "Muda o Status do Pedido para Pagamento Aprovado", description = "Serviço utilizado para alterar o status do Pedido para Pagamento Aprovado.")
    @PutMapping(value = "/{idPedido}/aprovacao-pagamento", produces = "application/json")
    @Transactional
    public ResponseEntity<?> updateStatusPago(@PathVariable Long idPedido) {
        var pedidoRetorno = aprovarPagamento.execute(idPedido);
        return new ResponseEntity<>(pedidoRetorno, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Muda o Status do Pedido para Entregue", description = "Serviço utilizado para alterar o status do Pedido para Entregue.")
    @PutMapping(value = "/{idPedido}/confirmacao-entrega", produces = "application/json")
    @Transactional
    public ResponseEntity<?> updateStatusEntregue(@PathVariable Long idPedido) {
        var pedidoRetorno = entregarPedido.execute(idPedido);
        return new ResponseEntity<>(pedidoRetorno, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Obtem todos pedidos de um cliente", description = "Serviço utilizado para obter todos pedidos de um cliente.")
    @GetMapping(value = "/cliente/{email}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> obtemPedidosPeloCliente(@PathVariable String email) {
        var pedidos = obtemPedidosPeloCliente.execute(email);
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }
}
