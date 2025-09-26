package org.uff;

import java.io.FileReader;

/**
 * Main — programa de apoio para exercitar o scanner gerado pelo JFlex.
 * Uso: java -cp out org.uff.Main <caminho-do-arquivo-de-entrada>
 * Ex.: java -cp out org.uff.Main src/main/exemplos/teste.calc
 */
public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Uso: java org.uff.Main <arquivo.calc>");
            return;
        }

        FileReader reader = new FileReader(args[0]);
        // CalcLexer lexer = new CalcLexer(reader); // classe gerada pelo JFlex a partir do Calc.flex
        MiniJavaLexer lexer = new MiniJavaLexer(reader); // classe gerada pelo JFlex a partir do MiniJava.flex

        String token;
        while (!(token = lexer.nextToken()).equals("EOF")) {
            System.out.println(token);
        }

        System.out.println("Fim da análise léxica.");
    }
}
