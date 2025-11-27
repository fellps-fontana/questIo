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
    private Long id; // Mantive Long (Front: number)

    @Column(nullable = false)
    private String title; // Front: title

    private String description; // Front: description

    private boolean active = true;

    @CreationTimestamp
    private LocalDateTime createdAt;

    // A MÁGICA: Um questionário tem várias perguntas
    @OneToMany(mappedBy = "questionario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Importante: Diz pro JSON serializar essa lista
    private List<PerguntaModel> questions = new ArrayList<>();
}