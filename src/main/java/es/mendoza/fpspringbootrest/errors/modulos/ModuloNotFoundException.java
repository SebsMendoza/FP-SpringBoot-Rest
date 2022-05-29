package es.mendoza.fpspringbootrest.errors.modulos;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ModuloNotFoundException extends RuntimeException {
    public ModuloNotFoundException(Long id) {
        super("No se puede encontrar el módulo con la ID: " + id);
    }
}
