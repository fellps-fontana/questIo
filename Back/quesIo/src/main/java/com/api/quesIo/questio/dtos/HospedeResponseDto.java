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
    private LocalDateTime checkin;
    private LocalDateTime checkout;

    // Para o front saber se mostra o botão "Fazer Checkout" ou "Concluído"
    private LocalDateTime checkoutRealizadoEm;

    // O CAMPO MÁGICO: O Java vai preencher isso na hora de devolver pro front
    private String linkAcesso;
    private Long assignedFormId;
}