package com.fase4.techchallenge.fiap.mslogisticaentregas.entity.tabeladefrete.gateway;

import com.fase4.techchallenge.fiap.mslogisticaentregas.entity.entregador.model.Entregador;
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

    public void remove(Long id) { tabelaDeFreteRepository.deleteById(id);}

    public Optional<TabelaDeFrete> findTabelaDeFreteByCepOrigemAndCepDestinoInicialAndCepDestinoFinalAndEntregador(Long cepOrigem, Long cepDestinoInicial, Long cepDestinoFinal, Entregador entregador)
    {
        return tabelaDeFreteRepository.findTabelaDeFreteByCepOrigemAndCepDestinoInicialAndCepDestinoFinalAndEntregador(cepOrigem, cepDestinoInicial, cepDestinoFinal, entregador);
    }

    public List<TabelaDeFrete> findByCepOrigemAndCepDestinoInicialLessThanEqualAndCepDestinoFinalGreaterThan(Long cepOrigem, Long cepDestino){
        return tabelaDeFreteRepository.findByCepOrigemAndCepDestinoInicialLessThanEqualAndCepDestinoFinalGreaterThan(cepOrigem,cepDestino,cepDestino);
    }


}

