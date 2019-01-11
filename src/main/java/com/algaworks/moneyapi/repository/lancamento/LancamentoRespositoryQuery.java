package com.algaworks.moneyapi.repository.lancamento;

import com.algaworks.moneyapi.model.Lancamento;
import com.algaworks.moneyapi.repository.filter.LancamentoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LancamentoRespositoryQuery {

    Page<Lancamento> filtrar(LancamentoFilter filter, Pageable pageable);
}
