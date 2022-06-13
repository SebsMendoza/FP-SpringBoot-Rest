package es.mendoza.fpspringbootrest.dto.modulos;

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
public class ModuloDTO {
    private Long id;
    private int anio;
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;
    @NotBlank(message = "Las siglas no pueden estar vacías")
    private String siglas;
    private String createdAt;
    private Set<Calificacion> notas;
}
