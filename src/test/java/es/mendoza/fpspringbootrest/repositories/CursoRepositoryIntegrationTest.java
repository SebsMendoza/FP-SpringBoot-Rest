package es.mendoza.fpspringbootrest.repositories;

import es.mendoza.fpspringbootrest.models.Curso;
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
public class CursoRepositoryIntegrationTest {
    private final Curso curso = Curso.builder()
            .id(-1L)
            .nombre("Curso Prueba")
            .siglas("CP")
            .build();

    @Autowired
    private CursoRepository cursoRepository;

    @Test
    @Order(2)
    public void getAllCursos() {
        assertTrue(cursoRepository.findAll().size() > 0);
    }

    @Test
    @Order(3)
    public void getCursoById() {
        Curso cur = cursoRepository.save(curso);
        Curso res = cursoRepository.findById(cur.getId()).get();
        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals(curso.getNombre(), res.getNombre()),
                () -> assertEquals(curso.getSiglas(), res.getSiglas())
        );
    }

    @Test
    @Order(1)
    public void save() {
        Curso res = cursoRepository.save(curso);
        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals(curso.getNombre(), res.getNombre()),
                () -> assertEquals(curso.getSiglas(), res.getSiglas())
        );
    }

    @Test
    @Order(4)
    public void updateCurso() {
        Curso cur = cursoRepository.save(curso);
        cur = cursoRepository.findById(cur.getId()).get();
        cur.setNombre("Nombre Cambiado");

        Curso res = cursoRepository.save(cur);
        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals("Nombre Cambiado", res.getNombre()),
                () -> assertEquals(curso.getSiglas(), res.getSiglas())
        );
    }

    @Test
    @Order(5)
    public void deleteCurso() {
        Curso res = cursoRepository.save(curso);
        res = cursoRepository.findById(res.getId()).get();

        cursoRepository.delete(res);
        assertNull(cursoRepository.findById(res.getId()).orElse(null));
    }
}
