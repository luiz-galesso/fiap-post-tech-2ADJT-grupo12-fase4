package com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.entrega;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.cliente.model.Cliente;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entrega.enums.SituacaoEntrega;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entrega.gateway.EntregaGateway;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entrega.model.Endereco;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entrega.model.Entrega;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.origem.gateway.OrigemGateway;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.origem.model.Origem;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.tabeladefrete.gateway.TabelaDeFreteGateway;
import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.tabeladefrete.model.TabelaDeFrete;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.entrega.controller.dto.EntregaInsertDTO;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.cliente.ObtemClientePeloEmail;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.entregador.AlocarRecursoEntregador;
import com.fase4.techchallenge.fiap.mslogisticaentregas.usecase.exception.BusinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CadastraEntrega {

    private final EntregaGateway entregaGateway;

    private final OrigemGateway origemGateway;

    private final ObtemClientePeloEmail obtemClientePeloEmail;

    private final TabelaDeFreteGateway tabelaDeFreteGateway;

    private final AlocarRecursoEntregador alocarRecursoEntregador;

    public Entrega execute(EntregaInsertDTO entregaInsertDTO) {

        Optional<Entrega> entregaOptional = entregaGateway.findByIdPedido(entregaInsertDTO.getIdPedido());
        if (entregaOptional.isPresent()) {
            return entregaOptional.get();
        }

        Origem origem = origemGateway.findById(1L).orElseThrow(() ->new BusinessErrorException("Não foi possível obter a origem do pedido"));

        Cliente cliente = obtemClientePeloEmail.execute(entregaInsertDTO.getEmailCliente());

        Endereco enderecoOrigem =
                new Endereco(origem.getLogradouro(),
                        origem.getNumero(),
                        origem.getBairro(),
                        origem.getComplemento(),
                        origem.getCep(),
                        origem.getCidade(),
                        origem.getEstado(),
                        origem.getReferencia());

        Endereco enderecoDestino =
                new Endereco(entregaInsertDTO.getEnderecoDestino().getLogradouro(),
                        entregaInsertDTO.getEnderecoDestino().getNumero(),
                        entregaInsertDTO.getEnderecoDestino().getBairro(),
                        entregaInsertDTO.getEnderecoDestino().getComplemento(),
                        entregaInsertDTO.getEnderecoDestino().getCep(),
                        entregaInsertDTO.getEnderecoDestino().getCidade(),
                        entregaInsertDTO.getEnderecoDestino().getEstado(),
                        entregaInsertDTO.getEnderecoDestino().getReferencia());

        TabelaDeFrete tabelaDeFrete = tabelaDeFreteGateway.findById(entregaInsertDTO.getIdTabelaFrete()).orElseThrow(()-> new BusinessErrorException("Não foi possível obter a tabela de frete"));

        alocarRecursoEntregador.execute(tabelaDeFrete.getEntregador().getId());

        Entrega entrega =
                new Entrega(null,
                        entregaInsertDTO.getIdPedido(),
                        tabelaDeFrete.getEntregador(),
                        cliente.getEmail(),
                        cliente.getNome(),
                        tabelaDeFrete.getPrazoEntregaEmHoras(),
                        tabelaDeFrete.getValorFrete(),
                        enderecoOrigem,
                        enderecoDestino,
                        entregaInsertDTO.getDataCriacao(),
                        LocalDateTime.now(),
                        LocalDateTime.now().plusHours(tabelaDeFrete.getPrazoEntregaEmHoras()),
                        SituacaoEntrega.GERADA,
                        LocalDateTime.now(),
                        null
                        );

        return this.entregaGateway.create(entrega);

    }
}
