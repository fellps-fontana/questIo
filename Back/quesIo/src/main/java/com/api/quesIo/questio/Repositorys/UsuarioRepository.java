package com.api.quesIo.questio.Repositorys;

import com.api.quesIo.questio.Models.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, UUID> {

    // Adicione <UsuarioModel> aqui dentro dos sinais de maior/menor
    Optional<UsuarioModel> findByEmail(String email);

}