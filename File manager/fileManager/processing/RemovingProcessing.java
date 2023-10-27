package ru.itis.belskaya.fileManager.processing;

import ru.itis.belskaya.fileManager.commands.Remover;
import java.io.FileNotFoundException;
import java.nio.file.FileSystemException;

public class RemovingProcessing implements Processing {
    @Override
    public void process(String newCommand) throws FileSystemException, FileNotFoundException {
        new Remover().removeByFullPath(PathProcessing.makePath(newCommand.substring(3)).toString());
    }

}
