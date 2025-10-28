package com.api.quesIo.clientes.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class QuestionarioDto {

    @NotNull(message = "O ID do cliente é obrigatório")
    private UUID clienteId;

    @NotBlank(message = "O título é obrigatório")
    private String titulo;


    private String descricao;
}
