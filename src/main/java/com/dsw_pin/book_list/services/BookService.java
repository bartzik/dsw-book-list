package com.dsw_pin.book_list.services;

import com.dsw_pin.book_list.model.Author;
import com.dsw_pin.book_list.model.Book;
import com.dsw_pin.book_list.model.Publisher;
import com.dsw_pin.book_list.repositories.AuthorRepository;
import com.dsw_pin.book_list.repositories.BookRepository;
import com.dsw_pin.book_list.repositories.PublisherRepository;
import com.dsw_pin.book_list.dtos.BookRecordDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

        // Verificação se Publisher existe
        Publisher publisher = publisherRepository.findById(bookRecordDto.publisherId())
                .orElseThrow(() -> new EntityNotFoundException("Editora não encontrada."));
        book.setPublisher(publisher);

        // Verificação se os autores existem
        Set<Author> authors = new HashSet<>(authorRepository.findAllById(bookRecordDto.authorIds()));
        if (authors.isEmpty()) {
            throw new EntityNotFoundException("Nenhum autor encontrado com os IDs fornecidos.");
        }
        book.setAuthors(authors);

        // Verificação do publicationYear
        if (bookRecordDto.publicationYear() <= 0) {
            throw new IllegalArgumentException("O ano de publicação deve ser maior que 0.");
        }
        book.setPublicationYear(bookRecordDto.publicationYear());

        return bookRepository.save(book);
    }


    @Transactional
    public void deleteBook(UUID id){
        bookRepository.deleteById(id);
    }
}
