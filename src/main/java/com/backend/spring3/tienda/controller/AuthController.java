// package com.backend.spring3.tienda.controller;
// import lombok.AllArgsConstructor;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import com.backend.spring3.tienda.dto.JwtAuthResponse;
// import com.backend.spring3.tienda.dto.LoginDto;
// import com.backend.spring3.tienda.dto.RegisterDto;
// import com.backend.spring3.tienda.service.AuthService;

// @CrossOrigin("*")
// @AllArgsConstructor
// @RestController
// @RequestMapping("/api/auth")
// public class AuthController {
    

//     private AuthService authService;

//     // Build Register REST API
//     @PostMapping("/register")
//     public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
//         String response = authService.register(registerDto);
//         return new ResponseEntity<>(response, HttpStatus.CREATED);
//     }

//     // Build Login REST API
//     @PostMapping("/login")
//     public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
//         String token = authService.login(loginDto);

//         JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
//         jwtAuthResponse.setAccessToken(token);

//         return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
//     }
// }
