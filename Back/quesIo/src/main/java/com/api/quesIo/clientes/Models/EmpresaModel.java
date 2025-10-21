package com.api.quesIo.clientes.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "tb_empresa")
public class EmpresaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nome;
    private String cnpj;
    private String email;
    private String telefone;
}
