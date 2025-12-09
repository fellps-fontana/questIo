package com.api.quesIo.questio.controllers;

import com.api.quesIo.questio.dtos.HospedeDto;
import com.api.quesIo.questio.dtos.HospedeResponseDto;
import com.api.quesIo.questio.dtos.QuestionarioDto;
import com.api.quesIo.questio.services.HospedeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*") // Liberado para testes (depois pode restringir)
@RestController
@RequestMapping("/hospedes") // CORREÇÃO: Plural (igual ao Front)
public class HospedeController {

    private final HospedeService hospedeService;

    public HospedeController(HospedeService hospedeService) {
        this.hospedeService = hospedeService;
    }

    // LISTAR (GET /hospedes)
    @GetMapping
    public ResponseEntity<List<HospedeResponseDto>> list() {
        return ResponseEntity.ok(hospedeService.findAll());
    }

    // CRIAR (POST /hospedes)
    @PostMapping
    public ResponseEntity<HospedeResponseDto> salvar(@RequestBody @Valid HospedeDto dto) {
        // Retorna 201 Created
        HospedeResponseDto salvo = hospedeService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    // EDITAR (PUT /hospedes/{id})
    @PutMapping("/{id}")
    public ResponseEntity<HospedeResponseDto> editar(
            @PathVariable UUID id,
            @RequestBody @Valid HospedeDto dto
    ) {
        return ResponseEntity.ok(hospedeService.update(id, dto));
    }

    // DELETAR (DELETE /hospedes/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> apagar(@PathVariable UUID id) {
        hospedeService.delete(id);
        return ResponseEntity.ok("Cliente apagado com sucesso!");
    }

    // ATRIBUIR (PUT /hospedes/{id}/atribuir/{formId})
    // Ajustado para PUT para bater com o Service do Angular
    @PutMapping("/{hospedeId}/atribuir/{formId}")
    public ResponseEntity<?> atribuirFormulario(
            @PathVariable UUID hospedeId,
            @PathVariable Long formId) {

        hospedeService.atribuirQuestionario(hospedeId, formId);
        return ResponseEntity.ok("Formulário atribuído!");
    }

    // DESATRIBUIR
    @PutMapping("/{hospedeId}/desatribuir")
    public ResponseEntity<?> desatribuir(@PathVariable UUID hospedeId) {
        hospedeService.atribuirQuestionario(hospedeId, null);
        return ResponseEntity.ok("Formulário desvinculado.");
    }

    // ROTA PÚBLICA (GET /hospedes/publico/{id}/questionario)
    // Essa é a rota que o Link do E-mail e o componente PublicForm usam
    @GetMapping("/publico/{id}/questionario")
    public ResponseEntity<QuestionarioDto> pegarFormulario(@PathVariable UUID id) {
        return ResponseEntity.ok(hospedeService.buscarQuestionarioDoHospede(id));
    }
}