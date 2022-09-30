package com.example.sgswimming.model;

import com.example.sgswimming.security.model.User;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@NamedEntityGraph(
        name = "client_data-operations",
        attributeNodes = {
                @NamedAttributeNode(value = "instructor", subgraph = "instructor-subgraph"),
                @NamedAttributeNode(value = "swimmers", subgraph = "swimmers-subgraph")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "instructor-subgraph",
                        attributeNodes = {
                                @NamedAttributeNode(value = "lessons", subgraph = "lesson-swimmers-subgraph")
                        }),
                @NamedSubgraph(
                        name = "lesson-swimmers-subgraph",
                        attributeNodes = {
                                @NamedAttributeNode("swimmers")
                        }),

                @NamedSubgraph(
                        name = "swimmers-subgraph",
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
    @JoinColumn(name = "client_data_id", referencedColumnName = "id")
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

    public Set<Instructor> getInstructors() {
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

    public Set<Swimmer> getSwimmers() {
        if (CollectionUtils.isEmpty(this.swimmers)) {
            return this.swimmers;
        } else {
            return this.instructor
                    .getLessons()
                    .stream()
                    .flatMap(lesson -> lesson.getSwimmers().stream())
                    .collect(Collectors.toSet());
        }
    }

    public Set<Lesson> getLessons() {
        if (this.instructor != null) {
            return instructor.getLessons();
        } else {
            return this.swimmers
                    .stream()
                    .flatMap(swimmer -> swimmer.getLessons().stream())
                    .collect(Collectors.toSet());
        }
    }
}
