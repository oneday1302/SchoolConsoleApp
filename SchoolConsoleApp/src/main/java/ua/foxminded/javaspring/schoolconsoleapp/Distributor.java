package ua.foxminded.javaspring.schoolconsoleapp;

import java.util.List;

public interface Distributor<T> {

    List<T> distribute();
}
