package com.example.sgswimming.bootstrap;

import com.example.sgswimming.model.Instructor;
import com.example.sgswimming.model.Lesson;
import com.example.sgswimming.model.Swimmer;
import com.example.sgswimming.repositories.InstructorRepository;
import com.example.sgswimming.repositories.LessonRepository;
import com.example.sgswimming.repositories.SwimmerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Set;

import static com.example.sgswimming.bootstrap.BootStrapHelper.*;

@RequiredArgsConstructor
@Component
public class Bootstrap implements CommandLineRunner {

    private final InstructorRepository instructorRepository;
    private final LessonRepository lessonRepository;
    private final SwimmerRepository swimmerRepository;

    private final int SWIMMER_AMOUNT = 12;
    private final int INSTRUCTOR_AMOUNT = 3;
    private final int LESSON_AMOUNT = 9;

    private final int LESSON_PER_SWIMMER = 3; //(SWIMMER_AMOUNT / LESSON_PER_SWIMMER) swimmer per lesson
    private final int LESSONS_PER_INSTRUCTOR = 3; //one instructor per lesson

    @Override
    public void run(String... args) throws Exception {

        Set<Swimmer> swimmerSet = createSwimmerSet(SWIMMER_AMOUNT);
        Set<Instructor> instructorSet = createInstructorSet(INSTRUCTOR_AMOUNT);
        Set<Lesson> lessonsSet = createLessonSet(LESSON_AMOUNT);

        bind(swimmerSet, instructorSet, lessonsSet);

        swimmerRepository.saveAll(swimmerSet);
        instructorRepository.saveAll(instructorSet);
        lessonRepository.saveAll(lessonsSet);

        System.out.printf(
                "Loaded bootstrap data.\ninstructor count: %d\nlesson count: %d\nswimmer count: %d\n",
                instructorRepository.count(), lessonRepository.count(), swimmerRepository.count());
    }

    private void bind(Set<Swimmer> swimmerSet, Set<Instructor> instructorSet, Set<Lesson> lessonsSet) {

        Iterator<Lesson> iterator = new CircularIterator<>(lessonsSet);

        swimmerSet.forEach(swimmer -> { //binding lessons to swimmers
            for (int i = 0; i < LESSON_PER_SWIMMER; i++) {
                Lesson lesson = iterator.next();
                swimmer.addLesson(lesson);
                lesson.addSwimmer(swimmer);
            }
        });

        instructorSet.forEach(instructor -> { //binding lessons to instructors
            for (int i = 0; i < LESSONS_PER_INSTRUCTOR; i++) {
                Lesson lesson = iterator.next();
                instructor.addLesson(lesson);
                lesson.setInstructor(instructor);
            }
        });
    }
}
