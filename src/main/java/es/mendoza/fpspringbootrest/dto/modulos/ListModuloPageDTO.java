package es.mendoza.fpspringbootrest.dto.modulos;

import es.mendoza.fpspringbootrest.config.APIConfig;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class ListModuloPageDTO {
    private final LocalDateTime consulta = LocalDateTime.now();
    private final String project = "SpringBootDAM";
    private final String version = APIConfig.API_VERSION;
    private List<ModuloDTO> data;
    private int currentPage;
    private long totalElements;
    private int totalPages;
}
