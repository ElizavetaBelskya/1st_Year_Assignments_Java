package ru.kpfu.itis.belskaya;

import javax.sound.midi.MidiUnavailableException;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    new KeyboardConnection();
                } catch (MidiUnavailableException e) {
                    e.printStackTrace();
                }
            }
        });
        
        boolean screenIsCalled = false;
        Scanner sc = new Scanner(System.in);
        while (true) {
            String line = sc.nextLine();
            if (line.equals("play")) {
                if (!screenIsCalled) {
                    t1.start();
                    screenIsCalled = true;
                }
            } else if (line.equals("save")) {
                try {
                    if (!KeyboardConnection.notesForSaving.isEmpty()) {
                        System.out.println((new Saver()).save(KeyboardConnection.notesForSaving));
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            } else if (line.equals("clear")) {
                KeyboardConnection.notesForSaving = "";
                KeyboardConnection.chords = "";
            } else if (line.startsWith("play ")) {
                KeyboardConnection.notesForSaving = "";
                KeyboardConnection.chords = "";
                Listener listener = new Listener();
                try {
                    String melody = listener.listen(line.substring(5));
                    (new MidiKeyboard()).playMelody(melody);
                } catch (IOException | MidiUnavailableException e) {
                    System.out.println(e.getMessage());
                }
            } else if (line.equals("stop")) {
                break;
            } else if (!line.isEmpty()) {
                System.err.println("Unknown command");
            }
        }
    }
}
