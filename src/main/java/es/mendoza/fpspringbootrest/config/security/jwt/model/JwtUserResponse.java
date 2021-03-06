package es.mendoza.fpspringbootrest.config.security.jwt.model;

import es.mendoza.fpspringbootrest.dto.usuarios.GetUsuarioDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class JwtUserResponse extends GetUsuarioDTO {
    @NotNull(message = "El token no puede ser nulo")
    private String token;

    @Builder(builderMethodName = "jwtUserResponseBuilder")
    public JwtUserResponse(String username, String avatar, String fullName, String email, Set<String> roles, String token) {
        super(username, avatar, fullName, email, roles);
        this.token = token;
    }
}
