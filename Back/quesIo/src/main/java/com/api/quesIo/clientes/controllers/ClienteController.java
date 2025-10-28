package com.api.quesIo.clientes.controllers;

import com.api.quesIo.clientes.Models.ClienteModel;
import com.api.quesIo.clientes.dtos.ClienteDto;
import com.api.quesIo.clientes.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService clienteService;
    public ClienteController(ClienteService clienteService) { this.clienteService = clienteService; }

    @GetMapping("/listar")
    public List<ClienteModel> list() {
        return clienteService.findAll();
    }

    @PostMapping("/editar/{id}")
    public ResponseEntity<?> editar(
            @RequestBody @Valid ClienteDto dto,
            @PathVariable(value = "id") UUID id
    ) {
        try {
            ClienteModel produtoEditado = clienteService.update(dto, id);
            return ResponseEntity.status(HttpStatus.CREATED).
                    body(produtoEditado);
        } catch (Exception e) {
            return ResponseEntity.status(
                    HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: "+ e.getMessage());
        }
    }


    @PostMapping("/salvar")
    public ResponseEntity<?> salvar(
            @RequestBody @Valid ClienteDto dto) {
        ClienteModel produtoSalvo = clienteService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                produtoSalvo);
    }

    @PostMapping("/apagar/{id}")
    public ResponseEntity<String> apagar(@PathVariable UUID id) {
        try {
            clienteService.delete(id);
            return ResponseEntity.ok(
                    "Cliente apagado com sucesso!");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "Deu ruim: " +e.getMessage()
            );
        }
    }




}
