package es.mendoza.fpspringbootrest.dto.cursos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ListCursosDTO {
    LocalDateTime consulta = LocalDateTime.now();
    String project = "SpringBootDAM";
    String version = "1.0";
    List<CursoDTO> data;
}
