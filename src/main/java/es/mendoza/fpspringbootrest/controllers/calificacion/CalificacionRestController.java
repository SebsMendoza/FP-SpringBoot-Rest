package es.mendoza.fpspringbootrest.controllers.calificacion;

import es.mendoza.fpspringbootrest.config.APIConfig;
import es.mendoza.fpspringbootrest.dto.calificaciones.CalificacionAlumnoModuloDTO;
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
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    public ResponseEntity<List<CalificacionDTO>> findAll(@RequestParam(required = false, name = "limit") Optional<String> limit) {
        List<Calificacion> calificaciones = null;
        try {
            calificaciones = calificacionRepository.findAll();
            if (limit.isPresent() && !calificaciones.isEmpty() && calificaciones.size() > Integer.parseInt(limit.get())) {
                return ResponseEntity.ok(calificacionMapper.toDTO(calificaciones.subList(0, Integer.parseInt(limit.get()))));
            } else {
                if (!calificaciones.isEmpty()) {
                    return ResponseEntity.ok(calificacionMapper.toDTO(calificaciones));
                } else {
                    throw new CalificacionesNotFoundException();
                }
            }
        } catch (Exception e) {
            throw new GeneralBadRequestException("Selecci??n de datos", "Par??metros de consulta incorrectos");
        }
    }
    /*
      -> Ester ser??a el findAll con el nuevo DTO
    @GetMapping("/")
    public ResponseEntity<List<CalificacionAlumnoModuloDTO>> findAll(@RequestParam(required = false, name = "limit") Optional<String> limit) {
        List<Calificacion> calificaciones = null;
        try {
            calificaciones = calificacionRepository.findAll();
            if (limit.isPresent() && !calificaciones.isEmpty() && calificaciones.size() > Integer.parseInt(limit.get())) {
                // calificacionMapper.toDTO(calificaciones.subList(0, Integer.parseInt(limit.get())));
                List<Calificacion> nuevaLista = calificaciones.subList(0, Integer.parseInt(limit.get()));
                return getAlumnoNotaModulo(nuevaLista);
            } else {
                if (!calificaciones.isEmpty()) {
                    return getAlumnoNotaModulo(calificaciones);
                    *//*
                    return ResponseEntity.ok(calificacionMapper.toDTO(calificaciones));
                     *//*
                } else {
                    throw new CalificacionesNotFoundException();
                }
            }
        } catch (Exception e) {
            throw new GeneralBadRequestException("Selecci??n de datos", "Par??metros de consulta incorrectos");
        }
    }
      ->Este m??todo arregla la salida de calificaciones eliminando la recursividad utilizando un nuevo DTO,
        pero estropea los test ya hechos. Cuando haya m??s tiempo, se cambiar??

    private ResponseEntity<List<CalificacionAlumnoModuloDTO>> getAlumnoNotaModulo(List<Calificacion> list) {
        ArrayList<CalificacionAlumnoModuloDTO> result = new ArrayList<>();
        for (Calificacion c : list) {
            CalificacionAlumnoModuloDTO anmDTO = CalificacionAlumnoModuloDTO.builder()
                    .id(c.getId())
                    .calificacion(Map.of(c.getModulo().getNombre(), c.getNota()))
                    .nombre(c.getAlumno().getNombre())
                    .email(c.getAlumno().getCorreo())
                    .build();
            result.add(anmDTO);
        }
        return ResponseEntity.ok(result);
    }*/

    //Obtener nota por id
    @ApiOperation(value = "Obtener una calificacion por id", notes = "Obtiene una calificacion por id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CalificacionDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = CalificacionNotFoundException.class)
    })
    @GetMapping("/{id}")
    public ResponseEntity<CalificacionDTO> findById(@PathVariable Long id) {
        Calificacion calificacion = calificacionRepository.findById(id).orElse(null);
        if (calificacion == null) {
            throw new CalificacionNotFoundException(id);
        } else {
            return ResponseEntity.ok(calificacionMapper.toDTO(calificacion));
        }
    }

    //Insertar nota
    @ApiOperation(value = "Crear una calificaci??n", notes = "Crea una calificaci??n")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Created", response = CalificacionDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @PostMapping("/")
    public ResponseEntity<CalificacionDTO> save(@RequestBody CreateCalificacionDTO calificacionDTO) {

        Calificacion calificacion = calificacionMapper.fromDTO(calificacionDTO);
        checkCalificacionData(calificacion);
        try {
            Calificacion notaInsertada = calificacionRepository.save(calificacion);
            return ResponseEntity.ok(calificacionMapper.toDTO(notaInsertada));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new GeneralBadRequestException("Insertar", "Error al insertar la calificaci??n" + e.getMessage());
        }
    }

    //Actualizar nota por id
    @ApiOperation(value = "Actualizar una calificaci??n", notes = "Actualiza una calificaci??n por id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CalificacionDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = CalificacionNotFoundException.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @PutMapping("/{id}")
    public ResponseEntity<CalificacionDTO> update(@PathVariable Long id, @RequestBody Calificacion calificacion) {
        Calificacion notaActualizada = calificacionRepository.findById(id).orElse(null);
        if (notaActualizada == null) {
            throw new CalificacionNotFoundException(id);
        }
        checkCalificacionData(calificacion);
        notaActualizada.setNota(calificacion.getNota());
        try {
            notaActualizada = calificacionRepository.save(notaActualizada);
            return ResponseEntity.ok(calificacionMapper.toDTO(notaActualizada));
        } catch (Exception e) {
            throw new GeneralBadRequestException("Actualizar", "Error al actualizar la calificaci??n. Campos incorrectos");
        }
    }

    //Borrar nota por id
    @ApiOperation(value = "Eliminar una calificaci??n", notes = "Elimina una calificaci??n dado su id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CalificacionDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = CalificacionNotFoundException.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<CalificacionDTO> delete(@PathVariable Long id) {
        Calificacion calificacion = calificacionRepository.findById(id).orElse(null);
        if (calificacion == null) {
            throw new CalificacionNotFoundException(id);
        }
        try {
            calificacionRepository.delete(calificacion);
            return ResponseEntity.ok(calificacionMapper.toDTO(calificacion));
        } catch (Exception e) {
            throw new GeneralBadRequestException("Eliminar", "Error al borrar la calificaci??n");
        }
    }

    private void checkCalificacionData(Calificacion calificacion) {
        if (calificacion.getNota() < 0) {
            throw new CalificacionBadRequestException("Nota", "La nota debe ser mayor que 0");
        }
    }

    @Operation(summary = "Obtiene la lista de calificaciones existentes", description = "Obtiene la lista de calificaciones existentes")
    @ApiOperation(value = "Obtiene una lista de calificaciones", notes = "Obtiene una lista de calificaciones paginada, filtrada y ordenada")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK: Lista de calificaciones", response = ListCalificacionPageDTO.class),
            @ApiResponse(code = 400, message = "Bad Request: Lista no encontrada", response = GeneralBadRequestException.class)
    })
    @GetMapping("/all")
    public ResponseEntity<?> listado() {
        List<Calificacion> calificacion = calificacionRepository.findAll();
        if (calificacion.isEmpty()) {
            throw new CalificacionesNotFoundException();
        } else {
            return ResponseEntity.ok(calificacionMapper.toListDTO(calificacion));
        }
    }
}