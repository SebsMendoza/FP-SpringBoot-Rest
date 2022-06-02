package es.mendoza.fpspringbootrest.dto;

import es.mendoza.fpspringbootrest.models.Modulo;

import java.time.LocalDateTime;
import java.util.Set;

public class CursoDTO {
    private Long id;
    private String nombre;
    private String siglas;
    private Set<Modulo> modulos;
    private LocalDateTime createdAt;
}
