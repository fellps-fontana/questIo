package com.api.quesIo.questio.controllers;

import com.api.quesIo.questio.Models.UsuarioModel;
import com.api.quesIo.questio.Repositorys.UsuarioRepository;
import com.api.quesIo.questio.dtos.UsuarioDto;
import com.api.quesIo.questio.dtos.UsuarioResponseDto;
import com.api.quesIo.questio.enums.FuncaoEnum;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors; // Importante para a lista

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioController(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    // --- MUDANÇA AQUI (GET) ---
    @GetMapping
    public List<UsuarioResponseDto> listar() {
        // Busca os models no banco e converte um por um para DTO
        return repository.findAll().stream()
                .map(usuario -> {
                    UsuarioResponseDto dto = new UsuarioResponseDto();
                    dto.setId(usuario.getId());
                    dto.setName(usuario.getNome());
                    dto.setEmail(usuario.getEmail());
                    dto.setRole(usuario.getFuncao().toString());
                    dto.setActive(usuario.isAtivo());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // --- MUDANÇA AQUI (POST) ---
    // Agora recebendo DTO na criação também, como conversamos
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody UsuarioDto dto) {

        if (repository.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("E-mail já cadastrado!");
        }

        UsuarioModel usuario = new UsuarioModel();
        usuario.setNome(dto.getName()); // Usando o DTO
        usuario.setEmail(dto.getEmail());

        // Criptografa a senha que veio do DTO
        usuario.setSenha(passwordEncoder.encode(dto.getPassword()));

        // Define padrões
        usuario.setFuncao(FuncaoEnum.USER);
        usuario.setAtivo(true);

        repository.save(usuario);

        return ResponseEntity.ok("Usuário criado com sucesso!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable UUID id) {
        repository.deleteById(id);
        return ResponseEntity.ok("Usuário removido com sucesso.");
    }
}