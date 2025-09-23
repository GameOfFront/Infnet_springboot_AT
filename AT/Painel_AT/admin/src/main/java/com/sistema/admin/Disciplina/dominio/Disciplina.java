package com.sistema.admin.Disciplina.dominio;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "disciplinas")
public class Disciplina {

    @Id
    private String id; // Mongo usa ObjectId → String

    @Indexed(unique = true)
    private String codigo;

    private String nome;

    private OffsetDateTime criadoEm;

    private OffsetDateTime atualizadoEm;

    public void prePersist() {
        this.criadoEm = OffsetDateTime.now();
        this.atualizadoEm = OffsetDateTime.now();
    }

    public void preUpdate() {
        this.atualizadoEm = OffsetDateTime.now();
    }
}
