package mx.edu.utez.PestamosLibros.model.Prestamo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.PestamosLibros.model.Lector.Lector;
import mx.edu.utez.PestamosLibros.model.Libro.Libro;

import java.time.LocalDate;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Prestamo")
public class Prestamo {
  //id (Long)
    //lector (ManyToOne)
    //libro (ManyToOne)
    //fechaPrestamo (LocalDate)
    //fechaDevolucionMaxima (LocalDate)
    //estado (PRESTADO, DEVUELTO, VENCIDO)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionMaxima;
    @Enumerated(EnumType.STRING)
    private Estado estado;
    @ManyToOne
    private Lector lector;
    @ManyToOne
    private Libro libro;

}
