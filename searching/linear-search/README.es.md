# Linear Search

**Categoría:** Searching

## El problema

Encontrar un valor en una colección sin ninguna suposición sobre su orden — los datos pueden estar
genuinamente desordenados, o bien ordenarlos solo para ejecutar una única búsqueda costaría más
que la propia búsqueda. Sea cual sea el motivo, no hay atajo disponible: sin un orden que explotar,
no hay forma de saber qué mitad de los datos descartar.

## La solución

Recorrer la colección desde el principio y probar cada elemento hasta que uno coincida, o hasta
que la colección se agote. Aquí no hay nada ingenioso a propósito — todo el valor de este módulo
está en establecer la línea base honesta que toda búsqueda más rápida (empezando por [Binary
Search](../binary-search) de este mismo repositorio) tiene que superar, y *por qué* no puede
superarse sin una suposición como "los datos están ordenados".

| Caso | Costo | Por qué |
|---|---|---|
| Mejor caso (coincidencia en el índice 0) | O(1) | la primerísima comparación ya tiene éxito |
| Peor caso (sin coincidencia, o coincidencia al final) | O(n) | hay que examinar todos los elementos |
| Promedio | O(n) | proporcional a qué tan adentro de la colección está la coincidencia |

## Ejemplo clásico

[`classic/LinearSearch`](src/main/java/com/algorithms/searching/linearsearch/classic/LinearSearch.java)
recibe un `Predicate<? super T>` en lugar de un valor objetivo fijo — una generalización
deliberada respecto de "encontrar este valor exacto": es lo que una búsqueda lineal real
normalmente necesita (encontrar el primer elemento *que cumpla una condición*, no solo igual a una
constante), y es lo que permite que el ejemplo aplicado de abajo reutilice este mismo método sin
cambios.
[`LinearSearchTest`](src/test/java/com/algorithms/searching/linearsearch/classic/LinearSearchTest.java)
cubre una coincidencia en el medio, ninguna coincidencia, una coincidencia en la primerísima
posición, una coincidencia en la últimísima posición, un array vacío, y ambas protecciones contra
argumento nulo.

## Ejemplo aplicado: localizador de llamadas con exceso en telecom

[`applied/FirstOverageCallFinder`](src/main/java/com/algorithms/searching/linearsearch/applied/FirstOverageCallFinder.java)
encuentra la primera llamada en un log diario de registros de detalle de llamadas (CDR) cuya
duración excede la franquicia del plan de un suscriptor, para disparar una alerta de exceso en
tiempo real. El log está ordenado por hora de llegada — las llamadas van entrando desde las
antenas en el orden en que ocurren — no por duración, así que no existe una vista ordenada por
duración contra la cual hacer búsqueda binaria, y reordenar todo el log por duración en cada
verificación costaría más que el propio recorrido lineal. Este es el caso honesto para linear
search: los datos genuinamente no están ordenados por el campo sobre el que se busca.
[`FirstOverageCallFinderTest`](src/test/java/com/algorithms/searching/linearsearch/applied/FirstOverageCallFinderTest.java)
cubre una llamada encontrada a mitad del log, ninguna llamada que exceda la franquicia, y la
protección contra argumento nulo.

## Benchmark

```bash
./gradlew :searching:linear-search:jmh
```

Ejecución real en esta máquina (JMH 1.37, JDK 26.0.2, 2 iteraciones de calentamiento + 3 de
medición, 1 fork). Peor caso: el objetivo nunca está presente, lo que obliga a un recorrido
completo. Misma tarea, mismos tamaños, misma máquina que el benchmark de [Binary
Search](../binary-search) de este repositorio, así que ambos son directamente comparables:

| Costo de la búsqueda (objetivo ausente) | size=100 | size=10,000 | size=1,000,000 |
|---|---:|---:|---:|
| Linear Search | 36.41 ns | 6,589.62 ns | 1,676,427.30 ns |

El costo crece aproximadamente al mismo ritmo que el tamaño — ~181x para un aumento de 100x en el
tamaño (100→10,000), ~254x para otro aumento de 100x (10,000→1,000,000) — con ruido alrededor del
~100x ideal, pero inequívocamente lineal, no sublineal. En size=1,000,000, la búsqueda equivalente
de [Binary Search](../binary-search) en esta misma máquina se ejecuta en 30.29 ns — el recorrido
lineal de este módulo es **~55,353x más lento** para exactamente la misma pregunta de "¿está
ahí?", únicamente porque no puede asumir que los datos están ordenados. Esa brecha es la razón de
ser de que una búsqueda sobre datos ordenados merezca su propio algoritmo.

## Cuándo no usarlo

- ¿Los datos ya están ordenados, o pueden ordenarse una vez y consultarse muchas veces? [Binary
  Search](../binary-search) de este repositorio responde la misma pregunta en O(log n) — el
  benchmark de arriba muestra exactamente cuánto importa eso a escala.
- ¿Se busca repetidamente en la misma colección valores distintos? Construir un índice una sola
  vez (una tabla hash, una estructura ordenada) se amortiza mejor que un recorrido O(n) nuevo en
  cada consulta.
- El valor real de este módulo aparece justamente cuando no se puede asumir que los datos están
  ordenados, o cuando se recorren con tan poca frecuencia que construir cualquier tipo de índice
  no compensa el overhead.

## Cobertura de pruebas

100% de cobertura de instrucciones, 100% de cobertura de ramas (JaCoCo). Reprodúcelo tú mismo:

```bash
./gradlew :searching:linear-search:jacocoTestReport
```

Informe en `searching/linear-search/build/reports/jacoco/test/html/index.html`.

## Lectura complementaria

- Cormen, Leiserson, Rivest & Stein — *Introduction to Algorithms* (CLRS), 3.ª/4.ª ed., Capítulo
  2 — la búsqueda lineal es la línea base implícita contra la que CLRS mide toda búsqueda más
  rápida.
- Sedgewick & Wayne — *Algorithms*, 4.ª ed., sección 3.1, "Symbol Tables" — presenta la búsqueda
  secuencial como el punto de partida que el resto de las estructuras de datos del capítulo
  mejoran.
