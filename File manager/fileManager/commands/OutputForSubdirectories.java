package ru.itis.belskaya.fileManager.commands;

import java.io.File;
import java.nio.file.Path;

public class OutputForSubdirectories {
    public void createList(Path path) {
        File directory = new File(path.toString());
        File[] subdirectories = directory.listFiles();
        int maxName = 0;
        for (File file : subdirectories) {
            if (file.getName().length() > maxName) {
                maxName = file.getName().length();
            }
        }
        for (int i = 0; i < subdirectories.length; i++) {
            System.out.print(subdirectories[i].getName());
            for (int j = 0; j < (maxName - subdirectories[i].getName().length() + 1); j++) {
                System.out.print(" ");
            }
            if ((i + 1) % 7 == 0) {
                System.out.println();
            }
        }
        System.out.println();
    }
}
