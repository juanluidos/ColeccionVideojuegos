package com.coleccion.videojuegos.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.coleccion.videojuegos.service.UserDetailServiceImpl;
import com.coleccion.videojuegos.service.UserService;
import com.coleccion.videojuegos.web.controllers.dto.AuthCreateUserRequest;
import com.coleccion.videojuegos.web.controllers.dto.AuthLoginRequest;
import com.coleccion.videojuegos.web.controllers.dto.AuthResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    
    @Autowired
    private UserDetailServiceImpl userDetailService;
    
    @Autowired
    private UserService userService;

    /**   Endpoint de Login **/
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest userRequest) {
        return new ResponseEntity<>(userDetailService.loginUser(userRequest), HttpStatus.OK);
    }

    /**   Endpoint de Signup **/
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid AuthCreateUserRequest userRequest) {
        return new ResponseEntity<>(userService.registerUser(userRequest), HttpStatus.CREATED);
    }    
}
