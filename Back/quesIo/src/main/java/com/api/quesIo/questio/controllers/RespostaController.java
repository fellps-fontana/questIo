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

    // --- ROTA NOVA (Que o Front atual está chamando) ---
    @PostMapping("/lote")
    public ResponseEntity<?> salvarLote(@RequestBody List<RespostaDto> dtos) {
        // Chama o método do service que lida com a lista completa
        service.salvarRespostasEmLote(dtos);
        return ResponseEntity.ok("Respostas salvas com sucesso!");
    }

    // --- ROTA ANTIGA (Mantida por segurança) ---
    @PostMapping("/enviar/{hospedeId}")
    public ResponseEntity<?> enviar(
            @PathVariable UUID hospedeId,
            @RequestBody List<RespostaDto> respostas) {

        // Se o seu service só tinha esse método, a gente usa ele aqui
        service.salvarRespostas(hospedeId, respostas);
        return ResponseEntity.ok("Enviado!");
    }

    // BUSCAR (Admin)
    @GetMapping("/form/{formId}")
    public ResponseEntity<List<RespostaDto>> listarPorFormulario(@PathVariable Long formId) {
        return ResponseEntity.ok(service.listarPorFormulario(formId));
    }
}