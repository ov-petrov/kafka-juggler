package ru.petrovov.kafkajuggler.database;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Paper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private Long number;
    private String info;
    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;
}
