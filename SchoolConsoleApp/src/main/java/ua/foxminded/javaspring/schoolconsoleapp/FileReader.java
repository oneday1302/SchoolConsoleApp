package ua.foxminded.javaspring.schoolconsoleapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class FileReader implements DataSource<String> {
    private String fileName;

    public FileReader(String fileName) {
        if (fileName == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        this.fileName = fileName;
    }

    @Override
    public Stream<String> getData() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(this.getClass().getResourceAsStream(fileName)))) {
            List<String> list = new LinkedList<>();
            while (reader.ready()) {
                list.add(reader.readLine());
            }
            return list.stream();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
