# Coin Change

**Categoria:** Greedy

## O problema

Dar troco de um valor usando o menor número possível de moedas/cédulas a partir de um conjunto
dado de denominações. Tentar todas as combinações para encontrar o mínimo verdadeiro é
exponencial. O greedy oferece um atalho muito mais barato — mas a pegadinha é que ele nem sempre
está certo, e saber exatamente quando ele deixa de estar certo é o verdadeiro propósito deste
módulo.

## A solução

Greedy: ordenar as denominações e, então, pegar repetidamente o máximo possível da maior
denominação que ainda cabe, passando para a próxima menor apenas quando a atual não couber mais.
Uma única passada sobre uma lista de denominações de tamanho fixo — O(denominações), completamente
independente do valor.

Essa é a contagem mínima de moedas verdadeira *apenas* para um sistema de denominações
**canônico**, no qual nenhuma combinação de moedas menores jamais supera uma maior que o greedy
teria escolhido. Sistemas de moeda reais — incluindo as cédulas e moedas do real brasileiro — são
canônicos, e é exatamente por isso que o greedy é o que todo caixa eletrônico de fato executa. Mas
nem todo conjunto de denominações é canônico, e, para um que não é, o greedy pode travar cedo
demais em uma moeda grande que uma combinação menor teria evitado, chegando a uma resposta válida
que não é a melhor.
[`classic/CoinChange.minCoinsDP`](src/main/java/com/algorithms/greedy/coinchange/classic/CoinChange.java)
resolve o mesmo problema com programação dinâmica — O(valor × denominações), mais lento, porém
correto para *qualquer* conjunto de denominações positivas — especificamente para que a diferença
entre os dois possa ser demonstrada, não apenas alegada.

## Exemplo clássico

[`classic/CoinChange`](src/main/java/com/algorithms/greedy/coinchange/classic/CoinChange.java)
implementa `greedyCoinCount` e `minCoinsDP` lado a lado.
[`CoinChangeTest`](src/test/java/com/algorithms/greedy/coinchange/classic/CoinChangeTest.java)
comprova diretamente o contraexemplo clássico dos livros-texto: com as denominações `{1, 3, 4}` e
o valor `6`, o greedy pega um `4` primeiro e termina com `4 + 1 + 1` — **3 moedas**. O método de DP
encontra `3 + 3` — **2 moedas**. Mesmas entradas, mesmo problema, duas respostas diferentes, porque
um dos dois métodos só está correto sob uma suposição de que o outro não precisa. O mesmo arquivo
de teste confirma que os dois *concordam* em um conjunto canônico (moedas dos EUA, `{1, 5, 10,
25}`), e que ambos os métodos falham ruidosamente (em vez de subcontar silenciosamente) quando um
valor genuinamente não pode ser formado a partir das moedas dadas.

## Exemplo aplicado: quiosque de saque de dinheiro de caixa eletrônico bancário

[`applied/CashDispenser`](src/main/java/com/algorithms/greedy/coinchange/applied/CashDispenser.java)
modela o quiosque de autoatendimento de saque de um banco legado, decidindo quantas cédulas/moedas
de cada denominação dispensar. As denominações do real brasileiro (de `R$200` até `1` centavo) são
canônicas, então o greedy fornece aqui a contagem mínima verdadeira de cédulas/moedas —
exatamente o que um quiosque com capacidade de cassete limitada por denominação precisa. Os
valores são tratados em centavos como `long` especificamente para manter a aritmética monetária
exata e evitar arredondamento de ponto flutuante.
[`CashDispenserTest`](src/test/java/com/algorithms/greedy/coinchange/applied/CashDispenserTest.java)
cobre um saque misto realista, uma correspondência exata com uma única cédula, um saque de valor
zero, e as proteções de segurança (valores negativos, valores acima do limite por transação do
quiosque).

## Benchmark

```bash
./gradlew :greedy:coin-change:jmh
```

Execução real nesta máquina (JMH 1.37, JDK 26.0.2, 2 iterações de aquecimento + 3 de medição, 1
fork). Ambos os métodos rodam contra o mesmo conjunto canônico de denominações em centavos de BRL,
então sempre concordam quanto à resposta — isto mede o custo de chegar até ela, não a correção:

| Custo | amount=10,000 | amount=500,000 | amount=5,000,000 |
|---|---:|---:|---:|
| greedy | 0.164 µs | 0.158 µs | 0.159 µs |
| DP | 198.98 µs | 14,734.68 µs | 149,802.96 µs |

O greedy permanece estável ao longo de três ordens de grandeza do valor, exatamente como
O(denominações) prevê — ele está executando a mesma passada fixa sobre 13 denominações,
independentemente de quão grande seja o valor. O DP acompanha sua previsão de O(amount) com
nitidez na ponta mais limpa do intervalo: ir de amount=500,000 para amount=5,000,000 é um aumento
de 10x no valor, e o custo medido do DP cresceu **10.16x** — uma correspondência bem próxima. (O
passo menor, 10,000 → 500,000, carrega margens de erro bem mais amplas nesta execução —
provavelmente ruído de aquecimento do JIT nessa ponta do intervalo — então a confirmação mais
limpa de 10x nos tamanhos maiores é a que vale a pena confiar.) Em amount=5,000,000, o DP é
**~942,157x** mais lento do que o greedy para uma resposta que o greedy já tinha.

## Quando não usar

- O conjunto de denominações não é comprovadamente canônico (denominações arbitrárias/
  personalizadas, níveis de pontos de fidelidade, moedas não decimais)? A velocidade do greedy não
  compensa uma resposta que pode estar errada — use `minCoinsDP`, ou prove a canonicidade
  primeiro.
- Precisa saber não apenas a contagem, mas *quais* moedas foram usadas? O caminho de DP deste
  módulo precisaria da mesma técnica de retrocesso pela tabela (backtracking) usada em
  [Knapsack](../../dynamic-programming/knapsack) para recuperar a seleção real, não apenas seu
  tamanho.
- O valor é muito grande e o conjunto de denominações é comprovadamente canônico? O greedy já é a
  escolha certa e rápida — este é precisamente o caso para o qual ele foi construído.

## Cobertura de testes

100% de cobertura de instruções, 100% de cobertura de branches (JaCoCo). Reproduza você mesmo:

```bash
./gradlew :greedy:coin-change:jacocoTestReport
```

Relatório em `greedy/coin-change/build/reports/jacoco/test/html/index.html`.

## Leitura complementar

- Cormen, Leiserson, Rivest & Stein — *Introduction to Algorithms* (CLRS), 3ª/4ª ed., Capítulo 16,
  "Greedy Algorithms" — cobre exatamente essa diferença entre a simplicidade do greedy e sua
  correção condicional.
- Kleinberg & Tardos — *Algorithm Design* — o Capítulo 4 desenvolve as condições gerais sob as
  quais uma escolha greedy é comprovadamente ótima (os estilos de prova "greedy stays ahead" e de
  argumento de troca), o mesmo tipo de raciocínio que separa um conjunto de denominações canônico
  de um em que o greedy apenas acerta por acaso.
