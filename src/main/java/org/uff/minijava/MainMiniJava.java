package org.uff.minijava;

import java_cup.runtime.Symbol;
import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainMiniJava {
    public static void main(String[] args) {

        String inputFilePath;

        if (args.length > 0) {
            inputFilePath = args[0];
        } else {
            FileSelector selector = new FileSelector();
            inputFilePath = selector.selectFile();
        }

        if (inputFilePath == null) {
            return;
        }

        System.out.println("\nProcessando: " + inputFilePath);

        try {
            Path inPath = Paths.get(inputFilePath);

            Path saidasDir = inPath.getParent().resolve("saidas");
            if (inPath.getParent().getFileName().toString().equals("entradas")) {
                saidasDir = inPath.getParent().getParent().resolve("saidas");
            }
            Files.createDirectories(saidasDir);

            Path outPath = saidasDir.resolve(inPath.getFileName());

            try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(outPath))) {

                /* Scanner */
                System.out.println("\nGerando tokens...");

                MiniJavaLexer lexerPrint = new MiniJavaLexer(new FileReader(inPath.toFile()));
                Symbol token;

                while ((token = lexerPrint.next_token()).sym != sym.EOF) {
                    String tokenName = getTokenName(token.sym);
                    String output = (token.value != null)
                            ? String.format("<%s \"%s\">", tokenName, token.value)
                            : String.format("<%s>", tokenName);

                    writer.println(output);
                }

                writer.println("<EOF>");

                writer.println();
                writer.println("RESULTADO DA ANÁLISE SINTÁTICA");
                writer.println();

                /* Passer */
                System.out.println("\nExecutando Parser...");

                MiniJavaLexer lexerParser = new MiniJavaLexer(new FileReader(inPath.toFile()));
                MiniJavaParser parser = new MiniJavaParser(lexerParser);

                parser.setErrorListener(message -> writer.println("[FALHA] " + message));

                try {
                    parser.parse();
                    writer.println("[SUCESSO] O código é sintaticamente válido.");
                    System.out.println("Sucesso!");
                } catch (Exception e) {
                    writer.println("[ERRO FATAL] A análise foi interrompida.");
                    System.err.println("A análise parou devido a erros.");
                }

                writer.println();
                writer.println("FIM DA ANÁLISE SINTÁTICA");

            }

            System.out.println("\nArquivo de saída gerado em: " + outPath.toAbsolutePath());

        } catch (IOException e) {
            System.err.println("Erro de I/O: " + e.getMessage());
        } catch (Error e) {
            System.err.println("\n[ERRO LÉXICO] " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar o arquivo:");
            e.printStackTrace();
        }
    }

    private static String getTokenName(int id) {
        try {
            for (Field field : sym.class.getFields()) {
                if (field.getType() == int.class && field.getInt(null) == id) {
                    return field.getName();
                }
            }
        } catch (Exception e) {
            return "UNKNOWN";
        }
        return "UNKNOWN";
    }
}