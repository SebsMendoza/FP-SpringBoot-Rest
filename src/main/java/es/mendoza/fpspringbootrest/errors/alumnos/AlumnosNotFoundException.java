package es.mendoza.fpspringbootrest.errors.alumnos;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AlumnosNotFoundException extends RuntimeException {
    public AlumnosNotFoundException() {
        super("La lista de alumnos está vacía o no existe");
    }
}
