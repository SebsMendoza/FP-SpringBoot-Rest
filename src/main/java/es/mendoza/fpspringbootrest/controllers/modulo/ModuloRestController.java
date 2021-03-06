package es.mendoza.fpspringbootrest.controllers.modulo;

import es.mendoza.fpspringbootrest.config.APIConfig;
import es.mendoza.fpspringbootrest.dto.modulos.CreateModuloDTO;
import es.mendoza.fpspringbootrest.dto.modulos.ListModuloPageDTO;
import es.mendoza.fpspringbootrest.dto.modulos.ModuloDTO;
import es.mendoza.fpspringbootrest.errors.GeneralBadRequestException;
import es.mendoza.fpspringbootrest.errors.modulos.ModuloBadRequestException;
import es.mendoza.fpspringbootrest.errors.modulos.ModuloNotFoundException;
import es.mendoza.fpspringbootrest.errors.modulos.ModulosNotFoundException;
import es.mendoza.fpspringbootrest.mapper.ModuloMapper;
import es.mendoza.fpspringbootrest.models.Modulo;
import es.mendoza.fpspringbootrest.repositories.ModuloRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(APIConfig.API_PATH + "/modulo")
public class ModuloRestController {
    private final ModuloRepository moduloRepository;
    private final ModuloMapper moduloMapper;

    //Obtenemos todos los módulos
    @ApiOperation(value = "Obtener todos los módulos", notes = "Obtiene todos los módulos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ModuloDTO.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Not Found", response = ModulosNotFoundException.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @GetMapping("/")
    public ResponseEntity<List<ModuloDTO>> findAll(@RequestParam(required = false, name = "limit") Optional<String> limit,
                                                   @RequestParam(required = false, name = "nombre") Optional<String> nombre) {
        List<Modulo> modulos = null;
        try {
            if (nombre.isPresent()) {
                modulos = moduloRepository.findByNombreContainsIgnoreCase(nombre.get());
            } else {
                modulos = moduloRepository.findAll();
            }
            if (limit.isPresent() && !modulos.isEmpty() && modulos.size() > Integer.parseInt(limit.get())) {
                return ResponseEntity.ok(moduloMapper.toDTO(modulos.subList(0, Integer.parseInt(limit.get()))));
            } else {
                if (!modulos.isEmpty()) {
                    return ResponseEntity.ok(moduloMapper.toDTO(modulos));
                } else {
                    throw new ModulosNotFoundException();
                }
            }
        } catch (Exception e) {
            throw new GeneralBadRequestException("Selección de datos", "Parámetros de consulta incorrectos");
        }
    }

    //Obtenemos un módulo por id
    @ApiOperation(value = "Obtener un módulo por id", notes = "Obtiene un módulo por id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ModuloDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = ModuloNotFoundException.class)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ModuloDTO> findById(@PathVariable Long id) {
        Modulo modulo = moduloRepository.findById(id).orElse(null);
        if (modulo == null) {
            throw new ModuloNotFoundException(id);
        } else {
            return ResponseEntity.ok(moduloMapper.toDTO(modulo));
        }
    }

    //Insertar un módulo
    @ApiOperation(value = "Crear un módulo", notes = "Crea un módulo")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Created", response = ModuloDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @PostMapping("/")
    public ResponseEntity<ModuloDTO> save(@RequestBody CreateModuloDTO moduloDTO) {
        Modulo modulo = moduloMapper.fromDTO(moduloDTO);
        checkModuloData(modulo);
        try {
            Modulo moduloInsertado = moduloRepository.save(modulo);
            return ResponseEntity.ok(moduloMapper.toDTO(moduloInsertado));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new GeneralBadRequestException("Insertar", "Error al insertar el módulo. Campos incorrectos" + e.getMessage());
        }
    }

    //Actualizando modulo por id
    @ApiOperation(value = "Actualizar un módulo", notes = "Actualiza un módulo por id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ModuloDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = ModuloNotFoundException.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ModuloDTO> update(@PathVariable Long id, @RequestBody Modulo modulo) {
        Modulo moduloActualizado = moduloRepository.findById(id).orElse(null);
        if (moduloActualizado == null) {
            throw new ModuloNotFoundException(id);
        }
        checkModuloData(modulo);
        moduloActualizado.setNombre(modulo.getNombre());
        moduloActualizado.setSiglas(modulo.getSiglas());

        try {
            moduloActualizado = moduloRepository.save(moduloActualizado);
            return ResponseEntity.ok(moduloMapper.toDTO(moduloActualizado));
        } catch (Exception e) {
            throw new GeneralBadRequestException("Actualizar", "Error al actualizar el módulo. Campos incorrectos");
        }
    }

    //Borrar un módulo
    @ApiOperation(value = "Eliminar un módulo", notes = "Elimina un módulo dado su id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ModuloDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = ModuloNotFoundException.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ModuloDTO> delete(@PathVariable Long id) {
        Modulo modulo = moduloRepository.findById(id).orElse(null);
        if (modulo == null) {
            throw new ModuloNotFoundException(id);
        }
        try {
            moduloRepository.delete(modulo);
            return ResponseEntity.ok(moduloMapper.toDTO(modulo));
        } catch (Exception e) {
            throw new GeneralBadRequestException("Eliminar", "Error al borrar el curso");
        }
    }

    private void checkModuloData(Modulo modulo) {
        if (modulo.getNombre() == null || modulo.getNombre().isEmpty()) {
            throw new ModuloBadRequestException("Nombre", "El nombre es obligatorio");
        }
        if (modulo.getSiglas() == null || modulo.getSiglas().isEmpty()) {
            throw new ModuloBadRequestException("Siglas", "Las siglas son obligatorias");
        }
    }

    //Obtener todos los módulos, paginable
    @Operation(summary = "Obtiene la lista de módulos existentes", description = "Obtiene la lista de módulos existentes")
    @ApiOperation(value = "Obtiene una lista de módulos", notes = "Obtiene una lista de módulos paginada, filtrada y ordenada")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK: Lista de módulos", response = ListModuloPageDTO.class),
            @ApiResponse(code = 400, message = "Bad Request: Lista no encontrada", response = GeneralBadRequestException.class)
    })
    @GetMapping("/all")
    public ResponseEntity<?> listado(
            @RequestParam(required = false, name = "nombre") Optional<String> nombre,
            @RequestParam(required = false, name = "siglas") Optional<String> siglas,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sort
    ) {
        Pageable paging = PageRequest.of(page, size, Sort.Direction.ASC, sort);
        Page<Modulo> pagedResult;
        try {
            if (nombre.isPresent()) {
                pagedResult = moduloRepository.findByNombreContainsIgnoreCase(nombre.get(), paging);
            } else if (siglas.isPresent()) {
                pagedResult = moduloRepository.findByNombreContainsIgnoreCase(siglas.get(), paging);
            } else {
                pagedResult = moduloRepository.findAll(paging);
            }
            ListModuloPageDTO listModuloPageDTO = ListModuloPageDTO.builder()
                    .data(moduloMapper.toDTO(pagedResult.getContent()))
                    .totalPages(pagedResult.getTotalPages())
                    .totalElements(pagedResult.getTotalPages())
                    .currentPage(pagedResult.getNumber())
                    .sort(pagedResult.getSort().toString())
                    .build();
            return ResponseEntity.ok(listModuloPageDTO);
        } catch (Exception e) {
            throw new GeneralBadRequestException("Selección de datos", "Parámetros de consulta incorrectos");
        }
    }
}
