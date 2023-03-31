set foreign_key_checks = 0;

delete from cidade;
delete from cozinha;
delete from estado;
delete from forma_de_pagamento;
delete from grupo;
delete from grupo_permissao;
delete from permissao;
delete from produto;
delete from restaurante;
delete from restaurante_forma_pagamento;
delete from usuario;
delete from usuario_grupo;

set foreign_key_checks = 1;

alter table cidade auto_increment= 1;
alter table cozinha auto_increment= 1;
alter table estado auto_increment= 1;
alter table  forma_de_pagamento auto_increment= 1;
alter table  grupo auto_increment= 1;
alter table  permissao auto_increment= 1;
alter table  produto auto_increment= 1;
alter table  restaurante auto_increment= 1;
alter table  usuario auto_increment= 1;

insert into cozinha(id,nome) values(1,'Brasileira');
insert into cozinha(id,nome) values(2,'Tailandesa');
insert into cozinha(id,nome) values(3,'Indiana');
insert into cozinha(id,nome) values(4,'Argentina');

insert into estado(nome) values('Rio de Janeiro');
insert into estado(nome) values('Amazonas');


insert into cidade(nome, estado_id) values('Petrópolis',1);
insert into cidade(nome, estado_id) values('Niteroi',1);
insert into cidade(nome, estado_id) values('Manaus',2);
insert into cidade(nome, estado_id) values('Barreirinha',2);


insert into restaurante(nome, taxa_frete,cozinha_id,data_cadastro, data_atualizacao,endereco_cep,endereco_logradouro,endereco_numero,endereco_complemento,endereco_bairro,endereco_cidade_id)values('Jacira',10.00,1,utc_timestamp,utc_timestamp,'20854-768','Rua Manuel Gonsales', '55', 'Apt166', 'Rio de Janeiro', 2);
insert into restaurante (nome, taxa_frete,cozinha_id,data_cadastro, data_atualizacao) values ('JONNY CRUISE',25.96,1,utc_timestamp,utc_timestamp);
insert into restaurante (nome,taxa_frete, cozinha_id,data_cadastro, data_atualizacao) values ('Jurema Food', 0.8,2,utc_timestamp,utc_timestamp);
insert into restaurante (nome,taxa_frete, cozinha_id,data_cadastro, data_atualizacao) values ('Tuk Tuk Delivery', 9.45,2,utc_timestamp,utc_timestamp);
insert into restaurante (nome,taxa_frete, cozinha_id,data_cadastro, data_atualizacao) values ('Java SteakHouse', 15,3,utc_timestamp,utc_timestamp);
insert into restaurante (nome,taxa_frete, cozinha_id,data_cadastro, data_atualizacao) values ('Bar da Maria',6,4,utc_timestamp,utc_timestamp);
insert into restaurante (nome,taxa_frete, cozinha_id,data_cadastro, data_atualizacao) values ('Lanchonete do Tio Sam',11,4,utc_timestamp,utc_timestamp);


insert into forma_de_pagamento (descricao) values('dinheiro');
insert into forma_de_pagamento (descricao) values('cartão de débito');
insert into forma_de_pagamento (descricao) values('cartão de crédito');


insert into permissao (nome,descricao) values('gerente','permissão para gerenciar os pagamentos');
insert into permissao (nome,descricao) values('contrataçao','permissão para contratar funcionarios');


insert into restaurante_forma_pagamento(restaurante_id, forma_pagamento_id) values (1,1),(1,2),(1,3),(3,2),(3,3);

insert into produto(id, nome, descricao, preco, ativo, restaurante_id)values(1,'Salada Árabe','salada composta de alface americano, grãos de bico, alcachofras e sementes especias organicas, tomate cereja e creme de ricota light', 45.90, true, 2);
insert into produto(id, nome, descricao, preco, ativo, restaurante_id)values(2,'Salada Brasileira','salada composta de alface americano, grãos de bico, alcachofras e sementes especias organicas, tomate cereja e creme de ricota light', 60.00, false, 1);


insert into grupo(id, nome) values(1,'RECURSOS HUMANOS');
insert into grupo(id, nome) values(2,'ADMINISTRAÇÃO');


insert into grupo_permissao(grupo_id,permissao_id) values(1,2);
insert into grupo_permissao(grupo_id,permissao_id) values(2,1);




insert into usuario(id,nome,email, senha, data_cadastro) values(1,'Isabella Meirelles','isabellaRh@gmail.com', '2547788964Ih', utc_timestamp);
insert into usuario(id,nome, email, senha, data_cadastro) values(2,'Jeferson Cristo', 'jefersonGestor@gmail.com', 'jefao2557Isa', utc_timestamp);

insert into usuario_grupo(usuario_id,grupo_id)values(1,1);
insert into usuario_grupo(usuario_id,grupo_id)values(2,2);
