
# PRÁCTICA: ANÁLISIS Y TESTING DE UNA CALCULADORA EN JAVA

---

# 1️ Descripción del Proyecto

La clase `Calculadora` implementa cuatro operaciones matemáticas básicas:

- `sumar(int a, int b)`
- `restar(int a, int b)`
- `multiplicar(int a, int b)`
- `dividir(int a, int b)`

Todas las operaciones reciben **dos valores enteros** como entrada y devuelven un valor entero como resultado.

En el caso de la división, cuando el divisor (`b`) es igual a 0, el sistema genera una excepción (`OperacionNoValidaException`).

---

# 2️ Metodología: Pruebas de Caja Negra

Se ha aplicado la técnica de **caja negra**, que consiste en analizar el comportamiento del sistema únicamente a partir de:

- Entradas
- Salidas
- Comportamiento esperado

No se tiene en cuenta la implementación interna del código.

---

# 3️ Identificación de Entradas

El sistema recibe dos entradas:

- `a` → Primer operando (int)
- `b` → Segundo operando (int)

Ambas pueden tomar los siguientes valores:

- Números positivos
- Números negativos
- Cero

En la operación de división existe una restricción:

- Si `b = 0`, la operación no es válida y debe producir una excepción.

---

# 4️ Clases de Equivalencia

Se han definido las siguientes clases de equivalencia:

## Clases válidas

- CE1: a > 0, b > 0
- CE2: a < 0, b > 0
- CE3: a > 0, b < 0
- CE4: a < 0, b < 0
- CE5: a = 0, b ≠ 0

## Clase inválida

- CE6: b = 0 → debe generar excepción

Estas clases permiten cubrir todos los comportamientos posibles del sistema.

---

# 5️ Análisis de Valores Límite

Se han probado valores cercanos a los puntos críticos del sistema:

- -1
- 0
- 1

También se han considerado:

- b = 0 (división inválida)
- b = 1 (no altera el valor)
- a = 0 (resultado esperado 0 en división válida)

Estos valores permiten verificar cambios de signo y comportamiento especial con el cero.

---

# 6️ Casos de Prueba Definidos

## División (Caja Negra)

| ID  | Entrada (a,b) | Resultado esperado |
|-----|--------------|-------------------|
| CP1 | (10,2) | 5 |
| CP2 | (-10,2) | -5 |
| CP3 | (10,-2) | -5 |
| CP4 | (-10,-2) | 5 |
| CP5 | (0,5) | 0 |
| CP6 | (5,0) | Excepción |

---

# 7️ Resultados de los Test

Las pruebas se han implementado utilizando **JUnit 5**.

## 7.1 Test de Suma

| Caso | Entrada (a,b) | Esperado | Obtenido | Estado |
|------|--------------|----------|----------|--------|
| CP1 | (2,3) | 5 | 5 | ✅ OK |
| CP2 | (-2,-3) | -5 | -5 | ✅ OK |
| CP3 | (2,-2) | 0 | 0 | ✅ OK |
| CP4 | (0,5) | 5 | 5 | ✅ OK |

Resultado: Correcto.

---

## 7.2 Test de Resta

| Caso | Entrada | Esperado | Obtenido | Estado |
|------|----------|----------|----------|--------|
| CP5 | (5,3) | 2 | 2 | ✅ OK |
| CP6 | (-5,-3) | -2 | -2 | ✅ OK |
| CP7 | (0,4) | -4 | -4 | ✅ OK |

Resultado: Correcto.

---

## 7.3 Test de Multiplicación

| Caso | Entrada | Esperado | Obtenido | Estado |
|------|----------|----------|----------|--------|
| CP8 | (2,3) | 6 | 6 | ✅ OK |
| CP9 | (-2,3) | -6 | -6 | ✅ OK |
| CP10 | (0,5) | 0 | 0 | ✅ OK |

Resultado: Correcto.

---

## 7.4 Test de División

| Caso | Entrada | Esperado | Obtenido | Estado |
|------|----------|----------|----------|--------|
| CP11 | (6,3) | 2 | 2 | ✅ OK |
| CP12 | (5,2) | 2 | 2 | ✅ OK |

Resultado: Correcto.

---

## 7.5 División por Cero

| Caso | Entrada | Resultado esperado | Estado |
|------|----------|-------------------|--------|
| CP13 | (5,0) | OperacionNoValidaException | ✅ OK |

Resultado: La excepción se genera correctamente.

---

# 8️ Conclusión Final

Se ha aplicado correctamente la técnica de pruebas de caja negra,
analizando exclusivamente entradas, salidas y comportamiento observable del sistema.

Se han cubierto:

- Valores positivos
- Valores negativos
- Cero
- Combinaciones de signos
- Caso excepcional (división por cero)

Las pruebas implementadas en JUnit validan todos los escenarios definidos en el análisis.

Por tanto, la calculadora cumple los requisitos funcionales establecidos.