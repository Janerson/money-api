package com.algaworks.moneyapi.resource;

import com.algaworks.moneyapi.model.Lancamento;
import com.algaworks.moneyapi.repository.LancamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/lancamento")
public class LancamentoResource {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @GetMapping
    public List<Lancamento> listarLancamentos() {
        return lancamentoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Lancamento lancamentoPorId(@PathVariable long id) {
        return lancamentoRepository.findById(id).get();
    }

    @PostMapping
    public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento lancamento) {
        Lancamento lancamento1 = lancamentoRepository.save(lancamento);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(lancamento1.getCodigo()).toUri();

        return ResponseEntity.created(uri).body(lancamento);
    }

}
