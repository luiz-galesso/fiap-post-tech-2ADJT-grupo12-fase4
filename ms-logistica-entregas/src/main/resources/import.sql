INSERT INTO TB_ORIGEM (ID, LOGRADOURO, NUMERO, BAIRRO, COMPLEMENTO, CEP, CIDADE, ESTADO, REFERENCIA) VALUES (1, 'Avenida dos Elefantes', '2345','Jardim central', null, 9370390, 'São Paulo', 'SP', null);

insert into tb_entregador (cnpj,nome,quantidade_recursos_disponiveis,situacao,id) values (52123949000183,'Rapidão Entregas',8, 'ATIVO',NEXTVAL('entregador_sequence'));
insert into tb_entregador (cnpj,nome,quantidade_recursos_disponiveis,situacao,id) values (93134603000167,'Veloz Transportes',20, 'ATIVO',NEXTVAL('entregador_sequence'));
insert into tb_entregador (cnpj,nome,quantidade_recursos_disponiveis,situacao,id) values (87321261000191,'Entrega Tudo LTDA',40, 'ATIVO',NEXTVAL('entregador_sequence'));
insert into tb_entregador (cnpj,nome,quantidade_recursos_disponiveis,situacao,id) values (18829694000130,'Zigzag Transportes',2, 'ATIVO',NEXTVAL('entregador_sequence'));

insert into tb_tabela_frete (id, cep_origem, cep_destino_inicial, cep_destino_final, valor_frete, prazo_entrega_em_horas, entregador_id) values (NEXTVAL('tabelafrete_sequence'), 9370390, 00000000, 20000000, 35, 48, 1);
insert into tb_tabela_frete (id, cep_origem, cep_destino_inicial, cep_destino_final, valor_frete, prazo_entrega_em_horas, entregador_id) values (NEXTVAL('tabelafrete_sequence'), 9370390, 20000001, 40000000, 27, 24, 1);
insert into tb_tabela_frete (id, cep_origem, cep_destino_inicial, cep_destino_final, valor_frete, prazo_entrega_em_horas, entregador_id) values (NEXTVAL('tabelafrete_sequence'), 9370390, 40000001, 60000000, 37, 48, 1);
insert into tb_tabela_frete (id, cep_origem, cep_destino_inicial, cep_destino_final, valor_frete, prazo_entrega_em_horas, entregador_id) values (NEXTVAL('tabelafrete_sequence'), 9370390, 60000001, 80000000, 13, 72, 1);
insert into tb_tabela_frete (id, cep_origem, cep_destino_inicial, cep_destino_final, valor_frete, prazo_entrega_em_horas, entregador_id) values (NEXTVAL('tabelafrete_sequence'), 9370390, 80000001, 100000000, 45, 96, 1);

insert into tb_tabela_frete (id, cep_origem, cep_destino_inicial, cep_destino_final, valor_frete, prazo_entrega_em_horas, entregador_id) values (NEXTVAL('tabelafrete_sequence'), 9370390, 00000000, 20000000, 34, 24, 2);
insert into tb_tabela_frete (id, cep_origem, cep_destino_inicial, cep_destino_final, valor_frete, prazo_entrega_em_horas, entregador_id) values (NEXTVAL('tabelafrete_sequence'), 9370390, 20000001, 40000000, 65, 48, 2);
insert into tb_tabela_frete (id, cep_origem, cep_destino_inicial, cep_destino_final, valor_frete, prazo_entrega_em_horas, entregador_id) values (NEXTVAL('tabelafrete_sequence'), 9370390, 40000001, 60000000, 32, 48, 2);
insert into tb_tabela_frete (id, cep_origem, cep_destino_inicial, cep_destino_final, valor_frete, prazo_entrega_em_horas, entregador_id) values (NEXTVAL('tabelafrete_sequence'), 9370390, 60000001, 80000000, 23, 48, 2);
insert into tb_tabela_frete (id, cep_origem, cep_destino_inicial, cep_destino_final, valor_frete, prazo_entrega_em_horas, entregador_id) values (NEXTVAL('tabelafrete_sequence'), 9370390, 80000001, 100000000, 12, 72, 2);

insert into tb_tabela_frete (id, cep_origem, cep_destino_inicial, cep_destino_final, valor_frete, prazo_entrega_em_horas, entregador_id) values (NEXTVAL('tabelafrete_sequence'), 9370390, 00000000, 20000000, 20, 24, 3;
insert into tb_tabela_frete (id, cep_origem, cep_destino_inicial, cep_destino_final, valor_frete, prazo_entrega_em_horas, entregador_id) values (NEXTVAL('tabelafrete_sequence'), 9370390, 20000001, 40000000, 66, 48, 3);
insert into tb_tabela_frete (id, cep_origem, cep_destino_inicial, cep_destino_final, valor_frete, prazo_entrega_em_horas, entregador_id) values (NEXTVAL('tabelafrete_sequence'), 9370390, 40000001, 60000000, 45, 48, 3);
insert into tb_tabela_frete (id, cep_origem, cep_destino_inicial, cep_destino_final, valor_frete, prazo_entrega_em_horas, entregador_id) values (NEXTVAL('tabelafrete_sequence'), 9370390, 60000001, 80000000, 34, 96, 3);
insert into tb_tabela_frete (id, cep_origem, cep_destino_inicial, cep_destino_final, valor_frete, prazo_entrega_em_horas, entregador_id) values (NEXTVAL('tabelafrete_sequence'), 9370390, 80000001, 100000000, 99, 72, 3);

insert into tb_tabela_frete (id, cep_origem, cep_destino_inicial, cep_destino_final, valor_frete, prazo_entrega_em_horas, entregador_id) values (NEXTVAL('tabelafrete_sequence'), 9370390, 00000000, 20000000, 11, 96, 3);
insert into tb_tabela_frete (id, cep_origem, cep_destino_inicial, cep_destino_final, valor_frete, prazo_entrega_em_horas, entregador_id) values (NEXTVAL('tabelafrete_sequence'), 9370390, 20000001, 40000000, 22, 72, 3);
insert into tb_tabela_frete (id, cep_origem, cep_destino_inicial, cep_destino_final, valor_frete, prazo_entrega_em_horas, entregador_id) values (NEXTVAL('tabelafrete_sequence'), 9370390, 40000001, 60000000, 15, 72, 3);
insert into tb_tabela_frete (id, cep_origem, cep_destino_inicial, cep_destino_final, valor_frete, prazo_entrega_em_horas, entregador_id) values (NEXTVAL('tabelafrete_sequence'), 9370390, 60000001, 80000000, 99, 24, 3);
insert into tb_tabela_frete (id, cep_origem, cep_destino_inicial, cep_destino_final, valor_frete, prazo_entrega_em_horas, entregador_id) values (NEXTVAL('tabelafrete_sequence'), 9370390, 80000001, 100000000, 10, 48, 3);






