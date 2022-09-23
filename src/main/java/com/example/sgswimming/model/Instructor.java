package com.example.sgswimming.model;

import lombok.*;

import javax.persistence.*;
import java.util.*;

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
    private List<Lesson> lessons = new ArrayList<>();

    @OneToMany
    private Set<ClientData> clientDataSet = new HashSet<>();

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }

    public void setLessons(List<Lesson> lessons) {
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
