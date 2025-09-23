package com.sistema.admin.Matricula.infra;

import com.sistema.admin.Matricula.dominio.Matricula;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface MatriculaRepository extends MongoRepository<Matricula, String> {

    Optional<Matricula> findByAlunoIdAndDisciplinaId(String alunoId, String disciplinaId);

    @Query("{ 'disciplina.id': ?0, 'nota': { $gte: ?1 } }")
    List<Matricula> findAprovados(String disciplinaId, BigDecimal nota);

    @Query("{ 'disciplina.id': ?0, 'nota': { $lt: ?1 } }")
    List<Matricula> findReprovados(String disciplinaId, BigDecimal nota);
}
