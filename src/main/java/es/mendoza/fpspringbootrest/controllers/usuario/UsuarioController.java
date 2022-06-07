package es.mendoza.fpspringbootrest.controllers.usuario;

import es.mendoza.fpspringbootrest.config.APIConfig;
import es.mendoza.fpspringbootrest.config.security.jwt.JwtTokenProvider;
import es.mendoza.fpspringbootrest.config.security.jwt.model.JwtUserResponse;
import es.mendoza.fpspringbootrest.config.security.jwt.model.LoginRequest;
import es.mendoza.fpspringbootrest.dto.usuarios.CreateUsuarioDTO;
import es.mendoza.fpspringbootrest.dto.usuarios.GetUsuarioDTO;
import es.mendoza.fpspringbootrest.mapper.UsuarioMapper;
import es.mendoza.fpspringbootrest.models.Usuario;
import es.mendoza.fpspringbootrest.models.UsuarioRol;
import es.mendoza.fpspringbootrest.service.users.UsuarioService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping(APIConfig.API_PATH + "/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final UsuarioMapper ususuarioMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    @ApiOperation(value = "Crea un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuario creado"),
            @ApiResponse(code = 400, message = "Error al crear usuario")
    })
    @PostMapping("/")
    public GetUsuarioDTO nuevoUsuario(@RequestBody CreateUsuarioDTO newUser) {
        return ususuarioMapper.toDTO(usuarioService.nuevoUsuario(newUser));

    }

    @ApiOperation(value = "Devuelve los datos del usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuario devuelto"),
            @ApiResponse(code = 401, message = "No autenticado"),
            @ApiResponse(code = 403, message = "No autorizado")
    })
    @GetMapping("/me")
    public GetUsuarioDTO me(@AuthenticationPrincipal Usuario user) {
        return ususuarioMapper.toDTO(user);
    }

    @ApiOperation(value = "Autentica un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuario autenticado y token generado"),
            @ApiResponse(code = 400, message = "Error al autenticar usuario"),
    })
    @PostMapping("/login")
    public JwtUserResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getUsername(),
                                loginRequest.getPassword()

                        )
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Usuario user = (Usuario) authentication.getPrincipal();
        String jwtToken = tokenProvider.generateToken(authentication);
        return convertUserEntityAndTokenToJwtUserResponse(user, jwtToken);

    }

    /**
     * Método que convierte un usuario y un token a una respuesta de usuario
     *
     * @param user     Usuario
     * @param jwtToken Token
     * @return JwtUserResponse con el usuario y el token
     */
    private JwtUserResponse convertUserEntityAndTokenToJwtUserResponse(Usuario user, String jwtToken) {
        return JwtUserResponse
                .jwtUserResponseBuilder()
                .fullName(user.getFullName())
                .email(user.getEmail())
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .roles(user.getRoles().stream().map(UsuarioRol::name).collect(Collectors.toSet()))
                .token(jwtToken)
                .build();
    }
}
