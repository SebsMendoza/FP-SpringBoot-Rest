package es.mendoza.fpspringbootrest.dto.usuarios;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUsuarioDTO {
    private String username;
    private String avatar;
    private String fullName;
    private String email;
    private Set<String> roles;
}
