package com.api.quesIo.questio.Repositorys;

import com.api.quesIo.questio.Models.PerguntaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PerguntaRepository extends JpaRepository<PerguntaModel, UUID> {
    // Aqui você pode adicionar métodos customizados se precisar
}