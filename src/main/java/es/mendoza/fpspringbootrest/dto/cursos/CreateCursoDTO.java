package es.mendoza.fpspringbootrest.dto.cursos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CreateCursoDTO {
    @NotBlank
    private String nombre;
    private String siglas;
}
