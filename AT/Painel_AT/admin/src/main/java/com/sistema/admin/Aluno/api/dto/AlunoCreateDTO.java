package com.sistema.admin.Aluno.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AlunoCreateDTO(

        @NotBlank(message = "Nome é obrigatório")
        @Size(max = 160)
        String nome,

        @NotBlank(message = "CPF é obrigatório")
        @Size(min = 11, max = 14, message = "CPF deve ter entre 11 e 14 caracteres")
        String cpf,

        @NotBlank(message = "E-mail é obrigatório")
        @Email(message = "E-mail inválido")
        String email,

        @Size(max = 30)
        String telefone,

        @Size(max = 255)
        String endereco
) {}
