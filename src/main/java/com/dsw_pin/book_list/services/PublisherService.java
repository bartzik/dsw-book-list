package com.dsw_pin.book_list.services;

import com.dsw_pin.book_list.model.Publisher;
import com.dsw_pin.book_list.repositories.PublisherRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Transactional
    public Publisher updatePublisher(UUID id, Publisher publisherDetails) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Editora não encontrada"));

        // Atualiza os campos da editora
        publisher.setName(publisherDetails.getName());

        return publisherRepository.save(publisher);
    }

    @Transactional
    public void deletePublisher(UUID id) {
        if (!publisherRepository.existsById(id)) {
            throw new EntityNotFoundException("Editora não encontrada para exclusão.");
        }
        publisherRepository.deleteById(id);
    }


}
