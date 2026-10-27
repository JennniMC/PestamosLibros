package mx.edu.utez.PestamosLibros.repository.Prestamo;

import mx.edu.utez.PestamosLibros.model.Prestamo.Estado;
import mx.edu.utez.PestamosLibros.model.Prestamo.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrestamoRepository extends JpaRepository<Prestamo,Long> {

    int countByLectorIdAndEstadoIn(Long lectorId, List<Estado> estados);


    List<Prestamo> findByLectorIdAndEstadoIn(Long lectorId, List<Estado> estados);
}
