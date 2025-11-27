package com.api.quesIo.questio.Models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "respostas")
public class RespostaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    // Guarda "5", "true", "Muito bom" -> Tudo virou String no banco
    @Column(nullable = false)
    private String valor;

    @CreationTimestamp
    private LocalDateTime respondidoEm;

    // Relacionamentos
    @ManyToOne
    @JoinColumn(name = "hospede_id", nullable = false)
    private HospedeModel hospede;

    @ManyToOne
    @JoinColumn(name = "pergunta_id", nullable = false)
    private PerguntaModel pergunta;
}