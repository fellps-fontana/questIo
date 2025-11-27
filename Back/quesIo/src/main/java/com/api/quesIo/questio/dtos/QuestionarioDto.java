package com.api.quesIo.questio.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.List;

@Data
public class QuestionarioDto {

    @NotBlank(message = "O título é obrigatório")
    private String title;

    private String description;

    // Agora o JSON de criação já pode vir com as perguntas!
    private List<PerguntaDto> questions;
}