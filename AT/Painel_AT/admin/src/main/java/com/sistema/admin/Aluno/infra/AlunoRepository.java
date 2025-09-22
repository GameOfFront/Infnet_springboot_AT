package com.sistema.admin.Aluno.infra;

import com.sistema.admin.Aluno.dominio.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    Optional<Aluno> findByCpf(String cpf);
    Optional<Aluno> findByEmail(String email);
}
