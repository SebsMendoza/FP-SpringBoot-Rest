package es.mendoza.fpspringbootrest.dto.modulos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CreateModuloDTO {
    @NotBlank
    private String nombre;
    private String siglas;
}
