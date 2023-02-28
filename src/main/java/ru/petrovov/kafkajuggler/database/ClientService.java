package ru.petrovov.kafkajuggler.database;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientService {
    private final Faker faker = Faker.instance();

    private final ClientRepo clientRepo;

    public void generateClients(int count) {
        var clients = new ArrayList<Client>(count);
        for (int i = 0; i < count; i++) {
            var name = faker.name();
            var client = new Client();
            client.setFirstName(name.firstName());
            client.setSecondName(name.lastName());
            clients.add(client);
            log.info("Added client: {}", client);
        }
        clientRepo.saveAll(clients);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStart() {
        generateClients(20);
        log.info("Find all clients: ");
        clientRepo.findAll().forEach(c -> log.info("Found client: {}", c));
    }
}
