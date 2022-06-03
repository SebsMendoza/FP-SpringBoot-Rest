package es.mendoza.fpspringbootrest.dto.alumnos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ListAlumnoDTO {
    LocalDateTime consulta = LocalDateTime.now();
    String project = "SpringBootDAM";
    String version = "1.0";
    List<AlumnoDTO> data;
}
