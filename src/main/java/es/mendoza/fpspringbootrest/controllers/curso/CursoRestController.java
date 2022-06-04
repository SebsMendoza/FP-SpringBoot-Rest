package es.mendoza.fpspringbootrest.controllers.curso;

import es.mendoza.fpspringbootrest.config.APIConfig;
import es.mendoza.fpspringbootrest.dto.cursos.CreateCursoDTO;
import es.mendoza.fpspringbootrest.dto.cursos.ListCursoPageDTO;
import es.mendoza.fpspringbootrest.errors.GeneralBadRequestException;
import es.mendoza.fpspringbootrest.errors.cursos.CursoBadRequestException;
import es.mendoza.fpspringbootrest.errors.cursos.CursoNotFoundException;
import es.mendoza.fpspringbootrest.errors.cursos.CursosNotFoundException;
import es.mendoza.fpspringbootrest.mapper.CursoMapper;
import es.mendoza.fpspringbootrest.models.Curso;
import es.mendoza.fpspringbootrest.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
@RestController
// Definimos la url o entrada de la API REST, este caso la raíz: localhost:8080/rest/
@RequestMapping(APIConfig.API_PATH + "/curso")
public class CursoRestController {
    private final CursoRepository cursoRepository;
    private final CursoMapper cursoMapper;

    @Autowired
    public CursoRestController(CursoRepository cursoRepository, CursoMapper cursoMapper) {
        this.cursoRepository = cursoRepository;
        this.cursoMapper = cursoMapper;
    }

    @CrossOrigin(origins = "http://localhost:7575")

    //Obtenemos todos los cursos
    @GetMapping("")
    public ResponseEntity<?> findAll(@RequestParam(name = "limit") Optional<String> limit,
                                     @RequestParam(name = "nombre") Optional<String> nombre) {
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
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Curso curso = cursoRepository.findById(id).orElse(null);
        if (curso == null) {
            throw new CursoNotFoundException(id);
        } else {
            return ResponseEntity.ok(cursoMapper.toDTO(curso));
        }
    }

    //Insertar curso
    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody CreateCursoDTO cursoDTO) {
        try {
            Curso curso = cursoMapper.fromDTO(cursoDTO);
            checkCursoData(curso);
            Curso cursoInsertado = cursoRepository.save(curso);
            return ResponseEntity.ok(cursoMapper.toDTO(cursoInsertado));
        } catch (Exception e) {
            throw new GeneralBadRequestException("Insertar", "Error al insertar el curso. Campos incorrectos");
        }
    }

    //Actualizar curso por id
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Curso curso) {
        try {
            Curso cursoActualizado = cursoRepository.findById(id).orElse(null);
            if (cursoActualizado == null) {
                throw new CursoNotFoundException(id);
            } else {
                checkCursoData(curso);
                cursoActualizado.setNombre(curso.getNombre());
                cursoActualizado.setSiglas(curso.getSiglas());
                cursoActualizado = cursoRepository.save(cursoActualizado);
                return ResponseEntity.ok(cursoMapper.toDTO(cursoActualizado));
            }
        } catch (ResponseStatusException e) {
            throw new GeneralBadRequestException("Actualizar", "Error al actualizar el curso. Campos incorrectos");
        }
    }

    //Borrar un curso
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            Curso curso = cursoRepository.findById(id).orElse(null);
            if (curso == null) {
                throw new CursoNotFoundException(id);
            } else {
                cursoRepository.delete(curso);
                return ResponseEntity.ok(cursoMapper.toDTO(curso));
            }
        } catch (ResponseStatusException e) {
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
    @GetMapping("/all")
    public ResponseEntity<?> listado(
            @RequestParam(required = false, name = "nombre") Optional<String> nombre,
            @RequestParam(required = false, name = "siglas") Optional<String> siglas,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable paging = PageRequest.of(page, size);
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
                    .build();
            return ResponseEntity.ok(listCursoPageDTO);
        } catch (Exception e) {
            throw new GeneralBadRequestException("Selección de datos", "Parámetros de consulta incorrectos");
        }
    }
}
