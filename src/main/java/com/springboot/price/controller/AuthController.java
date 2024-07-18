package com.springboot.price.controller;

import com.springboot.price.payload.JWTAuthResponse;
import com.springboot.price.payload.LoginDto;
import com.springboot.price.payload.RegisterDto;
import com.springboot.price.service.AuthService;

import com.springboot.price.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = { "http://localhost:5173"})
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;
    @Autowired
    private EmailService emailService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Build Login REST API
    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto){
        logger.info("Login request received for user: {}", loginDto.getUsernameOrEmail());
        String token = authService.login(loginDto);
        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        logger.info("Login successful for user: {}", loginDto.getUsernameOrEmail());
        emailService.setLoginNotification("kushal.jambhulkar@creditsaison-in.com", "Kushal");
        return ResponseEntity.ok(jwtAuthResponse);
    }

    // Build Register REST API
    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        logger.info("Registration request received for user: {}", registerDto.getUsername());
        String response = authService.register(registerDto);
        logger.info("Registration successful for user: {}", registerDto.getUsername());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
