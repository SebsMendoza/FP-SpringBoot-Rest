package es.mendoza.fpspringbootrest.errors.alumnos;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AlumnoNotFoundException extends RuntimeException {
    public AlumnoNotFoundException(Long id) {
        super("No se puede encontrar el alumno con la ID: " + id);
    }
}
