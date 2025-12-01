package com.api.quesIo.questio.controllers;

import com.api.quesIo.questio.dtos.HospedeDto;
import com.api.quesIo.questio.dtos.HospedeResponseDto; // Importante!
import com.api.quesIo.questio.dtos.QuestionarioDto;
import com.api.quesIo.questio.services.HospedeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200") // Origem exata do Angular
@RestController
@RequestMapping("/hospede") // Ou "/clientes", verifique como está no seu front
public class HospedeController {

    private final HospedeService hospedeService;

    public HospedeController(HospedeService hospedeService) {
        this.hospedeService = hospedeService;
    }

    // CORREÇÃO 1: O retorno agora é uma lista de DTOs (com o link)
    @GetMapping("/listar")
    public List<HospedeResponseDto> list() {
        return hospedeService.findAll();
    }

    // CORREÇÃO 2: O service agora devolve DTO, não Model
    @PostMapping("/salvar")
    public ResponseEntity<?> salvar(@RequestBody @Valid HospedeDto dto) {
        try {
            HospedeResponseDto produtoSalvo = hospedeService.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao salvar: " + e.getMessage());
        }
    }

    // CORREÇÃO 3: Ordem dos parâmetros e Tipo de retorno
    @PostMapping("/editar/{id}")
    public ResponseEntity<?> editar(
            @RequestBody @Valid HospedeDto dto,
            @PathVariable(value = "id") UUID id
    ) {
        try {
            // ATENÇÃO: A ordem correta é (id, dto) conforme definimos no Service
            HospedeResponseDto produtoEditado = hospedeService.update(id, dto);
            return ResponseEntity.ok(produtoEditado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: " + e.getMessage());
        }
    }

    @PostMapping("/apagar/{id}")
    public ResponseEntity<String> apagar(@PathVariable UUID id) {
        try {
            hospedeService.delete(id);
            return ResponseEntity.ok("Cliente apagado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: " + e.getMessage());
        }
    }

    // PATCH /clientes/{hospedeId}/atribuir/{formId}
    @PatchMapping("/{hospedeId}/atribuir/{formId}")
    public ResponseEntity<Void> atribuirFormulario(
            @PathVariable UUID hospedeId,
            @PathVariable Long formId) {

        hospedeService.atribuirQuestionario(hospedeId, formId);
        return ResponseEntity.ok().build();
    }

    // Rota para desatribuir (opcional)
    @PatchMapping("/{hospedeId}/desatribuir")
    public ResponseEntity<Void> desatribuir(@PathVariable UUID hospedeId) {
        hospedeService.atribuirQuestionario(hospedeId, null);
        return ResponseEntity.ok().build();
    }

    // GET /hospede/public/{id}
    @GetMapping("/public/{id}")
    public ResponseEntity<QuestionarioDto> pegarFormulario(@PathVariable UUID id) {
        // Esse método busca o questionário vinculado ao hóspede
        return ResponseEntity.ok(hospedeService.buscarQuestionarioDoHospede(id));
    }
}