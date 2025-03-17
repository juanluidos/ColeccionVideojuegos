package com.coleccion.videojuegos.web.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {

    /** 🔹 Manejo de acceso denegado (403 Forbidden) **/
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acceso denegado: No tienes permisos para esta acción.");
    }

    /** 🔹 Manejo de datos inválidos */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Datos inválidos: " + ex.getMessage());
    }

    /** 🔹 Manejo de errores inesperados */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en la aplicación: " + ex.getMessage());
    }

    /** 🔹 Manejo de errores de deserialización de Enum */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String message = ex.getMessage();

        if (message.contains("Cannot deserialize value of type")) {
            String fieldName = extractEnumFieldName(message);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: El valor proporcionado para el campo " + fieldName + " no es válido.");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error en la petición: JSON mal formado.");
    }

    /** 🔹 Manejo de cualquier otro error */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatusCode())
                .body("Error: " + ex.getReason());
    }

    /** 🛠 Extraer el nombre del campo Enum de manera limpia */
    private String extractEnumFieldName(String message) {
        try {
            // Extraer desde la posición donde empieza el nombre completo del enum
            int startIndex = message.indexOf("com.coleccion.videojuegos.entity.Enums.");
            if (startIndex != -1) {
                startIndex += "com.coleccion.videojuegos.entity.Enums.".length();
                String fieldName = message.substring(startIndex).split(" ")[0].replace("`", "").replace(".", "");
                return fieldName;
            }            String fieldName = message.substring(startIndex);

            // Eliminar caracteres no deseados al final
            fieldName = fieldName.split(" ")[0]
                    .replace("`", "") // Elimina tildes accidentales
                    .replace(".", ""); // Elimina puntos al inicio

            return fieldName;
        } catch (Exception e) {
            return "desconocido"; // Si hay un fallo, devolver un nombre genérico
        }
    }
}
