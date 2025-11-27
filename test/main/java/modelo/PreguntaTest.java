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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PreguntaTest {
    /**
     * Prueba el método de verificación de respuesta.
     */
    @Test
    void testVerificarRespuesta() {
        Pregunta pregunta = new Pregunta();
        pregunta.generarPregunta("Aprendiz"); // Generar una pregunta para obtener una respuesta válida

        // La respuesta del usuario debe coincidir con la respuesta correcta
        assertTrue(pregunta.verificarRespuesta(pregunta.getRespuestaCorrecta()));

        // La respuesta incorrecta no debe coincidir (probamos con un valor +1)
        assertFalse(pregunta.verificarRespuesta(pregunta.getRespuestaCorrecta() + 1));
    }

    /**
     * Prueba que las preguntas del nivel 'Aprendiz' solo usan suma o resta.
     */
    @Test
    void testGeneracionPreguntaAprendiz() {
        Pregunta pregunta = new Pregunta();
        pregunta.generarPregunta("Aprendiz");

        char operador = pregunta.getOperador();
        assertTrue(operador == '+' || operador == '-');

        // Además, el operando 1 y 2 deben ser <= 10 (según la lógica implementada)
        assertTrue(pregunta.getOperando1() <= 10 && pregunta.getOperando2() <= 10);
    }

    /**
     * Prueba que la lista de opciones de respuesta siempre contiene la respuesta correcta.
     */
    @Test
    void testOpcionesContienenRespuestaCorrecta() {
        Pregunta pregunta = new Pregunta();
        pregunta.generarPregunta("Estratega");

        int respuesta = pregunta.getRespuestaCorrecta();
        List<Integer> opciones = pregunta.getOpcionesDeRespuesta();

        assertTrue(opciones.size() >= 4, "Debe haber al menos 4 opciones.");
        assertTrue(opciones.contains(respuesta),
                "La lista de opciones debe contener la respuesta correcta.");
    }
}