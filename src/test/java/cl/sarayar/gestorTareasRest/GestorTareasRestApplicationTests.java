package cl.sarayar.gestorTareasRest;

import cl.sarayar.gestorTareasRest.entities.Usuario;
import cl.sarayar.gestorTareasRest.services.UsuariosService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;


@ActiveProfiles("test")
class GestorTareasRestApplicationTests {

	@Mock
	private UsuariosService us;

	@BeforeEach
	void init(){
		MockitoAnnotations.openMocks(this);

	}
	@Test
	void contextLoads() throws Exception{
		//Creamos el usuario admin por defecto
		Usuario admin = new Usuario();
		admin.setNombre("Fake");
		admin.setCorreo("fake@skynux.cl");
		when(us.getAll()).thenReturn(Arrays.asList(admin));
		GestorTareasRestApplication ga = new GestorTareasRestApplication(us);
		assertDoesNotThrow(()-> {
			ga.run(new String[]{});
		});

	}




}
