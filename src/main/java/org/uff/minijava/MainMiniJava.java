package org.uff.minijava;

import java.io.FileReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainMiniJava {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Uso: java org.uff.minijava.MainMiniJava <arquivo.mjava>");
            return;
        }

        try {
            Path inPath = Paths.get(args[0]).toAbsolutePath();
            Path entradasDir = inPath.getParent();
            Path baseDir     = entradasDir != null ? entradasDir.getParent() : inPath.getParent();
            Path saidasDir   = baseDir.resolve("saidas");
            Files.createDirectories(saidasDir);
            Path outPath     = saidasDir.resolve(inPath.getFileName());

            try (FileReader reader = new FileReader(inPath.toFile());
                 PrintWriter out = new PrintWriter(Files.newBufferedWriter(outPath))) {

                MiniJavaLexer lexer = new MiniJavaLexer(reader);
                Symbol token;
                do {
                    token = lexer.nextToken();
                    if (token != null) {
                        String line = token.toString();
                        System.out.println(line); // mantém saída no terminal
                        out.println(line);        // grava no arquivo
                    }
                } while (token != null && token.getToken() != Token.EOF);

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
