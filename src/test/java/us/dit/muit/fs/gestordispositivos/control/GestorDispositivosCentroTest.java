package us.dit.muit.fs.gestordispositivos.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import us.dit.muit.fs.gestordispositivos.modelo.Dispositivo;
import us.dit.muit.fs.gestordispositivos.modelo.Dispositivo.TipoDispositivo;

class GestorDispositivosCentroTest {

    @Test
    void altaDispositivoDebeLanzarExcepcionSiNumeroSerieYaExiste() {
        DispositivoDAO dispositivoDAO = mock(DispositivoDAO.class);
        GestorDispositivosCentro gestor = new GestorDispositivosCentro("CENTRO-1", dispositivoDAO);

        String numeroSerie = "NS-001";
        Dispositivo dispositivoExistente = new Dispositivo(
                TipoDispositivo.RESPIRADOR,
                "Respirador antiguo",
                numeroSerie
        );

        when(dispositivoDAO.getDispositivoByNumSerie(numeroSerie)).thenReturn(dispositivoExistente);

        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> gestor.altaDispositivo("Respirador nuevo", TipoDispositivo.RESPIRADOR, numeroSerie)
        );

        assertEquals(
                "Dispositivo con Numero de Serie " + numeroSerie + " ya existe",
                excepcion.getMessage()
        );

        verify(dispositivoDAO, never()).addDispositivo(org.mockito.ArgumentMatchers.any(Dispositivo.class));
    }
}
