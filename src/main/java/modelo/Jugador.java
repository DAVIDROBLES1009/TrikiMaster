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

public class Jugador {
    private String nombre;
    private char simbolo; // 'X' o 'O'
    private int puntuacion; // Contador de partidas ganadas

    /**
     * Constructor para inicializar un nuevo jugador.
     * @param nombre El nombre del jugador.
     * @param simbolo El símbolo que usará en el tablero ('X' o 'O').
     */
    public Jugador(String nombre, char simbolo) {
        this.nombre = nombre;
        this.simbolo = simbolo;
        this.puntuacion = 0;
    }

    /**
     * Incrementa la puntuación del jugador después de ganar una partida.
     */
    public void incrementarPuntuacion() {
        this.puntuacion++;
    }

    // --- Getters ---

    /** Obtiene el nombre del jugador. */
    public String getNombre() {
        return nombre;
    }

    /** Obtiene el símbolo del jugador ('X' o 'O'). */
    public char getSimbolo() {
        return simbolo;
    }

    /** Obtiene la puntuación (número de victorias) del jugador. */
    public int getPuntuacion() {
        return puntuacion;
    }
}
