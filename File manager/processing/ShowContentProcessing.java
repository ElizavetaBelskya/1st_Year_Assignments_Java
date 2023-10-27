
import ru.itis.belskaya.fileManager.FileManager;
import ru.itis.belskaya.fileManager.commands.OutputForSubdirectories;
import ru.itis.belskaya.fileManager.commands.OutputForSubdirectoriesWithInformation;

import java.io.IOException;


public class ShowContentProcessing implements Processing {
    @Override
    public void process(String newCommand) throws IOException {
        if (newCommand.equals("ls")) {
            new OutputForSubdirectories().createList(FileManager.path);
        } else if (newCommand.equals("ls -l")) {
            new OutputForSubdirectoriesWithInformation().createList(FileManager.path,  false);
        } else if (newCommand.startsWith("ls -l ")) {
            new OutputForSubdirectoriesWithInformation().createList(PathProcessing.makePath(newCommand.substring(6)), false);
        } else if (newCommand.equals("ls -lh")){
            new OutputForSubdirectoriesWithInformation().createList(FileManager.path, true);
        } else if (newCommand.startsWith("ls -lh")) {
            new OutputForSubdirectoriesWithInformation().createList(PathProcessing.makePath(newCommand.substring(6)), true);
        } else if (newCommand.startsWith("ls ")) {
            new OutputForSubdirectories().createList(PathProcessing.makePath(newCommand.substring(3)));
        } else {
            throw new IllegalArgumentException(newCommand + ": command not found");
        }
    }

}
