package co.com.toreminddo.dto;

import jakarta.validation.constraints.Email;
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
public class CrearUsuarioDTO {

    @NotBlank
    @Size(min = 2, max = 50)
    private String nombre;

    @NotBlank
    @Email
    private String email;

    @Positive
    private Integer edad;

}
