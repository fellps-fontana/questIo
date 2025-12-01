package com.api.quesIo.questio.Repositorys;

import com.api.quesIo.questio.Models.RespostaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface RespostaRepository extends JpaRepository<RespostaModel, UUID> {

    // Busca respostas por hóspede
    List<RespostaModel> findByHospedeId(UUID hospedeId);

    // 1. Busca todas as respostas do formulário (Usado no gráfico)
    @Query(value = """
        SELECT r.* FROM respostas r 
        INNER JOIN perguntas p ON r.pergunta_id = p.id 
        WHERE p.questionario_id = :formId
    """, nativeQuery = true)
    List<RespostaModel> buscarRespostasPorFormulario(@Param("formId") Long formId);

    // 2. CONTA PARTICIPANTES ÚNICOS (MÉTRICA)
    @Query("SELECT COUNT(DISTINCT r.hospede.id) FROM RespostaModel r WHERE r.pergunta.questionario.id = :formId")
    Long contarParticipantes(@Param("formId") Long formId);

    // 3. CONTA TOTAL DE RESPOSTAS (LINHAS)
    @Query("SELECT COUNT(r.id) FROM RespostaModel r WHERE r.pergunta.questionario.id = :formId")
    Long contarTotalRespostas(@Param("formId") Long formId); // MÉTODO NOVO

    // 4. Pega a data/hora da última resposta recebida
    @Query("SELECT MAX(r.respondidoEm) FROM RespostaModel r WHERE r.pergunta.questionario.id = :formId")
    LocalDateTime ultimaRespostaData(@Param("formId") Long formId);
}