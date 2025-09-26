package org.uff;

import java.io.FileReader;

/**
 * Main — programa de apoio para exercitar o scanner gerado pelo JFlex.
 * Uso: java -cp out org.uff.Calculadora <caminho-do-arquivo-de-entrada>
 * Ex.: java -cp out org.uff.Calculadora src/main/exemplos/teste.calc
 */
public class Calculadora {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Uso: java org.uff.Calculadora <arquivo.calc>");
            return;
        }

        FileReader reader = new FileReader(args[0]);
        CalcLexer lexer = new CalcLexer(reader);

        String token;
        while (!(token = lexer.nextToken()).equals("EOF")) {
            System.out.println(token);
        }

        System.out.println("Fim da análise léxica.");
    }
}
