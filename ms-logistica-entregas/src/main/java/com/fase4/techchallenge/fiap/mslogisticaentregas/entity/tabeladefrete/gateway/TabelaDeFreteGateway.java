package com.fase4.techchallenge.fiap.mslogisticaentregas.entity.tabeladefrete.gateway;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.tabeladefrete.model.TabelaDeFrete;
import com.fase4.techchallenge.fiap.mslogisticaentregas.infrastructure.tabeladefrete.repository.TabelaDeFreteRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TabelaDeFreteGateway {
    private final TabelaDeFreteRepository tabelaDeFreteRepository;

    public TabelaDeFreteGateway(TabelaDeFreteRepository tabelaDeFreteRepository) { this.tabelaDeFreteRepository = tabelaDeFreteRepository;}

    public TabelaDeFrete create(TabelaDeFrete tabelaDeFrete) { return this.tabelaDeFreteRepository.save(tabelaDeFrete);}

    public TabelaDeFrete update(TabelaDeFrete tabelaDeFrete) { return this.tabelaDeFreteRepository.save(tabelaDeFrete);}

    public Optional<TabelaDeFrete> findById(Long id) { return this.tabelaDeFreteRepository.findById(id);}

    public Optional<TabelaDeFrete> findTabelaDeFreteByCepOrigemAndCepDestinoAndEntregador(String cepOrigem, String cepDestino, Long idEntregador) { return this.tabelaDeFreteRepository.findTabelaDeFreteByCepOrigemAndCepDestinoAndEntregador_Id(cepOrigem,cepDestino,idEntregador);}

    public void remove(Long id) { tabelaDeFreteRepository.deleteById(id);}

    public List<TabelaDeFrete> findAllByIdEntregador(Long idEntregador) { return this.tabelaDeFreteRepository.findAllByEntregador_Id(idEntregador);}

    public Optional<TabelaDeFrete> findTabelaDeFreteByIdEntregadorAndCepOrigemAndCepDestinoAndIdTabelaNot(Long idEntregador, String cepOrigem, String cepDestino, Long idTabelaDeFrete) {
        return this.tabelaDeFreteRepository.findTabelaDeFreteByEntregador_IdAndCepOrigemAndCepDestinoAndIdNot(idEntregador,cepOrigem,cepDestino,idTabelaDeFrete);
    }

    public List<TabelaDeFrete> obterTabelaDeFretePeloCepOrigemECepDestinoDisponiveis(String cepOrigem, String cepDestino) {
        return this.tabelaDeFreteRepository.findAllByCepOrigemAndCepDestinoAndEntregador_QuantidadeRecursosDisponiveisGreaterThanAndEntregador_SituacaoOrderByValorFrete(cepOrigem,cepDestino,0L,"ATIVO");
    }

}

