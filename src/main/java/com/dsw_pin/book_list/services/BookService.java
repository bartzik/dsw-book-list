package com.dsw_pin.book_list.services;

import com.dsw_pin.book_list.model.Book;
import com.dsw_pin.book_list.model.Review;
import com.dsw_pin.book_list.repositories.AuthorRepository;
import com.dsw_pin.book_list.repositories.BookRepository;
import com.dsw_pin.book_list.repositories.PublisherRepository;
import dtos.BookRecordDto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
    }

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }


    @Transactional
    public Book saveBook(BookRecordDto bookRecordDto) {
        Book book = new Book();
        book.setTitle(bookRecordDto.title());
        book.setPublisher(publisherRepository.findById(bookRecordDto.publisherId()).orElseThrow(() -> new RuntimeException("Editora não encontrada.")));
        book.setAuthors(authorRepository.findAllById(bookRecordDto.authorIds()).stream().collect(Collectors.toSet()));

        return bookRepository.save(book);
    }

    @Transactional
    public void deleteBook(UUID id){
        bookRepository.deleteById(id);
    }
}
