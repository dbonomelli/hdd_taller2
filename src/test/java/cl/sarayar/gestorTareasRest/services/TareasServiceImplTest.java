package cl.sarayar.gestorTareasRest.services;

import cl.sarayar.gestorTareasRest.entities.Tarea;
import cl.sarayar.gestorTareasRest.repositories.TareasRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class TareasServiceImplTest {

    @Mock
    private TareasRepository tareasRepositoryMock;

    private TareasService tareasService;


    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
        this.tareasService = new TareasServiceImpl(tareasRepositoryMock);
    }

    @Test
    void testSaveOk(){
        Tarea tareaMock = new Tarea();
        when(tareasRepositoryMock.save(any(Tarea.class))).thenReturn(tareaMock);
        Tarea tarea = tareasService.save(tareaMock);
        assertEquals(tareaMock, tarea);
    }

    @Test
    void testFindAllOk(){
        List<Tarea> tareasMock = Arrays.asList(new Tarea(), new Tarea());
        when(tareasRepositoryMock.findAll()).thenReturn(tareasMock);
        List<Tarea> tareas = tareasService.findAll();
        assertEquals(2, tareas.size());
    }

    @Test
    void testRemoveOk(){
        boolean successfulDelete = tareasService.remove("1");
        assertTrue(successfulDelete);
    }

    @Test
    void testRemoveNotOk(){
        doThrow(new IllegalArgumentException()).when(tareasRepositoryMock).deleteById("nonexistance");
        boolean unsuccessfulDelete = tareasService.remove("nonexistance");
        assertFalse(unsuccessfulDelete);
    }

    @Test
    void testFindByIdOk(){
        Tarea tareaMock = new Tarea();
        when(tareasRepositoryMock.findById(anyString())).thenReturn(Optional.of(tareaMock));
        Tarea tarea = tareasService.findById("1");
        assertEquals(tareaMock, tarea);
    }

    @Test
    void testFindByIdNotOk(){
        when(tareasRepositoryMock.findById(anyString())).thenReturn(Optional.empty());
        Tarea tarea = tareasService.findById("1");
        assertNull(tarea);
    }

}