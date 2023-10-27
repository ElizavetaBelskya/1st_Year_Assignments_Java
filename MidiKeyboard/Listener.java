package ru.kpfu.itis.belskaya;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Listener {
    public String listen(String link) throws IOException {
        String formatWarning = "This file contains the notes for the midi keyboard: ";
        String notes = "";
        try (FileReader reader = new FileReader(link)) {
            int b;
            while ((b = reader.read()) != -1) {
                notes = notes.concat(String.valueOf((char) b));
            }

            if (!notes.startsWith(formatWarning)) {
                throw new IOException("This file does not contain notes for the midi-keyboard");
            }
        } catch (FileNotFoundException e) {
            throw new IOException("File not found");
        } catch (IOException e) {
            throw new IOException("Unable to read the file");
        }
        return notes.substring(formatWarning.length());
    }
}
