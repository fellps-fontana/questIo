package com.api.quesIo.questio.controllers;

import com.api.quesIo.questio.Models.HospedeModel;
import com.api.quesIo.questio.dtos.HospedeDto;
import com.api.quesIo.questio.services.HospedeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin("/") // Melhor especificar a porta do Angular do que usar '*'
@RestController
@RequestMapping("/hospede")
public class HospedeController {

    private final HospedeService hospedeService;

    public HospedeController(HospedeService hospedeService) {
        this.hospedeService = hospedeService;
    }

    @GetMapping("/listar")
    public List<HospedeModel> list() {
        return hospedeService.findAll();
    }

    // Alterei para receber o DTO atualizado
    @PostMapping("/salvar")
    public ResponseEntity<?> salvar(@RequestBody @Valid HospedeDto dto) {
        try {
            HospedeModel produtoSalvo = hospedeService.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao salvar: " + e.getMessage());
        }
    }

    @PostMapping("/editar/{id}")
    public ResponseEntity<?> editar(
            @RequestBody @Valid HospedeDto dto,
            @PathVariable(value = "id") UUID id
    ) {
        try {
            HospedeModel produtoEditado = hospedeService.update(dto, id);
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
}