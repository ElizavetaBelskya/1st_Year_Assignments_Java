

import ru.itis.belskaya.fileManager.FileManager;
import ru.itis.belskaya.fileManager.processing.PathProcessing;

public class SwitchingToAnotherDirectory {
    public void changeDirectory(String newPath) {
        FileManager.path = PathProcessing.makePath(newPath.substring(3)).normalize();
    }
}
