package ua.foxminded.javaspring.schoolconsoleapp;

import java.util.List;

public interface Generator<T> {

    List<T> generate();
}
