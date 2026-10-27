package mx.edu.utez.PestamosLibros.controller;

import jakarta.validation.Valid;
import mx.edu.utez.PestamosLibros.dtos.SolicitarPrestamoDTO;
import mx.edu.utez.PestamosLibros.model.Lector.Lector;
import mx.edu.utez.PestamosLibros.model.Prestamo.Prestamo;
import mx.edu.utez.PestamosLibros.service.PrestamoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin({"*"})
@RequestMapping("/api/prestamos")
public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @PostMapping
    public ResponseEntity<Prestamo> solicitarPrestamo(@Valid @RequestBody SolicitarPrestamoDTO dto) {
        Prestamo nuevoPrestamo = prestamoService.solicitarPrestamo(dto.getLectorId(), dto.getLibroId());
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPrestamo);
    }

    @PutMapping("/{id}/devolver")
    public ResponseEntity<Prestamo> devolverLibro(@PathVariable Long id) {
        Prestamo prestamoDevuelto = prestamoService.devolverLibro(id);
        return ResponseEntity.ok(prestamoDevuelto);
    }

    @PutMapping("/{id}/renovar")
    public ResponseEntity<Prestamo> renovarPrestamo(@PathVariable Long id) {
        Prestamo prestamoRenovado = prestamoService.renovarPrestamo(id);
        return ResponseEntity.ok(prestamoRenovado);
    }

    @PutMapping("/lectores/{lectorId}/rehabilitar")
    public ResponseEntity<Lector> rehabilitarLector(@PathVariable Long lectorId) {
        Lector lectorRehabilitado = prestamoService.rehabilitarLector(lectorId);
        return ResponseEntity.ok(lectorRehabilitado);
    }

    @GetMapping("/lectores/{lectorId}/activos")
    public ResponseEntity<List<Prestamo>> listarPrestamosActivosLector(@PathVariable Long lectorId) {
        List<Prestamo> lista = prestamoService.listarPrestamosActivosLector(lectorId);
        return ResponseEntity.ok(lista);
    }
}