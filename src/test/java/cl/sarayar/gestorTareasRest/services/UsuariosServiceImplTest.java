package cl.sarayar.gestorTareasRest.services;

import cl.sarayar.gestorTareasRest.config.auth.UserDetailsImpl;
import cl.sarayar.gestorTareasRest.entities.Usuario;
import cl.sarayar.gestorTareasRest.repositories.UsuariosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class UsuariosServiceImplTest {

    @Mock
    private UsuariosRepository usuariosRepositoryMock;

    private UsuariosService usuariosService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        this.usuariosService = new UsuariosServiceImpl(usuariosRepositoryMock);
    }

    /**
     * Tests the save method from the user repo is working correctly, should return a user.
     */
    @Test
    void testSaveUserOk() {
        Usuario mockUser = new Usuario();
        when(usuariosRepositoryMock.save(any(Usuario.class))).thenReturn(mockUser);
        Usuario result = usuariosService.save(mockUser);
        assertEquals(mockUser, result);

    }

    @Test
    void testFindUserListOk() {
        List<Usuario> mockUsers = Arrays.asList(new Usuario(), new Usuario());
        when(usuariosRepositoryMock.findAll()).thenReturn(mockUsers);

        List<Usuario> usuarios = usuariosService.getAll();
        assertNotNull(usuarios);
        assertEquals(2, usuarios.size());

    }

    @Test
    void testFindUserByEmailOk() {
        Usuario mockUser = new Usuario();
        when(usuariosRepositoryMock.findByCorreo(anyString())).thenReturn(Optional.of(mockUser));
        Usuario user = usuariosService.findByCorreo("some@usm.cl");
        assertEquals(mockUser, user);
    }

    @Test
    void testFindUserByEmailNotOk(){
        when(usuariosRepositoryMock.findByCorreo(anyString())).thenReturn(Optional.empty());
        Usuario user = usuariosService.findByCorreo("some@usm.cl");
        assertNull(user);
    }

    @Test
    void testFindUserByIdOk() {
        when(usuariosRepositoryMock.findById(anyString())).thenReturn(Optional.empty());
        Usuario user = usuariosService.findById("1");
        assertNull(user);
    }

    @Test
    void testFindUserByIdNotOk(){
        Usuario mock = new Usuario();
        when(usuariosRepositoryMock.findById(anyString())).thenReturn(Optional.of(mock));
        Usuario usuario = usuariosService.findById("1");
        assertNull(usuario.getId());
    }

    @Test
    void testLoadUserByUsernameOk() {
        Usuario mockUser = new Usuario();
        when(usuariosRepositoryMock.findByCorreo(anyString())).thenReturn(Optional.of(mockUser));
        UserDetailsImpl userDetails = (UserDetailsImpl) usuariosService.loadUserByUsername("some@usm.cl");
        assertEquals(mockUser, userDetails.getUsuario());

    }

    @Test
    void testLoadUserByUsernameNotOk() throws UsernameNotFoundException{
        when(usuariosRepositoryMock.findByCorreo(anyString())).thenReturn(Optional.empty());
        UserDetailsImpl userDetails = (UserDetailsImpl) usuariosService.loadUserByUsername("");
        assertNull(userDetails);

    }

    @Test
    void testExistsByEmailOk(){
        when(usuariosRepositoryMock.existsByCorreo(anyString())).thenReturn(Boolean.TRUE);
        boolean exists = usuariosService.existsByCorreo("some@some.cl");
        assertTrue(exists);
    }

}