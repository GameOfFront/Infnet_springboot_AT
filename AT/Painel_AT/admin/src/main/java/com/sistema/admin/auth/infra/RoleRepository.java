package com.sistema.admin.auth.infra;

import com.sistema.admin.auth.dominio.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByNome(String nome);
}
