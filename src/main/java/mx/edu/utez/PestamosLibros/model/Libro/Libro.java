package mx.edu.utez.PestamosLibros.model.Libro;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Libro")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Libro {
    //id (Long)
    //titulo (String)
    //ejemplaresDisponibles (int)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private int ejemplaresDisponibles;
}
