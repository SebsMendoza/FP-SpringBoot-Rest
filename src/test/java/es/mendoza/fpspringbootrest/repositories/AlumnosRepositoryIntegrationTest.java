package es.mendoza.fpspringbootrest.repositories;

import es.mendoza.fpspringbootrest.models.Alumno;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class AlumnosRepositoryIntegrationTest {

    private final Alumno alumno = Alumno.builder()
            .id(-1L)
            .nombre("Juliana")
            .correo("juli@prueba.com")
            .build();

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Test
    @Order(2)
    public void getAllAlumnos() {
        assertTrue(alumnoRepository.findAll().size() > 0);
    }

    @Test
    @Order(3)
    public void getAlumnoById() {
        Alumno alum = alumnoRepository.save(alumno);
        Alumno res = alumnoRepository.findById(alum.getId()).get();
        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals(alumno.getNombre(), res.getNombre()),
                () -> assertEquals(alumno.getCorreo(), res.getCorreo())
        );
    }

    @Test
    @Order(1)
    public void save() {
        Alumno res = alumnoRepository.save(alumno);
        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals(alumno.getNombre(), res.getNombre()),
                () -> assertEquals(alumno.getCorreo(), res.getCorreo())
        );
    }

    @Test
    @Order(4)
    public void updateAlumno() {
        Alumno alum = alumnoRepository.save(alumno);
        alum = alumnoRepository.findById(alum.getId()).get();
        alum.setNombre("Claudia");

        Alumno res = alumnoRepository.save(alum);
        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals("Claudia", res.getNombre()),
                () -> assertEquals(alumno.getCorreo(), res.getCorreo())
        );
    }

    @Test
    @Order(5)
    public void deleteAlumno() {
        Alumno res = alumnoRepository.save(alumno);
        res = alumnoRepository.findById(res.getId()).get();

        alumnoRepository.delete(res);
        assertNull(alumnoRepository.findById(res.getId()).orElse(null));
    }
}
