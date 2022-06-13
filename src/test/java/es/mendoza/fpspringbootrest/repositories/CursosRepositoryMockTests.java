package es.mendoza.fpspringbootrest.repositories;

import es.mendoza.fpspringbootrest.models.Curso;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CursosRepositoryMockTests {
    private final Curso curso = Curso.builder()
            .id(1L)
            .nombre("Desarrollo de Aplicaciones Multiplataforma")
            .siglas("DAM")
            .build();

    @MockBean
    private CursoRepository cursoRepository;

    @Test
    @Order(2)
    public void getAllCursos() {
        Mockito.when(cursoRepository.findAll())
                .thenReturn(List.of(curso));
        List<Curso> res = cursoRepository.findAll();
        assertAll(
                () -> assertEquals(List.of(curso), res),
                () -> assertEquals(curso.getId(), res.get(0).getId()),
                () -> assertEquals(curso.getNombre(), res.get(0).getNombre()),
                () -> assertEquals(curso.getSiglas(), res.get(0).getSiglas())
        );
        Mockito.verify(cursoRepository, Mockito.times(1))
                .findAll();
    }

    @Test
    @Order(3)
    public void getCursoById() {
        Mockito.when(cursoRepository.findById(curso.getId()))
                .thenReturn(java.util.Optional.of(curso));
        Curso res = cursoRepository.findById(curso.getId()).get();
        assertAll(
                () -> assertEquals(curso, res),
                () -> assertEquals(curso.getId(), res.getId()),
                () -> assertEquals(curso.getNombre(), res.getNombre()),
                () -> assertEquals(curso.getSiglas(), res.getSiglas())
        );
        Mockito.verify(cursoRepository, Mockito.times(1))
                .findById(curso.getId());
    }

    @Test
    @Order(1)
    public void save() {
        Mockito.when(cursoRepository.save(curso)).thenReturn(curso);
        Curso res = cursoRepository.save(curso);
        assertAll(
                () -> assertEquals(curso, res),
                () -> assertEquals(curso.getId(), res.getId()),
                () -> assertEquals(curso.getNombre(), res.getNombre()),
                () -> assertEquals(curso.getSiglas(), res.getSiglas())
        );
        Mockito.verify(cursoRepository, Mockito.times(1)).save(curso);
    }

    @Test
    @Order(4)
    public void updateCurso() {
        Mockito.when(cursoRepository.save(curso)).thenReturn(curso);
        Curso res = cursoRepository.save(curso);
        curso.setNombre("Desarrollo de Aplicaciones Web");
        assertEquals(curso.getNombre(), res.getNombre());
        Mockito.verify(cursoRepository, Mockito.times(1)).save(curso);
    }

    @Test
    @Order(5)
    public void deleteCurso() {
        Mockito.doNothing().when(cursoRepository).delete(curso);
        cursoRepository.delete(curso);
        Mockito.verify(cursoRepository, Mockito.times(1)).delete(curso);
    }
}
