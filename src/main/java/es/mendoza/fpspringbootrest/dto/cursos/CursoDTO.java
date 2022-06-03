package es.mendoza.fpspringbootrest.dto.cursos;

import es.mendoza.fpspringbootrest.models.Modulo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
@Getter
@Setter
public class CursoDTO {
    private Long id;
    private String nombre;
    private String siglas;
    private Set<Modulo> modulos;
    private LocalDateTime createdAt;
}
