package com.example.sgswimming.model;

import com.example.sgswimming.security.model.User;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user; //todo make user final?

    private String firstName;
    private String lastName;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "client_data_id", referencedColumnName = "id")
    private Instructor instructor;

    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            joinColumns = @JoinColumn(name = "client_data_id"),
            inverseJoinColumns = @JoinColumn(name = "swimmer_id"))
    private Set<Swimmer> swimmers = new HashSet<>();

    public void addSwimmer(Swimmer swimmer) {
        swimmers.add(swimmer);
    }
}
