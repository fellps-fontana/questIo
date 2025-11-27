package com.api.quesIo.questio.dtos;

import com.api.quesIo.questio.enums.QuestionType;
import lombok.Data;

@Data
public class PerguntaDto {
    private String prompt;
    private QuestionType type;
    private Integer orderIndex;
    private Boolean required;
}