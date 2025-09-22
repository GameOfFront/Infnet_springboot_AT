package com.sistema.admin.Disciplina.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DisciplinaDTO(
        Long id,

        @NotBlank(message = "Código é obrigatório")
        @Size(max = 20, message = "Código deve ter no máximo 20 caracteres")
        String codigo,

        @NotBlank(message = "Nome é obrigatório")
        @Size(max = 120, message = "Nome deve ter no máximo 120 caracteres")
        String nome
) {}
