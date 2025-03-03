package com.coleccion.videojuegos.web.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    
    @GetMapping("/prueba")
    public String getMethodName() {
        return "Prueba";
    }
}
