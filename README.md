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

### Parte 2: Compilador para Mini-Java
A construção das fases iniciais de um compilador para a linguagem "Mini-Java", um subconjunto simplificado da linguagem Java. As fases a serem implementadas incluem:
1.  **Analisador Léxico:** Reconhecimento dos tokens da linguagem (palavras-chave, identificadores, números, símbolos, etc.).
2.  **Analisador Sintático:** Verificação da estrutura gramatical do código.
3.  **Analisador Semântico:** Verificação de tipos e outras regras de significado.

## Tecnologias Utilizadas

* **Linguagem:** [Java](https://www.java.com/) (JDK 21)
* **Geração de Scanner:** [JFlex](https://jflex.de/)
* **Gerenciamento de Dependências:** [Maven](https://maven.apache.org/)

## Como Executar o Projeto

Siga as instruções abaixo para compilar e executar os scanners.

### Pré-requisitos

-   [JDK (Java Development Kit)](https://www.oracle.com/java/technologies/downloads/) - Versão 21 ou superior.
-   [Apache Maven](https://maven.apache.org/download.cgi) - Para gerenciamento do projeto e dependências.

### Passos

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/barbaraguarino/Compiladores.git](https://github.com/barbaraguarino/Compiladores.git)
    cd Compiladores
    git checkout minijava
    ```

2.  **Compile o projeto com Maven:**
    Este comando irá invocar o JFlex para gerar as classes `CalcLexer.java` e `MiniJavaLexer.java` e, em seguida, compilar todo o código-fonte.
    ```bash
    mvn clean compile
    ```

3.  **Execute o Scanner da Calculadora:**
    ```bash
    java -cp target/classes org.uff.calculadora.MainCalculadora src/main/resources/calculadora/entradas/calc_inicial.calc
    ```

4.  **Execute o Scanner da Mini-Java:**
    ```bash
    java -cp target/classes org.uff.minijava.MainMiniJava src/main/resources/minijava/entradas/Fatorial.mjava
    ```

## Autores

* [Barbara Nascimento](https://github.com/barbaraguarino)
* [Pedro Muniz](https://github.com/muniz034)
* [Giovana Beltrame](https://github.com/grbeltrame)
