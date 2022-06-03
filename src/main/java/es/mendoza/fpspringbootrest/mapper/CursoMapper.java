package es.mendoza.fpspringbootrest.mapper;

import es.mendoza.fpspringbootrest.dto.cursos.CreateCursoDTO;
import es.mendoza.fpspringbootrest.dto.cursos.CursoDTO;
import es.mendoza.fpspringbootrest.dto.cursos.ListCursosDTO;
import es.mendoza.fpspringbootrest.models.Curso;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CursoMapper {
    private final ModelMapper modelMapper;

    public CursoDTO toDTO(Curso curso) {
        return modelMapper.map(curso, CursoDTO.class);
    }

    public Curso fromDTO(CursoDTO cursoDTO) {
        return modelMapper.map(cursoDTO, Curso.class);
    }

    public List<CursoDTO> toDTO(List<Curso> cursos) {
        return cursos.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Curso fromDTO(CreateCursoDTO cursoDTO) {
        return modelMapper.map(cursoDTO, Curso.class);
    }

    public ListCursosDTO toListDTO(List<Curso> cursos) {
        ListCursosDTO listCursosDTO = new ListCursosDTO();
        listCursosDTO.setData(cursos.stream().map(this::toDTO).collect(Collectors.toList()));
        return listCursosDTO;
    }
}
