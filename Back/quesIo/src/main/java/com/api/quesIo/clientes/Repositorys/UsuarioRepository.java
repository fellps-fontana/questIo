package com.api.quesIo.clientes.Repositorys;

import com.api.quesIo.clientes.Models.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsuarioRepository  extends JpaRepository<UsuarioModel, UUID> {
}
