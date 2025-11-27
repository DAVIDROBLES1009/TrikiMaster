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

/**
 * Clase que gestiona la lógica principal del juego TrikiMaster (Triki Matemático).
 * Incluye el tablero, los jugadores, el turno y la gestión de preguntas.
 */
public class Juego {
    private Jugador[] jugadores;
    private Jugador turnoActual;
    private char[][] tablero; // Tablero 3x3: [fila][columna]
    private Pregunta preguntaActual;
    private int movimientosTotales;

    // FIX APLICADO: Atributo para almacenar el nivel de dificultad seleccionado
    private String nivelDificultad;

    // Constante para el tamaño del tablero
    private static final int TAMANO_TABLERO = 3;

    /**
     * Constructor que inicializa el juego.
     * @param nombreJ1 Nombre del primer jugador.
     * @param nombreJ2 Nombre del segundo jugador.
     */
    public Juego(String nombreJ1, String nombreJ2) {
        this.jugadores = new Jugador[2];
        // Jugador 1 siempre usa 'X', Jugador 2 siempre usa 'O'
        this.jugadores[0] = new Jugador(nombreJ1, 'X');
        this.jugadores[1] = new Jugador(nombreJ2, 'O');

        reiniciarJuego();
    }

    /**
     * Inicializa o reinicia el tablero, el turno y el contador.
     */
    public void reiniciarJuego() {
        this.tablero = new char[TAMANO_TABLERO][TAMANO_TABLERO];
        for (int i = 0; i < TAMANO_TABLERO; i++) {
            for (int j = 0; j < TAMANO_TABLERO; j++) {
                this.tablero[i][j] = ' '; // Inicializar todas las casillas a vacío
            }
        }
        this.turnoActual = jugadores[0]; // Siempre comienza el jugador 1 ('X')
        this.movimientosTotales = 0;
        this.preguntaActual = new Pregunta(); // Inicializar la pregunta (sin generar aún)
    }

    // FIX APLICADO: Método setter para el nivel de dificultad, requerido por GameController.
    /**
     * Establece el nivel de dificultad para la generación de preguntas.
     * @param nivel El nivel de dificultad ("Aprendiz", "Explorador", "Estratega", etc.).
     */
    public void setNivelDificultad(String nivel) {
        this.nivelDificultad = nivel;
    }

    /**
     * Genera una nueva pregunta matemática para el turno actual, usando el nivel de dificultad.
     */
    public void generarNuevaPregunta() {
        // Llama al método de Pregunta pasándole el nivel guardado
        this.preguntaActual.generarPregunta(this.nivelDificultad);
    }

    /**
     * Intenta realizar una jugada en la posición especificada.
     * @param fila Fila del tablero (0-2).
     * @param columna Columna del tablero (0-2).
     * @return true si la jugada fue válida y se realizó, false en caso contrario.
     */
    public boolean hacerJugada(int fila, int columna) {
        if (validarJugada(fila, columna)) {
            // Realizar la jugada
            tablero[fila][columna] = turnoActual.getSimbolo();
            movimientosTotales++;
            return true;
        }
        return false;
    }

    /**
     * Verifica si la casilla está dentro del tablero y está vacía.
     * @param fila Fila del tablero (0-2).
     * @param columna Columna del tablero (0-2).
     * @return true si la jugada es válida, false en caso contrario.
     */
    public boolean validarJugada(int fila, int columna) {
        // Validación de límites del tablero
        if (fila < 0 || fila >= TAMANO_TABLERO || columna < 0 || columna >= TAMANO_TABLERO) {
            return false;
        }
        // Validación de casilla vacía
        return tablero[fila][columna] == ' ';
    }

    /**
     * Cambia el turno al otro jugador.
     */
    public void cambiarTurno() {
        // Si el turno actual es el jugador 1 (índice 0), cambia al jugador 2 (índice 1)
        if (turnoActual == jugadores[0]) {
            turnoActual = jugadores[1];
        } else {
            // Si no, cambia de vuelta al jugador 1
            turnoActual = jugadores[0];
        }
    }

    /**
     * Verifica si el jugador actual ha ganado la partida.
     * Si gana, incrementa su puntuación.
     * @return true si hay victoria, false en caso contrario.
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
        // Si se han hecho 9 movimientos (tablero lleno) y no hay victoria
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

    /** Obtiene el arreglo de jugadores. */
    public Jugador[] getJugadores() {
        return jugadores;
    }

    /** Obtiene el nivel de dificultad actual. */
    public String getNivelDificultad() {
        return nivelDificultad;
    }

    /** Obtiene el número de movimientos totales realizados. */
    public int getMovimientosTotales() {
        return movimientosTotales;
    }
}
