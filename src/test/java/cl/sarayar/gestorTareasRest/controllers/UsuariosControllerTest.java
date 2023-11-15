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

    @Test
    void testAuthenticateUserOk(){
        Usuario userMock = new Usuario();
        ResponseEntity<?> login = usuariosController.authenticateUser(userMock);
        assertEquals(200, login.getStatusCodeValue());
    }

    @Test
    void testRegisterUserOk(){
        Usuario userMock = new Usuario();
        when(usuariosServiceMock.existsByCorreo(anyString())).thenReturn(Boolean.FALSE);
        when(usuariosServiceMock.save(any(Usuario.class))).thenReturn(userMock);
        ResponseEntity<?> register = usuariosController.registerUser(userMock);
        assertEquals(HttpStatus.OK, register.getStatusCode());

    }

    @Test
    void testRegisterUserDuplicated(){
        Usuario userMock = new Usuario("1", "", "some@usm.cl", "", 1);
        when(usuariosServiceMock.existsByCorreo(anyString())).thenReturn(Boolean.TRUE);
        when(usuariosServiceMock.save(any(Usuario.class))).thenReturn(userMock);
        ResponseEntity<?> register = usuariosController.registerUser(userMock);
        assertEquals(HttpStatus.BAD_REQUEST , register.getStatusCode());
    }

    @Test
    void testUpdateUserOk(){
        Usuario updateMock = new Usuario("1", "namenew", "somenew@usm.cl", "55", 1);
        when(usuariosServiceMock.findById(anyString())).thenReturn(updateMock);
        when(usuariosServiceMock.findByCorreo(anyString())).thenReturn(null);
        ResponseEntity<?> update = usuariosController.actualizarUsuario(updateMock);
        assertEquals(HttpStatus.OK, update.getStatusCode());
    }

    @Test
    void testUpdateUserDuplicate(){
        Usuario updateMock = new Usuario("1", "namenew", "somenew@usm.cl", "55", 1);
        Usuario emailMock = new Usuario("2", "", "", "", 1);
        when(usuariosServiceMock.findById(anyString())).thenReturn(updateMock);
        when(usuariosServiceMock.findByCorreo(anyString())).thenReturn(emailMock);
        ResponseEntity<?> update = usuariosController.actualizarUsuario(updateMock);
        assertEquals(HttpStatus.BAD_REQUEST, update.getStatusCode());
    }

    @Test
    void testGetAll(){
        List<Usuario> usuariosMock = Arrays.asList(new Usuario(), new Usuario());
        when(usuariosServiceMock.getAll()).thenReturn(usuariosMock);
        List<Usuario> users = usuariosController.getAll();
        assertEquals(2, users.size());
    }


}