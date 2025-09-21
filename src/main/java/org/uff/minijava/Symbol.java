package org.uff.minijava;

public final class Symbol {
    private final Token token;
    private final String lexeme;
    private final int line;
    private final int column;

    public Symbol(Token token, int line, int column, String lexeme) {
        this.token = token;
        this.lexeme = lexeme;
        this.line = line;
        this.column = column;
    }

    public Token getToken() {
        return token;
    }

    @Override
    public String toString() {
        return String.format("<Token: %-20s | Linha: %-4d | Coluna: %-4d | Lexema: '%s'>",
                token, line, column, lexeme);
    }
}