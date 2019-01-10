package com.algaworks.moneyapi.services;

import com.algaworks.moneyapi.model.Lancamento;
import com.algaworks.moneyapi.model.Pessoa;
import com.algaworks.moneyapi.repository.LancamentoRepository;
import com.algaworks.moneyapi.repository.PessoaRepository;
import com.algaworks.moneyapi.services.exception.PessoaInexistenteOuInativaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class LancamentoService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private LancamentoRepository lancamentoRepository;


    public Lancamento salvar(Lancamento lancamento) {
        Pessoa pessoa = pessoaRepository.getOne(lancamento.getPessoa().getCodigo());

        if (pessoa == null || !pessoa.isAtivo()) {
            throw new PessoaInexistenteOuInativaException();
        }
        
        return lancamentoRepository.save(lancamento);
    }

    public void removerLancamento(Long id){
        lancamentoRepository.delete(getLancamento(id));
    }

    private Lancamento getLancamento(Long id) {
        return lancamentoRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(0));
    }


}
