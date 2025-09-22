package com.sistema.admin.Disciplina.infra;

import com.sistema.admin.Disciplina.dominio.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
    Optional<Disciplina> findByCodigo(String codigo);
    Optional<Disciplina> findByNome(String nome);
}
