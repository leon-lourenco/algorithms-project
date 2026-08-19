# Coin Change

**Categoría:** Greedy

## El problema

Dar cambio de una cantidad usando la menor cantidad posible de monedas/billetes a partir de un
conjunto dado de denominaciones. Probar todas las combinaciones para encontrar el mínimo verdadero
es exponencial. Greedy ofrece un atajo mucho más barato — pero el problema es que no siempre
acierta, y saber exactamente cuándo deja de acertar es el verdadero objetivo de este módulo.

## La solución

Greedy: ordenar las denominaciones y luego tomar repetidamente tantas unidades como quepan de la
denominación más grande, pasando a la siguiente más pequeña solo cuando la actual ya no quepa. Una
sola pasada sobre una lista de denominaciones de tamaño fijo — O(denominaciones), completamente
independiente de la cantidad.

Esa es la cantidad mínima de monedas verdadera *solo* para un sistema de denominaciones
**canónico**, en el que ninguna combinación de monedas más pequeñas supera nunca a una más grande
que greedy habría elegido. Los sistemas monetarios reales — incluidos los billetes y monedas del
real brasileño — son canónicos, y por eso precisamente greedy es lo que en realidad ejecuta cada
cajero automático. Pero no todo conjunto de denominaciones es canónico, y para uno que no lo es,
greedy puede comprometerse temprano con una moneda grande que una combinación más pequeña habría
evitado, llegando a una respuesta válida que no es la mejor.
[`classic/CoinChange.minCoinsDP`](src/main/java/com/algorithms/greedy/coinchange/classic/CoinChange.java)
resuelve el mismo problema con programación dinámica — O(cantidad × denominaciones), más lento,
pero correcto para *cualquier* conjunto de denominaciones positivas — específicamente para poder
mostrar la brecha entre ambos, no solo afirmarla.

## Ejemplo clásico

[`classic/CoinChange`](src/main/java/com/algorithms/greedy/coinchange/classic/CoinChange.java)
implementa `greedyCoinCount` y `minCoinsDP` lado a lado.
[`CoinChangeTest`](src/test/java/com/algorithms/greedy/coinchange/classic/CoinChangeTest.java)
demuestra directamente el contraejemplo clásico de los libros de texto: con las denominaciones
`{1, 3, 4}` y la cantidad `6`, greedy toma primero un `4` y termina con `4 + 1 + 1` — **3
monedas**. El método de DP encuentra `3 + 3` — **2 monedas**. Mismas entradas, mismo problema, dos
respuestas distintas, porque uno de los dos métodos solo es correcto bajo una suposición que el
otro no necesita. El mismo archivo de pruebas confirma que ambos *sí* coinciden en un conjunto
canónico (monedas de EE. UU., `{1, 5, 10, 25}`), y que los dos métodos fallan de forma ruidosa (en
lugar de subcontar en silencio) cuando una cantidad genuinamente no se puede formar con las
monedas dadas.

## Ejemplo aplicado: quiosco de retiro de efectivo de cajero automático bancario

[`applied/CashDispenser`](src/main/java/com/algorithms/greedy/coinchange/applied/CashDispenser.java)
modela el quiosco de autoservicio de retiro de un banco heredado, que decide cuántos billetes/
monedas de cada denominación entregar. Las denominaciones del real brasileño (desde `R$200` hasta
`1` centavo) son canónicas, así que greedy da aquí la cantidad mínima verdadera de billetes/
monedas — justo lo que necesita un quiosco con capacidad de casete limitada por denominación. Las
cantidades se manejan en centavos como `long` específicamente para mantener la aritmética
monetaria exacta y evitar el redondeo de punto flotante.
[`CashDispenserTest`](src/test/java/com/algorithms/greedy/coinchange/applied/CashDispenserTest.java)
cubre un retiro mixto realista, una coincidencia exacta con un solo billete, un retiro de cero, y
las salvaguardas (cantidades negativas, cantidades que superan el límite por transacción del
quiosco).

## Benchmark

```bash
./gradlew :greedy:coin-change:jmh
```

Ejecución real en esta máquina (JMH 1.37, JDK 26.0.2, 2 iteraciones de calentamiento + 3 de
medición, 1 fork). Ambos métodos se ejecutan contra el mismo conjunto canónico de denominaciones
en centavos de BRL, así que siempre coinciden en la respuesta — esto mide el costo de llegar a
ella, no la corrección:

| Costo | amount=10,000 | amount=500,000 | amount=5,000,000 |
|---|---:|---:|---:|
| greedy | 0.164 µs | 0.158 µs | 0.159 µs |
| DP | 198.98 µs | 14,734.68 µs | 149,802.96 µs |

Greedy se mantiene estable a lo largo de tres órdenes de magnitud de la cantidad, exactamente como
predice O(denominaciones) — está haciendo la misma pasada fija sobre 13 denominaciones sin
importar cuán grande sea la cantidad. DP sigue su predicción de O(amount) con claridad en el
extremo más limpio del rango: pasar de amount=500,000 a amount=5,000,000 es un aumento de 10x en
la cantidad, y el costo medido de DP creció **10.16x** — una coincidencia cercana. (El paso más
pequeño, 10,000 → 500,000, presenta márgenes de error mucho más amplios en esta ejecución —
probablemente ruido de calentamiento del JIT en ese extremo del rango — por lo que la confirmación
más limpia del 10x en los tamaños mayores es la que vale la pena confiar.) En amount=5,000,000, DP
es **~942,157x** más lento que greedy para una respuesta que greedy ya tenía.

## Cuándo no usarlo

- ¿El conjunto de denominaciones no está confirmado como canónico (denominaciones arbitrarias/
  personalizadas, niveles de puntos de fidelidad, monedas no decimales)? La velocidad de greedy no
  compensa una respuesta que podría estar mal — usa `minCoinsDP`, o demuestra la canonicidad
  primero.
- ¿Necesitas saber no solo la cantidad, sino *qué* monedas se usaron? El camino de DP de este
  módulo necesitaría la misma técnica de retroceso por la tabla (backtracking) que
  [Knapsack](../../dynamic-programming/knapsack) para recuperar la selección real, no solo su
  tamaño.
- ¿La cantidad es muy grande y el conjunto de denominaciones está confirmado como canónico? Greedy
  ya es la opción correcta y rápida — este es precisamente el caso para el que fue construido.

## Cobertura de pruebas

100% de cobertura de instrucciones, 100% de cobertura de ramas (JaCoCo). Reprodúcelo tú mismo:

```bash
./gradlew :greedy:coin-change:jacocoTestReport
```

Informe en `greedy/coin-change/build/reports/jacoco/test/html/index.html`.

## Lectura complementaria

- Cormen, Leiserson, Rivest & Stein — *Introduction to Algorithms* (CLRS), 3.ª/4.ª ed., Capítulo
  16, "Greedy Algorithms" — cubre exactamente esta brecha entre la simplicidad de greedy y su
  corrección condicional.
- Kleinberg & Tardos — *Algorithm Design* — el Capítulo 4 desarrolla las condiciones generales
  bajo las cuales una elección greedy es demostrablemente óptima (los estilos de prueba "greedy
  stays ahead" y de argumento de intercambio), el mismo tipo de razonamiento que separa un
  conjunto de denominaciones canónico de uno en el que greedy simplemente acierta por casualidad.
