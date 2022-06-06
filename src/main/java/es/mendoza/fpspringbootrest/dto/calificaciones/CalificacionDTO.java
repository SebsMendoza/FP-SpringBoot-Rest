package es.mendoza.fpspringbootrest.dto.calificaciones;

import es.mendoza.fpspringbootrest.models.Alumno;
import es.mendoza.fpspringbootrest.models.Modulo;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CalificacionDTO {
    private Long id;
    @NotBlank(message = "El nombre no puede estar vacío")
    private double nota;
    private Alumno alumno;
    private Modulo modulo;
}
