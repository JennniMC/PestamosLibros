package mx.edu.utez.PestamosLibros.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SolicitarPrestamoDTO {

    @NotNull(message = "El id del Lector es obligatorio")
    private Long lectorId;

    @NotNull(message = "El id del Libro es obligatorio")
    private Long libroId;
}