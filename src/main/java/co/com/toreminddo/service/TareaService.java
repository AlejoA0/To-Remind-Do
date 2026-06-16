package co.com.toreminddo.service;

import co.com.toreminddo.exceptions.TareaException;
import co.com.toreminddo.model.Tarea;
import co.com.toreminddo.model.estado.EstadoTarea;
import co.com.toreminddo.model.prioridad.PrioridadTarea;
import co.com.toreminddo.repository.TareaRepository;
import co.com.toreminddo.repository.UsuarioRepository;
import co.com.toreminddo.utils.constants.RemindConstants;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TareaService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TareaRepository tareaRepository;

    /*
       Paso 1: Validar que los datos tengan sentido (¿existe el usuario? ¿existe el libro?)
       Paso 2: Validar que cumplan las reglas de negocio (¿el usuario tiene menos de 3? ¿el libro no está prestado?)
       Paso 3: Si todo pasa, hacer la operación (guardar, actualizar, etc.)
       Paso 4: Retornar el resultado o lanzar una excepción si algo falló
       Ese patrón es siempre igual, solo cambian los detalles específicos de cada método.
    */

    public Tarea crearTarea(String titulo, String descripcion, Long usuarioId) {

        Tarea nuevaTarea = new Tarea();

        nuevaTarea.setTitulo(titulo);
        nuevaTarea.setDescripcion(descripcion);

        nuevaTarea.setUsuario(
                usuarioRepository.findById(usuarioId)
                        .orElseThrow(() -> new TareaException("No se encuentra usuario para relacionarlo con la tarea"))
        );

        nuevaTarea.setFechaCreacion(new Date());
        nuevaTarea.setFechaLimite(calcularFechaLimite());

        try {
            return tareaRepository.save(nuevaTarea);
        } catch (DataAccessException errorDB) {
            throw new TareaException("Error al guardar la tarea en la base de datos");
        }
    }

    public Tarea obtenerTarea(Long tareaId) {
        return buscarTareaPorId(tareaId);
    }

    public List<Tarea> obtenerTodasTareas() {
        return tareaRepository.findByEsEliminadaFalse();
    }

    public Tarea cambiarEstado(Long idTarea, EstadoTarea estadoTarea) {
        Tarea tarea = buscarTareaPorId(idTarea);

        tarea.setEstadoTarea(estadoTarea);

        return tareaRepository.save(tarea);
    }

    public Tarea cambiarPrioridad(Long idTarea, PrioridadTarea prioridadTarea) {
        Tarea tarea = buscarTareaPorId(idTarea);

        tarea.setPrioridadTarea(prioridadTarea);

        return tareaRepository.save(tarea);
    }

    public Tarea editarTarea(Long idTarea, String titulo, String descripcion, Date fechaLimite) {
        Tarea tarea = buscarTareaPorId(idTarea);

        tarea.setTitulo(titulo);
        tarea.setDescripcion(descripcion);
        tarea.setFechaLimite(fechaLimite);

        return tareaRepository.save(tarea);
    }


    /**
     * Método que elimina lógicamente, no física, la tarea en la lógica del sistema
     * @param idTarea ID de la tarea a eliminar
     * @return Retorna la tarea con el atributo EsEliminada en true
     */
    public Tarea eliminarTarea(Long idTarea) {
        Tarea tareaPorEliminar = buscarTareaPorId(idTarea);

        tareaPorEliminar.setEsEliminada(true);

        return tareaRepository.save(tareaPorEliminar);
    }

    private @NonNull Tarea buscarTareaPorId(Long idTarea) {
        return tareaRepository.findById(idTarea)
                .orElseThrow(() -> new TareaException("No se encuentra tarea por el id solicitado"));
    }

    private @NonNull Date calcularFechaLimite() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, RemindConstants.DIAS_LIMITE_POR_DEFECTO);
        return calendar.getTime();
    }
}
