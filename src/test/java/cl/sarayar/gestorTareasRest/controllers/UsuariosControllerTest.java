package cl.sarayar.gestorTareasRest.controllers;

import cl.sarayar.gestorTareasRest.entities.Usuario;
import cl.sarayar.gestorTareasRest.services.UsuariosService;
import cl.sarayar.gestorTareasRest.utils.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class UsuariosControllerTest {

    @Mock
    private UsuariosService usuariosServiceMock;
    @Mock
    private JwtUtils jwtUtilsMock;

    private UsuariosController usuariosController;


    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
        this.usuariosController = new UsuariosController(usuariosServiceMock, jwtUtilsMock);
    }
    /**
     * Tests that the auth method executes properly and returns a status code of 200.
     */
    @Test
    void testAuthenticateUserOk(){
        Usuario userMock = new Usuario();
        ResponseEntity<?> login = usuariosController.authenticateUser(userMock);
        assertEquals(HttpStatus.OK, login.getStatusCode());
    }
    /**
     * Tests that the register method executes properly and returns a status code of 200.
     */
    @Test
    void testRegisterUserOk(){
        Usuario userMock = new Usuario();
        when(usuariosServiceMock.existsByCorreo(anyString())).thenReturn(Boolean.FALSE);
        when(usuariosServiceMock.save(any(Usuario.class))).thenReturn(userMock);
        ResponseEntity<?> register = usuariosController.registerUser(userMock);
        assertEquals(HttpStatus.OK, register.getStatusCode());

    }
    /**
     * Tests that the register does not execute properly and returns a status code of 400.
     * Condition applies that if the exists method is true, then the email is duplicated,
     * therefore it should not execute and should return a http status code.
     */
    @Test
    void testRegisterUserDuplicated(){
        Usuario userMock = new Usuario("1", "", "some@usm.cl", "", 1);
        when(usuariosServiceMock.existsByCorreo(anyString())).thenReturn(Boolean.TRUE);
        when(usuariosServiceMock.save(any(Usuario.class))).thenReturn(userMock);
        ResponseEntity<?> register = usuariosController.registerUser(userMock);
        assertEquals(HttpStatus.BAD_REQUEST , register.getStatusCode());
    }
    /**
     * Tests that the update method executes properly and returns a status code of 200.
     * Condition applies only if findByCorreo is != than null or is not equals to new id.
     */
    @Test
    void testUpdateUserOk(){
        Usuario updateMock = new Usuario("1", "namenew", "somenew@usm.cl", "55", 1);
        when(usuariosServiceMock.findById(anyString())).thenReturn(updateMock);
        when(usuariosServiceMock.findByCorreo(anyString())).thenReturn(null);
        ResponseEntity<?> update = usuariosController.actualizarUsuario(updateMock);
        assertEquals(HttpStatus.OK, update.getStatusCode());
    }
    /**
     * Tests that the update method executes properly and returns a status code of 400.
     * Condition applies if the value found in the emailMock turns out to be a valid object and not
     * a null one.
     */
    @Test
    void testUpdateUserDuplicate(){
        Usuario updateMock = new Usuario("1", "namenew", "somenew@usm.cl", "55", 1);
        Usuario emailMock = new Usuario("2", "", "", "", 1);
        when(usuariosServiceMock.findById(anyString())).thenReturn(updateMock);
        when(usuariosServiceMock.findByCorreo(anyString())).thenReturn(emailMock);
        ResponseEntity<?> update = usuariosController.actualizarUsuario(updateMock);
        assertEquals(HttpStatus.BAD_REQUEST, update.getStatusCode());
    }
    /**
     * Tests that the getAll method executes properly and returns the same amount of objects
     * added in the mock list, as in the service controller.
     */
    @Test
    void testGetAll(){
        List<Usuario> usuariosMock = Arrays.asList(new Usuario(), new Usuario());
        when(usuariosServiceMock.getAll()).thenReturn(usuariosMock);
        List<Usuario> users = usuariosController.getAll();
        assertEquals(2, users.size());
    }


}