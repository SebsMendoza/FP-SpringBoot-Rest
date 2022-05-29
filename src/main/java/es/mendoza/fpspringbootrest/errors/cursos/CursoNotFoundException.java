package es.mendoza.fpspringbootrest.errors.cursos;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CursoNotFoundException extends RuntimeException {
    public CursoNotFoundException(Long id) {
        super("No se puede encontrar el curso con la ID: " + id);
    }
}
