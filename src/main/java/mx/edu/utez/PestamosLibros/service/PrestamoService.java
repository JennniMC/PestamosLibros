package mx.edu.utez.PestamosLibros.service;

import mx.edu.utez.PestamosLibros.exceptions.customExceptions.CustomBadException;
import mx.edu.utez.PestamosLibros.model.Lector.Lector;
import mx.edu.utez.PestamosLibros.model.Libro.Libro;
import mx.edu.utez.PestamosLibros.model.Prestamo.Estado;
import mx.edu.utez.PestamosLibros.model.Prestamo.Prestamo;
import mx.edu.utez.PestamosLibros.repository.Lector.LectorRepository;
import mx.edu.utez.PestamosLibros.repository.Libro.LibroRepository;
import mx.edu.utez.PestamosLibros.repository.Prestamo.PrestamoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final LectorRepository lectorRepository;
    private final LibroRepository libroRepository;

    public PrestamoService(PrestamoRepository prestamoRepository,
                           LectorRepository lectorRepository,
                           LibroRepository libroRepository) {
        this.prestamoRepository = prestamoRepository;
        this.lectorRepository = lectorRepository;
        this.libroRepository = libroRepository;
    }


    @Transactional
    public Prestamo solicitarPrestamo(Long lectorId, Long libroId) {
        Lector lector = lectorRepository.findById(lectorId)
                .orElseThrow(() -> new CustomBadException("El lector especificado no existe."));

        if (!lector.isActivo()) {
            throw new CustomBadException("El lector se encuentra inactivo o bloqueado.");
        }

        Libro libro = libroRepository.findById(libroId)
                .orElseThrow(() -> new CustomBadException("El libro especificado no existe."));

        if (libro.getEjemplaresDisponibles() < 1) {
            throw new CustomBadException("El libro no cuenta con ejemplares disponibles.");
        }

        List<Estado> estadosActivos = List.of(Estado.PRESTADO, Estado.VENCIDO);
        int prestamosActivos = prestamoRepository.countByLectorIdAndEstadoIn(lectorId, estadosActivos);

        int limite = lector.isMembresiaPremium() ? 5 : 3;
        if (prestamosActivos >= limite) {
            throw new CustomBadException("El lector alcanzó el límite máximo de préstamos activos (" + limite + ").");
        }


        libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() - 1);
        libroRepository.save(libro);


        Prestamo prestamo = new Prestamo();
        prestamo.setLector(lector);
        prestamo.setLibro(libro);
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaDevolucionMaxima(LocalDate.now().plusDays(14));
        prestamo.setEstado(Estado.PRESTADO);

        return prestamoRepository.save(prestamo);
    }


    @Transactional
    public Prestamo devolverLibro(Long prestamoId) {
        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new CustomBadException("El préstamo no existe."));

        if (prestamo.getEstado() == Estado.DEVUELTO) {
            throw new CustomBadException("El préstamo ya fue devuelto anteriormente.");
        }

        prestamo.setEstado(Estado.DEVUELTO);


        Libro libro = prestamo.getLibro();
        libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() + 1);
        libroRepository.save(libro);


        if (LocalDate.now().isAfter(prestamo.getFechaDevolucionMaxima())) {
            Lector lector = prestamo.getLector();
            lector.setActivo(false);
            lectorRepository.save(lector);
        }

        return prestamoRepository.save(prestamo);
    }


    @Transactional
    public Prestamo renovarPrestamo(Long prestamoId) {
        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new CustomBadException("El préstamo no existe."));

        if (prestamo.getEstado() == Estado.VENCIDO || prestamo.getEstado() == Estado.DEVUELTO) {
            throw new CustomBadException("No se puede renovar un préstamo en estado VENCIDO o DEVUELTO.");
        }

        prestamo.setFechaDevolucionMaxima(prestamo.getFechaDevolucionMaxima().plusDays(7));
        return prestamoRepository.save(prestamo);
    }


    @Transactional
    public Lector rehabilitarLector(Long lectorId) {
        Lector lector = lectorRepository.findById(lectorId)
                .orElseThrow(() -> new CustomBadException("El lector no existe."));

        lector.setActivo(true);
        return lectorRepository.save(lector);
    }


    public List<Prestamo> listarPrestamosActivosLector(Long lectorId) {
        if (!lectorRepository.existsById(lectorId)) {
            throw new CustomBadException("El lector no existe.");
        }
        return prestamoRepository.findByLectorIdAndEstadoIn(lectorId, List.of(Estado.PRESTADO, Estado.VENCIDO));
    }
}