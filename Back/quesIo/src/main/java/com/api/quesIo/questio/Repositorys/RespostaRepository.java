package com.api.quesIo.questio.Repositorys;

import com.api.quesIo.questio.Models.RespostaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface RespostaRepository extends JpaRepository<RespostaModel, UUID> {

    // 1. Correção: O campo no Model agora se chama 'guest', não 'hospede'
    List<RespostaModel> findByGuestId(UUID guestId);

    // 2. Query Nativa (SQL Puro):
    // Como mantivemos os nomes das tabelas em PT (respostas, perguntas) e as
    // colunas @JoinColumn em PT (pergunta_id), o SQL Nativo NÃO MUDA.
    @Query(value = """
        SELECT r.* FROM respostas r 
        INNER JOIN perguntas p ON r.pergunta_id = p.id 
        WHERE p.questionario_id = :formId
    """, nativeQuery = true)
    List<RespostaModel> buscarRespostasPorFormulario(@Param("formId") Long formId);

    // 3. JPQL (Java): Aqui muda tudo! Temos que usar os nomes dos atributos em Inglês.
    // r.hospede -> r.guest
    // r.pergunta.questionario -> r.question.questionnaire
    @Query("SELECT COUNT(DISTINCT r.guest.id) FROM RespostaModel r WHERE r.question.questionnaire.id = :formId")
    Long contarParticipantes(@Param("formId") Long formId);

    // 4. JPQL: Correção do caminho (pergunta -> question, questionario -> questionnaire)
    @Query("SELECT COUNT(r.id) FROM RespostaModel r WHERE r.question.questionnaire.id = :formId")
    Long contarTotalRespostas(@Param("formId") Long formId);

    // 5. JPQL: Correção de 'respondidoEm' para 'answeredAt'
    @Query("SELECT MAX(r.answeredAt) FROM RespostaModel r WHERE r.question.questionnaire.id = :formId")
    LocalDateTime ultimaRespostaData(@Param("formId") Long formId);
}