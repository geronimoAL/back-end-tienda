package com.backend.spring3.tienda.service;

import com.backend.spring3.tienda.dto.JwtAuthResponse;
import com.backend.spring3.tienda.dto.LoginDto;
import com.backend.spring3.tienda.dto.RegisterDto;

public interface AuthService {
    
    String register(RegisterDto registerDto);

    JwtAuthResponse login(LoginDto loginDto);
}
