package com.algaworks.moneyapi.resource;

import com.algaworks.moneyapi.model.Pessoa;
import com.algaworks.moneyapi.repository.PessoaRepository;
import com.algaworks.moneyapi.services.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pessoa")
public class PessoaResource {

    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private PessoaService pessoaService;

    @GetMapping
    public List<Pessoa> listar() {
        return pessoaRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Pessoa> salvarPesssoa(@Valid @RequestBody Pessoa pessoa) {
        Pessoa pessoaRetorno = pessoaRepository.save(pessoa);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
                .buildAndExpand(pessoaRetorno.getCodigo()).toUri();
        return ResponseEntity.created(uri).body(pessoaRetorno);

    }

    @GetMapping("/{codigo}")
    public Pessoa buscarPeloId(@PathVariable("codigo") long codigo) {
        return pessoaRepository.findById(codigo).get();
    }

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void apagarPeloId(@PathVariable("codigo") long codigo) {
        pessoaRepository.deleteById(codigo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> atualizar(@PathVariable("id") Long id, @Valid @RequestBody Pessoa pessoa){
        return ResponseEntity.ok(pessoaService.atualizar(id,pessoa));

    }
}
