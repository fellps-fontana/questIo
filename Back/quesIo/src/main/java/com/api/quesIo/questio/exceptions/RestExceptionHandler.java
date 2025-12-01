package com.api.quesIo.questio.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice // Isso diz ao Spring: "Olhe todos os controllers"
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    // Captura nossos erros manuais (throw new RuntimeException("..."))
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value()); // 400
        body.put("error", "Erro na Requisição");
        body.put("message", ex.getMessage()); // A mensagem que você escreveu no Service

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // Você pode adicionar outros capturadores aqui se quiser
}