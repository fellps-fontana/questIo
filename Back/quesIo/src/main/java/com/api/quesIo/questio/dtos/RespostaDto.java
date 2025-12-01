package com.api.quesIo.questio.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.UUID;

@Data
public class RespostaDto {

    @NotNull(message = "O ID da pergunta é obrigatório")
    private UUID questionId;

    @NotNull(message = "O valor da resposta é obrigatório")
    private String value;

    // --- CONSTRUTORES (Ajuda o Jackson a criar o objeto) ---
    public RespostaDto() {}

    public RespostaDto(UUID questionId, String value) {
        this.questionId = questionId;
        this.value = value;
    }

    // --- GETTERS E SETTERS MANUAIS (Para garantir que funcione sem Lombok) ---

    public UUID getQuestionId() {
        return questionId;
    }

    public void setQuestionId(UUID questionId) {
        this.questionId = questionId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}