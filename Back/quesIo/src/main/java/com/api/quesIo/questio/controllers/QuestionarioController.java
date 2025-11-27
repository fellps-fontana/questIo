package com.api.quesIo.questio.controllers;

import com.api.quesIo.questio.Models.QuestionarioModel;
import com.api.quesIo.questio.dtos.QuestionarioDto;
import com.api.quesIo.questio.services.QuestionarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "/")
@RestController
@RequestMapping("/forms") // Padrão REST: substantivos no plural
public class QuestionarioController {

    private final QuestionarioService service;

    public QuestionarioController(QuestionarioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<QuestionarioModel> create(@RequestBody @Valid QuestionarioDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<QuestionarioModel>> list() {
        return ResponseEntity.ok(service.findAll());
    }
}