package com.api.quesIo.questio.Models;

import com.api.quesIo.questio.enums.QuestionType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "perguntas")
public class PerguntaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String prompt; // Mantido 'prompt' (Enunciado da pergunta)

    @Enumerated(EnumType.STRING)
    private QuestionType type; // Lembre-se de ter o Enum criado

    @Column(name = "ordem")
    private Integer orderIndex;

    private Boolean required = false;

    // --- MANTENHA ASSIM (EM INGLÊS) ---
    // Isso garante que o mappedBy="questionnaire" lá no QuestionarioModel funcione
    @ManyToOne
    @JoinColumn(name = "questionario_id")
    @JsonBackReference
    private QuestionarioModel questionnaire;
}