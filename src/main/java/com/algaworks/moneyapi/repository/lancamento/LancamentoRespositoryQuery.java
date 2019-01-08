package com.algaworks.moneyapi.repository.lancamento;

import com.algaworks.moneyapi.model.Lancamento;
import com.algaworks.moneyapi.repository.filter.LancamentoFilter;

import java.util.List;

public interface LancamentoRespositoryQuery {

    List<Lancamento> filtrar(LancamentoFilter filter);
}
