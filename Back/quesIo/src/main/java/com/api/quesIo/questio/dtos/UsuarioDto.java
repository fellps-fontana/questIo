package com.api.quesIo.questio.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioDto { // O nome da classe pode ficar em PT, sem problemas

    @NotBlank(message = "Name is required")
    private String name;      // Bate com Model.name

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;     // Bate com Model.email

    private String password;  // Bate com Model.password

    private String role;      // Bate com Model.role (precisa converter Enum no controller)
    private boolean active;   // Bate com Model.active
}