package com.api.quesIo.questio.services;

import com.api.quesIo.questio.Models.UsuarioModel;
import com.api.quesIo.questio.Repositorys.UsuarioRepository;
import com.api.quesIo.questio.dtos.UsuarioDto;
import com.api.quesIo.questio.enums.FuncaoEnum;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    // Injeção de dependência via Construtor (Prática recomendada)
    public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioModel create(UsuarioDto dto) {
        // Verifica se email já existe antes de tentar criar
        if (repository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("E-mail já cadastrado!");
        }

        UsuarioModel model = new UsuarioModel();

        // 1. CORREÇÃO DOS GETTERS (Usando os nomes do DTO em Inglês)
        model.setName(dto.getName());      // Antes estava getNome()
        model.setEmail(dto.getEmail());

        // 2. CRIPTOGRAFIA (Usando getPassword do DTO)
        String senhaCriptografada = passwordEncoder.encode(dto.getPassword());
        model.setPassword(senhaCriptografada);

        // 3. CAMPOS OBRIGATÓRIOS (Sem isso dá erro de SQL)
        model.setRole(FuncaoEnum.USER); // Define como usuário comum por padrão
        model.setActive(true);

        // 4. SALVAR NO BANCO (Faltava isso!)
        return repository.save(model);
    }
}
