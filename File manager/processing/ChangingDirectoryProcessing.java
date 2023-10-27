

import ru.itis.belskaya.fileManager.commands.SwitchingToAnotherDirectory;

public class ChangingDirectoryProcessing implements Processing {
    @Override
    public void process(String newCommand) {
        (new SwitchingToAnotherDirectory()).changeDirectory(newCommand);
    }
}
