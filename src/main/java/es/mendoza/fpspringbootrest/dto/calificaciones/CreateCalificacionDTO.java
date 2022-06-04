package es.mendoza.fpspringbootrest.dto.calificaciones;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CreateCalificacionDTO {
    @NotBlank
    private double nota;
}
