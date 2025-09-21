package org.uff.calculadora;


import java.io.FileReader;


/**
 * Main — programa de apoio para exercitar o scanner gerado pelo JFlex.
 * Uso: java -cp out org.uff.calculadora.Main <caminho-do-arquivo-de-entrada>
 * Ex.: java -cp out org.uff.calculadora.Main src/main/exemplos/teste.calc
 */
public class MainCalculadora {
    public static void main(String[] args) throws Exception {
        // Verifica se o usuário passou o caminho do arquivo de entrada
        if (args.length == 0) {
            System.out.println("Uso: java org.uff.calculadora.Main <arquivo.calc>");
            return;
        }


        // Abre o arquivo informado e passa o reader para o scanner
        FileReader reader = new FileReader(args[0]);
        CalcLexer lexer = new CalcLexer(reader); // classe gerada pelo JFlex a partir do Calc.flex


        // Varre tokens até encontrar EOF
        String token;
        while (!(token = lexer.nextToken()).equals("EOF")) {
            System.out.println(token);
        }


        System.out.println("Fim da análise léxica.");
    }
}