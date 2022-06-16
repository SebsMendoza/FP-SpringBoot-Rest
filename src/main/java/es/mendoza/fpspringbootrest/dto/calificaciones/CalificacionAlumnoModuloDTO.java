package es.mendoza.fpspringbootrest.dto.calificaciones;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CalificacionAlumnoModuloDTO {
    private long id;
    private Map<String, Double> calificacion;
    private String nombre;
    private String email;
}
