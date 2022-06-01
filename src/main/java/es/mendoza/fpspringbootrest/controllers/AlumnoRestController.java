package es.mendoza.fpspringbootrest.controllers;

import es.mendoza.fpspringbootrest.errors.GeneralBadRequestException;
import es.mendoza.fpspringbootrest.errors.alumnos.AlumnoBadRequestException;
import es.mendoza.fpspringbootrest.errors.alumnos.AlumnoNotFoundException;
import es.mendoza.fpspringbootrest.errors.alumnos.AlumnosNotFoundException;
import es.mendoza.fpspringbootrest.models.Alumno;
import es.mendoza.fpspringbootrest.repositories.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alumno")
public class AlumnoRestController {
    @Autowired
    AlumnoRepository alumnoRepository;

    //Obtenemos todos los alumnos
    @GetMapping("/alumnos")
    public ResponseEntity<List<Alumno>> findAll(@RequestParam(name = "limit") Optional<String> limit, @RequestParam(name = "nombre") Optional<String> nombre) {
        List<Alumno> alumnos = null;
        try {
            if (nombre.isPresent()) {
                alumnos = alumnoRepository.findByNombreContainsIgnoreCase(nombre.get());
            } else {
                alumnos = alumnoRepository.findAll();
            }
            if (limit.isPresent() && !alumnos.isEmpty() && alumnos.size() > Integer.parseInt(limit.get())) {
                return ResponseEntity.ok(alumnos.subList(0, Integer.parseInt(limit.get())));
            } else {
                if (!alumnos.isEmpty()) {
                    return ResponseEntity.ok(alumnos);
                } else {
                    throw new AlumnosNotFoundException();
                }
            }
        } catch (Exception e) {
            throw new GeneralBadRequestException("Selección de datos", "Parámetros de consulta incorrectos");
        }
    }

    //Obtenemos un alumno por ID
    @GetMapping("/alumnos/{id}")
    public ResponseEntity<Alumno> findById(@PathVariable Long id) {
        Alumno alumno = alumnoRepository.findById(id).orElse(null);
        if (alumno == null) {
            throw new AlumnoNotFoundException(id);
        } else {
            return ResponseEntity.ok(alumno);
        }
    }

    //Insertar alumno
    @PostMapping("/alumnos")
    public ResponseEntity<Alumno> save(@RequestBody Alumno alumno) {
        try {
            alumno.setCreatedAt(LocalDateTime.now());
            alumno.setId(null);
            if (alumno.getNombre() == null || alumno.getNombre().isEmpty()) {
                throw new AlumnoBadRequestException("Nombre", "El nombre es obligatorio");
            }
            if (alumno.getCorreo() == null || alumno.getCorreo().isEmpty()) {
                throw new AlumnoBadRequestException("Correo", "El correo es obligatorio");
            }
            Alumno alumnoInsertado = alumnoRepository.save(alumno);
            return ResponseEntity.ok(alumnoInsertado);
        } catch (Exception e) {
            throw new GeneralBadRequestException("Insertar", "Error al insertar el alumno. Campos incorrectos");
        }
    }

    //Actualizar alumno por id
    @PutMapping("/alumnos/{id}")
    public ResponseEntity<Alumno> update(@PathVariable Long id, @RequestBody Alumno alumno) {
        try {
            Alumno alumnoActualizado = alumnoRepository.findById(id).orElse(null);
            if (alumnoActualizado == null) {
                throw new AlumnoNotFoundException(id);
            } else {
                if (alumno.getNombre() == null || alumno.getNombre().isEmpty()) {
                    throw new AlumnoBadRequestException("Nombre", "El nombre es obligatorio");
                }
                if (alumno.getCorreo() == null || alumno.getCorreo().isEmpty()) {
                    throw new AlumnoBadRequestException("Correo", "El correo es obligatorio");
                }
                alumnoActualizado.setNombre(alumno.getNombre());
                alumnoActualizado.setCorreo(alumno.getCorreo());
                return ResponseEntity.ok(alumnoActualizado);
            }
        } catch (ResponseStatusException e) {
            throw new GeneralBadRequestException("Actualizar", "Error al actualizar el alumno. CAmpos incorrectos");
        }
    }

    //Borrar un alumno
    @DeleteMapping("/alumnos/{id}")
    public ResponseEntity<Alumno> delete(@PathVariable Long id) {
        try {
            Alumno alumno = alumnoRepository.findById(id).orElse(null);
            if (alumno == null) {
                throw new AlumnoNotFoundException(id);
            } else {
                alumnoRepository.delete(alumno);
                return ResponseEntity.ok(alumno);
            }
        } catch (ResponseStatusException e) {
            throw new GeneralBadRequestException("Eliminar", "Error al borrar el alumno");
        }
    }
}
