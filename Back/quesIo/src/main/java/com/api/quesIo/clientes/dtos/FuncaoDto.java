package com.api.quesIo.clientes.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FuncaoDto {
    @NotBlank(message = "O nome da função é obrigatório")
    private String nome;
}
