package com.example.sgswimming.model;

import lombok.*;
import org.springframework.data.jpa.repository.EntityGraph;

import javax.persistence.*;
import java.util.*;


@NamedEntityGraph(
        name = "instructor-read-dto",
        attributeNodes = {
                @NamedAttributeNode(value = "lessons", subgraph = "lessons-subgraph"),
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "lessons-subgraph",
                        attributeNodes = {
                                @NamedAttributeNode(value = "swimmers")
                        })
        })

@Data
@NoArgsConstructor
@ToString(exclude = {"lessons", "clientDataSet"})
@EqualsAndHashCode(exclude = {"lessons", "clientDataSet"})
@Entity
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;

    @Builder
    public Instructor(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @OneToMany(mappedBy = "instructor", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<Lesson> lessons = new HashSet<>();

    @OneToMany
    private Set<ClientData> clientDataSet = new HashSet<>();

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }

    public void setLessons(Set<Lesson> lessons) {
        this.lessons = lessons;
        lessons.forEach(lesson -> lesson.setInstructor(this));
    }

    public void addClientData(ClientData clientData) {
        clientDataSet.add(clientData);
    }

    public void removeClientData(ClientData clientData) {
        clientDataSet.remove(clientData);
    }
}
