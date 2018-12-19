package com.algaworks.moneyapi.exceptionhandler;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@ControllerAdvice
public class AlgamoneyExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        return handleExceptionInternal(ex, criarListErros(ex, "mensagem.invalida", null),
                headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        return handleExceptionInternal(ex, criarListaErros(ex.getBindingResult()), headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers,
                                                                         HttpStatus status, WebRequest request) {
        ;
        return handleExceptionInternal(ex, criarListErros(ex, null, null), headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({EmptyResultDataAccessException.class, NoSuchElementException.class})
    public ResponseEntity<Object> handleEmptyResultDataAccessException(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, criarListErros(ex, "recurso.nao-encontrado", request),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }


    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        return handleExceptionInternal(ex, criarListErros(ex, "recurso.nao-permitido", null),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }


    private List<Erro> criarListaErros(BindingResult result) {
        List<Erro> erros = new ArrayList<>();

        for (FieldError fieldError : result.getFieldErrors()) {
            String msgUser = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            String msgDev = fieldError.toString();
            erros.add(new Erro(msgUser, msgDev));
        }

        return erros;
    }

    private List<Erro> criarListErros(Exception ex, String msgResource, WebRequest request) {
        String msgUser = msgResource != null ? messageSource.getMessage(msgResource, null, LocaleContextHolder.getLocale())
                : ex.getLocalizedMessage();
        String msgDev = request == null ? ExceptionUtils.getRootCauseMessage(ex) : ExceptionUtils.getRootCauseMessage(ex)
                + " for " + ((ServletWebRequest) request).getRequest().getRequestURI();
        return Arrays.asList(new Erro(msgUser, msgDev));
    }

    private class Erro {
        private String msgUser;
        private String msgDev;

        public Erro(String msgUser, String msgDev) {
            this.msgUser = msgUser;
            this.msgDev = msgDev;
        }

        public String getMsgUser() {
            return msgUser;
        }

        public String getMsgDev() {
            return msgDev;
        }


    }
}
