package es.mendoza.fpspringbootrest.controllers;

import es.mendoza.fpspringbootrest.models.Curso;
import es.mendoza.fpspringbootrest.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/curso")
public class CursoRestController {
    @Autowired
    CursoRepository cursoRepository;

    //Obtenemos todos los cursos
    @GetMapping("/cursos")
    public ResponseEntity<List<Curso>> findAll(@RequestParam(name = "limit") Optional<String> limit, @RequestParam(name = "nombre") Optional<String> nombre) {
        List<Curso> cursos = null;
        if (nombre.isPresent()) {
            cursos = cursoRepository.findByNombreContainsIgnoreCase(nombre.get());
        } else {
            cursos = cursoRepository.findAll();
        }
        if (limit.isPresent() && !cursos.isEmpty() && cursos.size() > Integer.parseInt(limit.get())) {
            try {
                return ResponseEntity.ok(cursos.subList(0, Integer.parseInt(limit.get())));
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: " + e.getMessage());
            }
        } else {
            if (!cursos.isEmpty()) {
                return ResponseEntity.ok(cursos);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay cursos");
            }
        }
    }

    //Obtenemos un curso por ID
    @GetMapping("/cursos/{id}")
    public ResponseEntity<Curso> findById(@PathVariable Long id) {
        Curso curso = cursoRepository.findById(id).orElse(null);
        if (curso == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el curso con id: " + id);
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
            Curso cursoInsertado = cursoRepository.save(curso);
            return ResponseEntity.ok(cursoInsertado);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: " + e.getMessage());
        }
    }

    //Actualizar curso por id
    @PutMapping("/cursos/{id}")
    public ResponseEntity<Curso> update(@PathVariable Long id, @RequestBody Curso curso) {
        try {
            Curso cursoActualizado = cursoRepository.findById(id).orElse(null);
            if (cursoActualizado == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el curso con id: " + id);
            } else {
                cursoActualizado.setNombre(curso.getNombre());
                cursoActualizado.setSiglas(curso.getSiglas());
                return ResponseEntity.ok(cursoActualizado);
            }
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: " + e.getMessage());
        }
    }

    //Borrar un curso
    @DeleteMapping("/cursos/{id}")
    public ResponseEntity<Curso> delete(@PathVariable Long id) {
        try {
            Curso curso = cursoRepository.findById(id).orElse(null);
            if (curso == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el curso con id: " + id);
            } else {
                cursoRepository.delete(curso);
                return ResponseEntity.ok(curso);
            }
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: " + e.getMessage());
        }
    }
}
