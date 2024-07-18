package com.springboot.price.service;

import com.springboot.price.payload.LoginDto;
import com.springboot.price.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);


    String register(RegisterDto registerDto);

}
