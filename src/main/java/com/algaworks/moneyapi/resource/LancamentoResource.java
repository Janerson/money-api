package com.algaworks.moneyapi.resource;

import com.algaworks.moneyapi.exceptionhandler.AlgamoneyExceptionHandler;
import com.algaworks.moneyapi.model.Lancamento;
import com.algaworks.moneyapi.repository.LancamentoRepository;
import com.algaworks.moneyapi.services.LancamentoService;
import com.algaworks.moneyapi.services.exception.PessoaInexistenteOuInativaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/lancamento")
public class LancamentoResource {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private LancamentoService lancamentoService;

    @Autowired
    private MessageSource messageSource;

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
        Lancamento lancamento1 = lancamentoService.salvar(lancamento);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(lancamento1.getCodigo()).toUri();

        return ResponseEntity.created(uri).body(lancamento);
    }

    @ExceptionHandler({PessoaInexistenteOuInativaException.class})
    public ResponseEntity<Object> handlePessoaInexistenteOuInativaExcepetion(PessoaInexistenteOuInativaException ex){
        String mensagemUsuario = messageSource.getMessage("pessoa.inexistente-ou-inativa",null, LocaleContextHolder.getLocale());
        String mensagemDev = ex.getCause() != null ? ex.getCause().toString(): ex.toString();
        List<AlgamoneyExceptionHandler.Erro> erros = Arrays.asList(new AlgamoneyExceptionHandler.Erro(mensagemUsuario, mensagemDev));
        return ResponseEntity.badRequest().body(erros);
    }

}
