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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Pregunta {
    private int operando1;
    private int operando2;
    private char operador; // '+', '-', '*', '/'
    private int respuestaCorrecta;
    private List<Integer> opcionesDeRespuesta;
    private static final Random RANDOM = new Random();
    private static final int NUMERO_OPCIONES = 4; // Total de 4 opciones (1 correcta + 3 distractores)

    /**
     * Constructor que inicializa una nueva pregunta.
     */
    public Pregunta() {
        this.opcionesDeRespuesta = new ArrayList<>();
    }

    /**
     * Genera una pregunta (operación matemática) basada en el nivel de dificultad.
     * @param nivel El nivel de dificultad ("Aprendiz", "Explorador", "Estratega", etc.).
     */
    public void generarPregunta(String nivel) {
        int rangoMax = 0;
        char[] operadoresDisponibles;

        // Define el rango de números y operadores según el nivel [cite: 85]
        switch (nivel) {
            case "Aprendiz": // Suma y resta con números pequeños [cite: 85]
                rangoMax = 10;
                operadoresDisponibles = new char[]{'+', '-'};
                break;
            case "Explorador": // Suma, resta y multiplicación [cite: 85]
                rangoMax = 20;
                operadoresDisponibles = new char[]{'+', '-', '*'};
                break;
            case "Estratega": // Cuatro operaciones básicas con mayor rango numérico [cite: 85]
                rangoMax = 50;
                operadoresDisponibles = new char[]{'+', '-', '*', '/'};
                break;
            default: // Caso por defecto (simplicidad)
                rangoMax = 15;
                operadoresDisponibles = new char[]{'+'};
                break;
        }

        // 1. Asignar operador y operandos aleatorios
        this.operador = operadoresDisponibles[RANDOM.nextInt(operadoresDisponibles.length)];
        this.operando1 = RANDOM.nextInt(rangoMax) + 1;
        this.operando2 = RANDOM.nextInt(rangoMax) + 1;

        // 2. Ajustar operandos para Resta y División (para resultados enteros positivos)
        if (this.operador == '-' && this.operando2 > this.operando1) {
            int temp = this.operando1;
            this.operando1 = this.operando2;
            this.operando2 = temp;
        } else if (this.operador == '/') {
            // Asegura que la división sea exacta
            int resultadoTemp = RANDOM.nextInt(rangoMax / 2) + 1;
            this.operando1 = resultadoTemp * this.operando2;
        }

        // 3. Calcular la respuesta correcta
        calcularRespuesta();

        // 4. Generar las opciones de respuesta
        generarOpciones();
    }

    /**
     * Calcula la respuesta correcta de la operación y la almacena.
     */
    private void calcularRespuesta() {
        switch (this.operador) {
            case '+':
                this.respuestaCorrecta = this.operando1 + this.operando2;
                break;
            case '-':
                this.respuestaCorrecta = this.operando1 - this.operando2;
                break;
            case '*':
                this.respuestaCorrecta = this.operando1 * this.operando2;
                break;
            case '/':
                this.respuestaCorrecta = this.operando1 / this.operando2;
                break;
        }
    }

    /**
     * Genera opciones incorrectas (distractores) y mezcla la lista con la correcta.
     */
    private void generarOpciones() {
        this.opcionesDeRespuesta.clear();
        this.opcionesDeRespuesta.add(this.respuestaCorrecta); // Añadir la correcta

        // Generar distractores hasta tener el número de opciones deseado
        while (this.opcionesDeRespuesta.size() < NUMERO_OPCIONES) {
            // Distractores generados cerca de la respuesta correcta
            int distractor = this.respuestaCorrecta + RANDOM.nextInt(7) - 3;

            // Asegurarse de que el distractor sea positivo y no duplicado
            if (distractor > 0 && !this.opcionesDeRespuesta.contains(distractor)) {
                this.opcionesDeRespuesta.add(distractor);
            }
        }

        // Mezclar las opciones
        Collections.shuffle(this.opcionesDeRespuesta);
    }

    /**
     * Verifica si la respuesta proporcionada por el usuario es correcta.
     * @param respuestaUsuario La respuesta ingresada por el usuario.
     * @return true si la respuesta coincide con la respuesta correcta.
     */
    public boolean verificarRespuesta(int respuestaUsuario) {
        return respuestaUsuario == this.respuestaCorrecta;
    }

    // --- Getters ---

    /** Obtiene el primer operando. */
    public int getOperando1() { return operando1; }

    /** Obtiene el segundo operando. */
    public int getOperando2() { return operando2; }

    /** Obtiene el operador (+, -, *, /). */
    public char getOperador() { return operador; }

    /** Obtiene la respuesta correcta. */
    public int getRespuestaCorrecta() { return respuestaCorrecta; }

    /** Obtiene la lista de opciones de respuesta. */
    public List<Integer> getOpcionesDeRespuesta() { return opcionesDeRespuesta; }
}
