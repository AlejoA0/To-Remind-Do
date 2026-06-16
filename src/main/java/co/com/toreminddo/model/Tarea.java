package co.com.toreminddo.model;

import co.com.toreminddo.model.estado.EstadoTarea;
import co.com.toreminddo.model.prioridad.PrioridadTarea;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "prioridad", length = 30, nullable = false)
    private PrioridadTarea prioridadTarea = PrioridadTarea.MEDIUM;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private Date fechaCreacion;
    private Date fechaLimite;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", length = 30, nullable = false)
    private EstadoTarea estadoTarea = EstadoTarea.PENDING;

    private Boolean esEliminada = false;
}
