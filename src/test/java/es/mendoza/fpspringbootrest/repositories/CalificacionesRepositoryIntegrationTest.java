package es.mendoza.fpspringbootrest.repositories;

import es.mendoza.fpspringbootrest.models.Calificacion;
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
public class CalificacionesRepositoryIntegrationTest {
    private final Calificacion calificacion = Calificacion.builder()
            .id(-1L)
            .nota(5.3)
            .build();

    @Autowired
    private CalificacionRepository calificacionRepository;

    @Test
    @Order(2)
    public void getAllCalificaciones() {
        assertTrue(calificacionRepository.findAll().size() > 0);
    }

    @Test
    @Order(3)
    public void getCalificacionById() {
        Calificacion cali = calificacionRepository.save(calificacion);
        Calificacion res = calificacionRepository.findById(cali.getId()).get();
        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals(calificacion.getNota(), res.getNota())
        );
    }

    @Test
    @Order(1)
    public void save() {
        Calificacion res = calificacionRepository.save(calificacion);
        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals(calificacion.getNota(), res.getNota())
        );
    }

    @Test
    @Order(4)
    public void updateCalificacion() {
        Calificacion cali = calificacionRepository.save(calificacion);
        cali = calificacionRepository.findById(cali.getId()).get();
        cali.setNota(8.9);

        Calificacion res = calificacionRepository.save(cali);
        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals(8.9, res.getNota())
        );
    }

    @Test
    @Order(5)
    public void deleteCalificacion() {
        Calificacion res = calificacionRepository.save(calificacion);
        res = calificacionRepository.findById(res.getId()).get();

        calificacionRepository.delete(res);
        assertNull(calificacionRepository.findById(res.getId()).orElse(null));
    }
}
