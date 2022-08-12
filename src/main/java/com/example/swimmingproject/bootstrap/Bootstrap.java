package com.example.swimmingproject.bootstrap;

import com.example.swimmingproject.model.Instructor;
import com.example.swimmingproject.model.Lesson;
import com.example.swimmingproject.model.Swimmer;
import com.example.swimmingproject.repositories.InstructorRepository;
import com.example.swimmingproject.repositories.LessonRepository;
import com.example.swimmingproject.repositories.SwimmerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
@Component
public class Bootstrap implements CommandLineRunner {

    private final InstructorRepository instructorRepository;
    private final LessonRepository lessonRepository;
    private final SwimmerRepository swimmerRepository;

    @Override
    public void run(String... args) throws Exception {

        Set<Swimmer> swimmerSet = createSwimmerSet(12);
        Set<Instructor> instructorSet = createInstructorSet(3);
        Set<Lesson> lessonsSet = createLessonSet(9);

        randomlyBind(swimmerSet, instructorSet, lessonsSet);

        swimmerRepository.saveAll(swimmerSet);
        instructorRepository.saveAll(instructorSet);
        lessonRepository.saveAll(lessonsSet);

        System.out.printf(
                "Loaded bootstrap data.\ninstructor count: %d\nlesson count: %d\nswimmer count: %d\n",
                instructorRepository.count(), lessonRepository.count(), swimmerRepository.count());
    }

    private void randomlyBind(Set<Swimmer> swimmerSet, Set<Instructor> instructorSet, Set<Lesson> lessonsSet) {

        Iterator<Lesson> iterator = new CircularIterator<>(lessonsSet);

        int LESSON_PER_SWIMMER = 3; //(SWIMMER_AMOUNT / LESSON_PER_SWIMMER) swimmer per lesson
        swimmerSet.forEach(swimmer -> { //binding lessons to swimmers
            for (int i = 0; i < LESSON_PER_SWIMMER; i++) {
                Lesson lesson = iterator.next();
                swimmer.addLesson(lesson);
                lesson.addSwimmer(swimmer);
            }
        });

        int LESSONS_PER_INSTRUCTOR = 3; //one instructor per lesson
        instructorSet.forEach(instructor -> { //binding lessons to instructors
            for (int i = 0; i < LESSONS_PER_INSTRUCTOR; i++) {
                Lesson lesson = iterator.next();
                instructor.addLesson(lesson);
                lesson.setInstructor(instructor);
            }
        });
    }

    private Set<Swimmer> createSwimmerSet(int amount) {
        Set<Swimmer> result = new HashSet<>();
        for (int i = 0; i < amount; i++) {
            result.add(createSwimmer());
        }
        return result;
    }

    private Set<Instructor> createInstructorSet(int amount) {
        Set<Instructor> result = new HashSet<>();
        for (int i = 0; i < amount; i++) {
            result.add(createInstructor());
        }
        return result;
    }

    private Set<Lesson> createLessonSet(int amount) {
        Set<Lesson> result = new HashSet<>();
        for (int i = 0; i < amount; i++) {
            result.add(createLesson());
        }
        return result;
    }

    private Instructor createInstructor() {
        return Instructor.builder()
                .firstName(getRandomName())
                .lastName(getRandomName())
                .build();
    }

    private Lesson createLesson() {
        return Lesson.builder()
                .localDateTime(LocalDate.ofEpochDay(ThreadLocalRandom.current().nextInt(365 * 50, 365 * 53)).atStartOfDay())
                .description("lesson's description")
                .build();
    }

    private Swimmer createSwimmer() {
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

    private static class CircularIterator<T> implements Iterator<T> {

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
