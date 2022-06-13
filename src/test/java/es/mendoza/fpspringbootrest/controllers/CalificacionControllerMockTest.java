package es.mendoza.fpspringbootrest.controllers;

import es.mendoza.fpspringbootrest.controllers.calificacion.CalificacionRestController;
import es.mendoza.fpspringbootrest.dto.calificaciones.CalificacionDTO;
import es.mendoza.fpspringbootrest.dto.calificaciones.CreateCalificacionDTO;
import es.mendoza.fpspringbootrest.dto.cursos.CursoDTO;
import es.mendoza.fpspringbootrest.errors.calificaciones.CalificacionBadRequestException;
import es.mendoza.fpspringbootrest.errors.calificaciones.CalificacionNotFoundException;
import es.mendoza.fpspringbootrest.mapper.CalificacionMapper;
import es.mendoza.fpspringbootrest.models.Calificacion;
import es.mendoza.fpspringbootrest.repositories.CalificacionRepository;
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
public class CalificacionControllerMockTest {
    @MockBean
    private final CalificacionRepository calificacionRepository;
    @MockBean
    private final CalificacionMapper calificacionMapper;

    private final Calificacion calificacion = Calificacion.builder()
            .id(1L)
            .nota(8.9)
            .build();

    @InjectMocks
    private CalificacionRestController calificacionRestController;

    @Autowired
    public CalificacionControllerMockTest(CalificacionRepository calificacionRepository, CalificacionMapper calificacionMapper) {
        this.calificacionRepository = calificacionRepository;
        this.calificacionMapper = calificacionMapper;
    }

    @Test
    void getAllCalificacionesTestMock() {
        CalificacionDTO dto = CalificacionDTO.builder()
                .nota(calificacion.getNota())
                .build();

        Mockito.when(calificacionRepository.findAll()).thenReturn(List.of(calificacion));
        Mockito.when(calificacionMapper.toDTO(List.of(calificacion))).thenReturn(List.of(dto));

        ResponseEntity<List<CalificacionDTO>> response = calificacionRestController.findAll(java.util.Optional.empty());
        List<CalificacionDTO> res = response.getBody();
        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.get(0).getNota(), calificacion.getNota())
        );
    }

    @Test
    void getCalificacionByIdTestMock() {
        CalificacionDTO dto = CalificacionDTO.builder()
                .nota(calificacion.getNota())
                .build();

        Mockito.when(calificacionRepository.findById(1L)).thenReturn(Optional.of(calificacion));
        Mockito.when(calificacionMapper.toDTO(calificacion)).thenReturn(dto);

        ResponseEntity<CalificacionDTO> response = calificacionRestController.findById(1L);
        CalificacionDTO res = response.getBody();
        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getNota(), calificacion.getNota())
        );

        Mockito.verify(calificacionRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(calificacionMapper, Mockito.times(1)).toDTO(calificacion);
    }

    @Test
    void findByIDException() {
        Mockito.when(calificacionRepository.findById(1L)).thenReturn(Optional.empty());
        Exception ex = assertThrows(CalificacionNotFoundException.class, () -> {
            calificacionRestController.findById(1L);
        });

        assertFalse(ex.getMessage().contains("calificacion"));
        Mockito.verify(calificacionRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void saveCalificacionTestMock() {
        CreateCalificacionDTO createDTO = CreateCalificacionDTO.builder()
                .nota(calificacion.getNota())
                .build();

        CalificacionDTO dto = CalificacionDTO.builder()
                .nota(calificacion.getNota())
                .build();

        Mockito.when(calificacionRepository.save(calificacion)).thenReturn(calificacion);
        Mockito.when(calificacionMapper.fromDTO(createDTO)).thenReturn(calificacion);
        Mockito.when(calificacionMapper.toDTO(calificacion)).thenReturn(dto);

        ResponseEntity<CalificacionDTO> response = calificacionRestController.save(createDTO);
        CalificacionDTO res = response.getBody();
        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getNota(), calificacion.getNota())
        );

        Mockito.verify(calificacionRepository, Mockito.times(1)).save(calificacion);
        Mockito.verify(calificacionMapper, Mockito.times(1)).fromDTO(createDTO);
        Mockito.verify(calificacionMapper, Mockito.times(1)).toDTO(calificacion);
    }

    @Test
    void checkCalificacionDataNotaExceptionTest() {
        CreateCalificacionDTO createDTO = CreateCalificacionDTO.builder()
                .nota(-1.0)
                .build();

        Calificacion cali = Calificacion.builder()
                .nota(-1.0)
                .build();

        Mockito.when(calificacionMapper.fromDTO(createDTO)).thenReturn(cali);
        Exception ex = assertThrows(CalificacionBadRequestException.class, () -> {
            calificacionRestController.save(createDTO);
        });

        assertFalse(ex.getMessage().contains("calificacion"));
        Mockito.verify(calificacionMapper, Mockito.times(1)).fromDTO(createDTO);
    }

    @Test
    void updateCursoTestMock() {
        CalificacionDTO dto = CalificacionDTO.builder()
                .nota(calificacion.getNota())
                .build();

        Mockito.when(calificacionRepository.findById(1L)).thenReturn(java.util.Optional.of(calificacion));
        Mockito.when(calificacionRepository.save(calificacion)).thenReturn(calificacion);
        Mockito.when(calificacionMapper.toDTO(calificacion)).thenReturn(dto);

        ResponseEntity<CalificacionDTO> response = calificacionRestController.update(1L, calificacion);
        CalificacionDTO res = response.getBody();
        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getNota(), calificacion.getNota())
        );
        Mockito.verify(calificacionRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(calificacionRepository, Mockito.times(1)).save(calificacion);
        Mockito.verify(calificacionMapper, Mockito.times(1)).toDTO(calificacion);
    }

    @Test
    void deleteCalificacionByIdTestMock() {
        CalificacionDTO dto = CalificacionDTO.builder()
                .nota(calificacion.getNota())
                .build();

        Mockito.when(calificacionRepository.findById(1L)).thenReturn(java.util.Optional.of(calificacion));
        Mockito.when(calificacionMapper.toDTO(calificacion)).thenReturn(dto);

        ResponseEntity<CalificacionDTO> response = calificacionRestController.delete(1L);
        CalificacionDTO res = response.getBody();
        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getNota(), calificacion.getNota())
        );

        Mockito.verify(calificacionRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(calificacionMapper, Mockito.times(1)).toDTO(calificacion);
    }
}
