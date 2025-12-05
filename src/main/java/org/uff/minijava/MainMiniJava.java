package org.uff.minijava;

import java_cup.runtime.Symbol;
import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.*;

public class MainMiniJava {
    public static void main(String[] args) {
        String input = (args.length > 0) ? args[0] : new FileSelector().selectFile();
        if (input == null) return;

        Path inPath = Paths.get(input).toAbsolutePath();
        System.out.println("PROCESSANDO: " + inPath.getFileName());

        try {
            Path base = inPath.getParent();
            if (base.endsWith("entradas")) base = base.getParent();

            Path dirTokens = base.resolve("saidas/tokens");
            Path dirTree   = base.resolve("saidas/arvores");
            Files.createDirectories(dirTokens);
            Files.createDirectories(dirTree);

            String name = inPath.getFileName().toString().replace(".mjava", "");

            System.out.print("1. Tokens... ");
            Path tokenFile = dirTokens.resolve(name + "_tokens.txt");

            try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(tokenFile))) {
                MiniJavaLexer lexer = new MiniJavaLexer(new FileReader(inPath.toFile()));
                Symbol s;
                while ((s = lexer.next_token()).sym != sym.EOF) {
                    pw.println(s.value != null ? "<" + getName(s.sym) + " \"" + s.value + "\">" : "<" + getName(s.sym) + ">");
                }
                pw.println("<EOF>");
                System.out.println("OK");
                System.out.println("   -> " + tokenFile);
            } catch (Error e) {
                System.out.println("FALHA");
                System.out.println("   [ERRO LÉXICO] " + e.getMessage());
                return;
            } catch (Exception e) {
                System.out.println("FALHA: " + e.getMessage());
                return;
            }

            System.out.print("2. Árvore... ");
            Path treeFile = dirTree.resolve(name + "_arvore.txt");

            try (PrintStream ps = new PrintStream(Files.newOutputStream(treeFile), true, "UTF-8")) {
                ps.println("ANÁLISE SINTÁTICA: " + name + "\n\n TRACE ");

                MiniJavaParser parser = new MiniJavaParser(new MiniJavaLexer(new FileReader(inPath.toFile())));

                parser.setErrorListener(msg -> {
                    ps.println("[FALHA] " + msg );
                    System.out.println("\n   [ERRO SINTÁTICO] " + msg);
                });

                PrintStream console = System.err;
                System.setErr(ps);
                try {
                    parser.debug_parse();
                    System.setErr(console);
                    System.out.println("OK");
                    ps.println("\n FIM \n[SUCESSO] Código Válido.");
                } catch (Exception e) {
                    System.setErr(console);
                    System.out.println("FALHA");
                    ps.println("\n[ERRO FATAL] " + e.getMessage());
                }
                System.out.println("   -> " + treeFile);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getName(int id) {
        try {
            for (Field f : sym.class.getFields())
                if (f.getType() == int.class && f.getInt(null) == id)
                    return f.getName();
        } catch (Exception ignored) {}
        return "UNKNOWN";
    }
}