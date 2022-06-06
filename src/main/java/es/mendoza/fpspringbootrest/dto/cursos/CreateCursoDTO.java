package es.mendoza.fpspringbootrest.dto.cursos;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCursoDTO {
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;
    @NotBlank(message = "Las siglas no pueden estar vacías")
    private String siglas;
}
