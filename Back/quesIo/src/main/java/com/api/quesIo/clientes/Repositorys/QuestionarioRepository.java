package com.api.quesIo.clientes.Repositorys;

import com.api.quesIo.clientes.Models.QuestionarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface QuestionarioRepository extends JpaRepository<QuestionarioModel, UUID> {
}
