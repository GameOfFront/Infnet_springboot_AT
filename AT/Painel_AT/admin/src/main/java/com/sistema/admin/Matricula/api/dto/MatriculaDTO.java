package com.sistema.admin.Matricula.api.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record MatriculaDTO(
        Long id,

        @NotNull(message = "Aluno é obrigatório")
        Long alunoId,

        @NotNull(message = "Disciplina é obrigatória")
        Long disciplinaId,

        BigDecimal nota
) {}
