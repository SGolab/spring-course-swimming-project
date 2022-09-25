package com.example.sgswimming.repositories;

import com.example.sgswimming.model.Instructor;
import com.example.sgswimming.model.Lesson;
import com.example.sgswimming.model.Swimmer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
class LessonRepositoryCustomMethodsTest {

    @Autowired
    InstructorRepository instructorRepository;

    @Autowired
    SwimmerRepository swimmerRepository;

    @Autowired
    LessonRepository lessonRepository;

    static final String FIRST_NAME = "first name";
    static final String LAST_NAME = "last name";

    @Test
    void findAllByInstructorId() {
        Instructor instructor = new Instructor();
        instructor.setFirstName(FIRST_NAME);
        instructor.setLastName(LAST_NAME);
        Instructor savedInstructor = instructorRepository.save(instructor);

        List<Lesson> lessons = List.of(new Lesson());
        List<Lesson> savedLessons = lessonRepository.saveAll(lessons);

        savedInstructor.setLessons(savedLessons);
        savedLessons.forEach(lesson -> lesson.setInstructor(instructor));
        lessonRepository.saveAll(savedLessons);


        Instructor anotherInstructor = new Instructor();
        anotherInstructor.setFirstName(FIRST_NAME);
        anotherInstructor.setLastName(LAST_NAME);
        Instructor anotherSavedInstructor = instructorRepository.save(anotherInstructor);

        List<Lesson> anotherLessons = List.of(new Lesson());
        List<Lesson> anotherSavedLessons = lessonRepository.saveAll(anotherLessons);


        anotherSavedInstructor.setLessons(anotherSavedLessons);
        anotherSavedLessons.forEach(lesson -> lesson.setInstructor(anotherSavedInstructor));
        lessonRepository.saveAll(anotherSavedLessons);

        Set<Lesson> foundLessons = lessonRepository.findAllByInstructorId(savedInstructor.getId());

        assertEquals(2L, lessonRepository.findAll().size());
        assertEquals(1L, foundLessons.size());
    }

    @Test
    void findAllBySwimmersId() {
        Swimmer swimmer = new Swimmer();
        swimmer.setFirstName(FIRST_NAME);
        swimmer.setLastName(LAST_NAME);
        Swimmer savedSwimmer = swimmerRepository.save(swimmer);

        List<Lesson> lessons = List.of(new Lesson());
        List<Lesson> savedLessons = lessonRepository.saveAll(lessons);

        savedSwimmer.setLessons(savedLessons);
        savedLessons.forEach(lesson -> lesson.addSwimmer(swimmer));
        lessonRepository.saveAll(savedLessons);


        Swimmer anotherSwimmer = new Swimmer();
        anotherSwimmer.setFirstName(FIRST_NAME);
        anotherSwimmer.setLastName(LAST_NAME);
        Swimmer anotherSavedSwimmer = swimmerRepository.save(anotherSwimmer);

        List<Lesson> anotherLessons = List.of(new Lesson());
        List<Lesson> anotherSavedLessons = lessonRepository.saveAll(anotherLessons);

        anotherSavedSwimmer.setLessons(anotherSavedLessons);
        anotherSavedLessons.forEach(lesson -> lesson.addSwimmer(anotherSavedSwimmer));
        lessonRepository.saveAll(anotherSavedLessons);


        Set<Lesson> foundLessons = lessonRepository.findAllBySwimmersId(savedSwimmer.getId());

        assertEquals(2L, lessonRepository.findAll().size());
        assertEquals(1L, foundLessons.size());
    }
}