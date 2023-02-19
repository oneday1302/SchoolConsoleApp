package ua.foxminded.javaspring.schoolconsoleapp.distributor;

import java.util.List;

public interface Distributor<T> {

    List<T> distribute();
}
