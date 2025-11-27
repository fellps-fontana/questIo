package com.api.quesIo.questio.dtos;

import lombok.Data;
import java.util.UUID;

@Data
public class RespostaDto {
    private UUID questionId; // No front é 'questionId'
    private String value;    // No front é 'value' (O JSON converte numero/bool pra string aqui)
}