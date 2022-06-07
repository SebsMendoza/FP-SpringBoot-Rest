package es.mendoza.fpspringbootrest.controllers.calificacion;

import es.mendoza.fpspringbootrest.config.APIConfig;
import es.mendoza.fpspringbootrest.dto.calificaciones.CalificacionDTO;
import es.mendoza.fpspringbootrest.dto.calificaciones.CreateCalificacionDTO;
import es.mendoza.fpspringbootrest.dto.calificaciones.ListCalificacionPageDTO;
import es.mendoza.fpspringbootrest.errors.GeneralBadRequestException;
import es.mendoza.fpspringbootrest.errors.calificaciones.CalificacionBadRequestException;
import es.mendoza.fpspringbootrest.errors.calificaciones.CalificacionNotFoundException;
import es.mendoza.fpspringbootrest.errors.calificaciones.CalificacionesNotFoundException;
import es.mendoza.fpspringbootrest.mapper.CalificacionMapper;
import es.mendoza.fpspringbootrest.models.Calificacion;
import es.mendoza.fpspringbootrest.repositories.CalificacionRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(APIConfig.API_PATH + "/calificacion")
public class CalificacionRestController {
    private final CalificacionRepository calificacionRepository;
    private final CalificacionMapper calificacionMapper;

    //Obtener todas las notas
    @ApiOperation(value = "Obtener todas las calificaciones", notes = "Obtiene todas las calificaciones")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CalificacionDTO.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Not Found", response = CalificacionNotFoundException.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @GetMapping("/")
    public ResponseEntity<?> findAll(@RequestParam(name = "limit") Optional<String> limit) {
        List<Calificacion> notas = null;
        try {
            notas = calificacionRepository.findAll();
            if (limit.isPresent() && !notas.isEmpty() && notas.size() > Integer.parseInt(limit.get())) {
                return ResponseEntity.ok(calificacionMapper.toDTO(notas.subList(0, Integer.parseInt(limit.get()))));
            } else {
                if (!notas.isEmpty()) {
                    return ResponseEntity.ok(calificacionMapper.toDTO(notas));
                } else {
                    throw new CalificacionesNotFoundException();
                }
            }
        } catch (Exception e) {
            throw new GeneralBadRequestException("Selección de datos", "Parámetros de consulta incorrectos");
        }
    }

    //Obtener nota por id
    @ApiOperation(value = "Obtener una calificacion por id", notes = "Obtiene una calificacion por id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CalificacionDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = CalificacionNotFoundException.class)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Calificacion nota = calificacionRepository.findById(id).orElse(null);
        if (nota == null) {
            throw new CalificacionNotFoundException(id);
        } else {
            return ResponseEntity.ok(calificacionMapper.toDTO(nota));
        }
    }

    //Insertar nota
    @ApiOperation(value = "Crear una calificación", notes = "Crea una calificación")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Created", response = CalificacionDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody CreateCalificacionDTO calificacionDTO) {
        try {
            Calificacion calificacion = calificacionMapper.fromDTO(calificacionDTO);
            checkCalificacionData(calificacion);
            Calificacion notaInsertada = calificacionRepository.save(calificacion);
            return ResponseEntity.ok(calificacionMapper.toDTO(notaInsertada));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new GeneralBadRequestException("Insertar", "Error al insertar la calificación");
        }
    }

    //Actualizar nota por id
    @ApiOperation(value = "Actualizar una calificación", notes = "Actualiza una calificación por id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CalificacionDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = CalificacionNotFoundException.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @PutMapping("/{id}")
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
    @ApiOperation(value = "Eliminar una calificación", notes = "Elimina una calificación dado su id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CalificacionDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = CalificacionNotFoundException.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @DeleteMapping("/{id}")
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

    @ApiOperation(value = "Obtiene una lista de calificaciones", notes = "Obtiene una lista de calificaciones paginada, filtrada y ordenada")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ListCalificacionPageDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @GetMapping("/all")
    public ResponseEntity<?> listado() {
        List<Calificacion> notas = calificacionRepository.findAll();
        if (notas.isEmpty()) {
            throw new CalificacionesNotFoundException();
        } else {
            return ResponseEntity.ok(calificacionMapper.toListDTO(notas));
        }
    }
}