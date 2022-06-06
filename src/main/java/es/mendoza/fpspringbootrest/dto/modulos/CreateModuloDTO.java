package es.mendoza.fpspringbootrest.dto.modulos;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateModuloDTO {
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;
    @NotBlank(message = "Las siglas no pueden estar vacías")
    private String siglas;
}
