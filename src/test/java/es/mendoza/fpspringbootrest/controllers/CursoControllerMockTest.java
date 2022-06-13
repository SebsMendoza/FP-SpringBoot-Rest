package es.mendoza.fpspringbootrest.controllers;

import es.mendoza.fpspringbootrest.controllers.curso.CursoRestController;
import es.mendoza.fpspringbootrest.dto.cursos.CreateCursoDTO;
import es.mendoza.fpspringbootrest.dto.cursos.CursoDTO;
import es.mendoza.fpspringbootrest.errors.cursos.CursoBadRequestException;
import es.mendoza.fpspringbootrest.errors.cursos.CursoNotFoundException;
import es.mendoza.fpspringbootrest.mapper.CursoMapper;
import es.mendoza.fpspringbootrest.models.Curso;
import es.mendoza.fpspringbootrest.repositories.CursoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CursoControllerMockTest {
    @MockBean
    private final CursoRepository cursoRepository;
    @MockBean
    private final CursoMapper cursoMapper;

    private final Curso curso = Curso.builder()
            .id(1L)
            .nombre("Curso Prueba")
            .siglas("CP")
            .build();

    @InjectMocks
    private CursoRestController cursoRestController;

    @Autowired
    public CursoControllerMockTest(CursoRepository cursoRepository, CursoMapper cursoMapper) {
        this.cursoRepository = cursoRepository;
        this.cursoMapper = cursoMapper;
    }

    @Test
    void getAllCursosTestMock() {
        CursoDTO dto = CursoDTO.builder()
                .nombre(curso.getNombre())
                .siglas(curso.getSiglas())
                .build();

        Mockito.when(cursoRepository.findAll()).thenReturn(List.of(curso));
        Mockito.when(cursoMapper.toDTO(List.of(curso))).thenReturn(List.of(dto));

        ResponseEntity<List<CursoDTO>> response = cursoRestController.findAll(java.util.Optional.empty(), java.util.Optional.empty());
        List<CursoDTO> res = response.getBody();
        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.get(0).getNombre(), curso.getNombre()),
                () -> assertEquals(res.get(0).getSiglas(), curso.getSiglas())
        );
    }

    @Test
    void getCursoByIdTestMock() {
        CursoDTO dto = CursoDTO.builder()
                .nombre(curso.getNombre())
                .siglas(curso.getSiglas())
                .build();

        Mockito.when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));
        Mockito.when(cursoMapper.toDTO(curso)).thenReturn(dto);

        ResponseEntity<CursoDTO> response = cursoRestController.findById(1L);
        CursoDTO res = response.getBody();
        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getNombre(), curso.getNombre()),
                () -> assertEquals(res.getSiglas(), curso.getSiglas())
        );

        Mockito.verify(cursoRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(cursoMapper, Mockito.times(1)).toDTO(curso);
    }

    @Test
    void findByIDException() {
        Mockito.when(cursoRepository.findById(1L)).thenReturn(Optional.empty());
        Exception ex = assertThrows(CursoNotFoundException.class, () -> {
            cursoRestController.findById(1L);
        });

        assertTrue(ex.getMessage().contains("curso"));
        Mockito.verify(cursoRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void saveCursoTestMock() {
        CreateCursoDTO createDTO = CreateCursoDTO.builder()
                .nombre(curso.getNombre())
                .siglas(curso.getSiglas())
                .build();

        CursoDTO dto = CursoDTO.builder()
                .nombre(curso.getNombre())
                .siglas(curso.getSiglas())
                .build();

        Mockito.when(cursoRepository.save(curso)).thenReturn(curso);
        Mockito.when(cursoMapper.fromDTO(createDTO)).thenReturn(curso);
        Mockito.when(cursoMapper.toDTO(curso)).thenReturn(dto);

        ResponseEntity<CursoDTO> response = cursoRestController.save(createDTO);
        CursoDTO res = response.getBody();

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getNombre(), curso.getNombre()),
                () -> assertEquals(res.getSiglas(), curso.getSiglas())
        );

        Mockito.verify(cursoRepository, Mockito.times(1)).save(curso);
        Mockito.verify(cursoMapper, Mockito.times(1)).fromDTO(createDTO);
        Mockito.verify(cursoMapper, Mockito.times(1)).toDTO(curso);
    }

    @Test
    void checkCursoDataNameExceptionTest() {
        CreateCursoDTO createDTO = CreateCursoDTO.builder()
                .nombre("")
                .siglas(curso.getSiglas())
                .build();

        Curso cur = Curso.builder()
                .nombre("")
                .siglas(curso.getSiglas())
                .build();

        Mockito.when(cursoMapper.fromDTO(createDTO)).thenReturn(cur);
        Exception ex = assertThrows(CursoBadRequestException.class, () -> {
            cursoRestController.save(createDTO);
        });

        assertTrue(ex.getMessage().contains("nombre"));
        Mockito.verify(cursoMapper, Mockito.times(1)).fromDTO(createDTO);
    }

    @Test
    void updateCursoTestMock() {
        CursoDTO dto = CursoDTO.builder()
                .nombre(curso.getNombre())
                .siglas(curso.getSiglas())
                .build();

        Mockito.when(cursoRepository.findById(1L)).thenReturn(java.util.Optional.of(curso));
        Mockito.when(cursoRepository.save(curso)).thenReturn(curso);
        Mockito.when(cursoMapper.toDTO(curso)).thenReturn(dto);

        ResponseEntity<CursoDTO> response = cursoRestController.update(1L, curso);
        CursoDTO res = response.getBody();
        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getNombre(), curso.getNombre()),
                () -> assertEquals(res.getSiglas(), curso.getSiglas())
        );

        Mockito.verify(cursoRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(cursoRepository, Mockito.times(1)).save(curso);
        Mockito.verify(cursoMapper, Mockito.times(1)).toDTO(curso);
    }

    @Test
    void deleteCursoTestMock() {
        CursoDTO dto = CursoDTO.builder()
                .nombre(curso.getNombre())
                .siglas(curso.getSiglas())
                .build();

        Mockito.when(cursoRepository.findById(1L)).thenReturn(java.util.Optional.of(curso));
        Mockito.when(cursoMapper.toDTO(curso)).thenReturn(dto);

        ResponseEntity<CursoDTO> response = cursoRestController.delete(1L);
        CursoDTO res = response.getBody();
        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getNombre(), curso.getNombre()),
                () -> assertEquals(res.getSiglas(), curso.getSiglas())
        );
        Mockito.verify(cursoRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(cursoMapper, Mockito.times(1)).toDTO(curso);
    }
}