package com.api.quesIo.questio.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hospedes") // Tabela em Português
public class HospedeModel { // Classe em Português

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    private String phone;

    private String room;

    @Column(name = "check_in")
    private LocalDateTime checkIn;

    @Column(name = "check_out")
    private LocalDateTime checkOut;

    // Qual questionário este hóspede deve responder?
    @ManyToOne
    @JoinColumn(name = "questionario_id")
    private QuestionarioModel assignedQuestionnaire;

    @Column(name = "email_sent")
    private boolean emailSent;
}