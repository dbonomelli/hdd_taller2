package cl.sarayar.gestorTareasRest.listeners;

import cl.sarayar.gestorTareasRest.entities.Tarea;
import cl.sarayar.gestorTareasRest.services.GeneradorSecuenciaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class TareasModelListenerTest {

    @Mock
    private GeneradorSecuenciaService generadorSecuenciaServiceMock;

    private TareasModelListener tareasModelListener;
    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
        this.tareasModelListener = new TareasModelListener(generadorSecuenciaServiceMock);
    }

    @Test
    void testOnBeforeConvertOk(){
        Tarea tareaMock = new Tarea();
        tareaMock.setIdentificador(0);
        when(generadorSecuenciaServiceMock.generadorSecuencia(any())).thenReturn(1L);
        tareasModelListener.onBeforeConvert(new BeforeConvertEvent<>(tareaMock, "empty"));
        assertEquals(1L, tareaMock.getIdentificador());
    }
}