package es.mendoza.fpspringbootrest.errors.cursos;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CursosNotFoundException extends RuntimeException {
    public CursosNotFoundException() {
        super("La lista de cursos está vacía o no existe");
    }
}
