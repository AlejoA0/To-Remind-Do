package co.com.toreminddo.security;

import co.com.toreminddo.utils.constants.RemindConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Calendar;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    /*
        Patrón para generar un token JWT (siempre igual):
        1. Convertir la clave secreta de String a SecretKey (Keys.hmacShaKeyFor)
        2. Calcular la fecha actual (new Date())
        3. Calcular la fecha de expiración (fecha actual + tiempo de vida del token)
        4. Construir el token con el builder: de quién es (subject), cuándo se creó (issuedAt),
        cuándo expira (expiration), firmarlo con la clave (signWith)
        5. Convertir el builder al String final (compact)
    */

    public String generarToken(String email) {
        // 1. convertir la clave secreta de String a SecretKey
        SecretKey clave = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        // 2. convertir la fecha actual
        Date fechaActual = new Date();

        // 3. calcular fecha expiración
        Date fechaExpiracionToken = calcularFechaLimiteToken();

        // 4. construir el token con el builder
        return Jwts.builder()
                .subject(email) // de quién es?
                .issuedAt(fechaActual)  // cuándo se creó?
                .expiration(fechaExpiracionToken)   // cuándo expira?
                .signWith(clave)    // firmado con la clave convertida al formato necesario para firmar
                .compact(); // 5. convertir el builder al string final
    }

    public String extraerEmailDelToken(String token) {
        SecretKey clave = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        return Jwts.parser()
                .verifyWith(clave)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public Boolean validarToken(String token) {
        SecretKey clave = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        try {
            Jwts.parser()
                    .verifyWith(clave)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception error) {
            return false;
        }
    }

    private Date calcularFechaLimiteToken() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, RemindConstants.DIA_DURACION_TOKEN);
        return calendar.getTime();
    }

}
