package es.mendoza.fpspringbootrest.dto.alumnos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CreateAlumnoDTO {
    @NotBlank
    private String nombre;
    private String correo;
}
