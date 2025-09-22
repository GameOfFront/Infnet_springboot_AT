package com.sistema.admin.Aluno.dominio;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Entity
@Table(name = "tb_aluno")
public class Aluno {

    // Getters e Setters
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, length = 160)
    private String nome;

    @Setter
    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @Setter
    @Column(nullable = false, unique = true, length = 160)
    private String email;

    @Setter
    @Column(length = 30)
    private String telefone;

    @Setter
    @Column(length = 255)
    private String endereco;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private OffsetDateTime criadoEm = OffsetDateTime.now();

    @Setter
    @Column(name = "atualizado_em")
    private OffsetDateTime atualizadoEm;

}
