package es.mendoza.fpspringbootrest.dto.alumnos;

import es.mendoza.fpspringbootrest.models.Calificacion;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlumnoDTO {
    private Long id;
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;
    @NotBlank(message = "El correo no puede estar vacío")
    private String correo;
    private Set<Calificacion> notas;
    private String createdAt;
    private String updatedAt;
    private String imagen;
}
