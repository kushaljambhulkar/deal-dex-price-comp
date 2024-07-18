package com.springboot.price.service.impl;

import com.springboot.price.entity.Role;
import com.springboot.price.entity.User;
import com.springboot.price.exception.BlogPriceException;
import com.springboot.price.payload.LoginDto;
import com.springboot.price.payload.RegisterDto;
import com.springboot.price.repository.RoleRepository;
import com.springboot.price.repository.UserRepository;
import com.springboot.price.security.JwtTokenProvider;
import com.springboot.price.service.AuthService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {
        logger.info("Attempting to authenticate user: {}", loginDto.getUsernameOrEmail());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsernameOrEmail(), loginDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        logger.info("User authenticated successfully: {}", loginDto.getUsernameOrEmail());
        logger.debug("Generated JWT token for user: {}", token);

        return token;
    }

    @Override
    public String register(RegisterDto registerDto) {
        logger.info("Attempting to register user with username: {}", registerDto.getUsername());

        // add check for username exists in database
        if(userRepository.existsByUsername(registerDto.getUsername())){
            logger.warn("Username already exists: {}", registerDto.getUsername());
            throw new BlogPriceException(HttpStatus.BAD_REQUEST, "Username already exists!");
        }

        // add check for email exists in database
        if(userRepository.existsByEmail(registerDto.getEmail())){
            logger.warn("Email already exists: {}", registerDto.getEmail());
            throw new BlogPriceException(HttpStatus.BAD_REQUEST, "Email already exists!");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").orElseThrow(() -> {
            logger.error("User role not found in the database.");
            return new BlogPriceException(HttpStatus.INTERNAL_SERVER_ERROR, "User role not found.");
        });
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);

        logger.info("User registered successfully: {}", registerDto.getUsername());
        return "User registered successfully!";
    }
}
