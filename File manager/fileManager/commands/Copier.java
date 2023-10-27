package ru.itis.belskaya.fileManager.commands;

import java.io.*;


public class Copier {
    public void copy(File oldFile, File newFile) throws IOException {
        try (InputStream in = new BufferedInputStream(new FileInputStream(oldFile)); OutputStream out = new BufferedOutputStream(new FileOutputStream(newFile))){
            int b;
            while ((b = in.read())!=-1) {
                out.write(b);
            }
            out.flush();
        } catch (IOException e) {
            throw new IOException("Copying failed");
        }
    }
}
