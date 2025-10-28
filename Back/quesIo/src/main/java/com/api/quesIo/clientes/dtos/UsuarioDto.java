package com.api.quesIo.clientes.dtos;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;


@Data
public class UsuarioDto {

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Formato de email inválido")
    private String email;

    @NotNull(message = "O ID da empresa é obrigatório")
    private UUID empresaId; // <-- MUDANÇA: Usar o ID da empresa

    @NotBlank(message = "A senha é obrigatória")
    private String senha;

    @NotNull(message = "O ID da função é obrigatório")
    private UUID funcaoId;
}
