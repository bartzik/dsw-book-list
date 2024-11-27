package com.dsw_pin.book_list.controller;

import com.dsw_pin.book_list.model.Publisher;
import com.dsw_pin.book_list.repositories.PublisherRepository;
import com.dsw_pin.book_list.services.PublisherService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/booklist/publishers")
public class PublisherController {

    private final PublisherRepository publisherRepository;
    private final PublisherService publisherService;

    public PublisherController(PublisherRepository publisherRepository, PublisherService publisherService) {
        this.publisherRepository = publisherRepository;
        this.publisherService = publisherService;
    }

    @GetMapping
    public ResponseEntity<List<Publisher>> getAllPublishers() {
        List<Publisher> publishers = publisherRepository.findAll();
        return ResponseEntity.ok(publishers);
    }

    @PostMapping
    public ResponseEntity<Publisher> createPublisher(@RequestBody Publisher publisher) {
        Publisher savedPublisher = publisherRepository.save(publisher);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPublisher);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable UUID id, @RequestBody Publisher publisherDetails) {
        Publisher updatedPublisher = publisherService.updatePublisher(id, publisherDetails);
        return ResponseEntity.ok(updatedPublisher);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePublisher(@PathVariable UUID id) {
        try {
            publisherService.deletePublisher(id);
            return ResponseEntity.ok("Editora excluída com sucesso.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao excluir a editora: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getPublisherById(@PathVariable UUID id) {
        return publisherRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


}
