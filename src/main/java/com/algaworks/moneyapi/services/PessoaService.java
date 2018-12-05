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

    public Pessoa atualizar(Long id, Pessoa pessoa){
        Pessoa pessoaSalva = pessoaRepository.findById(id).orElseThrow(()-> new EmptyResultDataAccessException(1));
        BeanUtils.copyProperties(pessoa,pessoaSalva,"codigo");
        return pessoaRepository.save(pessoaSalva);
    }
}
