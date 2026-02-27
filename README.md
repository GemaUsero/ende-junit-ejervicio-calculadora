
# Descripción del Proyecto

La clase `Calculadora` implementa cuatro operaciones matemáticas básicas:

- `sumar(int a, int b)`
- `restar(int a, int b)`
- `multiplicar(int a, int b)`
- `dividir(int a, int b)`

Todas las operaciones reciben **dos valores enteros** como entrada y devuelven un valor entero como resultado.

En el caso de la división, si el divisor (`b`) es igual a 0, el método lanza una excepción controlada `OperacionNoValidaException`.

---

# Metodología: Pruebas de Caja Negra

Se ha aplicado la técnica de **caja negra**, donde no se analiza el código interno del programa, sino únicamente:

- Entradas
- Salidas
- Comportamiento esperado

##  Identificación de Entradas

El sistema recibe dos entradas:

- `a` (entero)
- `b` (entero)

Ambas pueden tomar valores:

- Positivos
- Negativos
- Cero

En el caso de la división:
- `b = 0` es una clase inválida que debe generar una excepción.

---

## Clases de Equivalencia

Para ambas entradas:

- Enteros positivos
- Enteros negativos
- Cero

Para división:

- Clase inválida → divisor igual a cero

---

## Análisis de Límites

Se han probado valores cercanos a los puntos críticos:

- -1
- 0
- 1

Estos valores permiten comprobar:
- Cambios de signo
- Comportamiento con cero
- División por cero

---

# Resultados de los Test

Las pruebas se han implementado con **JUnit 5**.

---

## 3.1 Test de Suma

| Caso | Entrada (a,b) | Resultado Esperado | Resultado Obtenido | Estado |
|------|--------------|-------------------|--------------------|--------|
| CP1 | (2,3) | 5 | 5 | ✅ OK |
| CP2 | (-2,-3) | -5 | -5 | ✅ OK |
| CP3 | (2,-2) | 0 | 0 | ✅ OK |
| CP4 | (0,5) | 5 | 5 | ✅ OK |

**Resultado:** Todas las pruebas de suma se ejecutan correctamente.

---

## 3.2 Test de Resta

| Caso | Entrada | Resultado Esperado | Resultado Obtenido | Estado |
|------|----------|-------------------|--------------------|--------|
| CP5 | (5,3) | 2 | 2 | ✅ OK |
| CP6 | (-5,-3) | -2 | -2 | ✅ OK |
| CP7 | (0,4) | -4 | -4 | ✅ OK |

**Resultado:** La operación de resta funciona correctamente en todos los casos probados.

---

## 3.3 Test de Multiplicación

| Caso | Entrada | Resultado Esperado | Resultado Obtenido | Estado |
|------|----------|-------------------|--------------------|--------|
| CP8 | (2,3) | 6 | 6 | ✅ OK |
| CP9 | (-2,3) | -6 | -6 | ✅ OK |
| CP10 | (0,5) | 0 | 0 | ✅ OK |

**Resultado:** La multiplicación funciona correctamente para valores positivos, negativos y cero.

---

##  3.4 Test de División

| Caso | Entrada | Resultado Esperado | Resultado Obtenido | Estado |
|------|----------|-------------------|--------------------|--------|
| CP11 | (6,3) | 2 | 2 | ✅ OK |
| CP12 | (5,2) | 2 (división entera) | 2 | ✅ OK |

**Resultado:** La división entera se realiza correctamente.

---

##  3.5 Test División por Cero

| Caso | Entrada | Resultado Esperado | Estado |
|------|----------|-------------------|--------|
| CP13 | (5,0) | OperacionNoValidaException | ✅ OK |

**Resultado:** La excepción se lanza correctamente cuando el divisor es cero.

---

# Conclusión

Tras ejecutar todas las pruebas definidas mediante la técnica de caja negra, se puede concluir que:

- La calculadora realiza correctamente las operaciones de suma, resta y multiplicación.
- La división funciona correctamente para valores válidos.
- Se controla adecuadamente el error de división por cero mediante una excepción.

Se han cubierto:

- Valores positivos
- Valores negativos
- Cero
- Casos límite
- Caso excepcional (división por cero)

La aplicación cumple los requisitos funcionales establecidos.

---

# ✔ Estado Final

 Todas las pruebas han sido superadas correctamente.


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

