

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;


public class Saver {
    public String save(String notes) throws IOException {
        String formatWarning = "This file contains the notes for the midi keyboard: ";
        Properties properties = new Properties();
        try (FileReader reader = new FileReader(System.getProperty("user.dir") + System.getProperty("file.separator") + "app.properties")){
            properties.load(reader);
        } catch (FileNotFoundException e) {
            throw new IOException("Unable to get the record number");
        } catch (IOException e) {
            throw new IOException("Unable to get the record number");
        }

        int num = Integer.parseInt(properties.getProperty("numberOfRecords")) + 1;
        String link = System.getProperty("user.dir") + "\\Record" + num;

        try {
            Files.createFile(Paths.get(link));
        } catch (IOException e) {
            throw new IOException("File creation error");
        }

        try (FileWriter writer = new FileWriter(link, true)) {
            writer.write(formatWarning);
            writer.write(notes);
        } catch (IOException e) {
            throw new IOException("There were problems when saving the record");
        }

        try (FileWriter writer = new FileWriter(System.getProperty("user.dir") + System.getProperty("file.separator") + "app.properties")){
            String newProperty = "numberOfRecords = " + num;
            writer.write(newProperty);
        } catch (IOException e) {
            throw new IOException("Failed to update the record counter");
        }

        return "Saved to " + link;
    }

}
