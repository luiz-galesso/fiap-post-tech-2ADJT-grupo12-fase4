#language: pt

  Funcionalidade: Produto
    @high
    Cenario: Cadastrar produto
      Dado que tenha um produto para cadastrar
      Quando eu cadastro o produto
      Entao o produto é cadastrado
      E deverá ser apresentado

    Cenario: Buscar produto
      Dado que o produto já foi cadastrado
      Quando efetuar a busca pelo ID
      Então exibir produto

    Cenário: Atualizar os dados de um produto existente
      Dado que o produto já foi cadastrado
      E que eu deseje atualizar os dados desse produto
      Quando eu atualizo o produto com novas informações
      Então o produto deve ser atualizado com sucesso

    Cenario: Remover produto
      Dado que o produto já foi cadastrado
      Quando deletar o produto
      Entao o produto deve ser deletado
