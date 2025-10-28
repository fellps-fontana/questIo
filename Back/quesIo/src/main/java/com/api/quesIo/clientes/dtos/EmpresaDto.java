package com.api.quesIo.clientes.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmpresaDto {
    @NotBlank(message = "O nome da empresa é obrigatório")
    private String nome;

    @NotBlank(message = "O CNPJ é obrigatório")

    @Size(min = 14, max = 14, message = "O CNPJ deve conter 14 dígitos")
    private String cnpj;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "O email fornecido é inválido")
    private String email;

    // O telefone pode ser opcional, então não usamos @NotBlank
    private String telefone;
}
