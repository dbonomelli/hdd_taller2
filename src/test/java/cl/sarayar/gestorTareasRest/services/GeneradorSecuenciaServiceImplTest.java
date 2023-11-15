package cl.sarayar.gestorTareasRest.services;

import cl.sarayar.gestorTareasRest.entities.Secuencia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoOperations;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class GeneradorSecuenciaServiceImplTest {

    @Mock
    private MongoOperations mongoOperationsMock;

    private GeneradorSecuenciaService generadorSecuenciaService;
    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
        this.generadorSecuenciaService = new GeneradorSecuenciaServiceImpl(mongoOperationsMock);
    }
    /**
     * Tests that the method generadorSecuenciaOk assigns a number to the sequence, if the sequence is on a
     * new object, should return the same number of seq in the new object.
     * if object already exists, then should increase by 1 (i think not really sure :D)
     */
    @Test
    void generadorSecuenciaOk(){
        Secuencia secuenciaMock = new Secuencia();
        secuenciaMock.setId("name");
        secuenciaMock.setSeq(1);
        when(mongoOperationsMock.findAndModify(any(), any(), any())).thenReturn(secuenciaMock);
        long sec = generadorSecuenciaService.generadorSecuencia("name");
        assertEquals(1, sec);
    }
}