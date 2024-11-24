package com.dsw_pin.book_list.controller;

import com.dsw_pin.book_list.model.Book;
import com.dsw_pin.book_list.repositories.BookRepository;
import com.dsw_pin.book_list.services.BookService;
import com.dsw_pin.book_list.dtos.BookRecordDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

        if (book.getPhotoUrl() != null && !book.getPhotoUrl().startsWith("http")) {
            String baseUrl = "http://localhost:8080"; // Substitua pelo domínio correto, se necessário
            book.setPhotoUrl(baseUrl + book.getPhotoUrl());
        }

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

    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<String> uploadPhoto(@RequestParam("file") MultipartFile file) {
        try {
            // Chama o serviço para salvar o arquivo e retorna a URL gerada
            String photoUrl = bookService.savePhoto(file);
            return ResponseEntity.ok(photoUrl);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao fazer upload da foto: " + e.getMessage());
        }
    }

}
