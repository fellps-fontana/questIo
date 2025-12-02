package com.api.quesIo.questio.Models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "hospedes")
public class HospedeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    // No Front é "name", no Banco vou forçar ser "nome"
    @Column(name = "nome", nullable = false)
    private String name;

    // Se não por @Column, o banco cria "email".
    // Mas coloquei para garantir que não seja nulo (nullable = false)
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "telefone")
    private String phone;

    @Column(name = "quarto")
    private String room;

    // Dica: No banco é comum usar underline (check_in),
    // mas no Java/JSON mantemos junto (checkin) para bater com o Front.
    @Column(name = "check_in")
    private LocalDateTime checkin;

    @Column(name = "check_out")
    private LocalDateTime checkout;

    @ManyToOne
    @JoinColumn(name = "questionario_id")
    private QuestionarioModel questionarioAtribuido;

    @Column(name = "x_enviado_email")
    private boolean xEnviadoEmail;
}