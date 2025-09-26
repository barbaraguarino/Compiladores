/*
 * MiniJava.flex — Especificação JFlex para o scanner do MiniJava
 * Objetivo: reconhecer palavras-chave, identificadores, números,
 * símbolos de pontuação e operadores da linguagem MiniJava.
 */

package org.uff; // gera em org/uff/MiniJavaLexer.java

%%

// Diretivas
%class MiniJavaLexer
%unicode
%line
%column
%public
%final
%type String
%function nextToken

/* =========================
Macros
========================= */
DIGIT      = [0-9]
LETTER     = [a-zA-Z]
IDENT      = {LETTER} ({LETTER}|{DIGIT}|_)*
NUMBER     = {DIGIT}+
WS         = [\t\r\n\f ]+

%%

/* =========================
Regras léxicas
========================= */

{WS} { /* ignora espaços/brancos */ }

/* --- Palavras-chave --- */
"class"       { return "CLASS"; }
"public"      { return "PUBLIC"; }
"static"      { return "STATIC"; }
"void"        { return "VOID"; }
"main"        { return "MAIN"; }
"String"      { return "STRING"; }
"extends"     { return "EXTENDS"; }
"return"      { return "RETURN"; }
"int"         { return "INT"; }
"boolean"     { return "BOOLEAN"; }
"if"          { return "IF"; }
"else"        { return "ELSE"; }
"while"       { return "WHILE"; }
"System.out.println" { return "PRINT"; }
"true"        { return "TRUE"; }
"false"       { return "FALSE"; }
"this"        { return "THIS"; }
"new"         { return "NEW"; }

/* --- Símbolos --- */
"("           { return "LPAREN"; }
")"           { return "RPAREN"; }
"{"           { return "LBRACE"; }
"}"           { return "RBRACE"; }
"["           { return "LBRACK"; }
"]"           { return "RBRACK"; }
";"           { return "SEMICOLON"; }
","           { return "COMMA"; }
"."           { return "DOT"; }
"="           { return "ASSIGN"; }

/* --- Operadores --- */
"&&"          { return "AND"; }
"<"           { return "LT"; }
"+"           { return "PLUS"; }
"-"           { return "MINUS"; }
"*"           { return "TIMES"; }
"!"           { return "NOT"; }
"=="           { return "EQ"; }
"!="           { return "NEQ"; }
"<="           { return "LEQ"; }
">="           { return "GEQ"; }

/* --- Literais --- */
{NUMBER}      { return "NUMBER(" + yytext() + ")"; }
{IDENT}       { return "IDENT(" + yytext() + ")"; }

/* --- Comentários --- */
// comentário de linha //
"//".*        { /* ignora até o fim da linha */ }
// comentário de bloco /* ... */
"/*"([^*]|\*+[^*/])*\*+"/" { /* ignora bloco */ }

/* --- Fim de arquivo --- */
<<EOF>>       { return "EOF"; }

/* --- Erros --- */
. {
  throw new RuntimeException(
    "Caractere inválido: " + yytext() +
    " em linha " + (yyline+1) + ", coluna " + (yycolumn+1)
  );
}
