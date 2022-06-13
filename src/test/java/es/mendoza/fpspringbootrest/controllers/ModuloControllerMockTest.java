package es.mendoza.fpspringbootrest.controllers;

import es.mendoza.fpspringbootrest.controllers.modulo.ModuloRestController;
import es.mendoza.fpspringbootrest.dto.modulos.CreateModuloDTO;
import es.mendoza.fpspringbootrest.dto.modulos.ModuloDTO;
import es.mendoza.fpspringbootrest.errors.modulos.ModuloBadRequestException;
import es.mendoza.fpspringbootrest.errors.modulos.ModuloNotFoundException;
import es.mendoza.fpspringbootrest.mapper.ModuloMapper;
import es.mendoza.fpspringbootrest.models.Modulo;
import es.mendoza.fpspringbootrest.repositories.ModuloRepository;
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
public class ModuloControllerMockTest {
    @MockBean
    private final ModuloRepository moduloRepository;
    @MockBean
    private final ModuloMapper moduloMapper;

    private final Modulo modulo = Modulo.builder()
            .id(1L)
            .nombre("Modulo prueba")
            .siglas("MP")
            .build();

    @InjectMocks
    private ModuloRestController moduloRestController;

    @Autowired
    public ModuloControllerMockTest(ModuloRepository moduloRepository, ModuloMapper moduloMapper) {
        this.moduloRepository = moduloRepository;
        this.moduloMapper = moduloMapper;
    }

    @Test
    void getAllModulosTestMock() {
        ModuloDTO dto = ModuloDTO.builder()
                .nombre(modulo.getNombre())
                .siglas(modulo.getSiglas())
                .build();

        Mockito.when(moduloRepository.findAll()).thenReturn(List.of(modulo));
        Mockito.when(moduloMapper.toDTO(List.of(modulo))).thenReturn(List.of(dto));

        ResponseEntity<List<ModuloDTO>> response = moduloRestController.findAll(java.util.Optional.empty(), java.util.Optional.empty());
        List<ModuloDTO> res = response.getBody();
        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.get(0).getNombre(), modulo.getNombre()),
                () -> assertEquals(res.get(0).getSiglas(), modulo.getSiglas())
        );
    }

    @Test
    void getModuloByIdTestMock() {
        ModuloDTO dto = ModuloDTO.builder()
                .nombre(modulo.getNombre())
                .siglas(modulo.getSiglas())
                .build();

        Mockito.when(moduloRepository.findById(1L)).thenReturn(Optional.of(modulo));
        Mockito.when(moduloMapper.toDTO(modulo)).thenReturn(dto);

        ResponseEntity<ModuloDTO> response = moduloRestController.findById(1L);
        ModuloDTO res = response.getBody();
        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getNombre(), modulo.getNombre()),
                () -> assertEquals(res.getSiglas(), modulo.getSiglas())
        );

        Mockito.verify(moduloRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(moduloMapper, Mockito.times(1)).toDTO(modulo);
    }

    @Test
    void findByIdModuloException() {
        Mockito.when(moduloRepository.findById(1L)).thenReturn(Optional.empty());
        Exception ex = assertThrows(ModuloNotFoundException.class, () -> {
            moduloRestController.findById(1L);
        });

        assertFalse(ex.getMessage().contains("modulo"));
        Mockito.verify(moduloRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void saveModuloTestMock() {
        CreateModuloDTO createDTO = CreateModuloDTO.builder()
                .nombre(modulo.getNombre())
                .siglas(modulo.getSiglas())
                .build();

        ModuloDTO dto = ModuloDTO.builder()
                .nombre(modulo.getNombre())
                .siglas(modulo.getSiglas())
                .build();

        Mockito.when(moduloRepository.save(modulo)).thenReturn(modulo);
        Mockito.when(moduloMapper.fromDTO(createDTO)).thenReturn(modulo);
        Mockito.when(moduloMapper.toDTO(modulo)).thenReturn(dto);

        ResponseEntity<ModuloDTO> response = moduloRestController.save(createDTO);
        ModuloDTO res = response.getBody();
        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getNombre(), modulo.getNombre()),
                () -> assertEquals(res.getSiglas(), modulo.getSiglas())
        );
        Mockito.verify(moduloRepository, Mockito.times(1)).save(modulo);
        Mockito.verify(moduloMapper, Mockito.times(1)).fromDTO(createDTO);
        Mockito.verify(moduloMapper, Mockito.times(1)).toDTO(modulo);
    }

    @Test
    void checkModuloDataNameExceptionTest() {
        CreateModuloDTO createDTO = CreateModuloDTO.builder()
                .nombre("")
                .siglas(modulo.getSiglas())
                .build();
        Modulo mod = Modulo.builder()
                .nombre("")
                .siglas(modulo.getSiglas())
                .build();

        Mockito.when(moduloMapper.fromDTO(createDTO)).thenReturn(mod);
        Exception ex = assertThrows(ModuloBadRequestException.class, () -> {
            moduloRestController.save(createDTO);
        });

        assertTrue(ex.getMessage().contains("nombre"));
        Mockito.verify(moduloMapper, Mockito.times(1)).fromDTO(createDTO);
    }

    @Test
    void updateModuloTestMock() {
        ModuloDTO dto = ModuloDTO.builder()
                .nombre(modulo.getNombre())
                .siglas(modulo.getSiglas())
                .build();

        Mockito.when(moduloRepository.findById(1L)).thenReturn(java.util.Optional.of(modulo));
        Mockito.when(moduloRepository.save(modulo)).thenReturn(modulo);
        Mockito.when(moduloMapper.toDTO(modulo)).thenReturn(dto);

        ResponseEntity<ModuloDTO> response = moduloRestController.update(1L, modulo);
        ModuloDTO res = response.getBody();
        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getNombre(), modulo.getNombre()),
                () -> assertEquals(res.getSiglas(), modulo.getSiglas())
        );

        Mockito.verify(moduloRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(moduloRepository, Mockito.times(1)).save(modulo);
        Mockito.verify(moduloMapper, Mockito.times(1)).toDTO(modulo);
    }

    @Test
    void deleteModuloTestMock() {
        ModuloDTO dto = ModuloDTO.builder()
                .nombre(modulo.getNombre())
                .siglas(modulo.getSiglas())
                .build();

        Mockito.when(moduloRepository.findById(1L)).thenReturn(java.util.Optional.of(modulo));
        Mockito.when(moduloMapper.toDTO(modulo)).thenReturn(dto);

        ResponseEntity<ModuloDTO> response = moduloRestController.delete(1L);
        ModuloDTO res = response.getBody();
        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getNombre(), modulo.getNombre()),
                () -> assertEquals(res.getSiglas(), modulo.getSiglas())
        );

        Mockito.verify(moduloRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(moduloMapper, Mockito.times(1)).toDTO(modulo);
    }
}
