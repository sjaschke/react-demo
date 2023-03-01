package de.etcconsult.react.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/clients")
public class ClientsController {

  private final ClientRepository clientRepository;

  public ClientsController(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @GetMapping
  public Flux<Client> getClients() {
    return clientRepository.findAll();
  }

  @GetMapping("/{id}")
  public Mono<Client> getClient(@PathVariable Long id) {
    return clientRepository.findById(id);
  }

  @PostMapping
  public Mono<Client> createClient(@RequestBody Client client)  {
    return  clientRepository.save(client);
  }

  @PutMapping("/{id}")
  public Mono<Client> updateClient(@PathVariable Long id, @RequestBody Client client) {
    return clientRepository.findById(id)
        .map(updatedClient -> updatedClient.setName(client.getName()).setEmail(client.getEmail()))
        .flatMap(clientRepository::save);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity deleteClient(@PathVariable Long id) {
    clientRepository.deleteById(id);
    return ResponseEntity.ok().build();
  }
}
