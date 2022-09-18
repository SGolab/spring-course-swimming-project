package com.example.sgswimming.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@ToString(exclude = "lessons")
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

    @OneToMany(mappedBy = "instructor", fetch = FetchType.EAGER)
    private List<Lesson> lessons = new ArrayList<>();

    @ManyToMany(mappedBy = "swimmers", fetch = FetchType.EAGER)
    private Set<ClientData> clientDataSet = new HashSet<>();

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }

    public void addClientData(ClientData clientData) {
        this.clientDataSet.add(clientData);
    }
}
