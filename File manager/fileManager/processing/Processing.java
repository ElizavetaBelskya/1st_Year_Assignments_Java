package ru.itis.belskaya.fileManager.processing;

import java.io.IOException;

public interface Processing {
    void process(String newCommand) throws IOException;
}
