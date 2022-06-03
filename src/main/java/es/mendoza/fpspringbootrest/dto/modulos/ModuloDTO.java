package es.mendoza.fpspringbootrest.dto.modulos;

import es.mendoza.fpspringbootrest.models.Calificacion;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
@Getter
@Setter
public class ModuloDTO {
    private Long id;
    private int anio;
    private String nombre;
    private String siglas;
    private LocalDateTime createdAt;
    private Set<Calificacion> notas;
}
