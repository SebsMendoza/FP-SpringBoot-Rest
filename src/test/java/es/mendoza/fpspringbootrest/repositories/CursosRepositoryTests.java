package es.mendoza.fpspringbootrest.repositories;

import es.mendoza.fpspringbootrest.models.Curso;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CursosRepositoryTests {
    private final Curso curso = Curso.builder()
            .id(1L)
            .nombre("Desarrollo de Aplicaciones Multiplataforma")
            .siglas("DAM")
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
        Curso find = cursoRepository.findByNombre(curso.getNombre()).get(0);
        Curso res = cursoRepository.findById(find.getId()).get();
        assertNotNull(res);
        assertEquals(find.getId(), res.getId());
    }

    @Test
    @Order(1)
    public void save() {
        cursoRepository.save(curso);
        Curso res = cursoRepository.findByNombre(curso.getNombre()).get(0);
        assertFalse(res.getNombre().isEmpty());
        assertNotNull(res);
    }

    @Test
    @Order(4)
    public void updateCurso() {
        curso.setNombre("Desarrollo de Aplicaciones Web");
        cursoRepository.save(curso);
        assertEquals(curso.getNombre(), cursoRepository.findById(curso.getId()).get().getNombre());
    }

    @Test
    @Order(5)
    public void deleteCurso() {
        Curso find = cursoRepository.findById(curso.getId()).orElse(null);
        cursoRepository.delete(find);
        assertNull(cursoRepository.findById(find.getId()).orElse(null));
    }
}
