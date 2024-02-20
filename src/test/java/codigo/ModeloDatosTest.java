package codigo;

import codigo.ModeloDatos;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

 class ModeloDatosTest {

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
