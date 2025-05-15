package com.pe.platform.iam;

import com.pe.platform.iam.domain.model.aggregates.User;
import com.pe.platform.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testCreateAndFindUserByUsername() {
        // Crear usuario
        User user = new User("testuser", "secret123");

        // Guardar en memoria
        userRepository.save(user);

        // Buscar por username
        Optional<User> found = userRepository.findByUsername("testuser");

        // Verificar resultados
        assertThat(found).isPresent();
        assertThat(found.get().getPassword()).isEqualTo("secret123");
    }
}
