package ua.foxminded.javaspring.schoolconsoleapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoursesGenerator implements Generator<Course> {
    private static final Map<String, String> DATA = new HashMap<>();

    public CoursesGenerator() {
        dataFill();
    }

    @Override
    public List<Course> generate() {
        List<Course> courses = new ArrayList<>();
        for (Map.Entry<String, String> entry : DATA.entrySet()) {
            courses.add(new Course(entry.getKey(), entry.getValue()));
        }
        return courses;
    }

    private void dataFill() {
        DATA.put(
                "Mathematics",
                "Mathematics and statistics play a key role in technological developments shaping our society.");
        DATA.put(
                "Biology", 
                "Study Biology and you’ll be developing the scientific problem-solving.");
        DATA.put(
                "Economics", 
                "This course is designed to give you the skills valued by employers.");
        DATA.put(
                "Engineering", 
                "New technologies, particularly nanotechnology, have created a demand for jobs.");
        DATA.put(
                "Geography",
                "The skill and knowledge of geography graduates is becoming increasingly valuable to organisations seeking to adapt to new ways of working.");
        DATA.put(
                "History",
                "Studying history will provide you with an adaptable set of skills, including critical thinking, analysis, and communication.");
        DATA.put(
                "Philosophy",
                "The discipline of philosophy is highly regarded by employers as they appreciate graduates’ communication skills.");
        DATA.put(
                "Physics",
                "People with physics qualifications are well placed to enter both scientific and non-scientific jobs.");
        DATA.put(
                "Psychology", 
                "Whether or not you later choose to train as a practitioner psychologist.");
        DATA.put(
                "Science",
                "Expand your career opportunities high quality of teaching and active involvement in international research programmes.");
    }
}
