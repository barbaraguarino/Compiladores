package org.uff.minijava;

%%

%class MiniJavaLexer
%unicode
%line
%column
%public
%final
%type Symbol
%function nextToken

LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator} | [ \t\f]

Identifier = [:letter:][:jletterdigit:]*
IntegerLiteral = 0 | [1-9][0-9]*

// Comentários
Comment = {TraditionalComment} | {EndOfLineComment}
TraditionalComment = "/*" [^*] ~"*/" | "/*" "*"+ "/"
EndOfLineComment = "//" .* {LineTerminator}?
%%

// Ignora comentários e espaços em branco
{WhiteSpace} { /* Ignorar */ }
{Comment}    { /* Ignorar */ }

// Palavras-chave
"class"                { return new Symbol(Token.CLASS, yyline + 1, yycolumn + 1, yytext()); }
"public"               { return new Symbol(Token.PUBLIC, yyline + 1, yycolumn + 1, yytext()); }
"static"               { return new Symbol(Token.STATIC, yyline + 1, yycolumn + 1, yytext()); }
"void"                 { return new Symbol(Token.VOID, yyline + 1, yycolumn + 1, yytext()); }
"main"                 { return new Symbol(Token.MAIN, yyline + 1, yycolumn + 1, yytext()); }
"String"               { return new Symbol(Token.STRING, yyline + 1, yycolumn + 1, yytext()); }
"extends"              { return new Symbol(Token.EXTENDS, yyline + 1, yycolumn + 1, yytext()); }
"return"               { return new Symbol(Token.RETURN, yyline + 1, yycolumn + 1, yytext()); }
"int"                  { return new Symbol(Token.INT, yyline + 1, yycolumn + 1, yytext()); }
"boolean"              { return new Symbol(Token.BOOLEAN, yyline + 1, yycolumn + 1, yytext()); }
"if"                   { return new Symbol(Token.IF, yyline + 1, yycolumn + 1, yytext()); }
"else"                 { return new Symbol(Token.ELSE, yyline + 1, yycolumn + 1, yytext()); }
"while"                { return new Symbol(Token.WHILE, yyline + 1, yycolumn + 1, yytext()); }
"System.out.println"   { return new Symbol(Token.SYSTEM_OUT_PRINTLN, yyline + 1, yycolumn + 1, yytext()); }
"length"               { return new Symbol(Token.LENGTH, yyline + 1, yycolumn + 1, yytext()); }
"true"                 { return new Symbol(Token.TRUE, yyline + 1, yycolumn + 1, yytext()); }
"false"                { return new Symbol(Token.FALSE, yyline + 1, yycolumn + 1, yytext()); }
"this"                 { return new Symbol(Token.THIS, yyline + 1, yycolumn + 1, yytext()); }
"new"                  { return new Symbol(Token.NEW, yyline + 1, yycolumn + 1, yytext()); }

// Literais e Identificadores
{IntegerLiteral}       { return new Symbol(Token.INTEGER_LITERAL, yyline + 1, yycolumn + 1, yytext()); }
{Identifier}           { return new Symbol(Token.ID, yyline + 1, yycolumn + 1, yytext()); }

// Operadores
"&&"                   { return new Symbol(Token.AND, yyline + 1, yycolumn + 1, yytext()); }
"<="                   { return new Symbol(Token.LESS_THAN_EQUAL, yyline + 1, yycolumn + 1, yytext()); }
">="                   { return new Symbol(Token.GREATER_THAN_EQUAL, yyline + 1, yycolumn + 1, yytext()); }
"=="                   { return new Symbol(Token.EQUAL, yyline + 1, yycolumn + 1, yytext()); }
"!="                   { return new Symbol(Token.NOT_EQUAL, yyline + 1, yycolumn + 1, yytext()); }
"<"                    { return new Symbol(Token.LESS_THAN, yyline + 1, yycolumn + 1, yytext()); }
">"                    { return new Symbol(Token.GREATER_THAN, yyline + 1, yycolumn + 1, yytext()); }
"+"                    { return new Symbol(Token.PLUS, yyline + 1, yycolumn + 1, yytext()); }
"-"                    { return new Symbol(Token.MINUS, yyline + 1, yycolumn + 1, yytext()); }
"*"                    { return new Symbol(Token.TIMES, yyline + 1, yycolumn + 1, yytext()); }
"/"                    { return new Symbol(Token.DIVIDE, yyline + 1, yycolumn + 1, yytext()); }
"="                    { return new Symbol(Token.ASSIGN, yyline + 1, yycolumn + 1, yytext()); }

// Separadores
"("                    { return new Symbol(Token.LPAREN, yyline + 1, yycolumn + 1, yytext()); }
")"                    { return new Symbol(Token.RPAREN, yyline + 1, yycolumn + 1, yytext()); }
"{"                    { return new Symbol(Token.LBRACE, yyline + 1, yycolumn + 1, yytext()); }
"}"                    { return new Symbol(Token.RBRACE, yyline + 1, yycolumn + 1, yytext()); }
"["                    { return new Symbol(Token.LBRACKET, yyline + 1, yycolumn + 1, yytext()); }
"]"                    { return new Symbol(Token.RBRACKET, yyline + 1, yycolumn + 1, yytext()); }
";"                    { return new Symbol(Token.SEMICOLON, yyline + 1, yycolumn + 1, yytext()); }
"."                    { return new Symbol(Token.DOT, yyline + 1, yycolumn + 1, yytext()); }
","                    { return new Symbol(Token.COMMA, yyline + 1, yycolumn + 1, yytext()); }

<<EOF>>                { return new Symbol(Token.EOF, yyline + 1, yycolumn + 1, "EOF"); }

// Tratamento de Erros
.                      { return new Symbol(Token.ERROR, yyline + 1, yycolumn + 1, yytext()); }