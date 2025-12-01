package com.api.quesIo.questio.controllers;

import com.api.quesIo.questio.dtos.LoginDto;
import com.api.quesIo.questio.dtos.UsuarioResponseDto;
import com.api.quesIo.questio.services.AuthServiceBackend;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthServiceBackend authService;

    public AuthController(AuthServiceBackend authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        try {
            // O serviço já busca no banco, verifica se existe e compara a senha criptografada
            UsuarioResponseDto usuarioLogado = authService.login(loginDto);

            return ResponseEntity.ok(usuarioLogado);

        } catch (Exception e) {
            // Se a senha estiver errada ou usuário não existir, o Service lança erro
            // Nós capturamos aqui e devolvemos Status 401 (Unauthorized)
            return ResponseEntity.status(401).body("Falha no login: " + e.getMessage());
        }
    }
}