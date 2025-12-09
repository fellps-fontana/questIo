package com.api.quesIo.questio.dtos;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class HospedeResponseDto {
    private UUID id;
    private String name;
    private String email;
    private String phone;
    private String room;

    // --- CORREÇÃO: Usando CamelCase para bater com o Model e Service ---
    private LocalDateTime checkIn;  // Antes: checkin
    private LocalDateTime checkOut; // Antes: checkout

    // --- NOVO CAMPO (Necessário para o dashboard saber se já enviou) ---
    private boolean emailSent;

    // O Java preenche isso com o Link: http://localhost:4200/responder/...
    private String linkAcesso;

    // ID do questionário vinculado
    private Long assignedFormId;
}