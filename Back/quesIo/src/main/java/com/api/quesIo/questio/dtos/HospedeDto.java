package com.api.quesIo.questio.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class HospedeDto {

    @NotBlank(message = "O nome é obrigatório")
    private String name;

    @NotBlank(message = "O email é obrigatório")
    @Email
    private String email;

    private String phone;

    @NotBlank(message = "O número do quarto é obrigatório")
    private String room;

    // O Spring aceita String ISO (ex: "2025-11-27T10:00:00") e converte para LocalDateTime
    private LocalDateTime checkin;
    private LocalDateTime checkout;


}