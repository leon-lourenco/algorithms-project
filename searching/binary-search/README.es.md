# Binary Search

**Categoría:** Searching

## El problema

[Linear Search](../linear-search) de este repositorio responde "¿esto está aquí?" en O(n) porque
no puede asumir nada sobre el orden de los datos. Si los datos *ya están* ordenados — una
suposición común y barata una vez que se han ordenado una sola vez — ese O(n) está dejando sobre
la mesa una cantidad enorme de información: cada comparación contra una colección desordenada casi
no dice nada sobre dónde más buscar; una comparación contra una colección *ordenada* dice qué
mitad entera descartar.

## La solución

Comparar el objetivo con el elemento del medio. Si coincide, listo. Si el objetivo es menor, se
puede descartar toda la mitad superior — todo elemento ahí está garantizado a ser mayor. Si es
mayor, se descarta la mitad inferior. Se repite con lo que quede. Cada comparación elimina la
mitad de los candidatos restantes, que es lo que hace que esto sea O(log n): el espacio de
búsqueda se reduce geométricamente, no linealmente. Implementado de forma iterativa, con una
ventana `[low, high]` que se va reduciendo, en lugar de recursiva, de modo que un array muy grande
nunca corre el riesgo de un frame de pila por cada bisección.

| Caso | Costo | Por qué |
|---|---|---|
| Mejor caso (coincidencia en el punto medio) | O(1) | la primerísima comparación ya tiene éxito |
| Peor caso (sin coincidencia, o coincidencia en un extremo) | O(log n) | el espacio de búsqueda igual se reduce a la mitad en cada paso |
| Promedio | O(log n) | el mismo patrón de reducción a la mitad, solo que con menos pasos que el peor caso |

## Ejemplo clásico

[`classic/BinarySearch`](src/main/java/com/algorithms/searching/binarysearch/classic/BinarySearch.java)
es genérico sobre `Comparator<? super T>` — sin el atajo de `Arrays.binarySearch`. El bucle reduce
una ventana `[low, high]` en cada iteración en lugar de recurrir a la recursión, lo que es
justamente lo que mantiene esto seguro en arrays demasiado grandes para que una pila de llamadas
recursiva los maneje con comodidad.
[`BinarySearchTest`](src/test/java/com/algorithms/searching/binarysearch/classic/BinarySearchTest.java)
cubre un objetivo en el medio, al inicio, al final, un objetivo ausente, un array vacío, un array
de un solo elemento, cada posición de un array de tamaño par (ejercitando ambas direcciones de
redondeo del punto medio), y ambas protecciones contra argumento nulo.

## Ejemplo aplicado: consulta de snapshot de claves PIX del BACEN

[`applied/RegisteredPixKeyLookup`](src/main/java/com/algorithms/searching/binarysearch/applied/RegisteredPixKeyLookup.java)
verifica si una clave PIX está registrada contra un snapshot nocturno, ya ordenado, de todas las
claves registradas — el tipo de exportación por lotes que un job de conciliación extrae una vez y
luego consulta muchas veces. A diferencia de un trie construido de forma incremental a medida que
las claves se registran en tiempo real, esto asume que todo el conjunto de claves ya se conoce y
está fijo para el día: ordenarlo una vez por adelantado y aplicar búsqueda binaria en cada consulta
es más barato que mantener una estructura viva para datos que no cambian hasta el snapshot del día
siguiente.
[`RegisteredPixKeyLookupTest`](src/test/java/com/algorithms/searching/binarysearch/applied/RegisteredPixKeyLookupTest.java)
cubre una clave registrada, una clave no registrada, y ambas protecciones contra argumento nulo.

## Benchmark

```bash
./gradlew :searching:binary-search:jmh
```

Ejecución real en esta máquina (JMH 1.37, JDK 26.0.2, 2 iteraciones de calentamiento + 3 de
medición, 1 fork). Mismo peor caso, mismos tamaños, misma máquina que el benchmark de [Linear
Search](../linear-search) de este repositorio:

| Costo de la búsqueda (objetivo ausente) | size=100 | size=10,000 | size=1,000,000 |
|---|---:|---:|---:|
| Binary Search | 9.60 ns | 20.62 ns | 30.29 ns |

Esa es la afirmación de O(log n), hecha inconfundible: un aumento de 100x en el tamaño
(100→10,000) cuesta solo ~2.15x más tiempo; un nuevo aumento de 100x (10,000→1,000,000) cuesta
solo ~1.47x más — multiplicadores decrecientes para el mismo crecimiento proporcional en los
datos, exactamente la firma de una escala logarítmica (`log₂(10,000)/log₂(100) ≈ 2.0`,
`log₂(1,000,000)/log₂(10,000) ≈ 1.5` — la forma prevista, coincidiendo casi con exactitud). Frente
a los 1,676,427.30 ns de [Linear Search](../linear-search) para la misma pregunta en
size=1,000,000, este módulo responde en 30.29 ns — **~55,353x más rápido**, en la misma máquina,
para la misma pregunta de "¿está ahí?". La única diferencia es un bit de información que linear
search no puede usar: los datos están ordenados.

## Cuándo no usarlo

- ¿Los datos no están ordenados, y no se van a consultar un número de veces suficiente como para
  justificar ordenarlos una sola vez? [Linear Search](../linear-search) de este repositorio evita
  por completo el costo de ordenar — vale la pena solo si el número de consultas se mantiene bajo.
- ¿Los datos cambian con frecuencia (inserciones/eliminaciones frecuentes) y necesitan seguir
  siendo consultables en todo momento? Mantener un array ordenado bajo ese nivel de cambio cuesta
  O(n) por inserción — una estructura de árbol autobalanceado (un árbol AVL o un B-tree, por
  ejemplo) mantiene ambas operaciones rápidas en lugar de sacrificar una por la otra.
- ¿Se necesita la clave *más cercana*, no solo una coincidencia exacta, o un rango ordenado? Esta
  implementación devuelve `-1` cuando no encuentra nada y descarta exactamente dónde habría ido el
  objetivo — una variante consciente de floor/ceiling necesitaría devolver ese límite en su lugar.

## Cobertura de pruebas

100% de cobertura de instrucciones, 100% de cobertura de ramas (JaCoCo). Reprodúcelo tú mismo:

```bash
./gradlew :searching:binary-search:jacocoTestReport
```

Informe en `searching/binary-search/build/reports/jacoco/test/html/index.html`.

## Lectura complementaria

- Cormen, Leiserson, Rivest & Stein — *Introduction to Algorithms* (CLRS), 3.ª/4.ª ed., el
  Problema 2-4 cubre recurrencias relacionadas con la búsqueda; la propia búsqueda binaria es un
  ejemplo recurrente a lo largo de los capítulos de divide y vencerás.
- Sedgewick & Wayne — *Algorithms*, 4.ª ed., sección 3.1, "Symbol Tables" — presenta la búsqueda
  binaria como `BinarySearch.rank`, la línea base que toda tabla de símbolos ordenada del libro
  mejora.
