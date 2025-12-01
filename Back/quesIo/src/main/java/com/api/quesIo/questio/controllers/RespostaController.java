package com.api.quesIo.questio.controllers;

import com.api.quesIo.questio.Models.RespostaModel;
import com.api.quesIo.questio.dtos.RespostaDto;
import com.api.quesIo.questio.services.RespostaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/respostas")
public class RespostaController {

    private final RespostaService service;

    public RespostaController(RespostaService service) {
        this.service = service;
    }

    // Endpoint para ENVIAR respostas (Hóspede)
    @PostMapping("/enviar/{hospedeId}")
    public ResponseEntity<List<RespostaModel>> enviar(
            @PathVariable UUID hospedeId,
            @RequestBody List<RespostaDto> respostas) {

        return ResponseEntity.ok(service.salvarRespostas(hospedeId, respostas));
    }

    // --- CORREÇÃO AQUI: Adicionado @GetMapping ---
    // Endpoint para BUSCAR respostas (Painel Admin)
    @GetMapping("/form/{formId}")
    public ResponseEntity<List<RespostaDto>> listarPorFormulario(@PathVariable Long formId) {
        return ResponseEntity.ok(service.listarPorFormulario(formId));
    }
}