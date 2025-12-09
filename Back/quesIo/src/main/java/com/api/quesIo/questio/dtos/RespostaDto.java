package com.api.quesIo.questio.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.UUID;

@Data
public class RespostaDto {

    // --- CAMPO QUE FALTAVA ---
    // O Front manda "guestId" dentro do JSON agora
    private UUID guestId;

    @NotNull(message = "O ID da pergunta é obrigatório")
    private UUID questionId;

    @NotNull(message = "O valor da resposta é obrigatório")
    private String value;

    // --- CONSTRUTORES ---
    public RespostaDto() {}

    public RespostaDto(UUID guestId, UUID questionId, String value) {
        this.guestId = guestId;
        this.questionId = questionId;
        this.value = value;
    }

    // --- GETTERS E SETTERS MANUAIS ---
    // (Se o Lombok falhar, esses métodos garantem que funcione)

    public UUID getGuestId() {
        return guestId;
    }

    public void setGuestId(UUID guestId) {
        this.guestId = guestId;
    }

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