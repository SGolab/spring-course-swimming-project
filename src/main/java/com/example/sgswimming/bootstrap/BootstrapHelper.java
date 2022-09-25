package com.example.sgswimming.bootstrap;

import com.example.sgswimming.model.Instructor;
import com.example.sgswimming.model.Lesson;
import com.example.sgswimming.model.Swimmer;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public abstract class BootstrapHelper {

    static Set<Swimmer> createSwimmerSet(int amount) {
        Set<Swimmer> result = new HashSet<>();
        for (int i = 0; i < amount; i++) {
            result.add(createSwimmer());
        }
        return result;
    }

    static Set<Instructor> createInstructorSet(int amount) {
        Set<Instructor> result = new HashSet<>();
        for (int i = 0; i < amount; i++) {
            result.add(createInstructor());
        }
        return result;
    }

    static Set<Lesson> createLessonSet(int amount) {
        Set<Lesson> result = new HashSet<>();
        for (int i = 0; i < amount; i++) {
            result.add(createLesson());
        }
        return result;
    }

    static Instructor createInstructor() {
        return Instructor.builder()
                .firstName(getRandomName())
                .lastName(getRandomName())
                .build();
    }

    static Lesson createLesson() {
        return Lesson.builder()
                .localDateTime(LocalDate.ofEpochDay(ThreadLocalRandom.current().nextInt(365 * 50, 365 * 53)).atStartOfDay())
                .description("lesson's description")
                .build();
    }

    static Swimmer createSwimmer() {
        return Swimmer.builder()
                .firstName(getRandomName())
                .lastName(getRandomName())
                .build();
    }

    private static final List<String> names = List.of("Stephany", "Camdyn", "Korey", "Eddy", "Preston", "Myka", "Braelyn", "Emmalynn", "Tatum", "Jaxen", "Jaya", "Don", "Bryer", "Teagan", "Brenna", "Khloee", "Ingrid", "Nigel", "Yaritza", "Jessa", "Rilynn", "Mark", "Angel", "Brady", "Deacon", "Melvin", "Dyllan", "Bronx", "Elisha", "Elan", "Kenia", "Alonzo", "Dean", "Cortez", "Latrell", "Lochlan", "Landin", "Alannah", "Kaiya", "Helena", "Braylan", "Chyna", "Mack", "Madison", "Darien", "Raegan", "Lorraine", "Ryleigh", "Raylan", "Aayden", "Lana", "Case", "Shanaya", "Steven", "Jaelynn", "Zeke", "Maryam", "Trinity", "Weston", "Micaela", "Eliyahu", "Kalen", "Ayman", "Yuna", "Zia", "Jaylynn", "Kole", "Joyce", "Tatianna", "Juancarlos", "Thea", "Soleil", "Alma", "Arleth", "Vicente", "Jethro", "Myra", "Hans", "Sidney", "Jeffery", "Coleman", "Adelynn", "Bryson", "Eddie", "Aiza", "Niall", "Annmarie", "Lina", "Rylie", "Kai", "Walter", "Ivory", "Violeta", "Jazzlyn", "Nick", "Immanuel", "Matthew", "Dyson", "Estevan", "Tala");
    private static final Random random = new Random();

    private static String getRandomName() {
        return names.get(random.nextInt(names.size()));
    }

    static class CircularIterator<T> implements Iterator<T> {

        Iterable<T> iterable;
        Iterator<T> iterator;

        public CircularIterator(Iterable<T> iterable) {
            this.iterable = iterable;
            this.iterator = iterable.iterator();
        }

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public T next() {
            if (!iterator.hasNext()) {
                iterator = iterable.iterator();
            }
            return iterator.next();
        }
    }
}
