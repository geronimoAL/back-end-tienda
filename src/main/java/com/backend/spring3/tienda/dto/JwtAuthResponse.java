package com.backend.spring3.tienda.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthResponse {

    private String id;
    private String accessToken;
    private String name;
    private String dateOfAdmission;
    private String role;
    private String email;
}
