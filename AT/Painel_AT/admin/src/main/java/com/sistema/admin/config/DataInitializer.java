package com.sistema.admin.config;

import com.sistema.admin.auth.dominio.Role;
import com.sistema.admin.auth.infra.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {
            // garante que exista uma ROLE_USER no banco
            roleRepository.findByNome("ROLE_USER").orElseGet(() -> {
                Role role = new Role();
                role.setNome("ROLE_USER");
                return roleRepository.save(role);
            });

            // opcional: cria também a ROLE_ADMIN
            roleRepository.findByNome("ROLE_ADMIN").orElseGet(() -> {
                Role role = new Role();
                role.setNome("ROLE_ADMIN");
                return roleRepository.save(role);
            });
        };
    }
}
