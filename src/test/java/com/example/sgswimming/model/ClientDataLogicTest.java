package com.example.sgswimming.model;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ClientDataLogicTest {

    @Test
    void setInstructorTest() {
        ClientData oldClientData = new ClientData();

        Lesson lesson = new Lesson();
        lesson.setSwimmers(List.of(new Swimmer(), new Swimmer(), new Swimmer()));

        Instructor instructor = new Instructor();
        instructor.setLessons(List.of(lesson, new Lesson(), new Lesson()));

        ClientData newClientData = new ClientData();
        newClientData.setInstructor(instructor);

        assertFalse(newClientData.getInstructors().isEmpty());
        assertFalse(newClientData.getLessons().isEmpty());
        assertFalse(newClientData.getSwimmers().isEmpty());
    }

    @Test
    void setSwimmersTest() {
        ClientData oldClientData = new ClientData();

        Lesson lesson = new Lesson();
        lesson.setInstructor(new Instructor());

        Swimmer swimmer = new Swimmer();
        swimmer.setLessons(List.of(lesson));

        List<Swimmer> swimmers = List.of(
                swimmer,
                Swimmer.builder().id(1L).build(),
                Swimmer.builder().id(2L).build());

        ClientData newClientData = new ClientData();
        newClientData.setSwimmers(swimmers);

        assertFalse(newClientData.getInstructors().isEmpty());
        assertFalse(newClientData.getLessons().isEmpty());
        assertFalse(newClientData.getSwimmers().isEmpty());
    }
}
