package com.api.quesIo.questio.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "questionarios")
public class QuestionarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    private boolean active = true;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "questionario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PerguntaModel> questions = new ArrayList<>();

    // --- CAMPOS CALCULADOS (NÃO SALVAM NO BANCO) ---
    // Adicione estes campos para corrigir o erro no Service

    @Transient
    private Integer totalAnswers;

    @Transient
    private LocalDateTime lastAnswerAt;
}