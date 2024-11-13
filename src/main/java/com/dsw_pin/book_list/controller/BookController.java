package com.dsw_pin.book_list.controller;

import com.dsw_pin.book_list.model.Book;
import com.dsw_pin.book_list.repositories.BookRepository;
import com.dsw_pin.book_list.services.BookService;
import com.dsw_pin.book_list.dtos.BookRecordDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("/booklist/books")
public class BookController {

    private final BookService bookService;
    private  final BookRepository bookRepository;

    public BookController(BookService bookService, BookRepository bookRepository) {
        this.bookService = bookService;
        this.bookRepository = bookRepository;
    }


    // busca todos os livros
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(){
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable UUID id) {
        Book book = bookRepository.findByIdWithReviews(id) // Usando um método customizado para carregar reviews junto
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));
        return ResponseEntity.ok(book);
    }


    // salva livros novos
    @PostMapping
    public ResponseEntity<Book> saveBook(@RequestBody BookRecordDto bookRecordDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.saveBook(bookRecordDto));
    }

    // apaga por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable UUID id){
        try {
            bookService.deleteBook(id);
            return ResponseEntity.status(HttpStatus.OK).body("Livro deletado com sucesso!");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro não encontrado!");
        }
    }
}
