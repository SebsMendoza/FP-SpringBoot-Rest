package es.mendoza.fpspringbootrest.controllers;

import es.mendoza.fpspringbootrest.errors.GeneralBadRequestException;
import es.mendoza.fpspringbootrest.errors.cursos.CursoBadRequestException;
import es.mendoza.fpspringbootrest.errors.cursos.CursoNotFoundException;
import es.mendoza.fpspringbootrest.errors.cursos.CursosNotFoundException;
import es.mendoza.fpspringbootrest.models.Curso;
import es.mendoza.fpspringbootrest.repositories.CursoRepository;
import es.mendoza.fpspringbootrest.service.uploads.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/curso")
public class CursoRestController {
    private final CursoRepository cursoRepository;

    @Autowired
    public CursoRestController(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    @CrossOrigin(origins = "http://localhost:7575")

    //Obtenemos todos los cursos
    @GetMapping("/cursos")
    public ResponseEntity<List<Curso>> findAll(@RequestParam(name = "limit") Optional<String> limit, @RequestParam(name = "nombre") Optional<String> nombre) {
        List<Curso> cursos = null;
        try {
            if (nombre.isPresent()) {
                cursos = cursoRepository.findByNombreContainsIgnoreCase(nombre.get());
            } else {
                cursos = cursoRepository.findAll();
            }
            if (limit.isPresent() && !cursos.isEmpty() && cursos.size() > Integer.parseInt(limit.get())) {
                return ResponseEntity.ok(cursos.subList(0, Integer.parseInt(limit.get())));
            } else {
                if (!cursos.isEmpty()) {
                    return ResponseEntity.ok(cursos);
                } else {
                    throw new CursosNotFoundException();
                }
            }
        } catch (Exception e) {
            throw new GeneralBadRequestException("Selección de datos", "Parámetros de consulta incorrectos");
        }
    }

    //Obtenemos un curso por ID
    @GetMapping("/cursos/{id}")
    public ResponseEntity<Curso> findById(@PathVariable Long id) {
        Curso curso = cursoRepository.findById(id).orElse(null);
        if (curso == null) {
            throw new CursoNotFoundException(id);
        } else {
            return ResponseEntity.ok(curso);
        }
    }

    //Insertar curso
    @PostMapping("/cursos")
    public ResponseEntity<Curso> save(@RequestBody Curso curso) {
        try {
            curso.setCreatedAt(LocalDateTime.now());
            curso.setId(null);
            checkCursoData(curso);
            Curso cursoInsertado = cursoRepository.save(curso);
            return ResponseEntity.ok(cursoInsertado);
        } catch (Exception e) {
            throw new GeneralBadRequestException("Insertar", "Error al insertar el curso. Campos incorrectos");
        }
    }

    //Actualizar curso por id
    @PutMapping("/cursos/{id}")
    public ResponseEntity<Curso> update(@PathVariable Long id, @RequestBody Curso curso) {
        try {
            Curso cursoActualizado = cursoRepository.findById(id).orElse(null);
            if (cursoActualizado == null) {
                throw new CursoNotFoundException(id);
            } else {
                checkCursoData(curso);
                cursoActualizado.setNombre(curso.getNombre());
                cursoActualizado.setSiglas(curso.getSiglas());
                return ResponseEntity.ok(cursoActualizado);
            }
        } catch (ResponseStatusException e) {
            throw new GeneralBadRequestException("Actualizar", "Error al actualizar el curso. Campos incorrectos");
        }
    }

    //Borrar un curso
    @DeleteMapping("/cursos/{id}")
    public ResponseEntity<Curso> delete(@PathVariable Long id) {
        try {
            Curso curso = cursoRepository.findById(id).orElse(null);
            if (curso == null) {
                throw new CursoNotFoundException(id);
            } else {
                cursoRepository.delete(curso);
                return ResponseEntity.ok(curso);
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
}
