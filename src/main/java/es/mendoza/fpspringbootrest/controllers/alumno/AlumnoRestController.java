package es.mendoza.fpspringbootrest.controllers.alumno;

import es.mendoza.fpspringbootrest.config.APIConfig;
import es.mendoza.fpspringbootrest.dto.alumnos.CreateAlumnoDTO;
import es.mendoza.fpspringbootrest.dto.alumnos.ListAlumnoPageDTO;
import es.mendoza.fpspringbootrest.errors.GeneralBadRequestException;
import es.mendoza.fpspringbootrest.errors.alumnos.AlumnoBadRequestException;
import es.mendoza.fpspringbootrest.errors.alumnos.AlumnoNotFoundException;
import es.mendoza.fpspringbootrest.errors.alumnos.AlumnosNotFoundException;
import es.mendoza.fpspringbootrest.mapper.AlumnoMapper;
import es.mendoza.fpspringbootrest.models.Alumno;
import es.mendoza.fpspringbootrest.repositories.AlumnoRepository;
import es.mendoza.fpspringbootrest.service.uploads.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(APIConfig.API_PATH + "/alumno")
public class AlumnoRestController {
    private final AlumnoRepository alumnoRepository;
    private final StorageService storageService;
    private final AlumnoMapper alumnoMapper;

    @Autowired
    public AlumnoRestController(AlumnoRepository alumnoRepository, StorageService storageService, AlumnoMapper alumnoMapper) {
        this.alumnoRepository = alumnoRepository;
        this.storageService = storageService;
        this.alumnoMapper = alumnoMapper;
    }

    @CrossOrigin(origins = "http://localhost:7575")

    //Obtenemos todos los alumnos
    @GetMapping("")
    public ResponseEntity<?> findAll(@RequestParam(name = "limit") Optional<String> limit, @RequestParam(name = "nombre") Optional<String> nombre) {
        List<Alumno> alumnos = null;
        try {
            if (nombre.isPresent()) {
                alumnos = alumnoRepository.findByNombreContainsIgnoreCase(nombre.get());
            } else {
                alumnos = alumnoRepository.findAll();
            }
            if (limit.isPresent() && !alumnos.isEmpty() && alumnos.size() > Integer.parseInt(limit.get())) {
                return ResponseEntity.ok(alumnoMapper.toDTO(alumnos.subList(0, Integer.parseInt(limit.get()))));
            } else {
                if (!alumnos.isEmpty()) {
                    return ResponseEntity.ok(alumnoMapper.toDTO(alumnos));
                } else {
                    throw new AlumnosNotFoundException();
                }
            }
        } catch (Exception e) {
            throw new GeneralBadRequestException("Selección de datos", "Parámetros de consulta incorrectos");
        }
    }

    //Obtenemos un alumno por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Alumno alumno = alumnoRepository.findById(id).orElse(null);
        if (alumno == null) {
            throw new AlumnoNotFoundException(id);
        } else {
            return ResponseEntity.ok(alumnoMapper.toDTO(alumno));
        }
    }

    //Insertar alumno
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody CreateAlumnoDTO alumnoDTO) {
        try {
            Alumno alumno = alumnoMapper.fromDTO(alumnoDTO);
            checkAlumnoData(alumno);
            Alumno alumnoInsertado = alumnoRepository.save(alumno);
            return ResponseEntity.ok(alumnoMapper.toDTO(alumnoInsertado));
        } catch (Exception e) {
            throw new GeneralBadRequestException("Insertar", "Error al insertar el alumno. Campos incorrectos");
        }
    }

    //Actualizar alumno por id
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Alumno alumno) {
        try {
            Alumno alumnoActualizado = alumnoRepository.findById(id).orElse(null);
            if (alumnoActualizado == null) {
                throw new AlumnoNotFoundException(id);
            } else {
                checkAlumnoData(alumno);
                alumnoActualizado.setNombre(alumno.getNombre());
                alumnoActualizado.setCorreo(alumno.getCorreo());
                alumnoActualizado = alumnoRepository.save(alumnoActualizado);
                return ResponseEntity.ok(alumnoMapper.toDTO(alumnoActualizado));
            }
        } catch (ResponseStatusException e) {
            throw new GeneralBadRequestException("Actualizar", "Error al actualizar el alumno. CAmpos incorrectos");
        }
    }

    //Borrar un alumno
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            Alumno alumno = alumnoRepository.findById(id).orElse(null);
            if (alumno == null) {
                throw new AlumnoNotFoundException(id);
            } else {
                alumnoRepository.delete(alumno);
                return ResponseEntity.ok(alumnoMapper.toDTO(alumno));
            }
        } catch (ResponseStatusException e) {
            throw new GeneralBadRequestException("Eliminar", "Error al borrar el alumno");
        }
    }

    private void checkAlumnoData(Alumno alumno) {
        if (alumno.getNombre() == null || alumno.getNombre().isEmpty()) {
            throw new AlumnoBadRequestException("Nombre", "El nombre es obligatorio");
        }
        if (alumno.getCorreo() == null || alumno.getCorreo().isEmpty()) {
            throw new AlumnoBadRequestException("Correo", "El correo es obligatorio");
        }
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> nuevoAlumno(@RequestPart("alumno") CreateAlumnoDTO alumnoDTO, @RequestPart("file") MultipartFile file) {
        String urlImagen = null;
        try {
            Alumno alumno = alumnoMapper.fromDTO(alumnoDTO);
            checkAlumnoData(alumno);
            if (!file.isEmpty()) {
                String imagen = storageService.store(file);
                urlImagen = storageService.getUrl(imagen);
                alumno.setImagen(urlImagen);
            }
            Alumno alumnoInsertado = alumnoRepository.save(alumno);
            return ResponseEntity.ok(alumnoMapper.toDTO(alumnoInsertado));
        } catch (AlumnoNotFoundException e) {
            throw new GeneralBadRequestException("Insertar", "Error al insertar el alumno. Campos incorrectos");
        }
    }

    //Obtener todos los alumnos, paginable
    @GetMapping("/all")
    public ResponseEntity<?> listado(
            @RequestParam(required = false, name = "nombre") Optional<String> nombre,
            @RequestParam(required = false, name = "correo") Optional<String> correo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable paging = PageRequest.of(page, size);
        Page<Alumno> pagedResult;
        try {
            if (nombre.isPresent()) {
                pagedResult = alumnoRepository.findByNombreContainsIgnoreCase(nombre.get(), paging);
            } else if (correo.isPresent()){
                pagedResult = alumnoRepository.findByNombreContainsIgnoreCase(correo.get(), paging);
            } else {
                pagedResult = alumnoRepository.findAll(paging);
            }
            List<Alumno> alumnos = pagedResult.getContent();
            ListAlumnoPageDTO listAlumnoPageDTO = ListAlumnoPageDTO.builder()
                    .data(alumnoMapper.toDTO(alumnos))
                    .totalPages(pagedResult.getTotalPages())
                    .totalElements(pagedResult.getTotalElements())
                    .currentPage(pagedResult.getNumber())
                    .build();
            return ResponseEntity.ok(listAlumnoPageDTO);
        } catch (Exception e) {
            throw new GeneralBadRequestException("Selección de datos", "Parámetros de consulta incorrectos");
        }
    }
}
