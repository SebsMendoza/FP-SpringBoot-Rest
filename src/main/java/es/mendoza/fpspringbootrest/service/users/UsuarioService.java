package es.mendoza.fpspringbootrest.service.users;

import es.mendoza.fpspringbootrest.dto.usuarios.CreateUsuarioDTO;
import es.mendoza.fpspringbootrest.errors.usuarios.NewUserWithDifferentPasswordsException;
import es.mendoza.fpspringbootrest.models.Usuario;
import es.mendoza.fpspringbootrest.models.UsuarioRol;
import es.mendoza.fpspringbootrest.repositories.UsuariosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuariosRepository usuariosRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<Usuario> findUserByUsername(String username) {
        return usuariosRepository.findByUsername(username);
    }

    public Optional<Usuario> findUserById(Long userId) {
        return usuariosRepository.findById(userId);
    }

    public Usuario nuevoUsuario(CreateUsuarioDTO newUser) {
        System.out.println(passwordEncoder.encode(newUser.getPassword()));
        if (newUser.getPassword().contentEquals(newUser.getPassword2())) {
            Usuario usuario = Usuario.builder()
                    .username(newUser.getUsername())
                    .password(passwordEncoder.encode(newUser.getPassword()))
                    .avatar(newUser.getAvatar())
                    .fullName(newUser.getFullname()).email(newUser.getEmail())
                    .roles(Stream.of(UsuarioRol.USER).collect(Collectors.toSet()))
                    .build();
            try {
                return usuariosRepository.save(usuario);
            } catch (DataIntegrityViolationException ex) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre de usuario ya existe");
            }
        } else {
            throw new NewUserWithDifferentPasswordsException();
        }

    }
}
