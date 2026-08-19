# The Grand Algorithms Project

[![CI](https://github.com/leon-lourenco/algorithms-project/actions/workflows/ci.yml/badge.svg)](https://github.com/leon-lourenco/algorithms-project/actions/workflows/ci.yml)

**Sitio de documentación:** [leon-lourenco.github.io/algorithms-project](https://leon-lourenco.github.io/algorithms-project/) — cada algoritmo con diagrama, ambos ejemplos y el informe de cobertura, navegable en Español/English/Português.

**Leer en:** [English](README.md) | [Português](README.pt-BR.md) | [Español](README.es.md)

Un proyecto Java modular que cubre los algoritmos clásicos enseñados en un curso universitario de
Ciencias de la Computación — ordenamiento, búsqueda, programación dinámica, estrategias voraces,
coincidencia de patrones, teoría de números y backtracking. Un módulo Gradle por algoritmo, cada
uno con su propio README, una implementación desde cero, una segunda implementación que aplica
ese algoritmo a un escenario real, y un microbenchmark JMH que convierte la afirmación de
complejidad (Big-O) del libro de texto en un número medido y reproducible. Todo es JVM puro: sin
demo alojada, sin servicios externos, `./gradlew build` y listo.

Este es un proyecto de portafolio de [Leon Lourenço](https://github.com/leon-lourenco),
ingeniero backend sénior.

## Algunos números reales

Cada afirmación de abajo está copiada literalmente de una ejecución local real de JMH/JaCoCo —
ver el README de cada módulo para la tabla completa y cómo reproducirla.

- **[Bubble Sort](sorting/bubble-sort)**, ordenando el mismo array de 10.000 elementos: el orden
  aleatorio es **~32.527x** más lento que el orden ya ordenado, en exactamente el mismo código.
  Esa es la afirmación de salida anticipada adaptativa, hecha medible.
- **[Quick Sort](sorting/quick-sort)** con pivote aleatorizado, mismo tamaño: el orden aleatorio
  es solo **~2,2x** más lento que el ya ordenado — no el 100x o más que un quicksort *no
  aleatorizado* mostraría exactamente en esa entrada. Prueba de que la aleatorización realmente
  neutraliza el riesgo clásico de peor caso.
- **[Merge Sort](sorting/merge-sort)** y **[Heap Sort](sorting/heap-sort)** se mantienen ambos
  dentro de ~1,3–2x entre sí en entrada ya ordenada, casi ordenada y aleatoria, en todo tamaño —
  la afirmación de "límite garantizado sin importar el orden de entrada", hecha medible.
- **[Binary Search](searching/binary-search)** frente a **[Linear Search](searching/linear-search)**
  sobre el mismo array de 1.000.000 de elementos: **~55.353x más rápido** para la misma pregunta
  de "¿está ahí?" — todo el valor de asumir datos ordenados, hecho medible.
- **[Fibonacci](dynamic-programming/fibonacci)** en n=35: la recursión ingenua es **~6.057.544x
  más lenta** que la versión tabulada para exactamente la misma respuesta — exponencial vs.
  espacio O(1), hecho medible.
- **[N-Queens](backtracking/n-queens)** con 8 reinas: el backtracking con poda es **~940x más
  rápido** que la fuerza bruta, y ambos coinciden en la misma respuesta — las famosas **92**
  soluciones publicadas por primera vez en 1850.

## Por qué classic + applied + benchmark

Una implementación de libro de texto demuestra que entiendes la mecánica de un algoritmo — el
invariante del bucle, la recursión, el paso de partición. No demuestra que sabes *cuándo*
recurrir a él en lugar de la alternativa, ni que la afirmación de Big-O del libro de texto
realmente se sostiene en una JVM real. Por eso cada módulo lleva tres cosas en lugar de una:

- **classic/** — el algoritmo en sí, hecho a mano (sin depender de `Arrays.sort`/
  `Collections.sort` como atajo), con pruebas que ejercitan sus casos límite reales (ya
  ordenado, ordenado al revés, duplicados, el límite vacío/un-elemento).
- **applied/** — el mismo algoritmo resolviendo un escenario real, elegido preguntando: ¿cuál es
  el problema real que resuelve este algoritmo, y dónde apareció exactamente ese problema? El
  mapeo no es solo fintech por defecto — se toma deliberadamente de donde, en la trayectoria del
  autor (pagos, seguros, telecomunicaciones, modernización de mainframe), el problema subyacente
  encaja mejor.
- **jmh/** — un microbenchmark JMH que mide la operación sobre la que trata la afirmación de
  complejidad del módulo, normalmente como un A/B directo entre órdenes de entrada: adaptativo
  vs. no, límite garantizado vs. caso promedio. Los números citados en cada README están
  copiados de una ejecución local real, no estimados.

## ¿Por qué Java?

Cada módulo aquí está escrito en Java a propósito, no por defecto — es el lenguaje que el autor
de este proyecto usa en producción a diario, así que implementar estos algoritmos sin ningún
atajo de `Arrays.sort`/`Collections.sort` es también una demostración de fluidez en el lenguaje,
no solo de algoritmos. Esa restricción es parte de por qué Java encaja específicamente bien
aquí: el lenguaje *ya trae* un sort maduro y muy optimizado como una llamada de una sola línea,
así que escribir deliberadamente evitándolo es un ejercicio real. Un lenguaje sin esa tentación
incorporada — C, por ejemplo — no plantearía exactamente la misma elección.

La otra razón es la madurez del tooling. Cada número de benchmark en este repositorio está
medido, no estimado: JMH ejecuta cada benchmark a través de iteraciones de calentamiento para
que el JIT ya haya compilado el camino caliente antes de que se cronometre nada, crea una JVM
nueva (mediante fork) por benchmark para evitar contaminación cruzada, y usa blackholes para
impedir que el JIT optimice y elimine justamente el código que se está midiendo. JaCoCo aporta
el mismo rigor a la cobertura — 100% aquí significa que cada instrucción y cada rama realmente
se ejecutaron bajo prueba. Construir ese nivel de rigor metodológico desde cero en C es un
proyecto aparte en sí mismo; en la JVM es `./gradlew jmh`.

El trade-off honesto: los números de la JVM incluyen a la JVM. El calentamiento del JIT, el
garbage collection y el overhead de cabecera de objeto están incorporados en cada microsegundo
citado en este repositorio. Este repositorio no finge que esa capa es invisible; se apoya en la
metodología de JMH específicamente para ver la forma algorítmica (adaptativo vs. no, O(n log n)
vs. O(n²)) *a través* de la JVM, no alrededor de ella.

## Los algoritmos

Cada módulo de abajo lleva la misma implementación classic/applied/benchmark, su propio README, y
cobertura genuina del 100% de instrucciones + ramas en JaCoCo.

| Algoritmo | Categoría | Escenario aplicado |
|-----------|----------|-------------------|
| [Bubble Sort](sorting/bubble-sort) | Ordenamiento | Corrección de libro mayor diario de mainframe legado (banco legado) |
| [Insertion Sort](sorting/insertion-sort) | Ordenamiento | Ordenamiento por lotes de registros de llamadas (telecom) |
| [Merge Sort](sorting/merge-sort) | Ordenamiento | Ordenamiento de informe de compliance de fraude (plataforma antifraude) |
| [Quick Sort](sorting/quick-sort) | Ordenamiento | Ordenamiento de percentil de reserva de siniestros (aseguradora) |
| [Heap Sort](sorting/heap-sort) | Ordenamiento | Ordenamiento de alarmas en equipo de borde (telecom) |
| [Linear Search](searching/linear-search) | Búsqueda | Detector de llamadas con exceso de franquicia (telecom) |
| [Binary Search](searching/binary-search) | Búsqueda | Consulta de snapshot de claves PIX (PIX/BACEN) |
| [Fibonacci](dynamic-programming/fibonacci) | Programación Dinámica | Conteo de rutas de pago entre bancos corresponsales (telecom) |
| [0/1 Knapsack](dynamic-programming/knapsack) | Programación Dinámica | Selección de proyectos de capex (telecom) |
| [Longest Common Subsequence](dynamic-programming/longest-common-subsequence) | Programación Dinámica | Diff de conciliación de libro mayor bancario (banco legado) |
| [Coin Change](greedy/coin-change) | Voraz | Entrega de billetes en retiro de cajero automático (banco legado) |
| [Huffman Coding](greedy/huffman-coding) | Voraz | Compresión de lotes de CDR (telecom) |
| [Knuth-Morris-Pratt](string-matching/knuth-morris-pratt) | Coincidencia de Patrones | Escaneo de narración en lista de vigilancia (plataforma antifraude) |
| [Euclidean GCD](math/euclidean-gcd) | Matemáticas | Reducción de proporción de split payment (PIX/BACEN) |
| [Sieve of Eratosthenes](math/sieve-of-eratosthenes) | Matemáticas | Dimensionamiento de caché de deduplicación (plataforma antifraude) |
| [Fast Exponentiation](math/fast-exponentiation) | Matemáticas | Proyección de crecimiento de reserva actuarial (aseguradora) |
| [N-Queens](backtracking/n-queens) | Backtracking | Asignación de carriles de liquidación (PIX/BACEN) |

## Estructura

Cada módulo de algoritmo sigue el mismo esqueleto:

```
<category>/<algorithm>/
├── build.gradle.kts          # presente solo cuando el módulo necesita dependencias extra
├── README.md                 # problema, solución, complejidad, ambos ejemplos, benchmark, cobertura
└── src/
    ├── main/java/com/algorithms/<category>/<algorithm>/
    │   ├── classic/           # la implementación desde cero
    │   └── applied/           # el uso en el escenario real
    ├── test/java/...          # refleja la división classic/applied
    └── jmh/java/com/algorithms/<category>/<algorithm>/benchmark/
        └── ...                # microbenchmark(s) JMH que prueban la afirmación de complejidad empíricamente
```

## Stack técnico

Java 26, Gradle 9.7 (Kotlin DSL, wrapper incluido — `./gradlew` funciona sin instalar Gradle),
JUnit 5, AssertJ, JaCoCo 0.8.15, JMH 1.37. Sin Spring, sin framework — cada módulo es Java puro,
ya que el punto es el algoritmo, no un contenedor.

**Nota sobre la integración de JMH:** el plugin de Gradle de la comunidad `me.champeau.jmh` tuvo
su último lanzamiento (0.7.3, enero de 2025) probado solo hasta Gradle 8.10/Java 21. En lugar de
pelear contra un plugin desactualizado usando Gradle 9.7/Java 26, el `src/jmh/java` de cada
módulo se conecta directamente como un source set de Gradle plano (ver el `build.gradle.kts`
raíz), con el propio annotation processor de JMH generando las clases de ejecución del
benchmark — ningún plugin de terceros de por medio.

## Ejecutarlo

```bash
./gradlew build                                          # compila todos los módulos
./gradlew test                                            # ejecuta las pruebas de todos los módulos
./gradlew :sorting:merge-sort:jacocoTestReport              # reporte de cobertura por módulo (HTML)
./gradlew :sorting:merge-sort:jmh                           # ejecución del benchmark JMH por módulo
```

Sin Docker, sin base de datos, sin llamadas de red — cada prueba y benchmark corre contra código
en proceso. Los números de cobertura y benchmark citados en el README de cada módulo están
copiados de una ejecución local real (JDK 26.0.2 en esta máquina), no estimados.

## Lectura complementaria

Libros citados a lo largo de los READMEs de los módulos de este repositorio, reunidos aquí como
referencia:

- Cormen, Leiserson, Rivest & Stein — *Introduction to Algorithms* (CLRS), 3ª/4ª ed. — la
  referencia académica canónica; prácticamente todo curso universitario de algoritmos usa este
  libro.
- Sedgewick & Wayne — *Algorithms*, 4ª ed. — orientado a Java y práctico, del curso de
  Princeton del mismo nombre; el ajuste más cercano al lenguaje y estilo de este repositorio.
- Skiena — *The Algorithm Design Manual* — fuerte en "cuándo usar qué" y estudios de caso
  reales, el mismo espíritu de las secciones "Cuándo no usarlo" de este repositorio.
- Knuth — *The Art of Computer Programming*, Vol. 3 (Sorting and Searching) — la fuente
  histórica y canónica para los módulos de ordenamiento y búsqueda de este repositorio.
- Kleinberg & Tardos — *Algorithm Design* — una referencia fuerte específicamente para los
  paradigmas de diseño voraz y programación dinámica cubiertos en los módulos de este
  repositorio.

## Licencia

MIT — ver [LICENSE](LICENSE).
