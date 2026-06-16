package co.com.toreminddo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CrearTareaDTO {

    @NotBlank
    @Size(min = 2, max = 50)
    private String titulo;

    @NotBlank
    @Size(min = 2, max = 200)
    private String descripcion;

    @Positive
    private Long usuarioId;
}
