package org.uff.minijava;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileSelector {

    private static final String DEFAULT_DIRECTORY = "src/main/resources/minijava/entradas";

    public String selectFile() {
        Path dirPath = Paths.get(DEFAULT_DIRECTORY);

        if (!Files.exists(dirPath) || !Files.isDirectory(dirPath)) {
            System.err.println("Diretório de entradas não encontrado: " + dirPath.toAbsolutePath());
            return null;
        }

        try (Stream<Path> stream = Files.list(dirPath)) {
            List<File> files = stream
                    .filter(file -> !Files.isDirectory(file))
                    .filter(file -> file.toString().endsWith(".mjava"))
                    .map(Path::toFile)
                    .sorted()
                    .collect(Collectors.toList());

            if (files.isEmpty()) {
                System.out.println("Nenhum arquivo .mjava encontrado em: " + dirPath);
                return null;
            }

            return promptUserForSelection(files);

        } catch (IOException e) {
            System.err.println("Erro ao ler diretório de arquivos: " + e.getMessage());
            return null;
        }
    }

    private String promptUserForSelection(List<File> files) {
        System.out.println("\nSELETOR DE ARQUIVOS MINIJAVA");
        System.out.println("Diretório: " + DEFAULT_DIRECTORY + "\n");

        for (int i = 0; i < files.size(); i++) {
            System.out.printf("[%d] %s%n", i + 1, files.get(i).getName());
        }

        System.out.println("[0] Sair");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("\nEscolha o número do arquivo para compilar: ");
            String input = scanner.nextLine();

            try {
                int choice = Integer.parseInt(input);

                if (choice == 0) {
                    System.out.println("Operação cancelada pelo usuário.");
                    return null;
                }

                if (choice > 0 && choice <= files.size()) {
                    File selectedFile = files.get(choice - 1);
                    System.out.println("\nArquivo selecionado: " + selectedFile.getName());
                    return selectedFile.getPath();
                } else {
                    System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
            }
        }
    }
}
