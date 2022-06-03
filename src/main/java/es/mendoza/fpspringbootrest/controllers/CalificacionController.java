package es.mendoza.fpspringbootrest.controllers;

import es.mendoza.fpspringbootrest.dto.calificaciones.CreateCalificacionDTO;
import es.mendoza.fpspringbootrest.errors.GeneralBadRequestException;
import es.mendoza.fpspringbootrest.errors.calificaciones.CalificacionBadRequestException;
import es.mendoza.fpspringbootrest.errors.calificaciones.CalificacionNotFoundException;
import es.mendoza.fpspringbootrest.errors.calificaciones.CalificacionesNotFoundException;
import es.mendoza.fpspringbootrest.mapper.CalificacionMapper;
import es.mendoza.fpspringbootrest.models.Calificacion;
import es.mendoza.fpspringbootrest.repositories.CalificacionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/calificacion")
public class CalificacionController {
    private final CalificacionRepository calificacionRepository;
    private final CalificacionMapper calificacionMapper;

    public CalificacionController(CalificacionRepository calificacionRepository, CalificacionMapper calificacionMapper) {
        this.calificacionRepository = calificacionRepository;
        this.calificacionMapper = calificacionMapper;
    }

    @CrossOrigin(origins = "http://localhost:7575")

    //Obtener todas las notas
    @GetMapping("/notas")
    public ResponseEntity<?> findAll() {
        List<Calificacion> notas = calificacionRepository.findAll();
        if (notas.isEmpty()) {
            throw new CalificacionesNotFoundException();
        } else {
            return ResponseEntity.ok(calificacionMapper.toDTO(notas));
        }
    }

    //Obtener nota por id
    @GetMapping("/notas/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Calificacion nota = calificacionRepository.findById(id).orElse(null);
        if (nota == null) {
            throw new CalificacionNotFoundException(id);
        } else {
            return ResponseEntity.ok(calificacionMapper.toDTO(nota));
        }
    }

    //Insertar nota
    @PostMapping("/notas")
    public ResponseEntity<?> save(@RequestBody CreateCalificacionDTO calificacionDTO) {
        try {
            Calificacion calificacion = calificacionMapper.fromDTO(calificacionDTO);
            checkCalificacionData(calificacion);
            Calificacion notaInsertada = calificacionRepository.save(calificacion);
            return ResponseEntity.ok(calificacionMapper.toDTO(notaInsertada));
        } catch (Exception e) {
            throw new GeneralBadRequestException("Insertar", "Error al insertar la calificación");
        }
    }

    //Actualizar nota por id
    @PutMapping("/notas/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Calificacion calificacion) {
        try {
            Calificacion notaActualizada = calificacionRepository.findById(id).orElse(null);
            if (notaActualizada == null) {
                throw new CalificacionNotFoundException(id);
            } else {
                checkCalificacionData(calificacion);
                notaActualizada.setNota(calificacion.getNota());
                notaActualizada = calificacionRepository.save(notaActualizada);
                return ResponseEntity.ok(calificacionMapper.toDTO(notaActualizada));
            }
        } catch (Exception e) {
            throw new GeneralBadRequestException("Actualizar", "Error al actualizar la calificación. Campos incorrectos");
        }
    }

    //Borrar nota por id
    @DeleteMapping("/notas/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            Calificacion nota = calificacionRepository.findById(id).orElse(null);
            if (nota == null) {
                throw new CalificacionNotFoundException(id);
            } else {
                calificacionRepository.delete(nota);
                return ResponseEntity.ok(calificacionMapper.toDTO(nota));
            }
        } catch (Exception e) {
            throw new GeneralBadRequestException("Eliminar", "Error al borrar la calificación");
        }
    }

    private void checkCalificacionData(Calificacion calificacion) {
        if (calificacion.getNota() < 0) {
            throw new CalificacionBadRequestException("Nota", "La nota debe ser mayor que 0");
        }
    }

    @GetMapping("/notas/all")
    public ResponseEntity<?> listado() {
        List<Calificacion> notas = calificacionRepository.findAll();
        if (notas.isEmpty()) {
            throw new CalificacionesNotFoundException();
        } else {
            return ResponseEntity.ok(calificacionMapper.toListDTO(notas));
        }
    }
}
