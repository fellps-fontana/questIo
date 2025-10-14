package com.api.quesIo.clientes.dtos;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.br.CNPJ;

@Data

public class ClienteDto {
    @NotBlank(message = "O nome é obrigatório")
    private String nome;
    private String endereco;
    @CNPJ
    private String cnpj;
    @Email
    private String email;
    private String telefone;
}
