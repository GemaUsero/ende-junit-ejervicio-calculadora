

# PRÁCTICA: ANÁLISIS Y TESTING DE UNA CALCULADORA EN JAVA

---

## 1️ Sentido del proyecto y posibles usos

### 1.1 Descripción general

El proyecto consiste en una clase `Calculadora` que implementa operaciones aritméticas básicas:

* Suma
* Resta
* Multiplicación
* División

Además, incluye una excepción personalizada `OperacionNoValidaException` para controlar divisiones entre cero, permitiendo manejar errores de forma controlada.

### 1.2 Sentido del proyecto

Este proyecto tiene un enfoque **didáctico y práctico**, que permite trabajar los siguientes conceptos:

* Métodos estáticos (`static`) en Java.
* Operaciones aritméticas básicas con enteros.
* Manejo de **excepciones personalizadas**.
* Pruebas unitarias con **JUnit 5**.
* Técnicas de prueba de **caja negra** y análisis funcional.
* Cobertura de código y control de errores.
* Validación del comportamiento esperado mediante testing automatizado.

Es un ejemplo simple, pero útil para entender la **importancia de las pruebas** y la separación entre **lógica y control de errores**.

### 1.3 Posibles usos

* Base para una calculadora más compleja.
* Ejercicio práctico en asignaturas de programación.
* Introducción a **pruebas automatizadas** y diseño de pruebas.
* Ejemplo de control de errores y buenas prácticas en Java.

---

## 2️ Análisis de la suma

### 2.1 Método sumar

```java
public static int sumar(int a, int b){
    return a + b;
}
```

* Método `static`: no requiere crear instancia de la clase.
* Realiza la operación básica de suma de enteros.
* Se puede usar con valores positivos, negativos o cero.

### 2.2 Pruebas de suma

#### 2.2.1 Prueba correcta

```java
@Test
void sumarPositivos() {
    assertEquals(5, Calculadora.sumar(2, 3));
}
```

* Verifica suma de enteros positivos.
* Confirma que el método retorna el valor esperado.

#### 2.2.2 Prueba incorrecta intencionada

```java
@Test
void sumarPositivosMal() {
    assertEquals(4, Calculadora.sumar(2, 3));
}
```

* Muestra qué ocurre cuando la prueba falla.
* Permite interpretar los errores que JUnit muestra.
* Enseña la importancia de definir correctamente el valor esperado.

#### 2.2.3 Uso de assertAll

```java
assertAll("Suma",
    () -> assertEquals(5, Calculadora.sumar(1, 4)),
    () -> assertEquals(5, Calculadora.sumar(2, 3)),
    () -> assertEquals(1, Calculadora.sumar(0, 1)),
    () -> assertEquals(-1, Calculadora.sumar(1, -2))
);
```

* Agrupa varias comprobaciones en un solo test.
* Ejecuta todas las comprobaciones incluso si alguna falla.
* Permite mostrar todos los errores al final de la prueba.

---

## 3️ Análisis del método dividir (Caja Negra)

### 3.1 Método dividir

```java
public static int dividir(int a, int b) throws OperacionNoValidaException {
    if (b == 0)
        throw new OperacionNoValidaException();
    else
        return a / b;
}
```

* Control explícito de error con excepción personalizada.
* Evita `ArithmeticException` estándar de Java.
* Obliga a manejar `throws` en la firma del método.

### 3.2 Excepción personalizada

```java
public class OperacionNoValidaException extends Exception {
    public static final String MSG = "No se puede dividir por cero";

    @Override
    public String getMessage() {
        return MSG;
    }
}
```

* Hereda de `Exception` → checked.
* Mensaje constante y claro.
* Permite personalizar el comportamiento de error.
* Obliga a tratar la excepción con `try/catch` o `throws`.

---

## 4️ Análisis Funcional (Caja Negra)

### Entradas

* `a` → Dividendo (int)
* `b` → Divisor (int)

### Salidas

* Resultado entero de `a / b` (trunca decimales)
* Excepción `OperacionNoValidaException` si `b == 0`

### Observaciones

* División entera: los decimales se truncan automáticamente.
* Caso especial: `Integer.MIN_VALUE / -1` produce overflow en Java, se devuelve `Integer.MIN_VALUE` debido a límites del tipo `int`.

---

## 5️ Clases de Equivalencia

### Clases válidas

| Clase | Descripción | Ejemplo | Resultado esperado |
| ----- | ----------- | ------- | ------------------ |
| CE1   | a>0, b>0    | 10/2    | 5                  |
| CE2   | a<0, b>0    | -10/2   | -5                 |
| CE3   | a>0, b<0    | 10/-2   | -5                 |
| CE4   | a<0, b<0    | -10/-2  | 5                  |
| CE5   | a=0, b≠0    | 0/5     | 0                  |

### Clases inválidas

| Clase | Descripción | Resultado esperado                  |
| ----- | ----------- | ----------------------------------- |
| CE6   | b=0         | Lanzar `OperacionNoValidaException` |

---

## 6️ Valores límite

| Caso                       | Justificación          | Resultado esperado |
| -------------------------- | ---------------------- | ------------------ |
| b = 0                      | División inválida      | Excepción          |
| a = 0                      | División válida        | 0                  |
| a = Integer.MAX_VALUE      | Valor extremo superior | Integer.MAX_VALUE  |
| a = Integer.MIN_VALUE      | Valor extremo inferior | Resultado correcto |
| b = 1                      | No altera el valor     | a                  |
| a = Integer.MIN_VALUE / -1 | Caso especial overflow | Integer.MIN_VALUE  |

---

## 7️ Casos de prueba (Caja Negra)

| ID  | Entrada (a,b)          | Resultado esperado |
| --- | ---------------------- | ------------------ |
| CP1 | (10,2)                 | 5                  |
| CP2 | (-10,2)                | -5                 |
| CP3 | (10,-2)                | -5                 |
| CP4 | (-10,-2)               | 5                  |
| CP5 | (0,5)                  | 0                  |
| CP6 | (5,0)                  | Excepción          |
| CP7 | (Integer.MAX_VALUE,1)  | Integer.MAX_VALUE  |
| CP8 | (Integer.MIN_VALUE,-1) | Integer.MIN_VALUE  |

---

## 8️ Implementación en JUnit 5

```java
package es.etg.dax.testing;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import es.etg.dax.testing.exception.OperacionNoValidaException;

public class CalculadoraTest {

    // División válida
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
    void dividirValorMaximoEntreUno() throws OperacionNoValidaException {
        assertEquals(Integer.MAX_VALUE, Calculadora.dividir(Integer.MAX_VALUE, 1));
    }

    @Test
    void dividirValorMinimoEntreMenosUno() throws OperacionNoValidaException {
        assertEquals(Integer.MIN_VALUE, Calculadora.dividir(Integer.MIN_VALUE, -1));
    }

    // División inválida
    @Test
    void dividirEntreCeroDebeLanzarExcepcion() {
        assertThrows(OperacionNoValidaException.class, () -> {
            Calculadora.dividir(5, 0);
        });
    }
}
```

---

## 9️ Conclusión final

* El análisis de **caja negra** es completo: entradas, salidas, clases de equivalencia y valores límite cubren todos los escenarios funcionales.
* Las pruebas JUnit reflejan correctamente los casos de prueba planificados.
* La división incluye control de excepciones con `OperacionNoValidaException`.
* Las pruebas de suma demuestran el uso de `assertEquals` y `assertAll`.
* El proyecto sirve para **prácticas de programación, testing y control de errores** y puede ampliarse para ejercicios más avanzados.

