package com.coleccion.videojuegos.web.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/public")
public class PublicController {
    
    @GetMapping("/prueba")
    public String getMethodName() {
        return "Prueba";
    }
}
