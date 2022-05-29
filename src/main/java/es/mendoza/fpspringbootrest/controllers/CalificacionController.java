package es.mendoza.fpspringbootrest.controllers;

import es.mendoza.fpspringbootrest.models.Calificacion;
import es.mendoza.fpspringbootrest.repositories.CalificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/calificacion")
public class CalificacionController {
    @Autowired
    CalificacionRepository calificacionRepository;

    //Obtener todas las notas
    @GetMapping("/notas")
    public ResponseEntity<List<Calificacion>> findAll() {
        List<Calificacion> notas = calificacionRepository.findAll();
        if (notas.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay notas registradas");
        } else {
            return ResponseEntity.ok(notas);
        }
    }

    //Obtener nota por id
    @GetMapping("/notas/{id}")
    public ResponseEntity<Calificacion> findById(@PathVariable Long id) {
        Calificacion nota = calificacionRepository.findById(id).orElse(null);
        if (nota == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la nota con id: " + id);
        } else {
            return ResponseEntity.ok(nota);
        }
    }

    //Insertar nota
    @PostMapping("/notas")
    public ResponseEntity<Calificacion> save(@RequestBody Calificacion calificacion) {
        try {
            calificacion.setId(null);
            Calificacion notaInsertada = calificacionRepository.save(calificacion);
            return ResponseEntity.ok(notaInsertada);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: " + e.getMessage());
        }
    }

    //Actualizar nota por id
    @PutMapping("/notas/{id}")
    public ResponseEntity<Calificacion> update(@PathVariable Long id, @RequestBody Calificacion calificacion) {
        try {
            Calificacion notaActualizada = calificacionRepository.findById(id).orElse(null);
            if (notaActualizada == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la nota con id: " + id);
            } else {
                notaActualizada.setNota(calificacion.getNota());
                notaActualizada = calificacionRepository.save(notaActualizada);
                return ResponseEntity.ok(notaActualizada);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: " + e.getMessage());
        }
    }

    //Borrar nota por id
    @DeleteMapping("/notas/{id}")
    public ResponseEntity<Calificacion> delete(@PathVariable Long id) {
        try {
            Calificacion nota = calificacionRepository.findById(id).orElse(null);
            if (nota == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el calificacion con id: " + id);
            } else {
                calificacionRepository.delete(nota);
                return ResponseEntity.ok(nota);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: " + e.getMessage());
        }
    }
}
