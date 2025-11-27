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

    private String prompt; // A pergunta em si (ex: "Como foi o atendimento?")

    @Enumerated(EnumType.STRING)
    private QuestionType type; // TEXT, STARS, etc.

    @Column(name = "ordem") // 'order' é palavra reservada em alguns bancos SQL
    private Integer orderIndex;

    private Boolean required = false;

    @ManyToOne
    @JoinColumn(name = "questionario_id")
    @JsonBackReference // Evita loop infinito no JSON
    private QuestionarioModel questionario;
}