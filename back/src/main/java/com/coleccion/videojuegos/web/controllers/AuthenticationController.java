package com.coleccion.videojuegos.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.coleccion.videojuegos.service.UserDetailServiceImpl;
import com.coleccion.videojuegos.service.UserService;
import com.coleccion.videojuegos.web.dto.AuthCreateUserRequest;
import com.coleccion.videojuegos.web.dto.AuthLoginRequest;
import com.coleccion.videojuegos.web.dto.AuthResponse;

import jakarta.validation.Valid;
import org.springframework.security.authentication.BadCredentialsException;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    
    @Autowired
    private UserDetailServiceImpl userDetailService;
    
    @Autowired
    private UserService userService;

    /** ✅ Endpoint de Login **/
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthLoginRequest userRequest) {
        try {
            return ResponseEntity.ok(userDetailService.loginUser(userRequest));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña incorrectos.");
        }
    }

    /** ✅ Endpoint de Signup **/
    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody @Valid AuthCreateUserRequest userRequest) {
        return userService.registerUser(userRequest);
    }
}
