package ua.foxminded.javaspring.schoolconsoleapp.generator;

import java.util.List;

public interface Generator<T> {

    List<T> generate();
}
