package com.backend.spring3.tienda.service.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
    
     private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    @Override
    public String register(RegisterDto registerDto) {
       // check username is already exists in database
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new TodoAPIException(HttpStatus.BAD_REQUEST, "Username already exists!");
        }

        // check email is already exists in database
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new TodoAPIException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER");
        roles.add(userRole);

        user.setRoles(roles);

        userRepository.save(user);

        return "User Registered Successfully!.";
    }
    @Override
    public JwtAuthResponse login(LoginDto loginDto) {
       Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),
                loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        Optional<User>userOptional=userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(),loginDto.getUsernameOrEmail());

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
