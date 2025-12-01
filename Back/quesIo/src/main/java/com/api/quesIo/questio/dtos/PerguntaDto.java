package com.api.quesIo.questio.dtos;

import com.api.quesIo.questio.enums.QuestionType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class PerguntaDto {

    private UUID id;

    @NotNull(message = "O enunciado da pergunta é obrigatório")
    private String prompt;

    @NotNull(message = "O tipo da pergunta é obrigatório")
    private QuestionType type; // STICKER, STARS, TEXT...

    private Integer orderIndex; // Ordem da pergunta na tela

    private Boolean required;
}