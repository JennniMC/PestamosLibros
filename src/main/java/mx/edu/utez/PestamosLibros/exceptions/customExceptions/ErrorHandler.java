package mx.edu.utez.PestamosLibros.exceptions.customExceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validar(
            MethodArgumentNotValidException ex) {
        Map<String, String> errores = new LinkedHashMap<>();
        for (FieldError err : ex.getBindingResult().getFieldErrors()) {
            errores.put(err.getField(), err.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler(CustomBadException.class)
    public ResponseEntity<Map<String, String>> manejarCustomBadRequest(
            CustomBadException ex) {
        Map<String, String> errores = new LinkedHashMap<>();
        errores.put("error", ex.getMessage());
        return ResponseEntity.badRequest().body(errores);
    }
}
