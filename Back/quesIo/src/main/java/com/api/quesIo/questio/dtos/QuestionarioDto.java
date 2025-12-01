package com.api.quesIo.questio.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.List;

@Data
public class QuestionarioDto {

    @NotBlank(message = "O título é obrigatório")
    private String title;

    private String description;

    // Lista de perguntas para criar tudo de uma vez
    private List<PerguntaDto> questions;
}