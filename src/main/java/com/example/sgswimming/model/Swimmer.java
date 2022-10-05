package com.example.sgswimming.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NamedEntityGraph(
        name = "swimmer-read-dto",
        attributeNodes = {
                @NamedAttributeNode(value = "lessons", subgraph = "lessons-subgraph"),
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "lessons-subgraph",
                        attributeNodes = {
                                @NamedAttributeNode("instructor"),
                                @NamedAttributeNode("swimmers")
                        })
        })

@Data
@NoArgsConstructor
@ToString(exclude = {"lessons", "clientDataSet"})
@EqualsAndHashCode(exclude = {"lessons", "clientDataSet"})
@Entity
public class Swimmer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;

    private Integer advanceLevel;
    private LocalDate birthDate;

    @ManyToMany(mappedBy = "swimmers", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<Lesson> lessons = new HashSet<>();

    @ManyToMany(mappedBy = "swimmers", fetch = FetchType.EAGER)
    private Set<ClientData> clientDataSet = new HashSet<>();

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }

    public void addClientData(ClientData clientData) {
        clientDataSet.add(clientData);
    }

    public void removeClientData(ClientData clientData) {
        clientDataSet.remove(clientData);
    }
}
