package com.springboot.blog.repository;

import com.springboot.price.entity.User;
import com.springboot.price.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByEmail() {
        User user = new User();
        user.setName("Test User");
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user.setPassword("password");
        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByEmail("testuser@example.com");
        assertTrue(foundUser.isPresent());
        assertEquals("testuser", foundUser.get().getUsername());
    }

    @Test
    public void testExistsByUsername() {
        User user = new User();
        user.setName("Test User");
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user.setPassword("password");
        userRepository.save(user);

        assertTrue(userRepository.existsByUsername("testuser"));
    }

    @Test
    public void testExistsByEmail() {
        User user = new User();
        user.setName("Test User");
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user.setPassword("password");
        userRepository.save(user);

        assertTrue(userRepository.existsByEmail("testuser@example.com"));
    }
}
