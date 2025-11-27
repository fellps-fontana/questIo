package com.api.quesIo.questio.dtos;

import com.api.quesIo.questio.enums.FuncaoEnum;
import lombok.Data;
import java.util.UUID;

@Data
public class UsuarioResponseDto {
    private UUID id;
    private String name;  // Mapeia para 'nome'
    private String email;
    private String role;  // Mapeia para 'funcao'
    private boolean active; // Mapeia para 'ativo'
}