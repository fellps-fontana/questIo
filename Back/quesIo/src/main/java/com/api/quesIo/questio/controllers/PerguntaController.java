package com.api.quesIo.questio.controllers;

import com.api.quesIo.questio.Models.PerguntaModel;
import com.api.quesIo.questio.dtos.PerguntaDto;
import com.api.quesIo.questio.services.PerguntaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "/")
@RestController
public class PerguntaController {

    private final PerguntaService service;

    public PerguntaController(PerguntaService service) {
        this.service = service;
    }

    // Rota: POST /forms/1/questions (Adiciona pergunta ao form 1)
    @PostMapping("/forms/{formId}/questions")
    public ResponseEntity<PerguntaModel> addQuestion(
            @PathVariable Long formId,
            @RequestBody PerguntaDto dto) {
        return ResponseEntity.ok(service.addQuestion(formId, dto));
    }

    // Rota: PUT /questions/uuid-da-pergunta (Edita uma pergunta específica)
    @PutMapping("/questions/{id}")
    public ResponseEntity<PerguntaModel> updateQuestion(
            @PathVariable UUID id,
            @RequestBody PerguntaDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    // Rota: DELETE /questions/uuid-da-pergunta
    @DeleteMapping("/questions/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}