package ru.kpfu.itis.belskaya;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import java.util.HashMap;

public class MidiKeyboard {
    public HashMap<Character, Integer> pianoMap = new HashMap<>();
    HashMap<Integer, String> mapOfChords = new HashMap<>();
    MidiChannel[] channels;
    public MidiKeyboard() throws MidiUnavailableException {
        Synthesizer synth = MidiSystem.getSynthesizer();
        synth.open();
        this.channels = synth.getChannels();
        pianoMap.put('q', 0);
        pianoMap.put('w', 1);
        pianoMap.put('e', 2);
        pianoMap.put('r', 3);
        pianoMap.put('t', 4);
        pianoMap.put('y', 5);
        pianoMap.put('u', 6);
        pianoMap.put('i', 7);
        pianoMap.put('o', 8);
        pianoMap.put('p', 9);
        pianoMap.put('[', 10);
        pianoMap.put(']', 11);
        pianoMap.put('й', 0);
        pianoMap.put('ц', 1);
        pianoMap.put('у', 2);
        pianoMap.put('к', 3);
        pianoMap.put('е', 4);
        pianoMap.put('н', 5);
        pianoMap.put('г', 6);
        pianoMap.put('ш', 7);
        pianoMap.put('щ', 8);
        pianoMap.put('з', 9);
        pianoMap.put('х', 10);
        pianoMap.put('ъ', 11);
        mapOfChords.put(0, "C");
        mapOfChords.put(1, "C#");
        mapOfChords.put(2, "D");
        mapOfChords.put(3, "D#");
        mapOfChords.put(4, "E");
        mapOfChords.put(5, "F");
        mapOfChords.put(6, "F#");
        mapOfChords.put(7, "G");
        mapOfChords.put(8, "G#");
        mapOfChords.put(9, "A");
        mapOfChords.put(10, "A#");
        mapOfChords.put(11, "B");
    }

    public void play(int octave, char key) {
        int chord = pianoMap.get(key);
        channels[0].noteOn(12*octave + chord, 50);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        channels[0].noteOff(12*octave + chord);
    }

    public void playMelody(String melody) {
        int octave = 6;
        for (char c: melody.toCharArray()) {
            if (c >= '0' & c < '9') {
                octave = Character.getNumericValue(c);
                System.out.print(c);
            } else if (pianoMap.containsKey(c)) {
                int chord = pianoMap.get(c);
                channels[0].noteOn(12*octave + chord, 50);
                try {
                    Thread.sleep(500);
                    System.out.print(c);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                channels[0].noteOff(12*octave + chord);
            }
        }
     }
}

