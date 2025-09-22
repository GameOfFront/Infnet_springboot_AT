package com.sistema.admin.Matricula.infra;

import com.sistema.admin.Matricula.dominio.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

    Optional<Matricula> findByAlunoIdAndDisciplinaId(Long alunoId, Long disciplinaId);

    @Query("SELECT m FROM Matricula m WHERE m.disciplina.id = :disciplinaId AND m.nota IS NOT NULL AND m.nota >= :nota")
    List<Matricula> findAprovados(Long disciplinaId, BigDecimal nota);

    @Query("SELECT m FROM Matricula m WHERE m.disciplina.id = :disciplinaId AND m.nota IS NOT NULL AND m.nota < :nota")
    List<Matricula> findReprovados(Long disciplinaId, BigDecimal nota);
}
