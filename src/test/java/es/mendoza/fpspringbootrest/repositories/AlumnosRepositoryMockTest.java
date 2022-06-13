package es.mendoza.fpspringbootrest.repositories;

import es.mendoza.fpspringbootrest.models.Alumno;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AlumnosRepositoryMockTest {
    private final Alumno alumno = Alumno.builder()
            .id(1L)
            .nombre("Sebastian")
            .correo("sebs@prueba.com")
            .build();

    @MockBean
    private AlumnoRepository alumnoRepository;

    @Test
    @Order(2)
    public void getAllAlumnos() {
        Mockito.when(alumnoRepository.findAll()).thenReturn(List.of(alumno));
        List<Alumno> res = alumnoRepository.findAll();
        assertAll(
                () -> assertEquals(List.of(alumno), res),
                () -> assertEquals(alumno.getId(), res.get(0).getId()),
                () -> assertEquals(alumno.getNombre(), res.get(0).getNombre()),
                () -> assertEquals(alumno.getCorreo(), res.get(0).getCorreo())
        );
        Mockito.verify(alumnoRepository, Mockito.times(1)).findAll();
    }

    @Test
    @Order(3)
    public void getAlumnoById() {
        Mockito.when(alumnoRepository.findById(alumno.getId())).thenReturn(java.util.Optional.of(alumno));
        Alumno res = alumnoRepository.findById(alumno.getId()).get();
        assertAll(
                () -> assertEquals(alumno, res),
                () -> assertEquals(alumno.getId(), res.getId()),
                () -> assertEquals(alumno.getNombre(), res.getNombre()),
                () -> assertEquals(alumno.getCorreo(), res.getCorreo())
        );
        Mockito.verify(alumnoRepository, Mockito.times(1)).findById(alumno.getId());
    }

    @Test
    @Order(1)
    public void save() {
        Mockito.when(alumnoRepository.save(alumno)).thenReturn(alumno);
        Alumno res = alumnoRepository.save(alumno);
        assertAll(
                () -> assertEquals(alumno, res),
                () -> assertEquals(alumno.getId(), res.getId()),
                () -> assertEquals(alumno.getNombre(), res.getNombre()),
                () -> assertEquals(alumno.getCorreo(), res.getCorreo())
        );
        Mockito.verify(alumnoRepository, Mockito.times(1)).save(alumno);
    }

    @Test
    @Order(4)
    public void updateAlumno() {
        Mockito.when(alumnoRepository.save(alumno)).thenReturn(alumno);
        Alumno res = alumnoRepository.save(alumno);
        alumno.setNombre("Jhoan");
        assertEquals(alumno.getNombre(), res.getNombre());
        Mockito.verify(alumnoRepository, Mockito.times(1)).save(alumno);
    }

    @Test
    @Order(5)
    public void deleteAlumno() {
        Mockito.doNothing().when(alumnoRepository).delete(alumno);
        alumnoRepository.delete(alumno);
        Mockito.verify(alumnoRepository, Mockito.times(1)).delete(alumno);
    }
}
