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
    /**
     * Tests if the find by id works ok, should return a list the same size as the mockUser List.
     */
    @Test
    void testUserListOk() {
        List<Usuario> mockUsers = Arrays.asList(new Usuario(), new Usuario());
        when(usuariosRepositoryMock.findAll()).thenReturn(mockUsers);

        List<Usuario> usuarios = usuariosService.getAll();
        assertNotNull(usuarios);
        assertEquals(2, usuarios.size());

    }
    /**
     * Tests if the find by email works ok, should return the same mock object if found in an Optional.
     */
    @Test
    void testFindUserByEmailOk() {
        Usuario mockUser = new Usuario();
        when(usuariosRepositoryMock.findByCorreo(anyString())).thenReturn(Optional.of(mockUser));
        Usuario user = usuariosService.findByCorreo("some@usm.cl");
        assertEquals(mockUser, user);
    }
    /**
     * Tests if the find by email is not found, should return the null if the object is not found.
     */
    @Test
    void testFindUserByEmailNotOk(){
        when(usuariosRepositoryMock.findByCorreo(anyString())).thenReturn(Optional.empty());
        Usuario user = usuariosService.findByCorreo("some@usm.cl");
        assertNull(user);
    }
    /**
     * Tests if the find by id works ok, should return the same mock object if found.
     */
    @Test
    void testFindUserByIdOk() {
        when(usuariosRepositoryMock.findById(anyString())).thenReturn(Optional.empty());
        Usuario user = usuariosService.findById("1");
        assertNull(user);
    }
    /**
     * Tests if the find by id is not found, should return the null if the object is not found.
     */
    @Test
    void testFindUserByIdNotOk(){
        Usuario mock = new Usuario();
        when(usuariosRepositoryMock.findById(anyString())).thenReturn(Optional.of(mock));
        Usuario usuario = usuariosService.findById("1");
        assertNull(usuario.getId());
    }
    /**
     * Tests if the user details load OK by providing a mockUser and some username, if found, should return
     * the object inside a casted userDetails object.
     */
    @Test
    void testLoadUserByUsernameOk() {
        Usuario mockUser = new Usuario();
        when(usuariosRepositoryMock.findByCorreo(anyString())).thenReturn(Optional.of(mockUser));
        UserDetailsImpl userDetails = (UserDetailsImpl) usuariosService.loadUserByUsername("some@usm.cl");
        assertEquals(mockUser, userDetails.getUsuario());

    }
    /**
     * Tests if the user details load is not loaded by providing a mockUser and some username,
     * if not found, should return a null inside a casted userDetails object.
     */
    @Test
    void testLoadUserByUsernameNotOk() throws UsernameNotFoundException{
        when(usuariosRepositoryMock.findByCorreo(anyString())).thenReturn(Optional.empty());
        UserDetailsImpl userDetails = (UserDetailsImpl) usuariosService.loadUserByUsername("");
        assertNull(userDetails);

    }
    /**
     * Tests if you successfully find an email, if its found then exists turns true.
     */
    @Test
    void testExistsByEmailOk(){
        when(usuariosRepositoryMock.existsByCorreo(anyString())).thenReturn(Boolean.TRUE);
        boolean exists = usuariosService.existsByCorreo("some@some.cl");
        assertTrue(exists);
    }

}