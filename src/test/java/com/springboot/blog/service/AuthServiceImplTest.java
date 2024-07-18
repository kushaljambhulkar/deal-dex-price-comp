// AuthServiceImplTest.java
package com.springboot.blog.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.springboot.price.exception.BlogPriceException;
import com.springboot.price.service.impl.AuthServiceImpl;
import com.springboot.price.repository.UserRepository;
import com.springboot.price.payload.RegisterDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthServiceImpl authService;

    private RegisterDto registerDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        registerDto = new RegisterDto();
        registerDto.setUsername("testuser");
        registerDto.setEmail("test@example.com");
        registerDto.setPassword("password");
    }

    @Test
    public void testRegisterUsernameExists() {
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        BlogPriceException exception = assertThrows(BlogPriceException.class, () -> {
            authService.register(registerDto);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Username already exists!", exception.getMessage());
    }

    @Test
    public void testRegisterEmailExists() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        BlogPriceException exception = assertThrows(BlogPriceException.class, () -> {
            authService.register(registerDto);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Email already exists!", exception.getMessage());
    }
}