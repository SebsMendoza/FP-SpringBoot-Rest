package es.mendoza.fpspringbootrest.errors.modulos;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ModulosNotFoundException extends RuntimeException {
    public ModulosNotFoundException() {
        super("La lista de modulos está vacía o no existe");
    }
}
