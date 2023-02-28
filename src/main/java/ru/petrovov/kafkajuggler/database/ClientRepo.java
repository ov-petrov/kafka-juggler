package ru.petrovov.kafkajuggler.database;

import org.springframework.data.repository.CrudRepository;

public interface ClientRepo extends CrudRepository<Client, Long> {
}
