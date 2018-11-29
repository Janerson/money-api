create table categorias(
  codigo bigint(20) primary key AUTO_INCREMENT,
  nome varchar(50) not null
) Engine=InnoDB default charset=utf8;

insert into categorias(nome) value ('Lazer');
insert into categorias(nome) value ('Alimentação');
insert into categorias(nome) value ('Farmacia');
insert into categorias(nome) value ('Supermercado');
insert into categorias(nome) value ('Saúde');
insert into categorias(nome) value ('Luz');
insert into categorias(nome) value ('Outros');