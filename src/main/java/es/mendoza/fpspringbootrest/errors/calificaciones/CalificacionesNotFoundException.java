package es.mendoza.fpspringbootrest.errors.calificaciones;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CalificacionesNotFoundException extends RuntimeException {
    public CalificacionesNotFoundException() {
        super("La lista de calificaciones está vacía o no existe");
    }
}
