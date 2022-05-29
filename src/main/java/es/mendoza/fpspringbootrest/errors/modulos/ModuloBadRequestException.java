package es.mendoza.fpspringbootrest.errors.modulos;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ModuloBadRequestException extends RuntimeException {
    public ModuloBadRequestException(String campo, String error) {
        super("Existe un erro en el campo: " + campo + " Error: " + error);
    }
}
