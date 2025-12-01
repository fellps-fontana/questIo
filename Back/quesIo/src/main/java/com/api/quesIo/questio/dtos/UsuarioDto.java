package com.api.quesIo.questio.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioDto {

    @NotBlank(message = "O nome é obrigatório")
    private String name; // Mapeia para Model.nome

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Formato de email inválido")
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    private String password; // Mapeia para Model.senha
}