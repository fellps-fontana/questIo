package com.api.quesIo.questio.controllers;

import com.api.quesIo.questio.Models.QuestionarioModel;
import com.api.quesIo.questio.dtos.QuestionarioDto;
import com.api.quesIo.questio.services.QuestionarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// CORREÇÃO 1: Libera a porta do Angular
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/forms")
public class QuestionarioController {

    private final QuestionarioService service;

    public QuestionarioController(QuestionarioService service) {
        this.service = service;
    }

    // Listar todos
    @GetMapping
    public ResponseEntity<List<QuestionarioModel>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    // Criar novo
    @PostMapping
    public ResponseEntity<QuestionarioModel> create(@RequestBody @Valid QuestionarioDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    // CORREÇÃO 2: Adicionei para o Front poder buscar um form específico (edição/visualização)
    @GetMapping("/{id}")
    public ResponseEntity<QuestionarioModel> getById(@PathVariable Long id) {
        // Você precisará adicionar o método 'findById' no seu Service se não tiver
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CORREÇÃO 3: Adicionei para o botão de Excluir do Front funcionar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // Você precisará adicionar o método 'delete' no seu Service se não tiver
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}