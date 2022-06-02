package es.mendoza.fpspringbootrest.dto;

import es.mendoza.fpspringbootrest.models.Calificacion;

import java.time.LocalDateTime;
import java.util.Set;

public class AlumnoDTO {
    private Long id;
    private String nombre;
    private String correo;
    private Set<Calificacion> notas;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String imagen;
}
