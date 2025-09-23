package com.sistema.admin.Matricula.api.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record MatriculaDTO(
        String id,  // era Long → agora String

        @NotNull(message = "Aluno é obrigatório")
        String alunoId,  // era Long → agora String

        @NotNull(message = "Disciplina é obrigatória")
        String disciplinaId,  // era Long → agora String

        BigDecimal nota
) {}
