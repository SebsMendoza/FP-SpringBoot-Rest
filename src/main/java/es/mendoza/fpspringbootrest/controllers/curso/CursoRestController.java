package es.mendoza.fpspringbootrest.controllers.curso;

import es.mendoza.fpspringbootrest.config.APIConfig;
import es.mendoza.fpspringbootrest.dto.cursos.CreateCursoDTO;
import es.mendoza.fpspringbootrest.dto.cursos.CursoDTO;
import es.mendoza.fpspringbootrest.dto.cursos.ListCursoPageDTO;
import es.mendoza.fpspringbootrest.errors.GeneralBadRequestException;
import es.mendoza.fpspringbootrest.errors.cursos.CursoBadRequestException;
import es.mendoza.fpspringbootrest.errors.cursos.CursoNotFoundException;
import es.mendoza.fpspringbootrest.errors.cursos.CursosNotFoundException;
import es.mendoza.fpspringbootrest.mapper.CursoMapper;
import es.mendoza.fpspringbootrest.models.Curso;
import es.mendoza.fpspringbootrest.repositories.CursoRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(APIConfig.API_PATH + "/curso")
public class CursoRestController {
    private final CursoRepository cursoRepository;
    private final CursoMapper cursoMapper;

    //Obtenemos todos los cursos
    @ApiOperation(value = "Obtener todos los cursos", notes = "Obtiene todos los cursos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CursoDTO.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Not Found", response = CursosNotFoundException.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @GetMapping("/")
    public ResponseEntity<List<CursoDTO>> findAll(@RequestParam(required = false, name = "limit") Optional<String> limit,
                                                  @RequestParam(required = false, name = "nombre") Optional<String> nombre) {
        List<Curso> cursos = null;
        try {
            if (nombre.isPresent()) {
                cursos = cursoRepository.findByNombreContainsIgnoreCase(nombre.get());
            } else {
                cursos = cursoRepository.findAll();
            }
            if (limit.isPresent() && !cursos.isEmpty() && cursos.size() > Integer.parseInt(limit.get())) {
                return ResponseEntity.ok(cursoMapper.toDTO(cursos.subList(0, Integer.parseInt(limit.get()))));
            } else {
                if (!cursos.isEmpty()) {
                    return ResponseEntity.ok(cursoMapper.toDTO(cursos));
                } else {
                    throw new CursosNotFoundException();
                }
            }
        } catch (Exception e) {
            throw new GeneralBadRequestException("Selección de datos", "Parámetros de consulta incorrectos");
        }
    }

    //Obtenemos un curso por ID
    @ApiOperation(value = "Obtener un curso por id", notes = "Obtiene un curso por id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CursoDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = CursoNotFoundException.class)
    })
    @GetMapping("/{id}")
    public ResponseEntity<CursoDTO> findById(@PathVariable Long id) {
        Curso curso = cursoRepository.findById(id).orElse(null);
        if (curso == null) {
            throw new CursoNotFoundException(id);
        } else {
            return ResponseEntity.ok(cursoMapper.toDTO(curso));
        }
    }

    //Insertar curso
    @ApiOperation(value = "Crear un curso", notes = "Crea un curso")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Created", response = CursoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @PostMapping("/")
    public ResponseEntity<CursoDTO> save(@RequestBody CreateCursoDTO cursoDTO) {
        try {
            Curso curso = cursoMapper.fromDTO(cursoDTO);
            checkCursoData(curso);
            Curso cursoInsertado = cursoRepository.save(curso);
            return ResponseEntity.ok(cursoMapper.toDTO(cursoInsertado));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new GeneralBadRequestException("Insertar", "Error al insertar el curso. Campos incorrectos" + e.getMessage());
        }
    }

    //Actualizar curso por id
    @ApiOperation(value = "Actualizar un curso", notes = "Actualiza un curso por id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CursoDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = CursosNotFoundException.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @PutMapping("/{id}")
    public ResponseEntity<CursoDTO> update(@PathVariable Long id, @RequestBody Curso curso) {
        Curso cursoActualizado = cursoRepository.findById(id).orElse(null);
        if (cursoActualizado == null) {
            throw new CursoNotFoundException(id);
        }
        checkCursoData(curso);
        cursoActualizado.setNombre(curso.getNombre());
        cursoActualizado.setSiglas(curso.getSiglas());
        try {
            cursoActualizado = cursoRepository.save(cursoActualizado);
            return ResponseEntity.ok(cursoMapper.toDTO(cursoActualizado));
        } catch (Exception e) {
            throw new GeneralBadRequestException("Actualizar", "Error al actualizar el curso. Campos incorrectos");
        }
    }

    //Borrar un curso
    @ApiOperation(value = "Eliminar un curso", notes = "Elimina un curso dado su id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CursoDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = CursoNotFoundException.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<CursoDTO> delete(@PathVariable Long id) {
        Curso curso = cursoRepository.findById(id).orElse(null);
        if (curso == null) {
            throw new CursoNotFoundException(id);
        }
        try {
            cursoRepository.delete(curso);
            return ResponseEntity.ok(cursoMapper.toDTO(curso));
        } catch (Exception e) {
            throw new GeneralBadRequestException("Eliminar", "Error al borrar el curso");
        }
    }

    private void checkCursoData(Curso curso) {
        if (curso.getNombre() == null || curso.getNombre().isEmpty()) {
            throw new CursoBadRequestException("Nombre", "El nombre es obligatorio");
        }
        if (curso.getSiglas() == null || curso.getSiglas().isEmpty()) {
            throw new CursoBadRequestException("Siglas", "Las siglas son obligatorias");
        }
    }

    //Obtener todos los cursos, paginable
    @Operation(summary = "Obtiene la lista de cursos existentes", description = "Obtiene la lista de cursos disponibles")
    @ApiOperation(value = "Obtiene una lista de cursos", notes = "Obtiene una lista de cursos paginada, filtrada y ordenada")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK: Lista de cursos", response = ListCursoPageDTO.class),
            @ApiResponse(code = 400, message = "Bad Request:  Lista no encontrada", response = GeneralBadRequestException.class)
    })
    @GetMapping("/all")
    public ResponseEntity<?> listado(
            @RequestParam(required = false, name = "nombre") Optional<String> nombre,
            @RequestParam(required = false, name = "siglas") Optional<String> siglas,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sort
    ) {
        Pageable paging = PageRequest.of(page, size, Sort.Direction.DESC, sort);
        Page<Curso> pagedResult;
        try {
            if (nombre.isPresent()) {
                pagedResult = cursoRepository.findByNombreContainsIgnoreCase(nombre.get(), paging);
            } else if (siglas.isPresent()) {
                pagedResult = cursoRepository.findByNombreContainsIgnoreCase(siglas.get(), paging);
            } else {
                pagedResult = cursoRepository.findAll(paging);
            }
            ListCursoPageDTO listCursoPageDTO = ListCursoPageDTO.builder()
                    .data(cursoMapper.toDTO(pagedResult.getContent()))
                    .totalPages(pagedResult.getTotalPages())
                    .totalElements(pagedResult.getTotalElements())
                    .currentPage(pagedResult.getNumber())
                    .sort(pagedResult.getSort().toString())
                    .build();
            return ResponseEntity.ok(listCursoPageDTO);
        } catch (Exception e) {
            throw new GeneralBadRequestException("Selección de datos", "Parámetros de consulta incorrectos");
        }
    }
}
