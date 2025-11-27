package com.api.quesIo.questio.config;

import com.api.quesIo.questio.Models.UsuarioModel;
import com.api.quesIo.questio.Repositorys.UsuarioRepository;
import com.api.quesIo.questio.enums.FuncaoEnum;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder; // Import novo

@Configuration
public class DataSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder; // Injeção do encoder

    public DataSeeder(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.findByEmail("admin@questio.com").isEmpty()) {

            UsuarioModel admin = new UsuarioModel();
            admin.setNome("Administrador Supremo");
            admin.setEmail("admin@questio.com");

            // AQUI A MÁGICA: Criptografa a senha antes de salvar
            admin.setSenha(passwordEncoder.encode("123456"));

            admin.setFuncao(FuncaoEnum.ADMIN);
            admin.setAtivo(true);

            usuarioRepository.save(admin);
            System.out.println("✅ USUÁRIO ADMIN CRIADO COM CRIPTOGRAFIA");
        }
    }
}