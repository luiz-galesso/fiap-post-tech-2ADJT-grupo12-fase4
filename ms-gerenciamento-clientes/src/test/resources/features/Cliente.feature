# language: pt

Funcionalidade: API - Mensagens

  @smoke
  Cenario: Registrar Mensagem
    Quando registrar uma nova mensagem
    Entao a mensagem é registrada com sucesso
    E deve ser apresentada

  @smoke
  Cenario: Buscar Mensagem
    Dado que uma mensagem ja foi publicada
    Quando efetuar a busca da mensagem
    Entao a mensagem é exibida com sucesso

  @low
  Cenario: Alterar Mensagem
    Dado que uma mensagem ja foi publicada
    Quando efetuar requisição para alteração da mensagem
    Entao a mensagem é atualizada com sucesso
    E deve ser apresentada

  @high
  Cenario: Remover Mensagem
    Dado que uma mensagem ja foi publicada
    Quando requisitar a remoção da mensagem
    Entao a mensagem é removida com sucesso

  @ignore
  Cenario: Remover Mensagem de uma forma diferente
    Dado que uma mensagem ja foi publicada
    Quando requisitar a remoção da mensagem
    Entao a mensagem é removida com sucesso