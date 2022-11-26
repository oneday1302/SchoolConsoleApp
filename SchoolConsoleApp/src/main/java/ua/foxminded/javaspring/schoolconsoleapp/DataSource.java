package ua.foxminded.javaspring.schoolconsoleapp;

import java.util.stream.Stream;

public interface DataSource<T> {
    
    Stream<T> getData();
}
