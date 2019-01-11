package com.algaworks.moneyapi.repository;

import com.algaworks.moneyapi.model.Lancamento;
import com.algaworks.moneyapi.repository.lancamento.LancamentoRespositoryQuery;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LancamentoRepository extends PagingAndSortingRepository<Lancamento, Long>, LancamentoRespositoryQuery {
}
