package com.api.quesIo.questio.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "respostas") // Tabela em Português
public class RespostaModel { // Classe em Português

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String value; // O valor da resposta (Ex: "5", "Excelente")

    @CreationTimestamp
    private LocalDateTime answeredAt;

    // Quem respondeu? (Classe em PT, campo em EN)
    @ManyToOne
    @JoinColumn(name = "hospede_id", nullable = false)
    private HospedeModel guest;

    // Respondeu qual pergunta? (Classe em PT, campo em EN)
    @ManyToOne
    @JoinColumn(name = "pergunta_id", nullable = false)
    private PerguntaModel question;
}