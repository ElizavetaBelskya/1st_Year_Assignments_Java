package ru.itis.belskaya.fileManager.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class GetterOfInformationAboutFile {

    public String[] getInformation(File [] files, boolean readable) {
        int[] spaces = getSpacesForInformation(files, readable);
        String [] inf = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            try {
                String[] information = getInformation(files[i], readable);
                String [] spaceStrings = new String[spaces.length];
                spaceStrings[0] = "%" + (spaces[0] - information[1].length() + 1) + "c";
                spaceStrings[1] = "%" + (spaces[1] - information[2].length() + 1) + "c";
                spaceStrings[2] = "%" + (spaces[2] - information[3].length() + 1) + "c";
                String s = information[0] + " " + information[1] + spaceStrings[0] + information[2] +
                        spaceStrings[1] + information[3] + spaceStrings[2] + files[i].getName();
                inf[i] = String.format(s, ' ', ' ', ' ');
            } catch (IOException e) {
                inf[i] = "Unable to get information about the file";
            }
        }
        return inf;
    }

    public int [] getSpacesForInformation(File [] files, boolean readable) {
        int [] spaces = {0, 0, 0};
        for (File file: files) {
            Path path = Paths.get(file.getPath());
            String owner, size, timeOfModification;
            if (!readable) {
                try {
                    owner = String.valueOf(Files.getOwner(path).getName());
                } catch (IOException e) {
                    owner = "";
                }
                try {
                    size = String.valueOf(Files.size(path));
                } catch (IOException e) {
                    size = "";
                }
                timeOfModification = String.valueOf(file.lastModified());
            } else {
                try {
                    owner = String.valueOf(Files.getOwner(path).getName());
                } catch (IOException e) {
                    owner = "";
                }
                try {
                    size = (Files.size(path)/1000) + "K";
                } catch (IOException e) {
                    size = "";
                }
                timeOfModification = "yyyy-MM-dd HH:mm:ss";
            }
            spaces[0] = Math.max(spaces[0], owner.length());
            spaces[1] = Math.max(spaces[1], size.length());
            spaces[2] = Math.max(spaces[2], timeOfModification.length());
        }
        return spaces;
    }

    public String[] getInformation(File file, boolean readable) throws IOException {
        String flags = "";
        flags = flags.concat(file.isDirectory()? "d": "-");
        flags = flags.concat(file.canRead()? "r" : "-");
        flags = flags.concat(file.canWrite()? "w" : "-");
        flags = flags.concat(file.canExecute()? "x" : "-");
        String owner, size, timeOfModification;
        Path path = file.toPath();
        if (!readable) {
            owner = String.valueOf(Files.getOwner(path).getName());
            size = String.valueOf(Files.size(path));
            timeOfModification = String.valueOf(file.lastModified());
        } else {
            owner = String.valueOf(Files.getOwner(path).getName());
            size = (Files.size(path)/1000) + "K";
            long time = file.lastModified();
            DateFormat TIMESTAMP = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            timeOfModification = TIMESTAMP.format(time);
        }
        String[] lines = {flags, owner, size, timeOfModification};
        return lines;
    }

}
