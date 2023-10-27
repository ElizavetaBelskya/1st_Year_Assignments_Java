package ru.itis.belskaya.fileManager;
import ru.itis.belskaya.fileManager.processing.StorageOfCommands;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
public class FileManager {
    public static Path path = Paths.get("C:\\");
    public void execute() {
        Scanner sc = new Scanner(System.in);
        StorageOfCommands map = new StorageOfCommands();
        while (true) {
            System.out.print(path.toString() + " >> ");
            String newCommand = sc.nextLine().trim();
            boolean isCommand = false;
            for (String s: map.mapOfCommands.keySet()) {
                if (newCommand.startsWith(s)) {
                    isCommand = true;
                    try {
                        map.mapOfCommands.get(s).process(newCommand);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
            if (!isCommand) {
                System.out.println(newCommand + ": command not found");
            }
        }
    }

}
