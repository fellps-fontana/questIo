package com.api.quesIo.clientes.controllers;

import com.api.quesIo.clientes.Models.FuncaoModel;
import com.api.quesIo.clientes.dtos.FuncaoDto;
import com.api.quesIo.clientes.services.FuncaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/funcoes")
public class FuncaoController {

    private final FuncaoService funcaoService;

    public FuncaoController(FuncaoService funcaoService) {
        this.funcaoService = funcaoService;
    }

    // Listar funções
    @GetMapping("/listar")
    public List<FuncaoModel> listar() {
        return funcaoService.findAll();
    }

    // Criar função
    @PostMapping("/salvar")
    public ResponseEntity<?> salvar(@RequestBody @Valid FuncaoDto dto) {
        try {
            FuncaoModel funcaoSalva = funcaoService.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(funcaoSalva);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar função: " + e.getMessage());
        }
    }

    // Editar função
    @PostMapping("/editar/{id}")
    public ResponseEntity<?> editar(
            @RequestBody @Valid FuncaoDto dto,
            @PathVariable UUID id
    ) {
        try {
            FuncaoModel funcaoEditada = funcaoService.update(dto, id);
            return ResponseEntity.status(HttpStatus.CREATED).body(funcaoEditada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao editar função: " + e.getMessage());
        }
    }

    // Apagar função
    @PostMapping("/apagar/{id}")
    public ResponseEntity<String> apagar(@PathVariable UUID id) {
        try {
            funcaoService.delete(id);
            return ResponseEntity.ok("Função apagada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao apagar função: " + e.getMessage());
        }
    }
}
