# Testing con Junit

1. ¿Qué sentido puede tener este proyecto y para que lo podrías usar?


2. Revisa las pruebas de la suma y comenta lo que te parezca de interés


3. Realiza un estudio de caja negra de la división e implementa las pruebas en junit: Se realizará en markdown.


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

Aunque se trata de una calculadora sencilla, sirve como base sólida para entender la importancia del control de errores y la validación del comportamiento del software mediante pruebas automatizadas.

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
``



```

Contiene cuatro métodos estáticos:

- `sumar(int a, int b)`
- `restar(int a, int b)`
- `multiplicar(int a, int b)`
- `dividir(int a, int b)`

Todos los métodos son `static`, lo que significa que no es necesario crear un objeto de la clase para utilizarlos. Esto tiene sentido porque una calculadora no necesita estado interno; simplemente ejecuta operaciones.

---

## Test suma

Prueba correcta
```java
@Test
void sumarPositivos() {
    assertEquals(5, Calculadora.sumar(2, 3));
}

Verifica el comportamiento básico.
## 2.1 Análisis del método dividir:

```


## dividir

```java

public static int dividir(int a, int b) throws OperacionNoValidaException{
    if (b==0)
        throw new OperacionNoValidaException();
    else
        return a/b;
}
```

Este método introduce un concepto importante:

-Control explícito de error
-Excepción personalizada
-Uso de throws en la firma del método

En lugar de permitir que Java lance una ArithmeticException, el profesor crea una excepción propia (OperacionNoValidaException). Esto es una buena práctica cuando se quiere:

Controlar el tipo de error.

Personalizar el mensaje.

Adaptar el comportamiento a las necesidades del sistema.

## 2.2. Análisis de la excepción personalizada

Clase:

```java

es.etg.dax.testing.exception.OperacionNoValidaException
public class OperacionNoValidaException extends Exception{

    public static final String MSG = "No se puede dividir por cero";

    @Override
    public String getMessage() {
        return MSG;
    }
}
```

Aspectos importantes:

Hereda de Exception → es una excepción checked.

Obliga a usar throws en el método dividir.

Define un mensaje constante (MSG).

Sobrescribe getMessage().

## Prueba incorrecta intencionada:

```java

@Test
void sumarPositivosMal() {
    assertEquals(4, Calculadora.sumar(2, 3));
}

```

-Qué ocurre cuando una prueba falla.

-Cómo JUnit muestra errores.

-La importancia de definir correctamente el valor esperado.

## Uso de assertAll

```java
assertAll("Suma",
    () -> assertEquals(5, Calculadora.sumar(1, 4)),
    () -> assertEquals(5, Calculadora.sumar(2, 3)),
    () -> assertEquals(1, Calculadora.sumar(0, 1)),
    () -> assertEquals(-1, Calculadora.sumar(1, -2))

);

```
assertAll permite:

Agrupar varias comprobaciones.

Ejecutarlas todas aunque una falle.

Mostrar todos los errores al final.

## 3.Estudio de Caja Negra – Método `dividir`

El análisis de caja negra del método `dividir` ha permitido:

- Identificar correctamente las entradas y salidas.
- Definir clases de equivalencia válidas e inválidas.
- Establecer valores límite relevantes.
- Determinar los casos de prueba necesarios para cubrir el comportamiento funcional.

Se ha comprobado que el único caso inválido es cuando el divisor es cero, situación en la que se lanza una excepción personalizada.

Este análisis demuestra la importancia de estudiar el comportamiento del método sin tener en cuenta su implementación interna.


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

### 5. Análisis de la clase CalculadoraTest

La clase de pruebas usa JUnit 5:

import static org.junit.jupiter.api.Assertions.*;

Se utilizan:

-assertEquals

-assertThrows

-assertAll

-@DisplayName

Esto indica un uso correcto del framework de testing.

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



