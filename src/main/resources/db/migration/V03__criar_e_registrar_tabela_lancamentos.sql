CREATE TABLE lancamentos(
  codigo bigint(20) PRIMARY KEY AUTO_INCREMENT,
  descricao varchar(50) not null,
  data_vencimento date not null,
  data_pagamento date,
  valor decimal(10,2) not null,
  observacao varchar(100),
  tipo varchar (20) not null,
  codigo_categoria bigint(20) not null,
  codigo_pessoa bigint(20) not null,
  foreign key (codigo_categoria) references categorias(codigo),
  foreign key (codigo_pessoa) references pessoas(codigo)
)ENGINE=InnoDB default CHARSET=utf8;

insert into lancamentos(descricao,data_vencimento,data_pagamento,valor,observacao,tipo,codigo_categoria,codigo_pessoa)values ('PAGAMENTO FARMACIA','2018-12-13','2019-02-01',150.80,'Nao esquecer de cobrar','DESPESA',3,2);