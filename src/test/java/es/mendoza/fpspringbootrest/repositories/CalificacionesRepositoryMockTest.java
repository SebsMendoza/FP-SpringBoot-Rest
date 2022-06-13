package es.mendoza.fpspringbootrest.repositories;

import es.mendoza.fpspringbootrest.models.Calificacion;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CalificacionesRepositoryMockTest {
    private final Calificacion calificacion = Calificacion.builder()
            .id(1L)
            .nota(9.2)
            .build();

    @MockBean
    private CalificacionRepository calificacionRepository;

    @Test
    @Order(2)
    public void getAllCalificaciones() {
        Mockito.when(calificacionRepository.findAll()).thenReturn(List.of(calificacion));
        List<Calificacion> res = calificacionRepository.findAll();
        assertAll(
                () -> assertEquals(List.of(calificacion), res),
                () -> assertEquals(calificacion.getId(), res.get(0).getId()),
                () -> assertEquals(calificacion.getNota(), res.get(0).getNota())
        );
        Mockito.verify(calificacionRepository, Mockito.times(1)).findAll();
    }

    @Test
    @Order(3)
    public void getCalificacionById() {
        Mockito.when(calificacionRepository.findById(calificacion.getId())).thenReturn(java.util.Optional.of(calificacion));
        Calificacion res = calificacionRepository.findById(calificacion.getId()).get();
        assertAll(
                () -> assertEquals(calificacion, res),
                () -> assertEquals(calificacion.getId(), res.getId()),
                () -> assertEquals(calificacion.getNota(), res.getNota())
        );
        Mockito.verify(calificacionRepository, Mockito.times(1)).findById(calificacion.getId());
    }

    @Test
    @Order(1)
    public void save() {
        Mockito.when(calificacionRepository.save(calificacion)).thenReturn(calificacion);
        Calificacion res = calificacionRepository.save(calificacion);
        assertAll(
                () -> assertEquals(calificacion, res),
                () -> assertEquals(calificacion.getId(), res.getId()),
                () -> assertEquals(calificacion.getNota(), res.getNota())
        );
        Mockito.verify(calificacionRepository, Mockito.times(1)).save(calificacion);
    }

    @Test
    @Order(4)
    public void updateCalificacion() {
        Mockito.when(calificacionRepository.save(calificacion)).thenReturn(calificacion);
        Calificacion res = calificacionRepository.save(calificacion);
        calificacion.setNota(7.4);
        assertEquals(calificacion.getNota(), res.getNota());
        Mockito.verify(calificacionRepository, Mockito.times(1)).save(calificacion);
    }

    @Test
    @Order(5)
    public void deleteCalificacion() {
        Mockito.doNothing().when(calificacionRepository).delete(calificacion);
        calificacionRepository.delete(calificacion);
        Mockito.verify(calificacionRepository, Mockito.times(1)).delete(calificacion);
    }
}
