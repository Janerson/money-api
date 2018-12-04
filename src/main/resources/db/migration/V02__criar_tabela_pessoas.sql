create table pessoas
(
  codigo bigint(20) primary key AUTO_INCREMENT,
  nome   varchar(50) not null,
  ativo  tinyint(0) default 0,
  logradouro varchar (50),
  numero varchar (10),
  complemento varchar (50),
  bairro varchar (50),
  cep varchar (50),
  cidade varchar (50),
  estado varchar (50)
) Engine=InnoDB default charset=utf8;