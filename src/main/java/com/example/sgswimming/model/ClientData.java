package com.example.sgswimming.model;

import com.example.sgswimming.security.model.User;
import lombok.*;
import org.hibernate.LazyInitializationException;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@NamedEntityGraph(
        name = "client_data-instructor-client",
        attributeNodes = {
                @NamedAttributeNode(value = "instructor", subgraph = "instructor-lessons-subgraph")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "instructor-lessons-subgraph",
                        attributeNodes = {
                                @NamedAttributeNode(value = "lessons", subgraph = "lesson-swimmers-subgraph")
                        }),
                @NamedSubgraph(
                        name = "lesson-swimmers-subgraph",
                        attributeNodes = {
                                @NamedAttributeNode("swimmers")
                        })
        })

@NamedEntityGraph(
        name = "client_data-swimmer-client",
        attributeNodes = @NamedAttributeNode(value = "swimmers", subgraph = "swimmers-lessons-subgraph"),
        subgraphs = {
                @NamedSubgraph(
                        name = "swimmers-lessons-subgraph",
                        attributeNodes = {
                                @NamedAttributeNode(value = "lessons", subgraph = "lesson-instructor-subgraph")
                        }),
                @NamedSubgraph(
                        name = "lesson-instructor-subgraph",
                        attributeNodes = {
                                @NamedAttributeNode(value = "instructor")
                        })
        })

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ClientData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user; //todo make user final?

    private String firstName;
    private String lastName;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "instructor_id", referencedColumnName = "id")
    private Instructor instructor;

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            joinColumns = @JoinColumn(name = "client_data_id"),
            inverseJoinColumns = @JoinColumn(name = "swimmer_id"))
    private Set<Swimmer> swimmers = new HashSet<>();

    public void addSwimmer(Swimmer swimmer) {
        swimmers.add(swimmer);
    }

    public Set<Instructor> getInstructorsCalculated() {

        checkIfValid();

        if (this.instructor != null) {
            return Set.of(this.instructor);
        } else {
            return this.swimmers
                    .stream()
                    .flatMap(swimmer -> swimmer.getLessons().stream())
                    .distinct()
                    .map(Lesson::getInstructor)
                    .collect(Collectors.toSet());
        }
    }

    public Set<Swimmer> getSwimmersCalculated() {

        checkIfValid();

        if (this.instructor != null) {
            return this.instructor
                    .getLessons()
                    .stream()
                    .flatMap(lesson -> lesson.getSwimmers().stream())
                    .collect(Collectors.toSet());
        } else {
            return this.swimmers;
        }
    }

    public Set<Lesson> getLessonsCalculated() {

        checkIfValid();

        if (this.instructor != null) {
            return instructor.getLessons();
        } else {
            return this.swimmers
                    .stream()
                    .flatMap(swimmer -> swimmer.getLessons().stream())
                    .collect(Collectors.toSet());
        }
    }

    private void checkIfValid() {
        if (this.instructor == null && CollectionUtils.isEmpty(this.swimmers)) {
            throw new IllegalArgumentException("User needs to have instructor or swimmers field declared.");
        }
    }
}
