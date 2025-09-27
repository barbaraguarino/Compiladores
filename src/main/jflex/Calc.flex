/*
* Calc.flex — Especificação JFlex para o scanner da Calculadora
* Objetivo: reconhecer números, parênteses e operadores +, -, *, /, // (divisão inteira) e ** (potência).
* Observação importante: no JFlex, quando várias regras podem casar, vale a regra que casa a **string mais longa**;
* em caso de empate, vale a **primeira** regra definida. Por isso "**" e "//" vêm **antes** de "*" e "/".
*/


package org.uff.calculadora;

%%


// Diretivas do JFlex
%class CalcLexer
%unicode
%line
%column
%public
%final
%type String
%function nextToken


/* =========================
Macros (para reuso)
========================= */
DIGIT = [0-9]
NUMBER = {DIGIT}+ ( \. {DIGIT}+ )? // inteiro OU decimal simples (ex.: 3, 3.14)
WS = [\t\r\n\f ]+ // whitespace (será ignorado)


%%
/* =========================
Regras léxicas (ordem importa!)
========================= */


{WS} { /* ignora espaços/brancos */ }


"(" { return "LPAREN"; }
")" { return "RPAREN"; }


"**" { return "POW"; } // precisa vir antes de "*"
"//" { return "INTDIV"; } // precisa vir antes de "/"
"*" { return "STAR"; }
"/" { return "SLASH"; }
"+" { return "PLUS"; }
"-" { return "MINUS"; }


{NUMBER} { return "NUMBER(" + yytext() + ")"; }


// (opcional) comentários de linha iniciados por '#'. Deixamos '#' para não conflitar com '//' (operador)
"#".* { /* ignora até o fim da linha */ }


<<EOF>> { return "EOF"; } // fim de arquivo sinaliza término da análise


. { // qualquer outro caractere é inválido
throw new RuntimeException(
"Caractere inválido: " + yytext() +
" em linha " + (yyline+1) + ", coluna " + (yycolumn+1)
);
}