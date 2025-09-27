package org.uff.minijava;

import java.io.FileReader;

public class MainMiniJava {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Uso: java org.uff.minijava.MainMiniJava <arquivo.mjava>");
            return;
        }

        try (FileReader reader = new FileReader(args[0])) {
            MiniJavaLexer lexer = new MiniJavaLexer(reader);
            Symbol token;
            do {
                token = lexer.nextToken();
                System.out.println(token);
            } while (token != null && token.getToken() != Token.EOF);

            System.out.println("Fim da análise léxica.");

        } catch (Exception e) {
            System.err.println("Erro ao processar o arquivo: " + e.getMessage());
        }
    }
}