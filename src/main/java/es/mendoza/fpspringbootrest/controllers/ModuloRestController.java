package es.mendoza.fpspringbootrest.controllers;

import es.mendoza.fpspringbootrest.errors.GeneralBadRequestException;
import es.mendoza.fpspringbootrest.errors.modulos.ModuloBadRequestException;
import es.mendoza.fpspringbootrest.errors.modulos.ModuloNotFoundException;
import es.mendoza.fpspringbootrest.errors.modulos.ModulosNotFoundException;
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
        try {
            if (nombre.isPresent()) {
                modulos = moduloRepository.findByNombreContainsIgnoreCase(nombre.get());
            } else {
                modulos = moduloRepository.findAll();
            }
            if (limit.isPresent() && !modulos.isEmpty() && modulos.size() > Integer.parseInt(limit.get())) {
                return ResponseEntity.ok(modulos.subList(0, Integer.parseInt(limit.get())));
            } else {
                if (!modulos.isEmpty()) {
                    return ResponseEntity.ok(modulos);
                } else {
                    throw new ModulosNotFoundException();
                }
            }
        } catch (Exception e) {
            throw new GeneralBadRequestException("Selección de datos", "Parámetros de consulta incorrectos");
        }
    }

    //Obtenemos un módulo por id
    @GetMapping("/modulos/{id}")
    public ResponseEntity<Modulo> findById(@PathVariable Long id) {
        Modulo modulo = moduloRepository.findById(id).orElse(null);
        if (modulo == null) {
            throw new ModuloNotFoundException(id);
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
            if (modulo.getNombre() == null || modulo.getNombre().isEmpty()) {
                throw new ModuloBadRequestException("Nombre", "El nombre es obligatorio");
            }
            if (modulo.getSiglas() == null || modulo.getSiglas().isEmpty()) {
                throw new ModuloBadRequestException("Siglas", "La siglas son obligatorias");
            }
            Modulo moduloInsertado = moduloRepository.save(modulo);
            return ResponseEntity.ok(moduloInsertado);
        } catch (Exception e) {
            throw new GeneralBadRequestException("Insertar", "Error al insertar el módulo. Campos incorrectos");
        }
    }

    //Actualizando modulo por id
    @PutMapping("/modulos/{id}")
    public ResponseEntity<Modulo> update(@PathVariable Long id, @RequestBody Modulo modulo) {
        try {
            Modulo moduloActualizado = moduloRepository.findById(id).orElse(null);
            if (moduloActualizado == null) {
                throw new ModuloNotFoundException(id);
            } else {
                if (modulo.getNombre() == null || modulo.getNombre().isEmpty()) {
                    throw new ModuloBadRequestException("Nombre", "El nombre es obligatorio");
                }
                if (modulo.getSiglas() == null || modulo.getSiglas().isEmpty()) {
                    throw new ModuloBadRequestException("Siglas", "las siglas son obligatorias");
                }
                moduloActualizado.setNombre(modulo.getNombre());
                moduloActualizado.setSiglas(modulo.getSiglas());
                return ResponseEntity.ok(moduloActualizado);
            }
        } catch (ResponseStatusException e) {
            throw new GeneralBadRequestException("Actualizar", "Error al actualizar el módulo. Campos incorrectos");
        }
    }

    //Borrar un módulo
    @DeleteMapping("/modulos/{id}")
    public ResponseEntity<Modulo> delete(@PathVariable Long id) {
        try {
            Modulo modulo = moduloRepository.findById(id).orElse(null);
            if (modulo == null) {
                throw new ModuloNotFoundException(id);
            } else {
                moduloRepository.delete(modulo);
                return ResponseEntity.ok(modulo);
            }
        } catch (ResponseStatusException e) {
            throw new GeneralBadRequestException("Eliminar", "Error al borrar el curso");
        }
    }
}
