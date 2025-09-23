package com.sistema.admin.auth.dominio;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "usuarios")
public class Usuario {

    @Id
    private String id; // Mongo usa ObjectId → String

    private String nome;

    private String email;

    private String passwordHash;

    private Boolean ativo = true;

    @DBRef
    private Set<Role> roles = new HashSet<>();

    private OffsetDateTime criadoEm;

    private OffsetDateTime atualizadoEm;

    public void prePersist() {
        OffsetDateTime agora = OffsetDateTime.now();
        this.criadoEm = agora;
        this.atualizadoEm = agora;
    }

    public void preUpdate() {
        this.atualizadoEm = OffsetDateTime.now();
    }
}
