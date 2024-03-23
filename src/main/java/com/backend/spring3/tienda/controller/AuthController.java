package com.backend.spring3.tienda.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.spring3.tienda.dto.JwtAuthResponse;
import com.backend.spring3.tienda.dto.LoginDto;
import com.backend.spring3.tienda.dto.RegisterDto;
import com.backend.spring3.tienda.service.AuthService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    

    private AuthService authService;

    private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);


    // Build Register REST API
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Build Login REST API
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
        JwtAuthResponse jwtAuthResponse = authService.login(loginDto);
        logger.info("token "+jwtAuthResponse.getAccessToken());
        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }
}
