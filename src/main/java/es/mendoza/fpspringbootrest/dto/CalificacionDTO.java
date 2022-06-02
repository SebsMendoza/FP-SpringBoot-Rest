package es.mendoza.fpspringbootrest.dto;

import es.mendoza.fpspringbootrest.models.Alumno;
import es.mendoza.fpspringbootrest.models.Modulo;

public class CalificacionDTO {
    private Long id;
    private double nota;
    private Alumno alumno;
    private Modulo modulo;
}
