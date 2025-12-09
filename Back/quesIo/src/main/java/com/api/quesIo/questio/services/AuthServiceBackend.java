package com.api.quesIo.questio.services;

import com.api.quesIo.questio.Models.UsuarioModel;
import com.api.quesIo.questio.Repositorys.UsuarioRepository;
import com.api.quesIo.questio.dtos.LoginDto;
import com.api.quesIo.questio.dtos.UsuarioResponseDto;
import org.springframework.security.crypto.password.PasswordEncoder; // Import novo
import org.springframework.stereotype.Service;

@Service
public class AuthServiceBackend {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder; // Injeção do encoder

    public AuthServiceBackend(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioResponseDto login(LoginDto loginDto) {
        UsuarioModel usuario = repository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!usuario.isActive()) {
            throw new RuntimeException("Usuário inativo");
        }

        // AGORA É PROFISSIONAL: Verifica se a senha bate com o hash
        if (!passwordEncoder.matches(loginDto.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Senha incorreta");
        }

        UsuarioResponseDto dto = new UsuarioResponseDto();
        dto.setId(usuario.getId());
        dto.setName(usuario.getName());
        dto.setEmail(usuario.getEmail());
        dto.setRole(usuario.getRole().toString());
        dto.setActive(usuario.isActive());

        return dto;
    }
}