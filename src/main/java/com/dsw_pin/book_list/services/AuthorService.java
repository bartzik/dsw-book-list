package com.dsw_pin.book_list.services;

import com.dsw_pin.book_list.model.Author;
import com.dsw_pin.book_list.repositories.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public void deleteAuthor(UUID id) {
        if (!authorRepository.existsById(id)) {
            throw new EntityNotFoundException("Autor não encontrado.");
        }
        authorRepository.deleteById(id);
    }

    public void updateAuthor(UUID id, Author updatedAuthor) {
        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Autor não encontrado."));

        // Atualizar apenas os campos permitidos
        existingAuthor.setName(updatedAuthor.getName());

        // Salvar alterações no banco
        authorRepository.save(existingAuthor);
    }
}
