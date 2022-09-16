package com.example.sgswimming.model;

import com.example.sgswimming.security.model.User;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ClientData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            joinColumns = @JoinColumn(name = "client_data_id"),
            inverseJoinColumns = @JoinColumn(name = "swimmer_id"))
    private Set<Instructor> instructors = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            joinColumns = @JoinColumn(name = "client_data_id"),
            inverseJoinColumns = @JoinColumn(name = "swimmer_id"))
    private Set<Swimmer> swimmers = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            joinColumns = @JoinColumn(name = "client_data_id"),
            inverseJoinColumns = @JoinColumn(name = "lesson_id"))
    private Set<Lesson> lessons = new HashSet<>();

    @Transactional
    public ClientData addSwimmer(Swimmer swimmer) {
        if (swimmer != null) {
            swimmers.add(swimmer);
            Collection<Lesson> fetchedLessons = swimmer.getLessons();
            lessons.addAll(fetchedLessons);
            instructors.addAll(
                    fetchedLessons
                            .stream()
                            .map(Lesson::getInstructor)
                            .distinct()
                            .collect(Collectors.toList()));
        }
        return this;
    }

    @Transactional
    public ClientData addLesson(Lesson lesson) {
        if (lesson != null) {
            lessons.add(lesson);
            swimmers.addAll(lesson.getSwimmers());
            instructors.add(lesson.getInstructor());
        }
        return this;
    }

    @Transactional
    public ClientData addInstructor(Instructor instructor) {
        if (instructor != null) {
            instructors.clear();
            instructors.add(instructor);
            lessons.addAll(instructor.getLessons());
            swimmers.addAll(
                    instructor.getLessons()
                            .stream()
                            .flatMap(lesson -> lesson.getSwimmers().stream())
                            .distinct()
                            .collect(Collectors.toList()));
        }
        return this;
    }
}
