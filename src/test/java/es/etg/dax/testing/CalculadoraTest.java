package es.etg.dax.testing;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import es.etg.dax.testing.exception.OperacionNoValidaException;

// 🔵 ENUM DEFINIDO ARRIBA DE LA CLASE
enum Operacion {
    SUMA,
    RESTA,
    MULTIPLICACION,
    DIVISION
}

// Las clases de test suelen tener el sufijo Test
public class CalculadoraTest {

    Calculadora calculadora = new Calculadora();

    @Test
    void sumarPositivos() {
        assertEquals(5, Calculadora.sumar(2, 3));
    }

    @Test
    void sumarPositivosMal() {
        assertEquals(4, Calculadora.sumar(2, 3));
    }

    @Test
    void sumar() {
        assertAll("Suma",
                () -> assertEquals(5, Calculadora.sumar(1, 4)),
                () -> assertEquals(5, Calculadora.sumar(2, 3)),
                () -> assertEquals(1, Calculadora.sumar(0, 1)),
                () -> assertEquals(-1, Calculadora.sumar(1, -2)));
    }

    @Test
    @DisplayName("Probar la división por cero")
    void dividirPorZeroException() {
        var ex = assertThrows(OperacionNoValidaException.class,
                () -> Calculadora.dividir(4, 0));

        assertEquals(OperacionNoValidaException.MSG, ex.getMessage());
    }

    @Test
    void testResta() {
        assertAll("Resta",
                () -> assertEquals(2, Calculadora.restar(5, 3)),
                () -> assertEquals(-2, Calculadora.restar(-5, -3)),
                () -> assertEquals(-4, Calculadora.restar(0, 4)));
    }

    @Test
    void testMultiplicacion() {
        assertAll("Multiplicacion",
                () -> assertEquals(6, Calculadora.multiplicar(2, 3)),
                () -> assertEquals(-6, Calculadora.multiplicar(-2, 3)),
                () -> assertEquals(0, Calculadora.multiplicar(0, 5)));
    }

    @Test
    void testDivision() throws OperacionNoValidaException {
        assertAll("Division",
                () -> assertEquals(2, Calculadora.dividir(6, 3)),
                () -> assertEquals(2, Calculadora.dividir(5, 2)));
    }

    @Test
    void testDivisionPorCero() {
        assertThrows(OperacionNoValidaException.class,
                () -> Calculadora.dividir(5, 0));
    }

    // 🔥 TEST PARAMETRIZADO CON ENUM
    @ParameterizedTest
    @EnumSource(Operacion.class)
    void probarOperaciones(Operacion op) throws OperacionNoValidaException {

        int a = 6;
        int b = 3;

        switch (op) {

            case SUMA:
                assertEquals(9, Calculadora.sumar(a, b));
                break;

            case RESTA:
                assertEquals(3, Calculadora.restar(a, b));
                break;

            case MULTIPLICACION:
                assertEquals(18, Calculadora.multiplicar(a, b));
                break;

            case DIVISION:
                assertEquals(2, Calculadora.dividir(a, b));
                break;
        }
    }
}