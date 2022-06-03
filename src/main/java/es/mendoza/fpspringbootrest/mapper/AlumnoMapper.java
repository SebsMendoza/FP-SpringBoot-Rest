package es.mendoza.fpspringbootrest.mapper;

import es.mendoza.fpspringbootrest.dto.alumnos.AlumnoDTO;
import es.mendoza.fpspringbootrest.dto.alumnos.CreateAlumnoDTO;
import es.mendoza.fpspringbootrest.dto.alumnos.ListAlumnoDTO;
import es.mendoza.fpspringbootrest.models.Alumno;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AlumnoMapper {
    private final ModelMapper modelMapper;

    public AlumnoDTO toDTO(Alumno alumno) {
        return modelMapper.map(alumno, AlumnoDTO.class);
    }

    public Alumno fromDTO(AlumnoDTO alumnoDTO) {
        return modelMapper.map(alumnoDTO, Alumno.class);
    }

    public List<AlumnoDTO> toDTO(List<Alumno> alumnos) {
        return alumnos.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Alumno fromDTO(CreateAlumnoDTO alumnoDTO) {
        return modelMapper.map(alumnoDTO, Alumno.class);
    }

    public ListAlumnoDTO toListDTO(List<Alumno> alumnos) {
        ListAlumnoDTO listAlumnoDTO = new ListAlumnoDTO();
        listAlumnoDTO.setData(alumnos.stream().map(this::toDTO).collect(Collectors.toList()));
        return listAlumnoDTO;
    }
}
