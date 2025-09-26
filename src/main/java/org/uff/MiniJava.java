package org.uff;

import java.io.FileReader;

/**
 * Main — programa de apoio para exercitar o scanner gerado pelo JFlex.
 * Uso: java -cp out org.uff.MiniJava <caminho-do-arquivo-de-entrada>
 * Ex.: java -cp out org.uff.MiniJava src/main/exemplos/teste.minijava
 */
public class MiniJava {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Uso: java org.uff.MiniJava <arquivo.calc>");
            return;
        }

        FileReader reader = new FileReader(args[0]);
        MiniJavaLexer lexer = new MiniJavaLexer(reader);

        String token;
        while (!(token = lexer.nextToken()).equals("EOF")) {
            System.out.println(token);
        }

        System.out.println("Fim da análise léxica.");
    }
}
