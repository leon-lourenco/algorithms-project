# Linear Search

**Categoria:** Searching

## O problema

Encontrar um valor em uma coleção sem nenhuma suposição sobre sua ordem — os dados podem
genuinamente estar desordenados, ou ordená-los apenas para executar uma única busca custaria mais
do que a própria busca. Seja qual for o motivo, não há atalho disponível: sem uma ordenação para
explorar, não há como saber qual metade dos dados descartar.

## A solução

Percorrer a coleção a partir do início e testar cada elemento até que um corresponda, ou até que a
coleção se esgote. Não há nada de engenhoso aqui de propósito — todo o valor deste módulo está em
estabelecer a linha de base honesta que toda busca mais rápida (a começar pelo [Binary
Search](../binary-search) deste próprio repositório) precisa superar, e *por que* ela não pode ser
superada sem uma suposição como "os dados estão ordenados".

| Caso | Custo | Por quê |
|---|---|---|
| Melhor caso (correspondência no índice 0) | O(1) | a primeiríssima comparação já tem sucesso |
| Pior caso (sem correspondência, ou correspondência no final) | O(n) | todos os elementos precisam ser examinados |
| Médio | O(n) | proporcional a quão adiante na coleção está a correspondência |

## Exemplo clássico

[`classic/LinearSearch`](src/main/java/com/algorithms/searching/linearsearch/classic/LinearSearch.java)
recebe um `Predicate<? super T>` em vez de um valor-alvo fixo — uma generalização deliberada em
relação a "encontrar este valor exato": é o que uma busca linear real geralmente precisa
(encontrar o primeiro elemento *que atenda a uma condição*, não apenas igual a uma constante), e é
isso que permite que o exemplo aplicado abaixo reutilize este mesmo método sem alterações.
[`LinearSearchTest`](src/test/java/com/algorithms/searching/linearsearch/classic/LinearSearchTest.java)
cobre uma correspondência no meio, nenhuma correspondência, uma correspondência na primeiríssima
posição, uma correspondência na últimíssima posição, um array vazio, e ambas as proteções contra
argumento nulo.

## Exemplo aplicado: localizador de chamada excedente em telecom

[`applied/FirstOverageCallFinder`](src/main/java/com/algorithms/searching/linearsearch/applied/FirstOverageCallFinder.java)
encontra a primeira chamada em um log diário de registros de detalhes de chamadas (CDR) cuja
duração excede a franquia do plano de um assinante, para disparar um alerta de excedente em tempo
real. O log é ordenado pelo horário de chegada — as chamadas chegam das torres na ordem em que
acontecem — e não pela duração, então não existe uma visão ordenada por duração contra a qual
fazer busca binária, e reordenar todo o log por duração a cada verificação custaria mais do que a
própria varredura linear. Este é o caso honesto para linear search: os dados genuinamente não
estão ordenados pelo campo em que a busca é feita.
[`FirstOverageCallFinderTest`](src/test/java/com/algorithms/searching/linearsearch/applied/FirstOverageCallFinderTest.java)
cobre uma chamada encontrada no meio do log, nenhuma chamada excedendo a franquia, e a proteção
contra argumento nulo.

## Benchmark

```bash
./gradlew :searching:linear-search:jmh
```

Execução real nesta máquina (JMH 1.37, JDK 26.0.2, 2 iterações de aquecimento + 3 de medição, 1
fork). Pior caso: o alvo nunca está presente, forçando uma varredura completa. Mesma tarefa,
mesmos tamanhos, mesma máquina do benchmark de [Binary Search](../binary-search) deste
repositório, então os dois são diretamente comparáveis:

| Custo da busca (alvo ausente) | size=100 | size=10,000 | size=1,000,000 |
|---|---:|---:|---:|
| Linear Search | 36.41 ns | 6,589.62 ns | 1,676,427.30 ns |

O custo cresce aproximadamente na mesma proporção do tamanho — ~181x para um aumento de 100x no
tamanho (100→10,000), ~254x para outro aumento de 100x (10,000→1,000,000) — com ruído em torno do
ideal de ~100x, mas inconfundivelmente linear, não sublinear. Em size=1,000,000, a busca
equivalente do [Binary Search](../binary-search) nesta mesma máquina roda em 30.29 ns — a
varredura linear deste módulo é **~55,353x mais lenta** para exatamente a mesma pergunta "está aí
ou não", puramente porque não pode presumir que os dados estão ordenados. Essa diferença é toda a
razão pela qual uma busca em dados ordenados merece seu próprio algoritmo.

## Quando não usar

- Os dados já estão ordenados, ou podem ser ordenados uma vez e consultados muitas vezes? O
  [Binary Search](../binary-search) deste repositório responde à mesma pergunta em O(log n) — o
  benchmark acima mostra exatamente o quanto isso importa em escala.
- Buscando repetidamente na mesma coleção por valores diferentes? Construir um índice uma única
  vez (uma tabela hash, uma estrutura ordenada) se amortiza melhor do que uma nova varredura O(n)
  a cada consulta.
- O verdadeiro valor deste módulo está justamente quando não é possível presumir que os dados
  estão ordenados, ou quando eles são varridos com tão pouca frequência que construir qualquer
  tipo de índice não compensa o overhead.

## Cobertura de testes

100% de cobertura de instruções, 100% de cobertura de branches (JaCoCo). Reproduza você mesmo:

```bash
./gradlew :searching:linear-search:jacocoTestReport
```

Relatório em `searching/linear-search/build/reports/jacoco/test/html/index.html`.

## Leitura complementar

- Cormen, Leiserson, Rivest & Stein — *Introduction to Algorithms* (CLRS), 3ª/4ª ed., Capítulo 2
  — a busca linear é a linha de base implícita contra a qual o CLRS mede toda busca mais rápida.
- Sedgewick & Wayne — *Algorithms*, 4ª ed., seção 3.1, "Symbol Tables" — enquadra a busca
  sequencial como o ponto de partida que as demais estruturas de dados do capítulo aprimoram.
