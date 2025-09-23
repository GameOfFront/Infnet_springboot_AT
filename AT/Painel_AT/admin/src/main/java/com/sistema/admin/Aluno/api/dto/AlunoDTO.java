package com.sistema.admin.Aluno.api.dto;

public record AlunoDTO(
        String id,
        String nome,
        String cpf,
        String email,
        String telefone,
        String endereco
) {}
