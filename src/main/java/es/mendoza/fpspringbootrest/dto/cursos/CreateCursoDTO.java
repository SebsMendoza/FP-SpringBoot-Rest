package es.mendoza.fpspringbootrest.dto.cursos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCursoDTO {
    private String nombre;
    private String siglas;
}
