
import ru.itis.belskaya.fileManager.processing.*;

import java.util.HashMap;

public class StorageOfCommands {
    public HashMap<String, Processing> mapOfCommands = new HashMap<>();
    public StorageOfCommands() {
        mapOfCommands.put("cd ", new ChangingDirectoryProcessing());
        mapOfCommands.put("mkdir ", new CreatingNewDirectoryProcessing());
        mapOfCommands.put("ls", new ShowContentProcessing());
        mapOfCommands.put("rm ", new RemovingProcessing());
        mapOfCommands.put("cp ", new CopyingProcessing());
    }
}
