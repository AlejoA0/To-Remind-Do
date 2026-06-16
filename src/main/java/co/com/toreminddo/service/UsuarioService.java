package co.com.toreminddo.service;

import co.com.toreminddo.exceptions.UsuarioException;
import co.com.toreminddo.model.Usuario;
import co.com.toreminddo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /*
       Paso 1: Validar que los datos tengan sentido (¿existe el usuario? ¿existe el libro?)
       Paso 2: Validar que cumplan las reglas de negocio (¿el usuario tiene menos de 3? ¿el libro no está prestado?)
       Paso 3: Si todo pasa, hacer la operación (guardar, actualizar, etc.)
       Paso 4: Retornar el resultado o lanzar una excepción si algo falló
       Ese patrón es siempre igual, solo cambian los detalles específicos de cada método.
    */

    public Usuario crearUsuario(String nombre, String email, Integer edad) {

        if (existeEmail(email)) {
            throw new UsuarioException("El email ya se encuentra registrado");
        }

        Usuario nuevoUsuario = new Usuario();

        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setEmail(email);
        nuevoUsuario.setEdad(edad);

        try {
            return usuarioRepository.save(nuevoUsuario);
        } catch (DataAccessException errorDB) {
            throw new UsuarioException("Error al guardar usuario en la base de datos");
        }
    }

    public Usuario obtenerUsuario(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioException("No se encuentra el usuario por el id solicitado"));
    }

    public List<Usuario> obtenerTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    private boolean existeEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

}
