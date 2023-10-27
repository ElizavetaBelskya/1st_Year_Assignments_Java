package ru.itis.belskaya.fileManager.commands;

import ru.itis.belskaya.fileManager.commands.GetterOfInformationAboutFile;

import java.io.File;
import java.nio.file.Path;

public class OutputForSubdirectoriesWithInformation {
    public void createList(Path path, boolean readable) {
        File directory = new File(path.toString());
        File [] subdirectories = directory.listFiles();
        String[] linesOfInformation = new GetterOfInformationAboutFile().getInformation(subdirectories, readable);
        for (int i = 0; i < subdirectories.length; i++) {
            if (!linesOfInformation[i].isEmpty()) {
                System.out.println(linesOfInformation[i]);
            }
        }
        System.out.println();
    }
}
