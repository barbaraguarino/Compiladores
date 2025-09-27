# Projetos de Compiladores

Repositório destinado ao desenvolvimento dos projetos da disciplina de Compiladores. 
O objetivo é aplicar os conceitos teóricos da construção de compiladores na prática, utilizando a linguagem Java e a ferramenta JFlex para a análise léxica.

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
A construção das fases iniciais de um compilador para a linguagem "Mini-Java", um subconjunto simplificado da linguagem Java. As fases a serem implementadas incluem:
1.  **Analisador Léxico:** Reconhecimento dos tokens da linguagem (palavras-chave, identificadores, números, símbolos, etc.).
2.  **Analisador Sintático:** Verificação da estrutura gramatical do código.
3.  **Analisador Semântico:** Verificação de tipos e outras regras de significado.

#### Gramática [EBNF da Mini-Java](src/main/resources/EBNF/EBNF_MiniJava)

A gramática formal da Mini-Java, que define a estrutura sintática da linguagem, foi adaptada para ser consistente com os tokens gerados pelo nosso scanner.

```ebnf
Goal         = MainClass, { ClassDecl }, EOF ;

MainClass    = CLASS, ID, LBRACE,
                 PUBLIC, STATIC, VOID, MAIN, LPAREN, STRING, LBRACKET, RBRACKET, ID, RPAREN,
                 LBRACE, Statement, RBRACE,
               RBRACE ;

ClassDecl    = CLASS, ID, [ EXTENDS, ID ], LBRACE, { VarDecl }, { MethodDecl }, RBRACE ;

VarDecl      = Type, ID, SEMICOLON ;

MethodDecl   = PUBLIC, Type, ID, LPAREN, [ FormalList ], RPAREN, LBRACE,
                 { VarDecl },
                 { Statement },
                 RETURN, Expression, SEMICOLON,
               RBRACE ;

FormalList   = Type, ID, { COMMA, Type, ID } ;

Type         = (INT, LBRACKET, RBRACKET)
             | BOOLEAN
             | INT
             | ID ;

Statement    = LBRACE, { Statement }, RBRACE
             | IF, LPAREN, Expression, RPAREN, Statement, ELSE, Statement
             | WHILE, LPAREN, Expression, RPAREN, Statement
             | SYSTEM_OUT_PRINTLN, LPAREN, Expression, RPAREN, SEMICOLON
             | ID, ASSIGN, Expression, SEMICOLON
             | ID, LBRACKET, Expression, RBRACKET, ASSIGN, Expression, SEMICOLON ;

Expression   = LogicalAndExpr ;

LogicalAndExpr     = RelationalExpr, { AND, RelationalExpr } ;
RelationalExpr     = AdditiveExpr, { (LESS_THAN | NOT_EQUAL), AdditiveExpr } ;
AdditiveExpr       = MultiplicativeExpr, { (PLUS | MINUS), MultiplicativeExpr } ;
MultiplicativeExpr = PrimaryExpr, { (TIMES | DIVIDE), PrimaryExpr } ;

PrimaryExpr  = INTEGER_LITERAL
             | TRUE
             | FALSE
             | ID
             | THIS
             | (NEW, INT, LBRACKET, Expression, RBRACKET)
             | (NEW, ID, LPAREN, RPAREN)
             | (LPAREN, Expression, RPAREN)
             | (PrimaryExpr, ( (DOT, LENGTH) | (LBRACKET, Expression, RBRACKET) | (DOT, ID, LPAREN, [ ExpList ], RPAREN) )) ;

ExpList      = Expression, { COMMA, Expression } ;
```

## Tecnologias Utilizadas

* **Linguagem:** [Java](https://www.java.com/) (JDK 21)
* **Geração de Scanner:** [JFlex](https://jflex.de/)
* **Gerenciamento de Dependências:** [Maven](https://maven.apache.org/)
* **IDEA:** [IntelliJ](https://www.jetbrains.com/idea/)

## Como Executar o Projeto

Siga as instruções abaixo para compilar e executar os scanners.

### Pré-requisitos

-   [JDK (Java Development Kit)](https://www.oracle.com/java/technologies/downloads/) - Versão 21 ou superior.
-   [Apache Maven](https://maven.apache.org/download.cgi) - Apenas para execução via linha de comando. Não é necessário instalar separadamente se for usar o IntelliJ IDEA.

### 1. Instalação (Clone do Repositório)

Primeiro, clone o repositório para a sua máquina local e entre na branch correta:
```bash
    git clone https://github.com/barbaraguarino/Compiladores.git
    cd Compiladores
    git checkout minijava
```

### 2. Executando via Linha de Comando

1. **Compile o projeto com Maven:**
    Este comando irá invocar o JFlex para gerar as classes `CalcLexer.java` e `MiniJavaLexer.java` e, em seguida, compilar todo o código-fonte.
    ```bash
    mvn clean compile
    ```

2. **Execute o Scanner da Calculadora:**
    ```bash
    java -cp target/classes org.uff.calculadora.MainCalculadora src/main/resources/exemplos/calculadora/entradas/calc_inicial.calc
    ```

3. **Execute o Scanner da Mini-Java:**
    ```bash
    java -cp target/classes org.uff.minijava.MainMiniJava src/main/resources/exemplos/minijava/entradas/Fatorial.mjava
    ```

### 3. Executando pelo IntelliJ IDEA

Se você estiver usando o IntelliJ IDEA, não é necessário instalar o Apache Maven separadamente, pois a IDE já vem com uma versão embutida e se integra perfeitamente ao projeto.

1. **Abra o Projeto:**
    * Vá em `File` > `Open...` e selecione a pasta raiz do projeto (`Compiladores`).
    * O IntelliJ irá detectar automaticamente o arquivo `pom.xml` e configurar o projeto.

2. **Compile e Gere os Scanners:**
    * No canto direito da IDE, abra a aba **Maven**.
    * Expanda `compiladores` > `Lifecycle`.
    * Dê um duplo-clique em **`compile`**. Este passo executa o JFlex para gerar os arquivos `CalcLexer.java` e `MiniJavaLexer.java` e compila todo o projeto. Você só precisa fazer isso uma vez ou sempre que modificar os arquivos `.flex`.

3. **Execute os Arquivos de Teste:**
    Para executar os testes, você precisa criar uma "Run Configuration" para cada `main`.

    * **Para a Calculadora:**
        1.  Abra o arquivo `MainCalculadora.java`.
        2.  Clique no ícone de "Play" (▶️) ao lado do método `main` e selecione **"Modify Run Configuration..."**.
        3.  No campo **"Program arguments"**, insira o caminho para um arquivo de teste, por exemplo: `src/main/resources/exemplos/calculadora/entradas/calc_simples.calc`.
        4.  Clique em `OK`. Agora você pode executar essa configuração pelo botão de Play no topo da tela.

    * **Para a Mini-Java:**
        1.  Abra o arquivo `MainMiniJava.java`.
        2.  Repita o processo: clique no "Play" (▶️) ao lado do `main` e selecione **"Modify Run Configuration..."**.
        3.  No campo **"Program arguments"**, insira o caminho para um arquivo de teste, por exemplo: `src/main/resources/exemplos/minijava/entradas/Fatorial.mjava`.
        4.  Clique em `OK`.

Para testar outros arquivos, basta editar o campo "Program arguments" na configuração correspondente ou criar novas configurações.

## Autores

* [Barbara Nascimento](https://github.com/barbaraguarino)
* [Pedro Muniz](https://github.com/muniz034)
* [Giovana Beltrame](https://github.com/grbeltrame)