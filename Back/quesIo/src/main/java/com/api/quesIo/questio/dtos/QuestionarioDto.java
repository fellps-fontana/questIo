package com.api.quesIo.questio.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.List;

@Data
public class QuestionarioDto {

    // Adicionado o ID (pode ser nulo na criação, mas obrigatório na resposta)
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    // Lista de perguntas (Questions)
    private List<PerguntaDto> questions;
}