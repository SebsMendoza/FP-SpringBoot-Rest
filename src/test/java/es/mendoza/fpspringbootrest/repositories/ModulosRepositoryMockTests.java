package es.mendoza.fpspringbootrest.repositories;

import es.mendoza.fpspringbootrest.models.Modulo;
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
public class ModulosRepositoryMockTests {
    private final Modulo modulo = Modulo.builder()
            .id(1L)
            .nombre("Base de Datos")
            .siglas("BBDD")
            .build();

    @MockBean
    private ModuloRepository moduloRepository;

    @Test
    @Order(2)
    public void getAllModulos() {
        Mockito.when(moduloRepository.findAll()).thenReturn(List.of(modulo));
        List<Modulo> res = moduloRepository.findAll();
        assertAll(
                () -> assertEquals(List.of(modulo), res),
                () -> assertEquals(modulo.getId(), res.get(0).getId()),
                () -> assertEquals(modulo.getNombre(), res.get(0).getNombre()),
                () -> assertEquals(modulo.getSiglas(), res.get(0).getSiglas())
        );
        Mockito.verify(moduloRepository, Mockito.times(1)).findAll();
    }

    @Test
    @Order(3)
    public void getModuloById() {
        Mockito.when(moduloRepository.findById(modulo.getId()))
                .thenReturn(java.util.Optional.of(modulo));
        Modulo res = moduloRepository.findById(modulo.getId()).get();
        assertAll(
                () -> assertEquals(modulo, res),
                () -> assertEquals(modulo.getId(), res.getId()),
                () -> assertEquals(modulo.getNombre(), res.getNombre()),
                () -> assertEquals(modulo.getSiglas(), res.getSiglas())
        );
        Mockito.verify(moduloRepository, Mockito.times(1)).findById(modulo.getId());
    }

    @Test
    @Order(1)
    public void save() {
        Mockito.when(moduloRepository.save(modulo)).thenReturn(modulo);
        Modulo res = moduloRepository.save(modulo);
        assertAll(
                () -> assertEquals(modulo, res),
                () -> assertEquals(modulo.getId(), res.getId()),
                () -> assertEquals(modulo.getNombre(), res.getNombre()),
                () -> assertEquals(modulo.getSiglas(), res.getSiglas())
        );
        Mockito.verify(moduloRepository, Mockito.times(1)).save(modulo);
    }

    @Test
    @Order(4)
    public void updateModulo() {
        Mockito.when(moduloRepository.save(modulo)).thenReturn(modulo);
        Modulo res = moduloRepository.save(modulo);
        modulo.setNombre("Data Base");
        assertEquals(modulo.getNombre(), res.getNombre());
        Mockito.verify(moduloRepository, Mockito.times(1)).save(modulo);
    }

    @Test
    @Order(5)
    public void deleteModulo() {
        Mockito.doNothing().when(moduloRepository).delete(modulo);
        moduloRepository.delete(modulo);
        Mockito.verify(moduloRepository, Mockito.times(1)).delete(modulo);
    }
}
