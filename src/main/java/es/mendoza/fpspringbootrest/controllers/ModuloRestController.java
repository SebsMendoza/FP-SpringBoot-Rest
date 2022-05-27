package es.mendoza.fpspringbootrest.controllers;

import es.mendoza.fpspringbootrest.models.Modulo;
import es.mendoza.fpspringbootrest.repositories.ModuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/modulo")
public class ModuloRestController {

    @Autowired
    ModuloRepository moduloRepository;

    //Obtenemos todos los módulos
    @GetMapping("/modulos")
    public ResponseEntity<List<Modulo>> findAll(@RequestParam(name = "limit") Optional<String> limit, @RequestParam(name = "nombre") Optional<String> nombre) {
        List<Modulo> modulos = null;
        if (nombre.isPresent()) {
            modulos = moduloRepository.findByNombreContainsIgnoreCase(nombre.get());
        } else {
            modulos = moduloRepository.findAll();
        }
        if (limit.isPresent() && !modulos.isEmpty() && modulos.size() > Integer.parseInt(limit.get())) {
            try {
                return ResponseEntity.ok(modulos.subList(0, Integer.parseInt(limit.get())));
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: " + e.getMessage());
            }
        } else {
            if (!modulos.isEmpty()) {
                return ResponseEntity.ok(modulos);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay modulos");
            }
        }
    }

    //Obtenemos un módulo por id
    @GetMapping("/modulos/{id}")
    public ResponseEntity<Modulo> findById(@PathVariable Long id) {
        Modulo modulo = moduloRepository.findById(id).orElse(null);
        if (modulo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el modulo con id: " + id);
        } else {
            return ResponseEntity.ok(modulo);
        }
    }

    //Insertar un módulo
    @PostMapping("/modulos")
    public ResponseEntity<Modulo> save(@RequestBody Modulo modulo) {
        try {
            modulo.setCreatedAt(LocalDateTime.now());
            modulo.setId(null);
            Modulo moduloInsertado = moduloRepository.save(modulo);
            return ResponseEntity.ok(moduloInsertado);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: " + e.getMessage());
        }
    }

    //Actualizando modulo por id
    @PutMapping("/modulos/{id}")
    public ResponseEntity<Modulo> update(@PathVariable Long id, @RequestBody Modulo modulo) {
        try {
            Modulo moduloActualizado = moduloRepository.findById(id).orElse(null);
            if (moduloActualizado == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el modulo con id: " + id);
            } else {
                moduloActualizado.setNombre(modulo.getNombre());
                moduloActualizado.setSiglas(modulo.getSiglas());
                return ResponseEntity.ok(moduloActualizado);
            }
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: " + e.getMessage());
        }
    }

    //Borrar un módulo
    @DeleteMapping("/modulos/{id}")
    public ResponseEntity<Modulo> delete(@PathVariable Long id) {
        try {
            Modulo modulo = moduloRepository.findById(id).orElse(null);
            if (modulo == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el modulo con id: " + id);
            } else {
                moduloRepository.delete(modulo);
                return ResponseEntity.ok(modulo);
            }
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: " + e.getMessage());
        }
    }
}
