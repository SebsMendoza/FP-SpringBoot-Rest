package es.mendoza.fpspringbootrest.mapper;

import es.mendoza.fpspringbootrest.dto.usuarios.GetUsuarioDTO;
import es.mendoza.fpspringbootrest.models.Usuario;
import es.mendoza.fpspringbootrest.models.UsuarioRol;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
@Component
public class UsuarioMapper {
    public GetUsuarioDTO toDTO(Usuario user) {
        return GetUsuarioDTO.builder()
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .roles(user.getRoles().stream()
                        .map(UsuarioRol::name)
                        .collect(Collectors.toSet())
                ).build();
    }
}
