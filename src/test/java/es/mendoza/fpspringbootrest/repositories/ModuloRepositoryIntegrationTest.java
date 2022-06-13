package es.mendoza.fpspringbootrest.repositories;

import es.mendoza.fpspringbootrest.models.Modulo;
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
public class ModuloRepositoryIntegrationTest {
    private final Modulo modulo = Modulo.builder()
            .id(-1L)
            .nombre("Nuevo Modulo")
            .siglas("NM")
            .build();

    @Autowired
    private ModuloRepository moduloRepository;

    @Test
    @Order(2)
    public void getAllModulos() {
        assertTrue(moduloRepository.findAll().size() > 0);
    }

    @Test
    @Order(3)
    public void getModuloById() {
        Modulo mod = moduloRepository.save(modulo);
        Modulo res = moduloRepository.findById(mod.getId()).get();
        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals(modulo.getNombre(), res.getNombre()),
                () -> assertEquals(modulo.getSiglas(), res.getSiglas())
        );
    }

    @Test
    @Order(1)
    public void save() {
        Modulo res = moduloRepository.save(modulo);
        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals(modulo.getNombre(), res.getNombre()),
                () -> assertEquals(modulo.getSiglas(), res.getSiglas())
        );
    }

    @Test
    @Order(4)
    public void updateModulo() {
        Modulo mod = moduloRepository.save(modulo);
        mod = moduloRepository.findById(mod.getId()).get();
        mod.setNombre("Nombre Nuevo");

        Modulo res = moduloRepository.save(mod);
        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals("Nombre Nuevo", res.getNombre()),
                () -> assertEquals(modulo.getSiglas(), res.getSiglas())
        );
    }

    @Test
    @Order(5)
    public void deleteModulo() {
        Modulo res = moduloRepository.save(modulo);
        res = moduloRepository.findById(res.getId()).get();

        moduloRepository.delete(res);
        assertNull(moduloRepository.findById(res.getId()).orElse(null));
    }
}
