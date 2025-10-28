package com.api.quesIo.clientes.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "tb_cliente")
public class ClienteModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nome;
    private String endereco;
    private String cpf;
    private String email;
    private String telefone;

}
