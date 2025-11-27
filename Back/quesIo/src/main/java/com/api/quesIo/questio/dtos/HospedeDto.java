package com.api.quesIo.questio.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class HospedeDto {

    @NotBlank(message = "O nome é obrigatório")
    private String name; // Front manda 'name'

    @Email
    @NotBlank(message = "O email é obrigatório")
    private String email;

    private String phone; // Front manda 'phone'

    private String room; // Novo campo

    // O Spring converte a String ISO do Angular para LocalDateTime automaticamente
    private LocalDateTime checkin;
    private LocalDateTime checkout;
}