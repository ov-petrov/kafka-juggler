package ru.petrovov.kafkajuggler.database;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String secondName;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    private List<Paper> papers;


}
