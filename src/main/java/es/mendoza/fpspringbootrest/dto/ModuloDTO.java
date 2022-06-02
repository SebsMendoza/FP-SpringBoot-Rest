package es.mendoza.fpspringbootrest.dto;

import es.mendoza.fpspringbootrest.models.Calificacion;

import java.time.LocalDateTime;
import java.util.Set;

public class ModuloDTO {
    private Long id;
    private int anio;
    private String nombre;
    private String siglas;
    private LocalDateTime createdAt;
    private Set<Calificacion> notas;
}
