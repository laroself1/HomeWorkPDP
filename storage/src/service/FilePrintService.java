package service;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class FilePrintService {

    public void printAllApplicationFiles(String path) {
        FileVisitor<Path> fileProcessor = new ProcessFile();
        try {
            Files.walkFileTree(Paths.get(path), fileProcessor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final class ProcessFile extends SimpleFileVisitor<Path> {

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            System.out.println("file:" + file);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
            System.out.println("directory:" + dir);
            return FileVisitResult.CONTINUE;
        }

    }

}

