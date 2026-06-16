package co.com.toreminddo.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EditarTareaDTO {

    @NotBlank
    @Size(min = 2, max = 50)
    private String titulo;

    @NotBlank
    @Size(min = 2, max = 200)
    private String descripcion;

    @FutureOrPresent
    private Date fechaLimite;
}
