

import ru.itis.belskaya.fileManager.commands.CreatingNewDirectory;

import java.nio.file.FileSystemException;
import java.nio.file.Paths;

public class CreatingNewDirectoryProcessing implements Processing {
    @Override
    public void process(String newCommand) throws FileSystemException {
        if (newCommand.startsWith("mkdir -p ")) {
            (new CreatingNewDirectory()).createDir(newCommand, true);
        } else {
            if ((newCommand.substring(6).split("\\s").length == 1) || Paths.get(newCommand.substring(6)).isAbsolute()) {
                (new CreatingNewDirectory()).createDir(newCommand, false);
            } else {
                (new CreatingNewDirectory()).createSeveralDir(newCommand);
            }
        }
    }
}
