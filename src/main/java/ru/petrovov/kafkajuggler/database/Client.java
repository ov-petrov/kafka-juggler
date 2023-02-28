package ru.petrovov.kafkajuggler.database;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@ToString(onlyExplicitlyIncluded = true)
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ToString.Include
    private String firstName;
    @ToString.Include
    private String secondName;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    private List<Paper> papers;


}
