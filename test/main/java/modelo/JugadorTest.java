/**
 *
 * @author DAVID ROBLES CANDELA
 * @author SEBASTIAN POSADA ROJAS
 * @author JHON PUENTES CASTILLO
 * @author DIRLEY PUENTES DIAZ
 * @author DAYRO RAMIREZ TRUJILLO
 *
 * @version 1.0
 */


package main.java.modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JugadorTest {
    /**
     * Prueba la inicialización y los métodos getter del constructor.
     */
    @Test
    void testConstructorYGetters() {
        String nombreEsperado = "David";
        char simboloEsperado = 'X';
        Jugador jugador = new Jugador(nombreEsperado, simboloEsperado);

        assertEquals(nombreEsperado, jugador.getNombre());
        assertEquals(simboloEsperado, jugador.getSimbolo());
        assertEquals(0, jugador.getPuntuacion());
    }

    /**
     * Prueba el método para incrementar la puntuación.
     */
    @Test
    void testIncrementarPuntuacion() {
        Jugador jugador = new Jugador("Dirley", 'O');

        jugador.incrementarPuntuacion();
        jugador.incrementarPuntuacion();

        assertEquals(2, jugador.getPuntuacion());
    }
}