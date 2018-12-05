package com.algaworks.moneyapi.services;

import com.algaworks.moneyapi.model.Pessoa;
import com.algaworks.moneyapi.repository.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public Pessoa atualizar(Long id, Pessoa pessoa) {
        Pessoa pessoaSalva = getPessoa(id);
        BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
        return pessoaRepository.save(pessoaSalva);
    }

    public void atualizarPropriedadeAtivo(Long id, Boolean ativo) {
        Pessoa pessoa = getPessoa(id);
        pessoa.setAtivo(ativo);
        pessoaRepository.save(pessoa);
    }

    private Pessoa getPessoa(Long id) {
        return pessoaRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(0));
    }

}
