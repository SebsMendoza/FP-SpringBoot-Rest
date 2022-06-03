package es.mendoza.fpspringbootrest.dto.alumnos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAlumnoDTO {
    private String nombre;
    private String correo;
}
