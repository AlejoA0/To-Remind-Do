package co.com.toreminddo.controller;

import co.com.toreminddo.dto.CrearUsuarioDTO;
import co.com.toreminddo.model.Usuario;
import co.com.toreminddo.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@Valid @RequestBody CrearUsuarioDTO dto) {

        Usuario usuarioCreado = usuarioService.crearUsuario(
                dto.getNombre(),
                dto.getEmail(),
                dto.getEdad()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCreado);
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable Long usuarioId) {
        Usuario usuarioObtenido = usuarioService.obtenerUsuario(usuarioId);

        return ResponseEntity.status(HttpStatus.OK).body(usuarioObtenido);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerTodosUsuarios() {
        List<Usuario> listadoUsuarios = usuarioService.obtenerTodosUsuarios();

        return ResponseEntity.status(HttpStatus.OK).body(listadoUsuarios);
    }

}
