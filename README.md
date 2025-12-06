# Projetos de Compiladores

Repositório destinado ao desenvolvimento dos projetos da disciplina de Compiladores. 
O objetivo é aplicar os conceitos teóricos da construção de compiladores na prática, utilizando a linguagem Java e a ferramenta JFlex para a análise léxica e Java CUP para análise sintática.

## Conteúdo

Este repositório contém a implementação de dois projetos principais:

### Parte 1: Scanner para uma Calculadora
Um analisador léxico (scanner) para uma linguagem de calculadora simples. O scanner é responsável por reconhecer os seguintes tokens:
- **Números:** Inteiros e de ponto flutuante.
- **Operadores:** `(`, `)`, `+`, `-`, `*`, `/`, `//` (divisão inteira), `**` (potência).
- **Erros:** Qualquer outro símbolo que não pertença à linguagem.

#### Gramática [EBNF da Calculadora](src/main/resources/EBNF/EBNF_Calculadora)

A estrutura de uma expressão válida para a calculadora é definida pela seguinte gramática formal.

```ebnf
Goal      = Expressao, EOF ;

Expressao = Termo, { (PLUS | MINUS), Termo } ;

Termo     = Fator, { (TIMES | DIVIDE | INTEGER_DIVIDE), Fator } ;

Fator     = Base, [ POWER, Fator ] ;

Base      = NUMBER
          | LPAREN, Expressao, RPAREN ;
```

### Parte 2: Compilador para Mini-Java
Implementação das fases de análise léxica e sintática de um compilador para a linguagem "Mini-Java", um subconjunto simplificado de Java.

O projeto abrange:
1.  **Analisador Léxico (Scanner):** Reconhecimento de tokens (palavras-chave, identificadores, literais) gerado via **JFlex**.
2.  **Analisador Sintático (Parser):** Verificação da estrutura gramatical e geração da árvore de derivação (trace) utilizando **Java CUP**.
3.  **Interface Interativa:** Seletor de arquivos via terminal para facilitar os testes.
4.  **Tratamento de Erros:** Reporte detalhado de erros léxicos e sintáticos (linha e coluna).


#### Gramática [EBNF da Mini-Java](src/main/resources/EBNF/EBNF_MiniJava)

A gramática formal da Mini-Java, que define a estrutura sintática da linguagem, foi adaptada para ser consistente com os tokens gerados pelo nosso scanner.

```ebnf
Goal         = MainClass { ClassDecl } EOF ;

MainClass    = CLASS ID LBRACE
                 PUBLIC STATIC VOID MAIN LPAREN STRING LBRACKET RBRACKET ID RPAREN
                 LBRACE Statement RBRACE
               RBRACE ;

ClassDecl    = CLASS ID [ EXTENDS ID ] LBRACE { VarDecl } { MethodDecl } RBRACE ;

VarDecl      = Type ID SEMICOLON ;

MethodDecl   = PUBLIC Type ID LPAREN [ FormalList ] RPAREN LBRACE
                 { VarDecl }
                 { Statement }
                 RETURN Expression SEMICOLON
               RBRACE ;

FormalList   = Type ID { COMMA Type ID } ;

Type         = (INT LBRACKET RBRACKET)
             | BOOLEAN
             | INT
             | ID ;

Statement    = LBRACE { Statement } RBRACE
             | IF LPAREN Expression RPAREN Statement ELSE Statement
             | WHILE LPAREN Expression RPAREN Statement
             | SYSTEM_OUT_PRINTLN LPAREN Expression RPAREN SEMICOLON
             | ID ASSIGN Expression SEMICOLON
             | ID LBRACKET Expression RBRACKET ASSIGN Expression SEMICOLON ;

Expression   = LogicalAndExpr ;

NegationExpr       = NEGATION PrimaryExpr ;
LogicalAndExpr     = (NegationExpr | RelationalExpr) { (AND | OR) (NegationExpr | RelationalExpr) } ;
RelationalExpr     = (NegationExpr | AdditiveExpr) { (LESS_THAN | GREATER_THAN | EQUAL | NOT_EQUAL | LESS_THAN_EQUAL | GREATER_THAN_EQUAL) (NegationExpr | AdditiveExpr) } ;
AdditiveExpr       = MultiplicativeExpr { (PLUS | MINUS) MultiplicativeExpr } ;
MultiplicativeExpr = PrimaryExpr { (TIMES | DIVIDE) PrimaryExpr } ;

PrimaryExpr  = INTEGER_LITERAL
             | TRUE
             | FALSE
             | ID
             | THIS
             | (NEW INT LBRACKET Expression RBRACKET)
             | (NEW ID LPAREN RPAREN)
             | (LPAREN Expression RPAREN)
             | (NEGATION PrimaryExpr)
             | (PrimaryExpr (DOT LENGTH | LBRACKET Expression RBRACKET | DOT ID LPAREN [ ExpList ] RPAREN)) ;

ExpList      = Expression { COMMA Expression } ;
```

## Tecnologias Utilizadas

