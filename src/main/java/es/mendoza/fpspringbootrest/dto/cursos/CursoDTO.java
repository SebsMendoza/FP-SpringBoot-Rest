package es.mendoza.fpspringbootrest.dto.cursos;

import es.mendoza.fpspringbootrest.models.Modulo;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CursoDTO {
    private Long id;
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;
    @NotBlank(message = "Las siglas no puede estar vacías")
    private String siglas;
    private Set<Modulo> modulos;
    private String createdAt;
}
