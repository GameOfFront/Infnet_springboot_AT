package com.sistema.admin.Matricula.dominio;

import com.sistema.admin.Aluno.dominio.Aluno;
import com.sistema.admin.Disciplina.dominio.Disciplina;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "matriculas")
public class Matricula {

    @Id
    private String id;

    @DBRef
    private Aluno aluno;

    @DBRef
    private Disciplina disciplina;

    private BigDecimal nota;

    private Instant criadoEm = Instant.now();

    private Instant atualizadoEm = Instant.now();

    public void prePersist() {
        this.criadoEm = Instant.now();
        this.atualizadoEm = Instant.now();
    }

    public void preUpdate() {
        this.atualizadoEm = Instant.now();
    }
}
