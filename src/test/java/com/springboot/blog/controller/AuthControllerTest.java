package com.springboot.blog.controller;

import com.springboot.price.controller.AuthController;
import com.springboot.price.payload.JWTAuthResponse;
import com.springboot.price.payload.LoginDto;
import com.springboot.price.payload.RegisterDto;
import com.springboot.price.service.AuthService;
import com.springboot.price.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@WebMvcTest(AuthController.class)
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private EmailService emailService;

    private LoginDto loginDto;
    private RegisterDto registerDto;

    @BeforeEach
    public void setUp() {
        loginDto = new LoginDto();
        loginDto.setUsernameOrEmail("testuser");
        loginDto.setPassword("password");

        registerDto = new RegisterDto();
        registerDto.setName("Test User");
        registerDto.setUsername("testuser");
        registerDto.setEmail("testuser@example.com");
        registerDto.setPassword("password");
    }

    @Test
    public void testLogin() throws Exception{
        String token = "dummyJwtToken";
        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        when(authService.login(Mockito.any(LoginDto.class))).thenReturn(token);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"usernameOrEmail\":\"testuser\", \"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value(token))
                .andDo(print());

    }

    @Test
    public void testRegister() throws Exception{
        when(authService.register(Mockito.any(RegisterDto.class))).thenReturn("User Registered Successful");
    }
}
