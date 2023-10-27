

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileSystemException;

public class Remover {

    public void removeByFullPath(String path) throws FileSystemException, FileNotFoundException {
        File file = new File(path);
        if (file.exists()) {
            boolean isRemoved;
            isRemoved = remove(file);
            if (!isRemoved) {
                throw new FileSystemException("Something went wrong");
            }
        } else {
            throw new FileNotFoundException(path + ": No such file or directory");
        }
    }

    private boolean remove(File file) {
        if (file.isFile() || (file.isDirectory() && (file.listFiles().length == 0))) {
            return file.delete();
        } else {
            boolean isRemoved;
            for (File subdirectory : file.listFiles()) {
                isRemoved = remove(subdirectory);
                if (isRemoved == false) {
                    return isRemoved;
                }
            }
            isRemoved = file.delete();
            return isRemoved;
        }
    }

}
