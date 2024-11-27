package com.dsw_pin.book_list.controller;

import com.dsw_pin.book_list.model.Author;
import com.dsw_pin.book_list.repositories.AuthorRepository;
import com.dsw_pin.book_list.services.AuthorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/booklist/authors")
public class AuthorController {

    private final AuthorRepository authorRepository;
    private final AuthorService authorService;

    public AuthorController(AuthorRepository authorRepository, AuthorService authorService) {
        this.authorRepository = authorRepository;
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return ResponseEntity.ok(authors);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        Author savedAuthor = authorRepository.save(author);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAuthor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable UUID id) {
        try {
            authorService.deleteAuthor(id);
            return ResponseEntity.ok("Autor excluído com sucesso.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao excluir o autor: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAuthor(@PathVariable UUID id, @RequestBody Author updatedAuthor) {
        try {
            authorService.updateAuthor(id, updatedAuthor);
            return ResponseEntity.ok("Autor atualizado com sucesso.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar o autor: " + e.getMessage());
        }
    }

}

