package com.sistema.admin.Aluno.dominio;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "alunos")
@Getter
@Setter
@NoArgsConstructor
public class Aluno {

    @Id
    private String id; // Mongo usa ObjectId (String)

    private String nome;

    @Indexed(unique = true) // índice único para CPF
    private String cpf;

    @Indexed(unique = true) // índice único para email
    private String email;

    private String telefone;

    private String endereco;

    private Instant criadoEm = Instant.now();

    private Instant atualizadoEm;
}
