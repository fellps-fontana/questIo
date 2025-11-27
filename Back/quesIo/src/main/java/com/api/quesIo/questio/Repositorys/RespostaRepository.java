package com.api.quesIo.questio.Repositorys;

import com.api.quesIo.questio.Models.RespostaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface RespostaRepository extends JpaRepository<RespostaModel, UUID> {
    // Útil para gerar relatórios depois: "Me dê todas as respostas do hóspede X"
    List<RespostaModel> findByHospedeId(UUID hospedeId);
}