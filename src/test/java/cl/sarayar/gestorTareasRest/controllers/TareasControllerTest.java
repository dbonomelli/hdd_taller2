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

    @Test
    void testFindAllOk(){
        List<Tarea> tareasMock = Arrays.asList(new Tarea(), new Tarea());
        when(tareasServiceMock.findAll()).thenReturn(tareasMock);
        List<Tarea> tareas = tareasController.getAll();
        assertEquals(2, tareas.size());
    }

    @Test
    void testPostOk(){
        Tarea tareaMock = new Tarea();
        when(tareasServiceMock.save(tareaMock)).thenReturn(tareaMock);
        ResponseEntity<?> tarea = tareasController.save(tareaMock);
        assertEquals(HttpStatus.OK, tarea.getStatusCode());
    }

    @Test
    void testUpdatePostOk(){
        Tarea tareaMock = new Tarea();
        tareaMock.setId("1");
        when(tareasServiceMock.findById(anyString())).thenReturn(tareaMock);
        when(tareasServiceMock.save(any(Tarea.class))).thenReturn(tareaMock);
        ResponseEntity<?> tarea = tareasController.update(tareaMock);
        assertEquals(HttpStatus.OK, tarea.getStatusCode());
    }

    @Test
    void testUpdatePostNotOk(){
        Tarea tareaMock = new Tarea();
        when(tareasServiceMock.findById(anyString())).thenReturn(null);
        ResponseEntity<?> tarea = tareasController.update(tareaMock);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, tarea.getStatusCode());
    }

    @Test
    void testDeleteOk(){
        when(tareasServiceMock.remove(anyString())).thenReturn(Boolean.TRUE);
        ResponseEntity<?> tarea = tareasController.delete("1");
        assertEquals(HttpStatus.OK, tarea.getStatusCode());
    }

    @Test
    void testDeleteNotOk(){
        when(tareasServiceMock.remove(anyString())).thenThrow(new NullPointerException());
        ResponseEntity<?> tarea = tareasController.delete("1");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, tarea.getStatusCode());
    }

}