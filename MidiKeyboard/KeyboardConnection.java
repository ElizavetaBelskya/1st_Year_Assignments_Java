

import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardConnection extends JFrame implements KeyListener {
    private MidiKeyboard keyboard = new MidiKeyboard();
    private int octave = 6;
    private Label label;
    public static String chords = "";
    public static String notesForSaving = "";

    public KeyboardConnection() throws MidiUnavailableException {
        this.setSize(500,500);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.addKeyListener(this);
        label = new Label();
        add(label);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        char c = e.getKeyChar();
        System.out.print(c);
        if (c >= '0' & c < '9') {
            this.octave = Character.getNumericValue(c);
            notesForSaving = notesForSaving.concat(String.valueOf(c));
        } else if (keyboard.pianoMap.containsKey(c)) {
            keyboard.play(octave, c);
            String chord = keyboard.mapOfChords.get(keyboard.pianoMap.get(c));
            chords = chords.concat(" " + chord);
            notesForSaving = notesForSaving.concat(String.valueOf(c));
            label.setText(chords);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
