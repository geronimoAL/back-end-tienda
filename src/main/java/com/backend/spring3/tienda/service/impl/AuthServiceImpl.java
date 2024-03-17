package com.backend.spring3.tienda.service.impl;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.security.core.Authentication;

import com.backend.spring3.tienda.dto.JwtAuthResponse;
import com.backend.spring3.tienda.dto.LoginDto;
import com.backend.spring3.tienda.dto.RegisterDto;
import com.backend.spring3.tienda.entity.Role;
import com.backend.spring3.tienda.entity.User;
import com.backend.spring3.tienda.exception.TodoAPIException;
import com.backend.spring3.tienda.repository.RoleRepository;
import com.backend.spring3.tienda.repository.UserRepository;
import com.backend.spring3.tienda.security.JwtTokenProvider;
import com.backend.spring3.tienda.service.AuthService;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService{
    
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
     private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    @Override
    public String register(RegisterDto registerDto) {
        
        logger.info("Entrando a registro controller");

        if(userRepository.existsByName(registerDto.getName())){
            throw new TodoAPIException(HttpStatus.BAD_REQUEST, "El nombre ya existe en la Base de datos !");
        }

        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new TodoAPIException(HttpStatus.BAD_REQUEST, "Email ya existe en la base de datos!");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setDateOfAdmission(LocalDate.now());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER");
        roles.add(userRole);

        user.setRoles(roles);

        userRepository.save(user);

        return "Usuario Registrado con éxito!.";
    }
    @Override
    public JwtAuthResponse login(LoginDto loginDto) {

       logger.info("entrando a login");
       Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(),
                loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        Optional<User>userOptional=userRepository.findByEmail(loginDto.getEmail());

        String role=null;
        String name=null;
        if(userOptional.isPresent()){
            User loggedUser=userOptional.get();
            name=loggedUser.getName();
            Optional<Role> roleOptional=loggedUser.getRoles().stream().findFirst();

            if(roleOptional.isPresent()){
             Role userRole=roleOptional.get();
             role=userRole.getName();
            }
        }

        JwtAuthResponse jwtAuthResponse=new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        jwtAuthResponse.setRole(role);
        jwtAuthResponse.setName(name);



        return jwtAuthResponse;
    }
}
