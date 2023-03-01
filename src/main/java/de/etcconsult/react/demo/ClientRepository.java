package de.etcconsult.react.demo;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ClientRepository extends ReactiveCrudRepository<Client, Long> {
}