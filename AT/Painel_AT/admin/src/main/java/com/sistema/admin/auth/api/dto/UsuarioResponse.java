package com.sistema.admin.auth.api.dto;

import java.util.Set;

public record UsuarioResponse(
        String id, // era Long → agora String
        String nome,
        String email,
        Boolean ativo,
        Set<String> roles
) {}
