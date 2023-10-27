
import ru.itis.belskaya.fileManager.commands.Copier;

import java.io.*;

public class CopyingProcessing implements Processing {
    @Override
    public void process(String newCommand) throws IOException {
        String[] pathOfFiles = filesToCopy(newCommand.substring(3));
        File oldFile = new File(PathProcessing.makePath(pathOfFiles[0]).toString());
        File newFile = new File(PathProcessing.makeNewPath(pathOfFiles[1]).toString());
        try {
            newFile.createNewFile();
        } catch (IOException e) {
            throw new IOException("Copying failed");
        }
        new Copier().copy(oldFile, newFile);
    }

    private String[] filesToCopy(String string) {
        String [] files;
        if (string.indexOf("\'") == -1) {
            files = string.split("\\s");
        } else {
            if (string.indexOf("\'") == 0) {
                string = string.substring(1);
            }

            if (string.lastIndexOf("\'") == string.length()-1) {
                string = string.substring(0, string.length()-1);
            }

            if (string.indexOf("\'") != string.lastIndexOf("\'")) {
                files = string.split("\'\\s\'");
            } else {
                files = string.split("\'");
                for (int i = 0; i < 2; i++) {
                    files[i] = files[i].trim();
                }
            }
        }
        return files;
    }
}
