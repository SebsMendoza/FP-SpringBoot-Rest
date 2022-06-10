package es.mendoza.fpspringbootrest.dto.calificaciones;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ListCalificacionesDTO {
    private final String consulta = LocalDateTime.now().toString();
    String project = "SpringBootDAM";
    String version = "1.0";
    List<CalificacionDTO> data;
}
