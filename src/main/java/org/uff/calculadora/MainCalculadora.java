package org.uff.calculadora;

import java.io.FileReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Main — programa de apoio para exercitar o scanner gerado pelo JFlex.
 * Uso: java -cp out org.uff.calculadora.MainCalculadora <caminho-do-arquivo-de-entrada>
 * Ex.: java -cp out org.uff.calculadora.MainCalculadora src/main/resources/calculadora/entradas/calc_inicial.calc
 */
public class MainCalculadora {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Uso: java org.uff.calculadora.MainCalculadora <arquivo.calc>");
            return;
        }

        try {
            // Caminhos de entrada/saída
            Path inPath = Paths.get(args[0]).toAbsolutePath();
            // .../calculadora/entradas/<Arquivo>.calc  ->  .../calculadora/saidas/<Arquivo>.calc
            Path entradasDir = inPath.getParent();
            Path baseDir     = entradasDir != null ? entradasDir.getParent() : inPath.getParent();
            Path saidasDir   = baseDir.resolve("saidas");
            Files.createDirectories(saidasDir);
            Path outPath     = saidasDir.resolve(inPath.getFileName());

            try (FileReader reader = new FileReader(inPath.toFile());
                 PrintWriter out = new PrintWriter(Files.newBufferedWriter(outPath))) {

                CalcLexer lexer = new CalcLexer(reader);
                String token;
                while (!(token = lexer.nextToken()).equals("EOF")) {
                    System.out.println(token); // imprime no terminal
                    out.println(token);        // grava no arquivo
                }

                System.out.println("Fim da análise léxica.");
                out.println("Fim da análise léxica.");
            }

        } catch (IOException e) {
            System.err.println("Erro de I/O: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro ao processar o arquivo: " + e.getMessage());
        }
    }
}
