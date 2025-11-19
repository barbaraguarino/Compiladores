package org.uff.minijava;
import java_cup.runtime.Symbol;

%%

%class MiniJavaLexer
%unicode
%line
%column
%cup

%{
    /*
       Cria um novo java_cup.runtime.Symbol com informações de localização.
       O CUP usa isso para reportar erros com precisão no Parser.
    */
    private Symbol symbol(int type) {
        return new Symbol(type, yyline + 1, yycolumn + 1);
    }

    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline + 1, yycolumn + 1, value);
    }
%}

/* DEFINIÇÕES E MACROS */

/* Fim de linha e Espaços em branco */
LineTerminator = \r|\n|\r\n
WhiteSpace     = {LineTerminator} | [ \t\f]

/* Identificadores e Literais Numéricos */
Identifier     = [:jletter:][:jletterdigit:]*
IntegerLiteral = 0 | [1-9][0-9]*

/* Comentários */
Comment            = {TraditionalComment} | {EndOfLineComment}
TraditionalComment = "/*" [^*] ~"*/" | "/*" "*"+ "/"
EndOfLineComment   = "//" .* {LineTerminator}?

%%

/* REGRAS LÉXICAS */

/* Ignorar espaços em branco e comentários */
{WhiteSpace} { /* ignora */ }
{Comment}    { /* ignora */ }


/* Palavras-chave */

/* Estrutura de Classe e Método */
"class"      { return symbol(sym.CLASS); }
"extends"    { return symbol(sym.EXTENDS); }
"public"     { return symbol(sym.PUBLIC); }
"static"     { return symbol(sym.STATIC); }
"void"       { return symbol(sym.VOID); }
"main"       { return symbol(sym.MAIN); }
"return"     { return symbol(sym.RETURN); }

/* Tipos Primitivos e Especiais */
"int"        { return symbol(sym.INT); }
"boolean"    { return symbol(sym.BOOLEAN); }
"String"     { return symbol(sym.STRING); }

/* Controle de Fluxo */
"if"         { return symbol(sym.IF); }
"else"       { return symbol(sym.ELSE); }
"while"      { return symbol(sym.WHILE); }

/* Constantes e Referências */
"true"       { return symbol(sym.TRUE); }
"false"      { return symbol(sym.FALSE); }
"null"       { return symbol(sym.NULL); }
"this"       { return symbol(sym.THIS); }
"new"        { return symbol(sym.NEW); }

/* Built-ins */
"System.out.println" { return symbol(sym.SYSTEM_OUT_PRINTLN); }
"length"             { return symbol(sym.LENGTH); }


/* Operadores */

/* Lógicos */
"&&"         { return symbol(sym.AND); }
"||"         { return symbol(sym.OR); }
"!"          { return symbol(sym.NEGATION); }

/* Relacionais */
"<"          { return symbol(sym.LESS_THAN); }
">"          { return symbol(sym.GREATER_THAN); }
"<="         { return symbol(sym.LESS_THAN_EQUAL); }
">="         { return symbol(sym.GREATER_THAN_EQUAL); }
"=="         { return symbol(sym.EQUAL); }
"!="         { return symbol(sym.NOT_EQUAL); }

/* Aritméticos e Atribuição */
"+"          { return symbol(sym.PLUS); }
"-"          { return symbol(sym.MINUS); }
"*"          { return symbol(sym.TIMES); }
"/"          { return symbol(sym.DIVIDE); }
"="          { return symbol(sym.ASSIGN); }


/* Separadores e Pontuação */
"("          { return symbol(sym.LPAREN); }
")"          { return symbol(sym.RPAREN); }
"{"          { return symbol(sym.LBRACE); }
"}"          { return symbol(sym.RBRACE); }
"["          { return symbol(sym.LBRACKET); }
"]"          { return symbol(sym.RBRACKET); }
";"          { return symbol(sym.SEMICOLON); }
"."          { return symbol(sym.DOT); }
","          { return symbol(sym.COMMA); }


/* Identificadores e Números */

{IntegerLiteral} { return symbol(sym.INTEGER_LITERAL, Integer.parseInt(yytext())); }
{Identifier}     { return symbol(sym.ID, yytext()); }


/* Fim de Arquivo e Erros */

<<EOF>>      { return symbol(sym.EOF); }

/* Qualquer caractere não reconhecido pelas regras acima gera erro */
.            { throw new Error("Caractere ilegal <" + yytext() + "> na linha " + (yyline+1) + ", coluna " + (yycolumn+1)); }