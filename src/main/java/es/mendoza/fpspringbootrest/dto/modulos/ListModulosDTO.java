package es.mendoza.fpspringbootrest.dto.modulos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ListModulosDTO {
    LocalDateTime consulta = LocalDateTime.now();
    String project = "SpringBootDAM";
    String version = "1.0";
    List<ModuloDTO> data;
}
