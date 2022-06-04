package es.mendoza.fpspringbootrest.errors.usuarios;

public class NewUserWithDifferentPasswordsException extends RuntimeException {
    public NewUserWithDifferentPasswordsException() {
        super("Las contraseñas no coinciden");
    }
}