* **Linguagem:** [Java](https://www.java.com/) (JDK 21)
* **Análise Léxica:** [JFlex](https://jflex.de/)
* **Análise Sintática:** [Java CUP](http://www2.cs.tum.edu/projects/cup/)
* **Gerenciamento de Dependências:** [Maven](https://maven.apache.org/)
* **IDE:** [IntelliJ IDEA](https://www.jetbrains.com/idea/)

## Como Executar o Projeto

Siga as instruções abaixo para compilar e executar os scanners.

### Pré-requisitos

-   [JDK (Java Development Kit)](https://www.oracle.com/java/technologies/downloads/) - Versão 21 ou superior.
-   [Apache Maven](https://maven.apache.org/download.cgi) - Apenas para execução via linha de comando. Não é necessário instalar separadamente se for usar o IntelliJ IDEA.

### Instalação (Clone do Repositório)

Primeiro, clone o repositório para a sua máquina local e entre na branch correta:
```bash
    git clone https://github.com/barbaraguarino/Compiladores.git
    cd Compiladores
```

### Executando via Linha de Comando

**Compile o projeto com Maven:**

Este comando irá invocar o JFlex para gerar as classes `CalcLexer.java` e `MiniJavaLexer.java` e, em seguida, compilar todo o código-fonte.
```bash
  mvn clean package -DskipTests
```

#### **Execute o Scanner da Calculadora:**
Execute para cada caso teste na pasta de entradas

```bash
  java -cp target/classes org.uff.calculadora.MainCalculadora src/main/resources/calculadora/entradas/calc_complexa.calc
```

```bash
  java -cp target/classes org.uff.calculadora.MainCalculadora src/main/resources/calculadora/entradas/calc_erro.calc
```

```bash
  java -cp target/classes org.uff.calculadora.MainCalculadora src/main/resources/calculadora/entradas/calc_inicial.calc
```

```bash
  java -cp target/classes org.uff.calculadora.MainCalculadora src/main/resources/calculadora/entradas/calc_simples.calc
```

#### Executando o Compilador da Mini-Java

O `MainMiniJava` realiza a análise léxica e sintática. Ao executar, o programa gera automaticamente dois arquivos de saída para cada entrada, organizados na pasta `saidas`:
- `saidas/tokens/NomeArquivo_tokens.txt`: Lista de tokens reconhecidos.
- `saidas/arvores/NomeArquivo_arvore.txt`: Trace da análise sintática ou mensagens de erro.

Existem duas formas de executar:

##### Opção A: Modo Interativo (Recomendado)
Se você executar sem argumentos, o programa abrirá um menu no terminal listando os arquivos da pasta de exemplos para você escolher.

###### No Linux/Mac

```bash
  java -cp "target/classes:target/dependency/*" org.uff.minijava.MainMiniJava
```

###### No Windows

```bash
  java -cp "target/classes;target/dependency/*" org.uff.minijava.MainMiniJava
```

##### Opção B: Via Linha de Comando (Arquivo Específico)

Você pode passar o caminho do arquivo diretamente como argumento.

**Nota**: O comando abaixo assume que você possui o .jar do java-cup-runtime no seu repositório local Maven ou configurado no classpath. Ajuste o caminho conforme seu ambiente.

###### No Linux/Mac

```bash
  # Sintaxe: java -cp <classpath> org.uff.minijava.MainMiniJava <caminho-do-arquivo>
  java -cp "target/classes:target/dependency/*" org.uff.minijava.MainMiniJava src/main/resources/minijava/entradas/BinarySearch.mjava
```

###### No Windows

```bash
  # Sintaxe: java -cp <classpath> org.uff.minijava.MainMiniJava <caminho-do-arquivo>
  java -cp "target/classes;target/dependency/*" org.uff.minijava.MainMiniJava src/main/resources/minijava/entradas/BinarySearch.mjava
```

**Exemplos de testes disponíveis na pasta `entradas`:**

- BinarySearch.mjava, BubbleSort.mjava, Fatorial.mjava, QuickSort.mjava, TreeVisitor.mjava, etc.
- Testes de Erro: erro_lexico.mjava e erro_sintatico.mjava (para verificar o tratamento de exceções).
 
### Executando pelo IntelliJ IDEA

Se você estiver usando o IntelliJ IDEA, não é necessário instalar o Apache Maven separadamente, pois a IDE já vem com uma versão embutida e se integra perfeitamente ao projeto.

1. **Abra o Projeto:**
    * Vá em `File` > `Open...` e selecione a pasta raiz do projeto (`Compiladores`).
    * O IntelliJ irá detectar automaticamente o arquivo `pom.xml` e configurar o projeto.

2. **Compile e Gere os Scanners:**
    * No canto direito da IDE, abra a aba **Maven**.
    * Expanda `compiladores` > `Lifecycle`.
    * Dê um duplo-clique em **`clean`** e em seguida duplo clique em **`packeage`**. Este passo executa o JFlex para gerar os arquivos `CalcLexer.java` e `MiniJavaLexer.java` e compila todo o projeto. Você só precisa fazer isso uma vez ou sempre que modificar os arquivos `.flex` e `.cup`.

3. **Execute os Arquivos de Teste:**
    Para executar os testes, você precisa criar uma "Run Configuration" para cada `main`.

    * **Para a Calculadora:**
        1.  Abra o arquivo `MainCalculadora.java`.
        2.  Clique no ícone de "Play" (▶️) ao lado do método `main` e selecione **"Modify Run Configuration..."**.
        3.  No campo **"Program arguments"**, insira o caminho para um arquivo de teste, por exemplo: `src/main/resources/calculadora/entradas/calc_simples.calc`.
        4.  Clique em `OK`. Agora você pode executar essa configuração pelo botão de Play no topo da tela.

    * **Para a Mini-Java:**
        1.  Abra o arquivo `MainMiniJava.java`.
        2.  Repita o processo: clique no "Play" (▶️) ao lado do `main` e selecione **"Modify Run Configuration..."**.
        3.  No campo **"Program arguments"**, insira o caminho para um arquivo de teste, por exemplo: `src/main/resources/minijava/entradas/Fatorial.mjava`.
        4.  Clique em `OK`.

Para testar outros arquivos, basta editar o campo "Program arguments" na configuração correspondente ou criar novas configurações.

## Autores

* [Barbara Nascimento](https://github.com/barbaraguarino)
* [Pedro Muniz](https://github.com/muniz034)
* [Giovana Beltrame](https://github.com/grbeltrame)
