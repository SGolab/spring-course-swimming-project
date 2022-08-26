package com.example.sgswimming.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }
}
