package com.dsw_pin.book_list.controller;

import com.dsw_pin.book_list.model.Book;
import com.dsw_pin.book_list.repositories.BookRepository;
import com.dsw_pin.book_list.services.BookService;
import com.dsw_pin.book_list.dtos.BookRecordDto;
import com.dsw_pin.book_list.services.FileStorageService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;
import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("/booklist/books")
public class BookController {

    private final BookService bookService;
    private final BookRepository bookRepository;

    @Autowired
    private FileStorageService fileStorageService;

    public BookController(BookService bookService, BookRepository bookRepository) {
        this.bookService = bookService;
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable UUID id) {
        Book book = bookRepository.findByIdWithReviews(id)
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));

        if (book.getPhotoUrl() != null && !book.getPhotoUrl().startsWith("http")) {
            String baseUrl = "http://localhost:8080";
            book.setPhotoUrl(baseUrl + book.getPhotoUrl());
        }

        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<Book> saveBook(@RequestBody BookRecordDto bookRecordDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.saveBook(bookRecordDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable UUID id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.ok("Livro excluído com sucesso.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao excluir o livro: " + e.getMessage());
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Book> updateBook(
            @PathVariable UUID id,
            @RequestParam("title") String title,
            @RequestParam("publicationYear") Integer publicationYear,
            @RequestParam("summary") String summary,
            @RequestParam("publisherId") UUID publisherId,
            @RequestParam("authorIds") List<UUID> authorIds,
            @RequestParam(value = "photo", required = false) MultipartFile photo
    ) {
        String photoUrl = null;
        if (photo != null && !photo.isEmpty()) {
            photoUrl = fileStorageService.save(photo);
        }

        BookRecordDto bookRecordDto = new BookRecordDto(
                title,
                publisherId,
                Set.copyOf(authorIds),
                publicationYear,
                photoUrl,
                summary
        );

        Book updatedBook = bookService.updateBook(id, bookRecordDto);
        return ResponseEntity.ok(updatedBook);
    }


    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<String> uploadPhoto(@RequestParam("file") MultipartFile file) {
        try {
            String photoUrl = fileStorageService.save(file);
            return ResponseEntity.ok(photoUrl);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao fazer upload da foto: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam String query) {
        List<Book> books = bookService.searchBooks(query);
        return ResponseEntity.ok(books);
    }
}
