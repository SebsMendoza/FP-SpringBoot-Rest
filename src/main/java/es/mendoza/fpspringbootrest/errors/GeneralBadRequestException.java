package es.mendoza.fpspringbootrest.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GeneralBadRequestException extends RuntimeException {
    public GeneralBadRequestException(String operacion, String error) {
        super("Error al procesar: " + operacion + " : " + error);
    }
}
