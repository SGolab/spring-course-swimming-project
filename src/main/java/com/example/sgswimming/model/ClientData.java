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
    private User user; //todo make user final?

    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            joinColumns = @JoinColumn(name = "client_data_id"),
            inverseJoinColumns = @JoinColumn(name = "swimmer_id"))
    private Set<Instructor> instructors = new HashSet<>();

    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            joinColumns = @JoinColumn(name = "client_data_id"),
            inverseJoinColumns = @JoinColumn(name = "swimmer_id"))
    private Set<Swimmer> swimmers = new HashSet<>();

    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            joinColumns = @JoinColumn(name = "client_data_id"),
            inverseJoinColumns = @JoinColumn(name = "lesson_id"))
    private Set<Lesson> lessons = new HashSet<>();

    @Transactional
    public void setSwimmers(Collection<Swimmer> swimmers) {

        this.instructors.clear();
        this.lessons.clear();
        this.swimmers.clear();

        if (swimmers != null && !swimmers.isEmpty()) {

            List<Lesson> newLessons = swimmers
                    .stream()
                    .flatMap(swimmer -> swimmer.getLessons().stream())
                    .distinct()
                    .collect(Collectors.toList());

            List<Instructor> newInstructors = newLessons
                    .stream()
                    .map(Lesson::getInstructor)
                    .distinct()
                    .collect(Collectors.toList());

            this.instructors.addAll(newInstructors);
            this.lessons.addAll(newLessons);
            this.swimmers.addAll(swimmers);
        }
    }

    @Transactional
    public void setInstructor(Instructor instructor) {

        this.instructors.clear();
        this.lessons.clear();
        this.swimmers.clear();

        if (instructor != null) {
            List<Lesson> newLessons = instructor.getLessons();
            List<Swimmer> newSwimmers = instructor
                    .getLessons()
                    .stream()
                    .flatMap(lesson -> lesson.getSwimmers().stream())
                    .distinct()
                    .collect(Collectors.toList());

            this.instructors.add(instructor);
            this.lessons.addAll(newLessons);
            this.swimmers.addAll(newSwimmers);
        }
    }
}
