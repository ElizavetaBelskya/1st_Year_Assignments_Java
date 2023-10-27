package ru.itis.belskaya.fileManager.commands;

import ru.itis.belskaya.fileManager.FileManager;
import ru.itis.belskaya.fileManager.processing.PathProcessing;
import java.io.File;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystemException;
import java.nio.file.Path;

public class CreatingNewDirectory {
    public void createDir(String newCommand, boolean hasParents) throws FileSystemException{
        int startSymbol = 6;
        if (hasParents) {
            startSymbol = 9;
        }
        Path newPath = PathProcessing.makeNewPath(newCommand.substring(startSymbol));
        makeDir(newPath);
    }
    public void createSeveralDir(String newCommand) throws FileSystemException {
        for (String newDirectory: newCommand.substring(6).split("\\s")) {
            Path newPath = FileManager.path.resolve(newDirectory);
            makeDir(newPath);
        }
    }
    private void makeDir(Path path) throws FileSystemException {
        if (!new File(path.toString()).exists()) {
            File f = new File(path.toString());
            if (!f.mkdirs()) {
                throw new FileSystemException("Something went wrong during creating a new directory");
            }
        } else {
            throw new FileAlreadyExistsException(path.toString() + ": file already exists");
        }
    }
}
