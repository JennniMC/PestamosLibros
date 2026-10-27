package mx.edu.utez.PestamosLibros.repository.Libro;

import mx.edu.utez.PestamosLibros.model.Libro.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libro,Long> {
}
