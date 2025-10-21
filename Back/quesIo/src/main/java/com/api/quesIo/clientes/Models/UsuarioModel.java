package com.api.quesIo.clientes.Models;

import jakarta.persistence.*;
import lombok.Data;
import com.api.quesIo.clientes.Models.FuncaoModel;
import java.util.UUID;

@Entity
@Data
@Table(name = "tb_usuario")
public class UsuarioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nome;
    private String endereco;
    private String cpf;
    private String email;

    @ManyToOne(fetch = FetchType.LAZY) // Carregamento preguiçoso para melhor performance
    @JoinColumn(name = "empresa_id", nullable = false)
    private EmpresaModel empresa; // Chave estrangeira para a entidade Empresa
    private String senha;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funcao_id", nullable = false)
    private FuncaoModel funcao;
    @Column(nullable = false)
    private boolean ativo = true; //
}
