package co.com.toreminddo.controller;

import co.com.toreminddo.dto.CrearUsuarioDTO;
import co.com.toreminddo.dto.RespuestaAutenticacionDTO;
import co.com.toreminddo.dto.ValidarUsuarioDTO;
import co.com.toreminddo.exceptions.UsuarioException;
import co.com.toreminddo.model.Usuario;
import co.com.toreminddo.security.JwtService;
import co.com.toreminddo.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<RespuestaAutenticacionDTO> registrarUsuario(@Valid @RequestBody CrearUsuarioDTO dto) {
        Usuario usuarioCreado = usuarioService.crearUsuario(
                dto.getNombre(),
                dto.getEmail(),
                dto.getEdad(),
                dto.getClave()
        );

        String tokenGenerado = jwtService.generarToken(
                usuarioCreado.getEmail()
        );

        RespuestaAutenticacionDTO respuestaAutenticacion
                = new RespuestaAutenticacionDTO(usuarioCreado,tokenGenerado);

        return ResponseEntity.status(HttpStatus.CREATED).body(respuestaAutenticacion);
    }

    @PostMapping("/login")
    public ResponseEntity<RespuestaAutenticacionDTO> loggearUsuario
            (@Valid @RequestBody ValidarUsuarioDTO dto) {
        Usuario usuarioEncontrado = usuarioService.obtenerUsuarioPorEmail(dto.getEmail());

        boolean esMismaClave = passwordEncoder.matches(
                dto.getClave(),
                usuarioEncontrado.getClave()
        );

        if (!esMismaClave) {
            throw new UsuarioException("La contraseña no coincide.");
        }

        String tokenGenerado = jwtService.generarToken(usuarioEncontrado.getEmail());

        RespuestaAutenticacionDTO respuestaAutenticacionDTO
                = new RespuestaAutenticacionDTO(usuarioEncontrado, tokenGenerado);

        return ResponseEntity.status(HttpStatus.OK).body(respuestaAutenticacionDTO);
    }

}
