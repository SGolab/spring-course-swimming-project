package com.example.sgswimming.model;

import com.example.sgswimming.security.model.User;
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
public class Swimmer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;

    @ManyToMany(mappedBy = "swimmers", fetch = FetchType.EAGER)
    private List<Lesson> lessons = new ArrayList<>();

    @ManyToMany(mappedBy = "swimmers", fetch = FetchType.EAGER)
    private Set<ClientData> clientData = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Swimmer(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }
}
