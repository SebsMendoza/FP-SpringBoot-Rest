package es.mendoza.fpspringbootrest.controllers;

import es.mendoza.fpspringbootrest.controllers.alumno.AlumnoRestController;
import es.mendoza.fpspringbootrest.dto.alumnos.AlumnoDTO;
import es.mendoza.fpspringbootrest.dto.alumnos.CreateAlumnoDTO;
import es.mendoza.fpspringbootrest.errors.alumnos.AlumnoBadRequestException;
import es.mendoza.fpspringbootrest.errors.alumnos.AlumnoNotFoundException;
import es.mendoza.fpspringbootrest.mapper.AlumnoMapper;
import es.mendoza.fpspringbootrest.models.Alumno;
import es.mendoza.fpspringbootrest.repositories.AlumnoRepository;
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
public class AlumnoControllerMockTest {
    @MockBean
    private final AlumnoRepository alumnoRepository;
    @MockBean
    private final AlumnoMapper alumnoMapper;

    private final Alumno alumno = Alumno.builder()
            .id(1L)
            .nombre("Maria")
            .correo("mar@prueba.com")
            .build();

    @InjectMocks
    private AlumnoRestController alumnoRestController;

    @Autowired
    public AlumnoControllerMockTest(AlumnoRepository alumnoRepository, AlumnoMapper alumnoMapper) {
        this.alumnoRepository = alumnoRepository;
        this.alumnoMapper = alumnoMapper;
    }

    @Test
    void getAllAlumnosTestMock() {
        AlumnoDTO dto = AlumnoDTO.builder()
                .nombre(alumno.getNombre())
                .correo(alumno.getCorreo())
                .build();

        Mockito.when(alumnoRepository.findAll()).thenReturn(List.of(alumno));
        Mockito.when(alumnoMapper.toDTO(List.of(alumno))).thenReturn(List.of(dto));

        ResponseEntity<List<AlumnoDTO>> response = alumnoRestController.findAll(java.util.Optional.empty(), java.util.Optional.empty());
        List<AlumnoDTO> res = response.getBody();
        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.get(0).getNombre(), alumno.getNombre()),
                () -> assertEquals(res.get(0).getCorreo(), alumno.getCorreo())
        );
    }

    @Test
    void getAlumnoByIdTestMock() {
        AlumnoDTO dto = AlumnoDTO.builder()
                .nombre(alumno.getNombre())
                .correo(alumno.getCorreo())
                .build();

        Mockito.when(alumnoRepository.findById(1L)).thenReturn(Optional.of(alumno));
        Mockito.when(alumnoMapper.toDTO(alumno)).thenReturn(dto);

        ResponseEntity<AlumnoDTO> response = alumnoRestController.findById(1L);
        AlumnoDTO res = response.getBody();
        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getNombre(), alumno.getNombre()),
                () -> assertEquals(res.getCorreo(), alumno.getCorreo())
        );

        Mockito.verify(alumnoRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(alumnoMapper, Mockito.times(1)).toDTO(alumno);
    }

    @Test
    void findByIDException() {
        Mockito.when(alumnoRepository.findById(1L)).thenReturn(Optional.empty());
        Exception ex = assertThrows(AlumnoNotFoundException.class, () -> {
            alumnoRestController.findById(1L);
        });

        assertFalse(ex.getMessage().contains("maria"));
        Mockito.verify(alumnoRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void saveAlumnoTestMock() {
        CreateAlumnoDTO createDTO = CreateAlumnoDTO.builder()
                .nombre(alumno.getNombre())
                .correo(alumno.getCorreo())
                .build();

        AlumnoDTO dto = AlumnoDTO.builder()
                .nombre(alumno.getNombre())
                .correo(alumno.getCorreo())
                .build();

        Mockito.when(alumnoRepository.save(alumno)).thenReturn(alumno);
        Mockito.when(alumnoMapper.fromDTO(createDTO)).thenReturn(alumno);
        Mockito.when(alumnoMapper.toDTO(alumno)).thenReturn(dto);

        ResponseEntity<AlumnoDTO> response = alumnoRestController.save(createDTO);
        AlumnoDTO res = response.getBody();
        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getNombre(), alumno.getNombre()),
                () -> assertEquals(res.getCorreo(), alumno.getCorreo())
        );

        Mockito.verify(alumnoRepository, Mockito.times(1)).save(alumno);
        Mockito.verify(alumnoMapper, Mockito.times(1)).fromDTO(createDTO);
        Mockito.verify(alumnoMapper, Mockito.times(1)).toDTO(alumno);
    }

    @Test
    void checkAlumnoDataNameExceptionTest() {
        CreateAlumnoDTO createDTO = CreateAlumnoDTO.builder()
                .nombre("")
                .correo(alumno.getCorreo())
                .build();

        Alumno alum = Alumno.builder()
                .nombre("")
                .correo(alumno.getCorreo())
                .build();

        Mockito.when(alumnoMapper.fromDTO(createDTO)).thenReturn(alum);
        Exception ex = assertThrows(AlumnoBadRequestException.class, () -> {
            alumnoRestController.save(createDTO);
        });

        assertTrue(ex.getMessage().contains("nombre"));
        Mockito.verify(alumnoMapper, Mockito.times(1)).fromDTO(createDTO);
    }

    @Test
    void updateAlumnoTestMock() {
        AlumnoDTO dto = AlumnoDTO.builder()
                .nombre(alumno.getNombre())
                .correo(alumno.getCorreo())
                .build();

        Mockito.when(alumnoRepository.findById(1L)).thenReturn(java.util.Optional.of(alumno));
        Mockito.when(alumnoRepository.save(alumno)).thenReturn(alumno);
        Mockito.when(alumnoMapper.toDTO(alumno)).thenReturn(dto);

        ResponseEntity<AlumnoDTO> response = alumnoRestController.update(1L, alumno);
        AlumnoDTO res = response.getBody();
        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getNombre(), alumno.getNombre()),
                () -> assertEquals(res.getCorreo(), alumno.getCorreo())
        );

        Mockito.verify(alumnoRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(alumnoRepository, Mockito.times(1)).save(alumno);
        Mockito.verify(alumnoMapper, Mockito.times(1)).toDTO(alumno);
    }

    @Test
    void deleteAlumnoTestMock() {
        AlumnoDTO dto = AlumnoDTO.builder()
                .nombre(alumno.getNombre())
                .correo(alumno.getCorreo())
                .build();

        Mockito.when(alumnoRepository.findById(1L)).thenReturn(java.util.Optional.of(alumno));
        Mockito.when(alumnoMapper.toDTO(alumno)).thenReturn(dto);

        ResponseEntity<AlumnoDTO> response = alumnoRestController.delete(1L);
        AlumnoDTO res = response.getBody();
        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getNombre(), alumno.getNombre()),
                () -> assertEquals(res.getCorreo(), alumno.getCorreo())
        );

        Mockito.verify(alumnoRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(alumnoMapper, Mockito.times(1)).toDTO(alumno);
    }

}
