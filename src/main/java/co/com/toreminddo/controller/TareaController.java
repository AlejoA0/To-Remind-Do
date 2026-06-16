package co.com.toreminddo.controller;

import co.com.toreminddo.dto.CrearTareaDTO;
import co.com.toreminddo.dto.EditarTareaDTO;
import co.com.toreminddo.model.Tarea;
import co.com.toreminddo.model.estado.EstadoTarea;
import co.com.toreminddo.model.prioridad.PrioridadTarea;
import co.com.toreminddo.service.TareaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tareas")
public class TareaController {

    @Autowired
    private TareaService tareaService;

    @PostMapping
    public ResponseEntity<Tarea> crearTarea(@Valid @RequestBody CrearTareaDTO dto) {
        Tarea tareaCreada = tareaService.crearTarea(
                dto.getTitulo(),
                dto.getDescripcion(),
                dto.getUsuarioId()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(tareaCreada);
    }

    @GetMapping("/{tareaId}")
    public ResponseEntity<Tarea> obtenerTarea(@PathVariable Long tareaId) {
        Tarea tareaObtenida = tareaService.obtenerTarea(tareaId);

        return ResponseEntity.status(HttpStatus.OK).body(tareaObtenida);
    }

    @GetMapping
    public ResponseEntity<List<Tarea>> obtenerTodasTareas() {
        List<Tarea> listadoTareas = tareaService.obtenerTodasTareas();

        return ResponseEntity.status(HttpStatus.OK).body(listadoTareas);
    }

    @PatchMapping("/estado/{tareaId}/{estadoTarea}")
    public ResponseEntity<Tarea> cambiarEstado(@PathVariable Long tareaId, @PathVariable EstadoTarea estadoTarea) {
        Tarea tareaEstadoCambiado = tareaService.cambiarEstado(tareaId, estadoTarea);

        return ResponseEntity.status(HttpStatus.OK).body(tareaEstadoCambiado);
    }

    @PatchMapping("/prioridad/{tareaId}/{prioridadTarea}")
    public ResponseEntity<Tarea> cambiarPrioridad(@PathVariable Long tareaId, @PathVariable PrioridadTarea prioridadTarea) {
        Tarea tareaPrioridadCambiada = tareaService.cambiarPrioridad(tareaId, prioridadTarea);

        return ResponseEntity.status(HttpStatus.OK).body(tareaPrioridadCambiada);
    }

    @PatchMapping("/editar/{tareaId}")
    public ResponseEntity<Tarea> editarTarea(@Valid @RequestBody EditarTareaDTO dto, @PathVariable Long tareaId) {
        Tarea tareaEditada = tareaService.editarTarea(
                tareaId,
                dto.getTitulo(),
                dto.getDescripcion(),
                dto.getFechaLimite()
        );

        return ResponseEntity.status(HttpStatus.OK).body(tareaEditada);
    }

    @DeleteMapping("eliminar/{tareaId}")
    public ResponseEntity<Tarea> eliminarTarea(@PathVariable Long tareaId) {
        Tarea tareaEliminada = tareaService.eliminarTarea(tareaId);

        return ResponseEntity.status(HttpStatus.OK).body(tareaEliminada);
    }

}
