package es.mendoza.fpspringbootrest.errors.calificaciones;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CalificacionNotFoundException extends RuntimeException {
    public CalificacionNotFoundException(Long id) {
        super("No se puede encontrar la calificación con la ID: " + id);
    }
}
