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

class JuegoTest {
    /**
     * Prueba que el tablero se inicialice vacío y el turno inicie con el Jugador 1 ('X').
     */
    @Test
    void testInicializacionJuego() {
        Juego juego = new Juego("P1", "P2");

        assertEquals('X', juego.getTurnoActual().getSimbolo(), "El turno debe iniciar con 'X'.");

        // Verificar tablero vacío (' ')
        char[][] tablero = juego.getTablero();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(' ', tablero[i][j], "El tablero debe estar vacío al inicio.");
            }
        }
    }

    /**
     * Prueba las condiciones de victoria (Tres en raya en una fila).
     */
    @Test
    void testVerificarVictoriaFila() {
        Juego juego = new Juego("P1", "P2");

        // Simular jugadas del J1 ('X') para ganar en la Fila 0
        juego.hacerJugada(0, 0); // X
        juego.cambiarTurno(); juego.hacerJugada(1, 0); // O (defensa)
        juego.cambiarTurno(); juego.hacerJugada(0, 1); // X
        juego.cambiarTurno(); juego.hacerJugada(2, 0); // O (defensa)
        juego.cambiarTurno(); juego.hacerJugada(0, 2); // X - Victoria

        assertTrue(juego.verificarVictoria(), "Debe haber victoria en la fila 0.");
        assertEquals(1, juego.getJugadores()[0].getPuntuacion(), "El ganador debe tener 1 punto.");
    }

    /**
     * Prueba la condición de empate.
     */
    @Test
    void testVerificarEmpate() {
        Juego juego = new Juego("P1", "P2");

        // Simulación de un tablero lleno sin victoria
        // X | O | X
        // O | X | X
        // O | X | O

        // 1. X (0,0), 2. O (0,1), 3. X (0,2)
        juego.hacerJugada(0, 0); juego.cambiarTurno();
        juego.hacerJugada(0, 1); juego.cambiarTurno();
        juego.hacerJugada(0, 2); juego.cambiarTurno();

        // 4. O (1,0), 5. X (1,1), 6. O (2,2)
        juego.hacerJugada(1, 0); juego.cambiarTurno();
        juego.hacerJugada(1, 1); juego.cambiarTurno();
        juego.hacerJugada(2, 2); juego.cambiarTurno(); // Jugada de O

        // 7. X (1,2), 8. O (2,0), 9. X (2,1) - La última jugada debe ser de X
        juego.hacerJugada(1, 2); juego.cambiarTurno();
        juego.hacerJugada(2, 0); juego.cambiarTurno();
        // El último movimiento es X
        juego.hacerJugada(2, 1);

        assertFalse(juego.verificarVictoria(), "No debe haber victoria.");
        assertTrue(juego.verificarEmpate(), "El juego debe terminar en empate.");
    }
}