package com.api.quesIo.clientes.controllers;

import com.api.quesIo.clientes.Models.EmpresaModel;
import com.api.quesIo.clientes.dtos.EmpresaDto;
import com.api.quesIo.clientes.services.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/empresas")
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    // Listar todas as empresas
    @GetMapping("/listar")
    public List<EmpresaModel> list() {
        return empresaService.findAll();
    }

    // Criar nova empresa
    @PostMapping("/salvar")
    public ResponseEntity<?> salvar(@RequestBody @Valid EmpresaDto dto) {
        try {
            EmpresaModel empresaSalva = empresaService.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(empresaSalva);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar empresa: " + e.getMessage());
        }
    }

    // Editar empresa existente
    @PostMapping("/editar/{id}")
    public ResponseEntity<?> editar(
            @RequestBody @Valid EmpresaDto dto,
            @PathVariable(value = "id") UUID id
    ) {
        try {
            EmpresaModel empresaEditada = empresaService.update(dto, id);
            return ResponseEntity.status(HttpStatus.CREATED).body(empresaEditada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao editar empresa: " + e.getMessage());
        }
    }

    // Deletar empresa
    @PostMapping("/apagar/{id}")
    public ResponseEntity<String> apagar(@PathVariable UUID id) {
        try {
            empresaService.delete(id);
            return ResponseEntity.ok("Empresa apagada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao apagar empresa: " + e.getMessage());
        }
    }
}
