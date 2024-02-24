import codigo.Jugador;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ModeloDatosTest {

    @Mock
    private Connection mockConnection;
    @Mock
    private Statement mockStatement;
    @Mock
    private ResultSet mockResultSet;

    @Test
    void testActualizarJugador() throws Exception {
        MockitoAnnotations.openMocks(this);
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);

        int votosIniciales = 1;
        when(mockStatement.executeUpdate(anyString())).thenReturn(1);
        when(mockResultSet.getInt("votos")).thenReturn(votosIniciales + 1);

        ModeloDatos modeloDatos = new ModeloDatos();
        modeloDatos.setConMock(mockConnection);

        String nombre = "Rudy";
        modeloDatos.actualizarJugador(nombre);
        List<Jugador> jugadores = modeloDatos.obtenerJugadores();
        if (jugadores != null && !jugadores.isEmpty()) {
            Jugador jugador = jugadores.get(0);
            assertEquals(nombre, jugador.getNombre());
            assertEquals(votosIniciales + 1, jugador.getVotos());
        } else
            fail("Fallo: La lista de jugadores está vacía.");
    }

    @Test
    void testExisteJugador() {
        System.out.println("Prueba de existeJugador");
        String nombre = "";
        ModeloDatos instance = new ModeloDatos();
        boolean expResult = false;

        boolean result = instance.existeJugador(nombre);
        assertEquals(expResult, result);

        // fail("Fallo forzado.");
    }
}
