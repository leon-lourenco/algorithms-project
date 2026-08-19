# Binary Search

**Categoria:** Searching

## O problema

O [Linear Search](../linear-search) deste repositório responde "isso está aqui?" em O(n) porque
não pode presumir nada sobre a ordem dos dados. Se os dados *já estiverem* ordenados — uma
suposição comum e barata, uma vez que tenham sido ordenados uma única vez — esse O(n) está
deixando uma quantidade enorme de informação sobre a mesa: cada comparação contra uma coleção
desordenada quase não diz nada sobre onde mais procurar; uma comparação contra uma coleção
*ordenada* diz qual metade inteira descartar.

## A solução

Comparar o alvo com o elemento do meio. Se corresponder, terminou. Se o alvo for menor, toda a
metade superior pode ser descartada — todo elemento ali é garantidamente maior. Se for maior,
descarta-se a metade inferior. Repete-se no que sobrar. Cada comparação elimina metade dos
candidatos restantes, o que é o que torna isso O(log n): o espaço de busca encolhe
geometricamente, não linearmente. Implementado de forma iterativa, com uma janela `[low, high]`
que encolhe, em vez de recursiva, de modo que um array muito grande nunca corre o risco de um
frame de pilha por bisseção.

| Caso | Custo | Por quê |
|---|---|---|
| Melhor caso (correspondência no ponto médio) | O(1) | a primeiríssima comparação já tem sucesso |
| Pior caso (sem correspondência, ou correspondência em um extremo) | O(log n) | o espaço de busca ainda assim é reduzido à metade a cada passo |
| Médio | O(log n) | mesmo padrão de redução pela metade, só que com menos passos que o pior caso |

## Exemplo clássico

[`classic/BinarySearch`](src/main/java/com/algorithms/searching/binarysearch/classic/BinarySearch.java)
é genérico sobre `Comparator<? super T>` — sem o atalho de `Arrays.binarySearch`. O laço reduz
uma janela `[low, high]` a cada iteração em vez de usar recursão, o que é o que mantém isso seguro
em arrays grandes demais para uma pilha de chamadas recursiva lidar confortavelmente.
[`BinarySearchTest`](src/test/java/com/algorithms/searching/binarysearch/classic/BinarySearchTest.java)
cobre um alvo no meio, no início, no final, um alvo ausente, um array vazio, um array de um único
elemento, cada posição de um array de tamanho par (exercitando as duas direções de arredondamento
do ponto médio), e ambas as proteções contra argumento nulo.

## Exemplo aplicado: consulta de snapshot de chaves PIX do BACEN

[`applied/RegisteredPixKeyLookup`](src/main/java/com/algorithms/searching/binarysearch/applied/RegisteredPixKeyLookup.java)
verifica se uma chave PIX está registrada em um snapshot noturno, já ordenado, de todas as chaves
registradas — o tipo de exportação em lote que um job de reconciliação extrai uma vez e depois
consulta muitas vezes. Diferentemente de uma trie construída incrementalmente à medida que as
chaves são registradas em tempo real, isto presume que todo o conjunto de chaves já é conhecido e
está fixo para o dia: ordená-lo uma vez, de antemão, e fazer busca binária a cada consulta é mais
barato do que manter uma estrutura viva para dados que não mudam até o snapshot do dia seguinte.
[`RegisteredPixKeyLookupTest`](src/test/java/com/algorithms/searching/binarysearch/applied/RegisteredPixKeyLookupTest.java)
cobre uma chave registrada, uma chave não registrada, e ambas as proteções contra argumento nulo.

## Benchmark

```bash
./gradlew :searching:binary-search:jmh
```

Execução real nesta máquina (JMH 1.37, JDK 26.0.2, 2 iterações de aquecimento + 3 de medição, 1
fork). Mesmo pior caso, mesmos tamanhos, mesma máquina do benchmark de [Linear
Search](../linear-search) deste repositório:

| Custo da busca (alvo ausente) | size=100 | size=10,000 | size=1,000,000 |
|---|---:|---:|---:|
| Binary Search | 9.60 ns | 20.62 ns | 30.29 ns |

Essa é a alegação de O(log n), tornada inconfundível: um aumento de 100x no tamanho (100→10,000)
custa apenas ~2.15x mais tempo; um novo aumento de 100x (10,000→1,000,000) custa apenas ~1.47x
mais — multiplicadores decrescentes para o mesmo crescimento proporcional nos dados, exatamente a
assinatura de uma escala logarítmica (`log₂(10,000)/log₂(100) ≈ 2.0`,
`log₂(1,000,000)/log₂(10,000) ≈ 1.5` — a forma prevista, batendo quase exatamente). Contra os
1,676,427.30 ns do [Linear Search](../linear-search) para a mesma pergunta em size=1,000,000, este
módulo responde em 30.29 ns — **~55,353x mais rápido**, na mesma máquina, para a mesma pergunta
"está aí ou não". A única diferença é um bit de informação que o linear search não tem como usar:
os dados estão ordenados.

## Quando não usar

- Os dados não estão ordenados, e não serão consultados um número de vezes suficiente para
  justificar ordená-los uma vez? O [Linear Search](../linear-search) deste repositório evita
  completamente o custo de ordenação — vale a pena só se o número de consultas permanecer pequeno.
- Os dados mudam com frequência (inserções/remoções frequentes) e precisam continuar pesquisáveis
  a cada passo? Manter um array ordenado sob esse tipo de alteração custa O(n) por inserção para
  manter — uma estrutura de árvore autobalanceada (uma árvore AVL ou uma B-tree, por exemplo)
  mantém as duas operações rápidas em vez de trocar uma pela outra.
- Precisa da chave *mais próxima*, não apenas de uma correspondência exata, ou de um intervalo
  ordenado? Esta implementação retorna `-1` quando não encontra e descarta exatamente onde o alvo
  teria ficado — uma variante ciente de floor/ceiling precisaria retornar esse limite em vez disso.

## Cobertura de testes

100% de cobertura de instruções, 100% de cobertura de branches (JaCoCo). Reproduza você mesmo:

```bash
./gradlew :searching:binary-search:jacocoTestReport
```

Relatório em `searching/binary-search/build/reports/jacoco/test/html/index.html`.

## Leitura complementar

- Cormen, Leiserson, Rivest & Stein — *Introduction to Algorithms* (CLRS), 3ª/4ª ed., o Problema
  2-4 cobre recorrências relacionadas a busca; a própria busca binária é um exemplo recorrente ao
  longo dos capítulos de dividir para conquistar.
- Sedgewick & Wayne — *Algorithms*, 4ª ed., seção 3.1, "Symbol Tables" — apresenta a busca binária
  como `BinarySearch.rank`, a linha de base que toda tabela de símbolos ordenada do livro
  aprimora.
