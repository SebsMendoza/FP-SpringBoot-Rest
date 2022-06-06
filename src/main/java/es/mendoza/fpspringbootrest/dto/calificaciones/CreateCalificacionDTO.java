package es.mendoza.fpspringbootrest.dto.calificaciones;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCalificacionDTO {
    @NotBlank(message = "La nota no puede estar vacía")
    private double nota;
}
