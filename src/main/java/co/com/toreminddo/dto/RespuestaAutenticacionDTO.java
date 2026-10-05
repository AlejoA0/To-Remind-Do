package co.com.toreminddo.dto;


import co.com.toreminddo.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RespuestaAutenticacionDTO {
    private Usuario usuario;
    private String token;
}
