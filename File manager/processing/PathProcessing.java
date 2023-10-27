

import ru.itis.belskaya.fileManager.FileManager;

import java.awt.geom.IllegalPathStateException;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathProcessing {
    public static Path makePath(String newPathString) throws IllegalPathStateException {
        if (newPathString.isEmpty()) {
            throw new IllegalPathStateException("Missing operand");
        } else {
            Path newPath = Paths.get(newPathString);
            if ((newPath.isAbsolute()) && (new File(newPath.toString())).exists()) {
                return newPath;
            } else if (new File(FileManager.path.resolve(newPathString).toString()).exists()) {
                return FileManager.path.resolve(newPathString);
            } else {
                throw new IllegalPathStateException(newPathString + ": No such file or directory");
            }
        }
    }

    public static Path makeNewPath(String newPathString) {
        if (Paths.get(newPathString).isAbsolute()) {
            return Paths.get(newPathString);
        } else {
            return FileManager.path.resolve(newPathString);
        }
    }
}
