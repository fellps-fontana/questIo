package com.api.quesIo.questio.dtos;

import lombok.Data;
import java.util.UUID;

@Data
public class UsuarioResponseDto {
    private UUID id;
    private String name;
    private String email;
    private String role;
    private boolean active;
}