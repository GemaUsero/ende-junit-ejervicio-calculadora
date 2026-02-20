# Testing con Junit

Este es un ejemplo sencillo de pruebas unitarias usando Junit 5

Observa que este proyecto no tiene ninguna clase con el método `main`, no nos hace fatal. Además, tampoco tiene ningún `scanner` ni ningún `print`.

Haz un fork de este proyecto en tu repositorio de Github y contesta a las siguientes preguntas:

1. ¿Qué sentido puede tener este proyecto y para que lo podrías usar?


2. Revisa las pruebas de la suma y comenta lo que te parezca de interés




3. Realiza un estudio de caja negra de la división e implementa las pruebas en junit: Se realizará en markdown.



## Instrucciones

El alumno deberá hacer un fork de este proyecto e implementar la solución solicitada (preguntas y código).

>Se deberá utilizar este fichero, y los artefactos de código del proyecto, para resolver el ejercicio.


**Si no se puede acceder al repositorio la evaluación del ejercicio será de 0. No se evaluarán entregas modificadas/entregadas fuera del plazo establecido en la tarea**


# PRÁCTICA: ANÁLISIS Y TESTING DE UNA CALCULADORA EN JAVA

---

# 1. Sentido del proyecto y posibles usos

## 1.1 Descripción general

El proyecto consiste en una clase `Calculadora` que implementa cuatro operaciones aritméticas básicas:

- Suma
- Resta
- Multiplicación
- División

Además, incluye una excepción personalizada llamada `OperacionNoValidaException` que se lanza cuando se intenta dividir entre cero.

---

## 1.2 Sentido del proyecto

Este proyecto tiene un enfoque principalmente didáctico y permite trabajar los siguientes conceptos:

- Métodos estáticos en Java.
- Operaciones aritméticas básicas.
- Manejo de excepciones.
- Creación de excepciones personalizadas.
- Pruebas unitarias con JUnit.
- Técnicas de prueba de caja negra.

Es un ejemplo claro y sencillo para introducir conceptos de calidad del software.

---

## 1.3 Posibles usos

Aunque es un proyecto simple, puede utilizarse como:

- Base para una calculadora más compleja.
- Ejercicio práctico en asignaturas de programación.
- Introducción al testing automatizado.
- Ejemplo para explicar el manejo de errores en Java.

---

# 2. Revisión de las pruebas de la suma

## 2.1 Método analizado

```java
public static int sumar(int a, int b){
    return a+b;
}

```
## 3.Estudio de Caja Negra – Método `dividir`

## 1️ Identificación del método a probar



```java
public static int dividir(int a, int b) throws OperacionNoValidaException{
    if (b==0)
        throw new OperacionNoValidaException();
    else
        return a/b;
}
```

---
## 2️ Análisis Funcional (Caja Negra)


### ✔ Entradas

* `a` → Dividendo (int)
* `b` → Divisor (int)

### ✔ Salidas

* Resultado entero de `a / b`
* Excepción `OperacionNoValidaException` si `b == 0`

---

# 3️ Clases de Equivalencia

##  Clases Válidas

| Clase | Descripción  | Ejemplo  | Resultado esperado |
| ----- | ------------ | -------- | ------------------ |
| CE1   | a > 0, b > 0 | 10 / 2   | 5                  |
| CE2   | a < 0, b > 0 | -10 / 2  | -5                 |
| CE3   | a > 0, b < 0 | 10 / -2  | -5                 |
| CE4   | a < 0, b < 0 | -10 / -2 | 5                  |
| CE5   | a = 0, b ≠ 0 | 0 / 5    | 0                  |

##  Clases Inválidas

| Clase | Descripción | Resultado esperado                  |
| ----- | ----------- | ----------------------------------- |
| CE6   | b = 0       | Lanzar `OperacionNoValidaException` |

---

# 4️ Análisis de Valores Límite

| Caso                  | Justificación          | Resultado esperado |
| --------------------- | ---------------------- | ------------------ |
| b = 0                 | División inválida      | Excepción          |
| a = 0                 | División válida        | 0                  |
| a = Integer.MAX_VALUE | Valor extremo superior | MAX_VALUE          |
| a = Integer.MIN_VALUE | Valor extremo inferior | Resultado correcto |
| b = 1                 | No altera el valor     | a                  |

---

# 5️ Casos de Prueba

| ID  | Entrada (a,b)         | Resultado Esperado |
| --- | --------------------- | ------------------ |
| CP1 | (10,2)                | 5                  |
| CP2 | (-10,2)               | -5                 |
| CP3 | (10,-2)               | -5                 |
| CP4 | (-10,-2)              | 5                  |
| CP5 | (0,5)                 | 0                  |
| CP6 | (5,0)                 | Excepción          |
| CP7 | (Integer.MAX_VALUE,1) | Integer.MAX_VALUE  |

---

# Implementación en JUnit 5

```java
package es.etg.dax.testing;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import es.etg.dax.testing.exception.OperacionNoValidaException;

public class CalculadoraTest {

    @Test
    void dividirDosPositivos() throws OperacionNoValidaException {
        assertEquals(5, Calculadora.dividir(10, 2));
    }

    @Test
    void dividirNegativoEntrePositivo() throws OperacionNoValidaException {
        assertEquals(-5, Calculadora.dividir(-10, 2));
    }

    @Test
    void dividirPositivoEntreNegativo() throws OperacionNoValidaException {
        assertEquals(-5, Calculadora.dividir(10, -2));
    }

    @Test
    void dividirDosNegativos() throws OperacionNoValidaException {
        assertEquals(5, Calculadora.dividir(-10, -2));
    }

    @Test
    void dividirCeroEntreNumero() throws OperacionNoValidaException {
        assertEquals(0, Calculadora.dividir(0, 5));
    }

    @Test
    void dividirEntreCeroDebeLanzarExcepcion() {
        assertThrows(OperacionNoValidaException.class, () -> {
            Calculadora.dividir(5, 0);
        });
    }

    @Test
    void dividirValorMaximoEntreUno() throws OperacionNoValidaException {
        assertEquals(Integer.MAX_VALUE, Calculadora.dividir(Integer.MAX_VALUE, 1));
    }
}
```

---



