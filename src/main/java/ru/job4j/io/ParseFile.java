package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    private String content(Predicate<Character> predicate) {
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            int i;
            while ((i = reader.read()) != -1) {
                if (predicate.test((char) i)) {
                    output.append((char) i);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return output.toString();

    }

    public String getContent() {
        return content(ch -> true);
    }

    public String getContentWithoutUnicode() {
        return content(ch -> ch > 0x80);
    }
}