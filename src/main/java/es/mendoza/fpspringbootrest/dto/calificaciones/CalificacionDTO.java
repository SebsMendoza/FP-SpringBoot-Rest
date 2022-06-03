package es.mendoza.fpspringbootrest.dto.calificaciones;

import es.mendoza.fpspringbootrest.models.Alumno;
import es.mendoza.fpspringbootrest.models.Modulo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalificacionDTO {
    private Long id;
    private double nota;
    private Alumno alumno;
    private Modulo modulo;
}
