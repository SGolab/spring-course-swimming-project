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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Component
public class Bootstrap implements CommandLineRunner {

    private final InstructorRepository instructorRepository;
    private final LessonRepository lessonRepository;
    private final SwimmerRepository swimmerRepository;

    @Override
    public void run(String... args) throws Exception {
        instructorRepository.save(createInstructor());
        instructorRepository.save(createInstructor());
        instructorRepository.save(createInstructor());

        System.out.printf(
                "Loaded bootstrap data.\ninstructor count: %d\nlesson count: %d\nswimmer count: %d\n",
                instructorRepository.count(), lessonRepository.count(), swimmerRepository.count());
    }

    private static final List<String> names = List.of("Stephany", "Camdyn", "Korey", "Eddy", "Preston", "Myka", "Braelyn", "Emmalynn", "Tatum", "Jaxen", "Jaya", "Don", "Bryer", "Teagan", "Brenna", "Khloee", "Ingrid", "Nigel", "Yaritza", "Jessa", "Rilynn", "Mark", "Angel", "Brady", "Deacon", "Melvin", "Dyllan", "Bronx", "Elisha", "Elan", "Kenia", "Alonzo", "Dean", "Cortez", "Latrell", "Lochlan", "Landin", "Alannah", "Kaiya", "Helena", "Braylan", "Chyna", "Mack", "Madison", "Darien", "Raegan", "Lorraine", "Ryleigh", "Raylan", "Aayden", "Lana", "Case", "Shanaya", "Steven", "Jaelynn", "Zeke", "Maryam", "Trinity", "Weston", "Micaela", "Eliyahu", "Kalen", "Ayman", "Yuna", "Zia", "Jaylynn", "Kole", "Joyce", "Tatianna", "Juancarlos", "Thea", "Soleil", "Alma", "Arleth", "Vicente", "Jethro", "Myra", "Hans", "Sidney", "Jeffery", "Coleman", "Adelynn", "Bryson", "Eddie", "Aiza", "Niall", "Annmarie", "Lina", "Rylie", "Kai", "Walter", "Ivory", "Violeta", "Jazzlyn", "Nick", "Immanuel", "Matthew", "Dyson", "Estevan", "Tala");
    private static final Random random = new Random();

    private Instructor createInstructor() {
        Instructor instructor = Instructor.builder()
                .firstName(getRandomName())
                .lastName(getRandomName())
                .build();

        instructor = instructorRepository.save(instructor);

        instructor.addLesson(createLesson(instructor));
        instructor.addLesson(createLesson(instructor));
        instructor.addLesson(createLesson(instructor));

        return instructorRepository.save(instructor);
    }

    private Lesson createLesson(Instructor instructor) {
        return lessonRepository.save(
                Lesson.builder()
                        .localDateTime(LocalDateTime.now())
                        .description("lesson's description")
                        .swimmers(List.of(
                                createSwimmer(),
                                createSwimmer(),
                                createSwimmer()))
                        .instructor(instructor)
                        .build());
    }

    private Swimmer createSwimmer() {
        return swimmerRepository.save(
                Swimmer.builder()
                        .firstName(getRandomName())
                        .lastName(getRandomName())
                        .build());
    }

    private static String getRandomName() {
        return names.get(random.nextInt(names.size()));
    }
}
