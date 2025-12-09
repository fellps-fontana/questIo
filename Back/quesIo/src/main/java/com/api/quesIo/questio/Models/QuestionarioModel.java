package com.api.quesIo.questio.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "questionarios") // Tabela em Português
public class QuestionarioModel { // Classe em Português

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    private boolean active = true;

    @CreationTimestamp
    private LocalDateTime createdAt;

    // Relacionamento OneToMany: Uma lista de PerguntaModel chamada 'questions'
    @OneToMany(mappedBy = "questionnaire", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PerguntaModel> questions = new ArrayList<>();

    // Campos calculados (não salvam no banco)
    @Transient
    private Integer totalAnswers;

    @Transient
    private LocalDateTime lastAnswerAt;
}