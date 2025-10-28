package com.api.quesIo.clientes.Models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tb_avaliacao")
public class QuestionarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    private ClienteModel cliente;

    private String titulo;

    private String descricao;

    private boolean ativo = true;
}
