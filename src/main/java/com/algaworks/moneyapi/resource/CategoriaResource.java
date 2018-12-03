package com.algaworks.moneyapi.resource;

import com.algaworks.moneyapi.model.Categoria;
import com.algaworks.moneyapi.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categoria")
public class CategoriaResource {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public List<Categoria> listar() {
        return categoriaRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria) {
        Categoria respostaCategoria = categoriaRepository.save(categoria);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
                .buildAndExpand(categoria.getCodigo()).toUri();

        return ResponseEntity.created(uri).body(respostaCategoria);
    }

    @GetMapping("/{codigo}")
    public Categoria buscarPeloCodigo(@PathVariable("codigo") Long id){
        //TODO atualizar para versão mais atual do spring-boot
        return categoriaRepository.findOne(id);
    }
}
