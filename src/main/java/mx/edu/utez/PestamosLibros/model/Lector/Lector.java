package mx.edu.utez.PestamosLibros.model.Lector;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Lector")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Lector {
    //id (Long)
    //nombre (String)
    //activo (boolean)
    //membresia Premium (boolean)

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private boolean activo;
    private boolean membresiaPremium;
}
