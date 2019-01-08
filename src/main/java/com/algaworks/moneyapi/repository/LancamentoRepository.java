package com.algaworks.moneyapi.repository;

import com.algaworks.moneyapi.model.Lancamento;
import com.algaworks.moneyapi.repository.lancamento.LancamentoRespositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRespositoryQuery {
}
