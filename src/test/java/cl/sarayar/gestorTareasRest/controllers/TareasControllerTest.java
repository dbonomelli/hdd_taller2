package cl.sarayar.gestorTareasRest.controllers;

import cl.sarayar.gestorTareasRest.entities.Tarea;
import cl.sarayar.gestorTareasRest.services.TareasService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TareasControllerTest {

    @Mock
    private TareasService tareasServiceMock;

    private TareasController tareasController;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
        this.tareasController = new TareasController(tareasServiceMock);
    }

    /**
     * Tests that the find all method in the controller and returns the same size of list
     * as the controller.
     */
    @Test
    void testFindAllOk(){
        List<Tarea> tareasMock = Arrays.asList(new Tarea(), new Tarea());
        when(tareasServiceMock.findAll()).thenReturn(tareasMock);
        List<Tarea> tareas = tareasController.getAll();
        assertEquals(2, tareas.size());
    }
    /**
     * Tests that the post method in the controller works OK and returns a status of 200.
     */
    @Test
    void testPostOk(){
        Tarea tareaMock = new Tarea();
        when(tareasServiceMock.save(tareaMock)).thenReturn(tareaMock);
        ResponseEntity<?> tarea = tareasController.save(tareaMock);
        assertEquals(HttpStatus.OK, tarea.getStatusCode());
    }
    /**
     * Tests that the update method in the controller works OK and returns a status of 200.
     */
    @Test
    void testUpdatePostOk(){
        Tarea tareaMock = new Tarea();
        tareaMock.setId("1");
        when(tareasServiceMock.findById(anyString())).thenReturn(tareaMock);
        when(tareasServiceMock.save(any(Tarea.class))).thenReturn(tareaMock);
        ResponseEntity<?> tarea = tareasController.update(tareaMock);
        assertEquals(HttpStatus.OK, tarea.getStatusCode());
    }
    /**
     * Tests that the update method does not execute properly and returns a status code of 500.
     */
    @Test
    void testUpdatePostNotOk(){
        Tarea tareaMock = new Tarea();
        when(tareasServiceMock.findById(anyString())).thenReturn(null);
        ResponseEntity<?> tarea = tareasController.update(tareaMock);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, tarea.getStatusCode());
    }
    /**
     * Tests that the delete method executes properly and returns a status code of 200.
     * Condition is based on a boolean value, if true it should go through.
     */
    @Test
    void testDeleteOk(){
        when(tareasServiceMock.remove(anyString())).thenReturn(Boolean.TRUE);
        ResponseEntity<?> tarea = tareasController.delete("1");
        assertEquals(HttpStatus.OK, tarea.getStatusCode());
    }
    /**
     * Tests that the delete method does not execute properly and returns a status code of 500.
     * Condition is based on a random exception that triggers the Exception line.
     */
    @Test
    void testDeleteNotOk(){
        when(tareasServiceMock.remove(anyString())).thenThrow(new NullPointerException());
        ResponseEntity<?> tarea = tareasController.delete("1");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, tarea.getStatusCode());
    }

}