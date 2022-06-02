package es.mendoza.fpspringbootrest.mapper;

import es.mendoza.fpspringbootrest.dto.CalificacionDTO;
import es.mendoza.fpspringbootrest.dto.CreateCalificacionDTO;
import es.mendoza.fpspringbootrest.models.Calificacion;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CalificacionMapper {
    private final ModelMapper modelMapper;

    public CalificacionDTO toDTO(Calificacion calificacion) {
        return modelMapper.map(calificacion, CalificacionDTO.class);
    }

    public Calificacion fromDTO(CalificacionDTO calificacionDTO) {
        return modelMapper.map(calificacionDTO, Calificacion.class);
    }

    public List<CalificacionDTO> toDTO(List<Calificacion> calificaciones) {
        return calificaciones.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Calificacion fromDTO(CreateCalificacionDTO calificacionDTO) {
        return modelMapper.map(calificacionDTO, Calificacion.class);
    }
}
