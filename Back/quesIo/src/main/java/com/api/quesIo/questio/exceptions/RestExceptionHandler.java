package com.api.quesIo.questio.infra;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {

    // Captura erro de Chave Estrangeira (SQL Constraint)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleSqlConstraint(DataIntegrityViolationException ex) {

        Map<String, String> resposta = new HashMap<>();

        // Mensagem amigável para o Front-end
        resposta.put("titulo", "Não foi possível excluir");
        resposta.put("message", "Este registro está sendo usado em outra parte do sistema (ex: possui respostas ou vínculos). Remova os vínculos antes de excluir.");

        // Retorna Status 409 (Conflict) em vez de 500
        return ResponseEntity.status(HttpStatus.CONFLICT).body(resposta);
    }

    // (Opcional) Captura erros gerais para não mostrar stacktrace
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneral(Exception ex) {
        Map<String, String> resposta = new HashMap<>();
        resposta.put("titulo", "Erro Interno");
        resposta.put("message", "Ocorreu um erro inesperado: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resposta);
    }
}