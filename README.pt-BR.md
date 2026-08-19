# The Grand Algorithms Project

[![CI](https://github.com/leon-lourenco/algorithms-project/actions/workflows/ci.yml/badge.svg)](https://github.com/leon-lourenco/algorithms-project/actions/workflows/ci.yml)

**Site de documentação:** [leon-lourenco.github.io/algorithms-project](https://leon-lourenco.github.io/algorithms-project/) — cada algoritmo com diagrama, os dois exemplos e o relatório de cobertura, navegável em Português/English/Español.

**Leia em:** [English](README.md) | [Português](README.pt-BR.md) | [Español](README.es.md)

Um projeto Java modular cobrindo os algoritmos clássicos ensinados num curso universitário de
Ciência da Computação — ordenação, busca, programação dinâmica, estratégias gulosas, casamento
de padrões, teoria dos números e backtracking. Um módulo Gradle por algoritmo, cada um com seu
próprio README, uma implementação do zero, uma segunda implementação aplicando esse algoritmo a
um cenário real, e um microbenchmark JMH que transforma a afirmação de complexidade (Big-O) do
livro-texto num número medido e reproduzível. Tudo é JVM puro: sem demo hospedada, sem serviços
externos, `./gradlew build` e pronto.

Este é um projeto de portfólio de [Leon Lourenço](https://github.com/leon-lourenco), engenheiro
backend sênior.

## Alguns números reais

Cada afirmação abaixo foi copiada literalmente de uma execução local real de JMH/JaCoCo — veja
o README de cada módulo para a tabela completa e como reproduzi-la.

- **[Bubble Sort](sorting/bubble-sort)**, ordenando o mesmo array de 10.000 elementos: a ordem
  aleatória é **~32.527x** mais lenta que a ordem já ordenada, no mesmo código exato. Essa é a
  afirmação de saída antecipada adaptativa, tornada mensurável.
- **[Quick Sort](sorting/quick-sort)** com pivô randomizado, mesmo tamanho: a ordem aleatória é
  só **~2,2x** mais lenta que a já ordenada — não os mais de 100x que um quicksort *não
  randomizado* mostraria exatamente nessa entrada. Prova de que a randomização realmente
  neutraliza o risco clássico de pior caso.
- **[Merge Sort](sorting/merge-sort)** e **[Heap Sort](sorting/heap-sort)** ficam ambos dentro
  de ~1,3–2x um do outro entre entrada já ordenada, quase ordenada e aleatória, em todo tamanho —
  a afirmação de "limite garantido independentemente da ordem de entrada", tornada mensurável.
- **[Binary Search](searching/binary-search)** contra **[Linear Search](searching/linear-search)**
  no mesmo array de 1.000.000 de elementos: **~55.353x mais rápido** para a mesma pergunta "está
  aqui?" — todo o valor de assumir dados ordenados, tornado mensurável.
- **[Fibonacci](dynamic-programming/fibonacci)** em n=35: a recursão ingênua é **~6.057.544x mais
  lenta** que a versão tabulada para a mesma resposta exata — exponencial vs. espaço O(1), tornado
  mensurável.
- **[N-Queens](backtracking/n-queens)** com 8 rainhas: o backtracking com poda é **~940x mais
  rápido** que a força bruta, e ambos concordam na mesma resposta — as famosas **92** soluções
  publicadas pela primeira vez em 1850.

## Por que classic + applied + benchmark

Uma implementação de livro-texto prova que você entende a mecânica de um algoritmo — o
invariante do laço, a recursão, o passo de particionamento. Não prova que você sabe *quando*
recorrer a ele em vez da alternativa, e não prova que a afirmação de Big-O do livro-texto
realmente se sustenta numa JVM real. Por isso cada módulo carrega três coisas em vez de uma:

- **classic/** — o algoritmo em si, feito à mão (sem depender de `Arrays.sort`/
  `Collections.sort` como atalho), com testes que exercitam seus casos-limite reais (já
  ordenado, ordenado ao contrário, duplicatas, a fronteira vazio/um-elemento).
- **applied/** — o mesmo algoritmo resolvendo um cenário real, escolhido perguntando: qual é o
  problema real que esse algoritmo resolve, e onde esse problema exato já apareceu? O
  mapeamento não é fintech-apenas por padrão — é deliberadamente puxado de onde quer que, na
  trajetória do autor (pagamentos, seguros, telecom, modernização de mainframe), o problema
  subjacente se encaixe melhor.
- **jmh/** — um microbenchmark JMH que mede a operação sobre a qual a afirmação de complexidade
  do módulo trata, normalmente como um A/B direto entre ordens de entrada: adaptativo vs. não,
  limite garantido vs. caso médio. Os números citados em cada README são copiados de uma
  execução local real, não estimados.

## Por que Java?

Todo módulo aqui é escrito em Java de propósito, não por padrão — é a linguagem que o autor
deste projeto usa em produção no dia a dia, então implementar esses algoritmos sem atalho de
`Arrays.sort`/`Collections.sort` é também uma demonstração de fluência na linguagem, não só de
algoritmos. Essa restrição é parte do motivo de o Java se encaixar especificamente bem aqui: a
linguagem *já vem* com um sort maduro e altamente otimizado como uma chamada de uma linha só,
então escrever deliberadamente contornando ele é um exercício real. Uma linguagem sem essa
tentação embutida — C, por exemplo — não colocaria exatamente a mesma escolha na mesa.

O outro motivo é a maturidade do ferramental. Todo número de benchmark neste repositório é
medido, não estimado: o JMH roda cada benchmark através de iterações de warmup para que o JIT já
tenha compilado o caminho quente antes de qualquer coisa ser cronometrada, cria uma JVM nova
(via fork) por benchmark para evitar contaminação cruzada, e usa blackholes para impedir que o
JIT otimize embora justamente o código que está sendo medido. O JaCoCo traz o mesmo rigor para a
cobertura — 100% aqui significa que toda instrução e todo branch genuinamente rodaram sob teste.
Construir esse nível de rigor metodológico do zero em C é um projeto separado por si só; na JVM,
é `./gradlew jmh`.

O trade-off honesto: números de JVM incluem a JVM. O warmup do JIT, o garbage collection e o
overhead de cabeçalho de objeto estão embutidos em cada microssegundo citado neste repositório.
Este repositório não finge que essa camada é invisível; ele se apoia na metodologia do JMH
especificamente para enxergar o formato algorítmico (adaptativo vs. não, O(n log n) vs. O(n²))
*através* da JVM, e não ao redor dela.

## Os algoritmos

Todo módulo abaixo carrega a mesma implementação classic/applied/benchmark, seu próprio README,
e cobertura genuína de 100% de instrução + branch no JaCoCo.

| Algoritmo | Categoria | Cenário aplicado |
|-----------|----------|-------------------|
| [Bubble Sort](sorting/bubble-sort) | Ordenação | Correção de razão diária de mainframe legado (banco legado) |
| [Insertion Sort](sorting/insertion-sort) | Ordenação | Ordenação em lote de registros de chamada (telecom) |
| [Merge Sort](sorting/merge-sort) | Ordenação | Ordenação de relatório de compliance de fraude (plataforma antifraude) |
| [Quick Sort](sorting/quick-sort) | Ordenação | Ordenação de percentil de reserva de sinistros (seguradora) |
| [Heap Sort](sorting/heap-sort) | Ordenação | Ordenação de alarmes em equipamento de borda (telecom) |
| [Linear Search](searching/linear-search) | Busca | Detector de chamada com estouro de franquia (telecom) |
| [Binary Search](searching/binary-search) | Busca | Consulta de snapshot de chaves PIX (PIX/BACEN) |
| [Fibonacci](dynamic-programming/fibonacci) | Programação Dinâmica | Contagem de rotas de pagamento entre bancos correspondentes (telecom) |
| [0/1 Knapsack](dynamic-programming/knapsack) | Programação Dinâmica | Seleção de projetos de capex (telecom) |
| [Longest Common Subsequence](dynamic-programming/longest-common-subsequence) | Programação Dinâmica | Diff de reconciliação de razão bancário (banco legado) |
| [Coin Change](greedy/coin-change) | Guloso | Distribuição de cédulas em saque de caixa eletrônico (banco legado) |
| [Huffman Coding](greedy/huffman-coding) | Guloso | Compressão de lote de CDR (telecom) |
| [Knuth-Morris-Pratt](string-matching/knuth-morris-pratt) | Casamento de Padrões | Varredura de narração em lista de observação (plataforma antifraude) |
| [Euclidean GCD](math/euclidean-gcd) | Matemática | Redução de proporção de split de pagamento (PIX/BACEN) |
| [Sieve of Eratosthenes](math/sieve-of-eratosthenes) | Matemática | Dimensionamento de cache de deduplicação (plataforma antifraude) |
| [Fast Exponentiation](math/fast-exponentiation) | Matemática | Projeção de crescimento de reserva atuarial (seguradora) |
| [N-Queens](backtracking/n-queens) | Backtracking | Atribuição de faixas de liquidação (PIX/BACEN) |

## Estrutura

Todo módulo de algoritmo segue o mesmo esqueleto:

```
<category>/<algorithm>/
├── build.gradle.kts          # presente só quando o módulo precisa de dependências extras
├── README.md                 # problema, solução, complexidade, os dois exemplos, benchmark, cobertura
└── src/
    ├── main/java/com/algorithms/<category>/<algorithm>/
    │   ├── classic/           # a implementação do zero
    │   └── applied/           # o uso no cenário real
    ├── test/java/...          # espelha a divisão classic/applied
    └── jmh/java/com/algorithms/<category>/<algorithm>/benchmark/
        └── ...                # microbenchmark(s) JMH provando a afirmação de complexidade empiricamente
```

## Stack técnica

Java 26, Gradle 9.7 (Kotlin DSL, wrapper commitado — `./gradlew` funciona sem instalar o
Gradle), JUnit 5, AssertJ, JaCoCo 0.8.15, JMH 1.37. Sem Spring, sem framework — todo módulo é
Java puro, já que o ponto é o algoritmo, não um container.

**Nota sobre a integração do JMH:** o plugin Gradle da comunidade `me.champeau.jmh` teve seu
último lançamento (0.7.3, janeiro de 2025) testado só até Gradle 8.10/Java 21. Em vez de lutar
contra um plugin desatualizado usando Gradle 9.7/Java 26, o `src/jmh/java` de cada módulo é
conectado diretamente como um source set Gradle puro (veja o `build.gradle.kts` raiz), com o
próprio annotation processor do JMH gerando as classes de execução do benchmark — nenhum plugin
de terceiros no meio do caminho.

## Rodando o projeto

```bash
./gradlew build                                          # compila todos os módulos
./gradlew test                                            # roda os testes de todos os módulos
./gradlew :sorting:merge-sort:jacocoTestReport              # relatório de cobertura por módulo (HTML)
./gradlew :sorting:merge-sort:jmh                           # execução do benchmark JMH por módulo
```

Sem Docker, sem banco de dados, sem chamadas de rede — todo teste e benchmark roda contra
código em processo. Os números de cobertura e benchmark citados no README de cada módulo são
copiados de uma execução local real (JDK 26.0.2 nesta máquina), não estimados.

## Leitura complementar

Livros citados ao longo dos READMEs dos módulos deste repositório, reunidos aqui como
referência:

- Cormen, Leiserson, Rivest & Stein — *Introduction to Algorithms* (CLRS), 3ª/4ª ed. — a
  referência acadêmica canônica; praticamente todo curso universitário de algoritmos usa esse
  livro.
- Sedgewick & Wayne — *Algorithms*, 4ª ed. — orientado a Java e prático, do curso de Princeton
  de mesmo nome; o encaixe mais próximo com a linguagem e o estilo deste repositório.
- Skiena — *The Algorithm Design Manual* — forte em "quando usar o quê" e estudos de caso
  reais, o mesmo espírito das seções "Quando não usar" deste repositório.
- Knuth — *The Art of Computer Programming*, Vol. 3 (Sorting and Searching) — a fonte
  histórica e canônica para os módulos de ordenação e busca deste repositório.
- Kleinberg & Tardos — *Algorithm Design* — uma referência forte especificamente para os
  paradigmas de design guloso e programação dinâmica cobertos nos módulos deste repositório.

## Licença

MIT — veja [LICENSE](LICENSE).
