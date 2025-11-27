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

public class Juego {
    private Jugador[] jugadores;
    private Jugador turnoActual;
    private char[][] tablero; // Tablero 3x3: [fila][columna]
    private Pregunta preguntaActual;
    private int movimientosTotales;

    // Constante para el tamaño del tablero
    private static final int TAMANO_TABLERO = 3;

    /**
     * Constructor que inicializa el juego.
     * @param nombreJ1 Nombre del primer jugador.
     * @param nombreJ2 Nombre del segundo jugador.
     */
    public Juego(String nombreJ1, String nombreJ2) {
        this.jugadores = new Jugador[2];
        // Player 1 siempre usa 'X', Player 2 siempre usa 'O'
        this.jugadores[0] = new Jugador(nombreJ1, 'X');
        this.jugadores[1] = new Jugador(nombreJ2, 'O');

        reiniciarJuego();
    }

    /**
     * Inicializa o reinicia el tablero, el turno y el contador.
     */
    public void reiniciarJuego() {
        this.tablero = new char[TAMANO_TABLERO][TAMANO_TABLERO];
        // Rellenar el tablero con espacios vacíos (' ')
        for (int i = 0; i < TAMANO_TABLERO; i++) {
            for (int j = 0; j < TAMANO_TABLERO; j++) {
                this.tablero[i][j] = ' ';
            }
        }
        this.turnoActual = this.jugadores[0]; // Player 1 (X) siempre empieza
        this.movimientosTotales = 0;
        // Genera la primera pregunta. (El nivel se debe establecer desde el controlador/vista)
        generarNuevaPregunta("Aprendiz");
    }

    /**
     * Genera una nueva pregunta matemática y la asigna a preguntaActual.
     * @param nivel El nivel de dificultad para la pregunta.
     */
    public void generarNuevaPregunta(String nivel) {
        this.preguntaActual = new Pregunta();
        this.preguntaActual.generarPregunta(nivel);
    }

    /**
     * Realiza una jugada en el tablero si la casilla está vacía.
     * @param fila Fila del tablero (0, 1, 2).
     * @param columna Columna del tablero (0, 1, 2).
     * @return true si la jugada fue exitosa, false si la casilla ya estaba ocupada.
     */
    public boolean hacerJugada(int fila, int columna) {
        if (fila >= 0 && fila < TAMANO_TABLERO && columna >= 0 && columna < TAMANO_TABLERO &&
                tablero[fila][columna] == ' ') {

            tablero[fila][columna] = turnoActual.getSimbolo();
            movimientosTotales++;
            return true;
        }
        return false;
    }

    /**
     * Cambia el turno al otro jugador.
     */
    public void cambiarTurno() {
        if (turnoActual == jugadores[0]) {
            turnoActual = jugadores[1];
        } else {
            turnoActual = jugadores[0];
        }
    }

    /**
     * Verifica si el último movimiento ha resultado en una línea de 3.
     * Si hay victoria, incrementa la puntuación del ganador.
     * @return true si el jugador actual ganó.
     */
    public boolean verificarVictoria() {
        char simbolo = turnoActual.getSimbolo();

        // 1. Verificar filas y columnas
        for (int i = 0; i < TAMANO_TABLERO; i++) {
            // Verificar fila i
            if (tablero[i][0] == simbolo && tablero[i][1] == simbolo && tablero[i][2] == simbolo) {
                turnoActual.incrementarPuntuacion();
                return true;
            }
            // Verificar columna i
            if (tablero[0][i] == simbolo && tablero[1][i] == simbolo && tablero[2][i] == simbolo) {
                turnoActual.incrementarPuntuacion();
                return true;
            }
        }

        // 2. Verificar diagonales
        // Diagonal principal (\)
        if (tablero[0][0] == simbolo && tablero[1][1] == simbolo && tablero[2][2] == simbolo) {
            turnoActual.incrementarPuntuacion();
            return true;
        }
        // Diagonal secundaria (/)
        if (tablero[0][2] == simbolo && tablero[1][1] == simbolo && tablero[2][0] == simbolo) {
            turnoActual.incrementarPuntuacion();
            return true;
        }

        return false;
    }

    /**
     * Verifica si el tablero está lleno sin un ganador (Empate).
     * @return true si es empate.
     */
    public boolean verificarEmpate() {
        return movimientosTotales == 9 && !verificarVictoria();
    }

    // --- Getters ---

    /** Obtiene el jugador cuyo turno es actual. */
    public Jugador getTurnoActual() {
        return turnoActual;
    }

    /** Obtiene la pregunta matemática actual. */
    public Pregunta getPreguntaActual() {
        return preguntaActual;
    }

    /** Obtiene la matriz del tablero. */
    public char[][] getTablero() {
        return tablero;
    }

    /** Obtiene ambos jugadores. */
    public Jugador[] getJugadores() {
        return jugadores;
    }
}
