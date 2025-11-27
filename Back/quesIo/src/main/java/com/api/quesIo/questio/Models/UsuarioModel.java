package com.api.quesIo.questio.Models;

import com.api.quesIo.questio.enums.FuncaoEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "usuarios")
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false) // Nome é obrigatório
    private String nome;

    @Column(nullable = false, unique = true) // E-mail obrigatório e ÚNICO no banco
    private String email;

    @Column(nullable = false) // Senha obrigatória
    private String senha;

    @Enumerated(EnumType.STRING) // Salva no banco como texto: "ADMIN" ou "USER"
    @Column(nullable = false)
    private FuncaoEnum funcao;

    @Column(nullable = false)
    private boolean ativo = true;
}