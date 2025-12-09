package com.api.quesIo.questio.Models;

import com.api.quesIo.questio.enums.FuncaoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuarios") // Tabela em Português
public class UsuarioModel { // Classe em Português

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;      // Campo em Inglês

    @Column(nullable = false, unique = true)
    private String email;     // Campo em Inglês

    @Column(nullable = false)
    private String password;  // Campo em Inglês

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FuncaoEnum role;  // Campo em Inglês

    @Column(nullable = false)
    private boolean active = true; // Campo em Inglês
}