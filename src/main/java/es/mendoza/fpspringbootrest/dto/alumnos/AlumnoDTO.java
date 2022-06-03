package es.mendoza.fpspringbootrest.dto.alumnos;

import es.mendoza.fpspringbootrest.models.Calificacion;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
@Getter
@Setter
public class AlumnoDTO {
    private Long id;
    private String nombre;
    private String correo;
    private Set<Calificacion> notas;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String imagen;
}
