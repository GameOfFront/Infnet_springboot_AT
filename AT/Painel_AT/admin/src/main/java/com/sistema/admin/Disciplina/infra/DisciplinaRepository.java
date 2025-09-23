package com.sistema.admin.Disciplina.infra;

import com.sistema.admin.Disciplina.dominio.Disciplina;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DisciplinaRepository extends MongoRepository<Disciplina, String> {
    Optional<Disciplina> findByCodigo(String codigo);
    Optional<Disciplina> findByNome(String nome);
}
