package cl.sarayar.gestorTareasRest.config.auth.dto;

import cl.sarayar.gestorTareasRest.entities.Usuario;
import lombok.*;

@Getter
@Setter
@ToString
@Generated
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {

	private String token;
	private Usuario usuario;
}
